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
package org.jasig.ssp.service.security.lti.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.portal.api.sso.SsoTicket;
import org.jasig.portal.api.sso.SsoTicketService;
import org.jasig.portal.api.url.BuildUrlRequest;
import org.jasig.portal.api.url.UrlBuilderService;
import org.jasig.ssp.dao.security.lti.LtiConsumerDao;
import org.jasig.ssp.factory.LtiConsumerTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.security.lti.LtiConsumer;
import org.jasig.ssp.model.security.lti.LtiLaunchRequest;
import org.jasig.ssp.model.security.lti.LtiLaunchResponse;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.exception.UserNotAuthorizedException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.impl.PersonAttributesSearchException;
import org.jasig.ssp.service.impl.UPortalPersonAttributesService;
import org.jasig.ssp.service.security.lti.ConsumerDetailsDisabledException;
import org.jasig.ssp.service.security.lti.ConsumerDetailsNotFoundException;
import org.jasig.ssp.service.security.lti.LtiConsumerService;
import org.jasig.ssp.transferobject.LtiConsumerTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ltiConsumerService")
@Transactional
public class LtiConsumerServiceImpl extends
		AbstractAuditableCrudService<LtiConsumer> implements
		LtiConsumerService, ConsumerDetailsService {

	private static final String DEFAULT_TARGET = "default";

	@Autowired
	private LtiConsumerTOFactory factory;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private PersonService personService;

	@Autowired
	private LtiConsumerDao ltiConsumerDao;

	@Autowired
	private UPortalPersonAttributesService personAttributesService;

	@Value("#{configProperties.ssp_platform_sso_ticket_service_shared_secret}")
	private String sharedSsoSecret;

	private static String SSP_ROSTER_CLASS_IDENTIFIER = "sectionCode";

	private static String USERNAME_KEY = "name";

	@Override
	protected LtiConsumerDao getDao() {
		return ltiConsumerDao;
	}

	/**
	 * Overridden to throw an {@link UnsupportedOperationException}. Use
	 * {@link #create(org.jasig.ssp.transferobject.LtiConsumerTO)} so the
	 * service knows exactly what you're trying to do.
	 * 
	 * @param client
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	@Override
	public LtiConsumer create(final LtiConsumer client)
			throws ObjectNotFoundException, ValidationException {
		throw new UnsupportedOperationException("Use create(LTIConsumerTO)");
	}

	/**
	 * Overridden to throw an {@link UnsupportedOperationException}. Use
	 * {@link #save(org.jasig.ssp.transferobject.LtiConsumerTO)} so the service
	 * knows exactly what you're trying to do.
	 * 
	 * @param client
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	@Override
	public LtiConsumer save(LtiConsumer client) throws ObjectNotFoundException,
			ValidationException {
		throw new UnsupportedOperationException("Use save(LTIConsumerTO)");
	}

	@Override
	public LtiConsumer create(LtiConsumerTO obj)
			throws ObjectNotFoundException, ValidationException {
		final LtiConsumer model = factory.from(obj);
		return super.create(model);
	}

	@Override
	public LtiConsumer save(LtiConsumerTO obj) throws ObjectNotFoundException {
		return getDao().save(factory.from(obj));
	}

	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
		// ConsumerDetailsService contract requires that this method must
		// not return null. All failures must be represented by OAuthException.
		final LtiConsumer consumer;
		try {
			consumer = findByConsumerKey(consumerKey);
			if ( consumer == null ) {
				throw new ObjectNotFoundException(consumerKey, LtiConsumer.class.getName());
			}

			// Technically you might be loading the consumer for other reasons that don't
			// have to do with processing an authentication request, but in practice that's
			// all we use this method for. So in order to avoid any possibly holes whereby
			// a disabled LtiConsumer successfully authenticates requests, we put that sort
			// of checking here rather than in processLaunch()
			if ( consumer.getObjectStatus() != ObjectStatus.ACTIVE ) {
				throw new ConsumerDetailsDisabledException("Consumer with key [" + consumerKey + "] has been disabled");
			}
			if ( StringUtils.isBlank(consumer.getSecret()) ) {
				throw new ConsumerDetailsDisabledException("Consumer with key [" + consumerKey + "] has been disabled because it has no secret");
			}

			// Wrap in the same try catch b/c there's no semantic collision currently between
			// the possible exception types thrown by lookup and initialization ops, and
			// we're doing our best to ensure all failures are represented a OAuthException as
			// required by the contract
			BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
			consumerDetails.setConsumerKey(consumer.getConsumerKey());
			consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(
					consumer.getSecret()));
			consumerDetails.setRequiredToObtainAuthenticatedToken(false);
			return consumerDetails;
		} catch ( ObjectNotFoundException e ) {
			// contract requires an OAuthException for all failures, including any sort of disabled/missing consumer
			throw new ConsumerDetailsNotFoundException("Failed to load consumer by key [" + consumerKey + "]", e);
		} catch ( OAuthException e ) {
			throw e;
		} catch ( AuthenticationException e ) {
			// Shouldn't happen, but if it does, it's probably not a InternalAuthenticationServiceException
			// as handled below. And we can be fairly sure the issue isn't a missing Consumer. So just... disabled.
			throw new ConsumerDetailsDisabledException("Failed to load consumer by key [" + consumerKey + "]", e);
		} catch ( Exception e ) {
			final InternalAuthenticationServiceException ssWrap =
				new InternalAuthenticationServiceException("Failed to load consumer by key [" + consumerKey + "]", e);
			throw new OAuthException("Failed to load consumer by key [" + consumerKey + "]", ssWrap);
		}

	}

	private LtiConsumer findByConsumerKey(String consumerKey) {
		return getDao().findByConsumerKey(consumerKey);
	}

	@Override
	public LtiLaunchResponse processLaunch(LtiLaunchRequest launchRequest)
			throws UserNotEnabledException, UserNotAuthorizedException,
			PersonAttributesSearchException, RuntimeException {

		final SspUser sspUser = securityService.currentlyAuthenticatedUser();
		if ( sspUser == null ) {
			throw new UserNotAuthorizedException("No currently authenticated user");
		}

		final String authenticatedUsername = sspUser.getUsername();
		final LtiConsumer client;
		try {
			client = findByConsumerKey(authenticatedUsername);
			if ( client == null ) {
				throw new ObjectNotFoundException(authenticatedUsername, LtiConsumer.class.getName());
			}
		} catch ( Exception e ) {
			throw new UserNotAuthorizedException("Failed to correlate currently "
					+ "authenticated user with a LtiConsumer with key [" +authenticatedUsername + "]", e);
		}

		final Map<String, String> convertor = getParametersConversions(client);
		final Map<String, String> parameters = launchRequest.getParameters();
		final Map<String, String> convertedParameters = new HashMap<String, String>();
		for (String key : parameters.keySet()) {
			if (convertor != null && convertor.containsKey(key)) {
				convertedParameters
						.put(convertor.get(key), parameters.get(key));
			} else {
				convertedParameters.put(key, parameters.get(key));
			}
		}
		launchRequest.setParameters(convertedParameters);

		final String requestUserUserName = findUser(launchRequest, client);
		final String targetLaunchUrl = buildLaunchUrl(launchRequest, requestUserUserName);

		final LtiLaunchResponse launchResponse = new LtiLaunchResponse();
		launchResponse.setRedirectUrl(targetLaunchUrl);
		return launchResponse;
	}

	private String findUser(LtiLaunchRequest launchRequest, LtiConsumer client)
			throws UserNotEnabledException, PersonAttributesSearchException {

		final String userId = launchRequest.getParameters().get(
				client.getSspUserIdField());
		if ( StringUtils.isBlank(userId) ) {
			throw new PersonAttributesSearchException("No user identifier specified");
		}

		final Map<String, String> query = new HashMap<String, String>();
		query.put(client.getSspUserIdField(), userId);
		final List<Map<String, Object>> people;
		try {
			people = personAttributesService.searchForUsers(query);
			if(people == null || people.isEmpty()){
				throw new UserNotEnabledException("UserId:" + userId + " not found");
			}
		} catch ( PersonAttributesSearchException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new PersonAttributesSearchException("System failure looking up user with [" +
					client.getSspUserIdField() + "] identifier [" + userId + "]", e);
		}

		final Map<String, Object> personMap = people.get(0);
		final String userName = (String) personMap.get(USERNAME_KEY);
		if ( StringUtils.isBlank(userName) ) {
			throw new PersonAttributesSearchException("User with [" +
					client.getSspUserIdField() + "] identifier [" + userId + "] exists but has no username");
		}
		return userName;
	}

	private String buildLaunchUrl(LtiLaunchRequest launchRequest, String requestUserUserName)
			throws RuntimeException {

		final SsoTicket ssoTicket;

		try {
			ssoTicket = SsoTicketService.IMPL.get().issueTicket(
					requestUserUserName, sharedSsoSecret);

		// We don't have good domain-specific exceptions to use here, so
		// for now we just make sure there's some sort of reasonable,
		// domain-specific message
		} catch ( SecurityException e ) {
			throw new RuntimeException("Current authentication/authorization context "
					+ "and/or configuration insufficient to issue new Platform SSO "
					+ "tickets. Attempt was for username [" + requestUserUserName + "]", e);
		} catch ( RuntimeException e ) {
			throw new RuntimeException("System-level failure trying to issue a Platform " +
					"SSO ticket for username [" + requestUserUserName + "]", e);
		}

		if ( ssoTicket == null || StringUtils.isBlank(ssoTicket.getUuid()) ) {
			throw new RuntimeException("Platform issued a blank SSO ticket" +
					"SSP ticket for username [" + requestUserUserName + "]");
		}

		if ( StringUtils.isBlank(launchRequest.getTarget()) ) {
			launchRequest.setTarget(DEFAULT_TARGET);
		}
		final BuildUrlRequest buildUrlRequest = new BuildUrlRequest(
				launchRequest.getParameters(), launchRequest.getTarget(), ssoTicket);
		final String url;

		try {
			url = UrlBuilderService.IMPL.get().buildUrl(buildUrlRequest);

		// We don't have good domain-specific exceptions to use here, so
		// for now we just make sure there's some sort of reasonable,
		// domain-specific message. Converting to unchecked exceptions in
		// all cases ensures transactions are rolled back, as well.
		} catch ( UnsupportedEncodingException e ) {
			// intentionally not including ticket UUID in message b/c that's effectively a password
			throw new RuntimeException("System-level failure trying to issue a Platform SSO redirect URL for username ["
					+ requestUserUserName + "]", e);
		} catch ( MalformedURLException e ) {
			// intentionally not including ticket UUID in message b/c that's effectively a password
			throw new RuntimeException("System-level failure trying to issue a Platform SSO redirect URL for username ["
					+ requestUserUserName + "]", e);
		} catch ( RuntimeException e ) {
			// intentionally not including ticket UUID in message b/c that's effectively a password
			throw new RuntimeException("System-level failure trying to issue a Platform SSO redirect URL for username ["
					+ requestUserUserName + "]", e);
		}

		if (StringUtils.isBlank(url)) {
			// intentionally not including ticket UUID in message b/c that's effectively a password
			throw new RuntimeException("Platform issued a blank SSO redirect URL for username ["
					+ requestUserUserName + "]");
		}

		return url;
	}

	private Map<String, String> getParametersConversions(LtiConsumer client) {
		Map<String, String> conversions = new HashMap<String, String>();
		conversions.put(client.getLtiUserIdField(), client.getSspUserIdField());
		conversions.put(client.getLtiSectionCodeField(),
				SSP_ROSTER_CLASS_IDENTIFIER);
		return conversions;
	}

}
