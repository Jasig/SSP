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
package org.jasig.ssp.service.security.oauth2.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.security.oauth2.OAuth2ClientDao;
import org.jasig.ssp.factory.OAuth2ClientTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.security.PersistentGrantedAuthority;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.security.oauth2.OAuth2ClientService;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("oauth2ClientDetailsService")
@Transactional
public class OAuth2ClientServiceImpl extends AbstractAuditableCrudService<OAuth2Client>
		implements OAuth2ClientService, ClientDetailsService  {

	@Autowired
	private OAuth2ClientTOFactory factory;

	@Autowired
	private OAuth2ClientDao oAuth2ClientDao;

	@Autowired
	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

	@Autowired
	private ConsumerTokenServices consumerTokenService;

	@Autowired
	private ResourceServerTokenServices resourceServerTokenService;

	@Override
	protected AuditableCrudDao<OAuth2Client> getDao() {
		return oAuth2ClientDao;
	}

	/**
	 * @param passwordEncoder the password encoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Overridden to throw an {@link UnsupportedOperationException}. Use
	 * {@link #create(org.jasig.ssp.transferobject.OAuth2ClientTO)} so the
	 * service knows exactly what you're trying to do.
	 *
	 * @param client
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	@Override
	public OAuth2Client create(final OAuth2Client client) throws ObjectNotFoundException,
			ValidationException {
		throw new UnsupportedOperationException("Use create(OAuth2ClientTO)");
	}

	/**
	 * Overridden to throw an {@link UnsupportedOperationException}. Use
	 * {@link #save(org.jasig.ssp.transferobject.OAuth2ClientTO)} so the
	 * service knows exactly what you're trying to do.
	 *
	 * @param client
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	@Override
	public OAuth2Client save(OAuth2Client client) throws ObjectNotFoundException,
			ValidationException {
		throw new UnsupportedOperationException("Use save(OAuth2ClientTO)");
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OAuth2Client client = ((OAuth2ClientDao)getDao()).findByClientId(clientId);
		return client == null ? null : asClientDetails(client);
	}

	// Don't want any Hib references hanging around, so we create a whole new
	// object rather than pass the Hib entity into the guts of SpringSecurity
	private ClientDetails asClientDetails(OAuth2Client client) {
		// We happen to know all these setters will take defensive copies of
		// Hib collections, so we don't really have anything to worry about
		// w/r/t stale sessions. Also note that we don't support the full
		// breadth of ClientDetail features right now, so the list of property
		// setters here is intentionally short.
		BaseClientDetails clientDetails = new BaseClientDetails();
		clientDetails.setClientId(client.getClientId());
		clientDetails.setClientSecret(client.getSecret());
		clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
		clientDetails.setAuthorities(client.getAuthorities());
		clientDetails.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
		return clientDetails;
	}



	@Override
	public OAuth2Client create(OAuth2ClientTO obj) throws
			ObjectNotFoundException, ValidationException {
		final OAuth2Client model = factory.from(obj);
		beforeWrite(model, obj);
		return super.create(model);
	}

	@Override
	public OAuth2Client save(OAuth2ClientTO obj) throws ObjectNotFoundException {
		OAuth2Client existingModel = oAuth2ClientDao.get(obj.getId());
		OAuth2Client newModel = null;
		boolean invalidateClientOnlyTokens = false;
		String previousClientId = null;
		if ( existingModel != null ) {
			previousClientId = existingModel.getClientId();
			if ( obj.isSecretChange() ) {
				invalidateClientOnlyTokens = true;
			} else {
				final List<PersistentGrantedAuthority> existingAuthorities =
						new ArrayList<PersistentGrantedAuthority>
								(existingModel.getAuthorities());
				newModel = factory.from(obj);
				invalidateClientOnlyTokens = !(areMatching(existingAuthorities, newModel.getAuthorities()));
			}
		}

		if ( newModel == null ) {
			newModel = factory.from(obj);
		}

		beforeWrite(newModel, obj);
		newModel = getDao().save(newModel);

		// Save has side-effects on objectStatus so wait until here to check it
		boolean invalidateAllTokens = false;
		invalidateAllTokens =
				existingModel != null &&
						(!(previousClientId.equals(newModel.getClientId())) ||
								(newModel.getObjectStatus() != null &&
									newModel.getObjectStatus() != ObjectStatus.ACTIVE));

		if ( invalidateAllTokens ) {
			invalidateAllTokensByClientId(previousClientId);
		} else if ( invalidateClientOnlyTokens ) {
			invalidateClientOnlyTokensByClientId(previousClientId);;
		}

		return newModel;
	}

	private void invalidateAllTokensByClientId(String clientId) {
		final Collection<OAuth2AccessToken> tokensByClientId =
				consumerTokenService.findTokensByClientId(clientId);
		for ( OAuth2AccessToken token : tokensByClientId ) {
			consumerTokenService.revokeToken(token.getValue());
		}
	}

	private void invalidateClientOnlyTokensByClientId(String clientId) {
		final Collection<OAuth2AccessToken> tokensByClientId =
				consumerTokenService.findTokensByClientId(clientId);
		for ( OAuth2AccessToken token : tokensByClientId ) {
			final OAuth2Authentication authN =
					resourceServerTokenService.loadAuthentication(token.getValue());
			if ( authN.isClientOnly() ) {
				consumerTokenService.revokeToken(token.getValue());
			}
		}
	}

	private void beforeWrite(OAuth2Client client, OAuth2ClientTO from) {
		if ( from.isSecretChange() ) {
			encodeSecret(client);
		}
		ensureSchoolId(client);
	}

	private void encodeSecret(OAuth2Client client) {
		client.setSecret(StringUtils.trimToNull(client.getSecret()));
		if ( client.getSecret() != null ) {
			client.setSecret(passwordEncoder.encode(client.getSecret()));
		}
	}

	private void ensureSchoolId(OAuth2Client client) {
		if (StringUtils.isBlank(client.getSchoolId()) ) {
			client.setSchoolId(client.getClientId());
		}
	}

	private boolean areMatching(List<PersistentGrantedAuthority> auth1,
							 List<PersistentGrantedAuthority> auth2) {
		if ( auth1 == auth2 ) {
			return true;
		}
		if ( auth1 == null || auth2 == null ) {
			return false;
		}
		return auth1.equals(auth2);
	}
}
