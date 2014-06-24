/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.security.oauth.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.jasig.ssp.dao.security.oauth.OAuth1NonceDao;
import org.jasig.ssp.model.security.oauth.OAuth1Nonce;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.security.oauth.OAuth1NonceServiceMaintenance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.nonce.ExpiringTimestampNonceServices;
import org.springframework.security.oauth.provider.nonce.NonceAlreadyUsedException;
import org.springframework.security.oauth.provider.verifier.VerificationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("oAuth1PersistentNonceService")
@Transactional
public class OAuth1PersistentNonceServiceImpl extends ExpiringTimestampNonceServices
		implements OAuth1NonceServiceMaintenance {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OAuth1PersistentNonceServiceImpl.class);

	private static final String TIMESTAMP_EXPIRY_CONFIG = "lti_oauth_timestamp_expiry_seconds";

	@Autowired
	private ConfigService configService;

	@Autowired
	private OAuth1NonceDao oAuth1NonceDao;

	@Override
	public void validateNonce(ConsumerDetails consumerDetails, long timestamp, String nonce)
			throws AuthenticationException {
		// public entry points to this service have to make sure to grab
		// the latest config
		configureTimestampExpiry();
		// this actually only validates the timestamp, not the nonce
		super.validateNonce(consumerDetails, timestamp, nonce);
		// the actual nonce validation
		validatePersistentNonce(consumerDetails, timestamp, nonce);
	}

	private void validatePersistentNonce(final ConsumerDetails consumerDetails, final long timestamp,
										 final String nonce) throws OAuthException {

		// NB the OAuth1 spec says that a nonce can be reused within the expiry window, it's just
		// that a nonce cannot be reused for the same consumer+timestamp pair. So yes, we could
		// techincally receive the same nonce for the same consumer several times and that's not
		// considered a replay attack.
		final OAuth1Nonce existingNonce;
		try {
			existingNonce = oAuth1NonceDao.get(consumerDetails.getConsumerKey(), timestamp, nonce);
		} catch ( AuthenticationException e ) {
			throw e;
		} catch ( RuntimeException e ) {
			// System failure. But representing it as an AuthenticationExcepiton subclass ensures
			// our SpringSecurity exception handling is triggered.
			final String message = nonceDescription(new StringBuilder("Nonce lookup failed: "),
					consumerDetails, timestamp, nonce).toString();
			throw new AuthenticationServiceException(message, e);
		}

		if ( existingNonce == null ) {

			// We have a PK on consumer key + timestamp + nonce that prevents strictly-defined replays
			try {
				oAuth1NonceDao.save(new OAuth1Nonce(consumerDetails.getConsumerKey(), timestamp, nonce));
			} catch ( ConstraintViolationException e ) {
				if ( e.getConstraintName().equalsIgnoreCase("pk_oauth_nonce") ) {
					final String message = nonceAlreadyUsedMessageStr(consumerDetails, timestamp, nonce);
					// Log original exception here *and* rethrow since NonceAlreadyUsedException doesn't
					// allow nesting. Only 'info' b/c this isn't actually a system error...
					// this is a business rule issue that's being handled.
					LOGGER.info(message, e);
					throw new NonceAlreadyUsedException(message);
				} else {
					// Probably a length/nullity volation or similar. No need to be too detailed,
					// but it's not an "already used" problem.
					final String message = nonceDescription(new StringBuilder("Invalid nonce content: "),
							consumerDetails, timestamp, nonce).toString();
					// Log original exception here *and* rethrow since VerificationFailedException doesn't
					// allow nesting. Only 'info' b/c this isn't actually a system error...
					// this is a business rule issue that's being handled.
					LOGGER.info(message, e);
					throw new VerificationFailedException(message);
				}
			} catch ( AuthenticationException e ) {
				throw e;
			} catch ( RuntimeException e ) {
				final String message = nonceDescription(new StringBuilder("Nonce storage failed: "),
						consumerDetails, timestamp, nonce).toString();
				throw new InternalAuthenticationServiceException(message, e);
			}

			return;
		}

		// Otherwise consumerkey+timestamp+nonce is being reused. At this point in the code path, it
		// doesn't matter whether timestamp is within or without the expiry window: the OAuth1 spec
		// does not allow consumerkey+timestamp+nonce reuse, period. You just get a special exemption
		// from tracking infinitely many nonce records so long as you enforce timestamp expiry, which
		// in our case should have already happened in super.validateNonce().
		throw new NonceAlreadyUsedException(nonceAlreadyUsedMessageStr(consumerDetails, timestamp, nonce));

	}

	@Override
	public void removeExpired () {
		// public entry points to this service have to make sure to grab
		// the latest config
		configureTimestampExpiry();
		LOGGER.info("Removing expired OAuth nonces.");
		final int deletedCnt = oAuth1NonceDao.deleteWithTimestampEarlierThan(getExpiryCutoff());
		LOGGER.info("Removed {} expired OAuth nonces.", deletedCnt);
	}

	private void configureTimestampExpiry() {
		// Intentionally inline the config lookup. We want the config lookup to occur
		// at the public entry points to this service and not in arbitrary nests of
		// private calls
		setValidityWindowSeconds(configService.
				getByNameExceptionOrDefaultAsInt(TIMESTAMP_EXPIRY_CONFIG));
	}

	private long getExpiryCutoff() {
		// largely copy/paste from Spring's InMemoryNonceServices
		final long now = System.currentTimeMillis() / 1000;
		final long cutoff = now - getValidityWindowSeconds();
		return cutoff;
	}

	private String nonceAlreadyUsedMessageStr(ConsumerDetails consumerDetails, long timestamp, String nonce) {
		return nonceAlreadyUsedMessage(consumerDetails, timestamp, nonce).toString();
	}

	private StringBuilder nonceAlreadyUsedMessage(ConsumerDetails consumerDetails, long timestamp, String nonce) {
		return nonceDescription(new StringBuilder("Nonce already used: "), consumerDetails, timestamp, nonce);
	}

	private StringBuilder nonceDescription(StringBuilder prefix, ConsumerDetails consumerDetails, long timestamp, String nonce) {
		return prefix.append("[Consumer Key: ").append(consumerDetails.getConsumerKey()).append("]")
				.append("[Timestamp: ").append(timestamp).append("]")
				.append("[Nonce: ").append(nonce).append("]");
	}

	private StringBuilder nonceDescription(StringBuilder prefix, OAuth1Nonce nonce) {
		return prefix.append("[Consumer Key: ").append(nonce.getConsumerKey()).append("]")
				.append("[Timestamp: ").append(nonce.getNonceTimestamp()).append("]")
				.append("[Nonce: ").append(nonce.getNonce()).append("]");
	}

}
