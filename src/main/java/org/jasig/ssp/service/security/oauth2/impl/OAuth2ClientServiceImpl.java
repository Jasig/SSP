package org.jasig.ssp.service.security.oauth2.impl;

import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.security.oauth2.OAuth2ClientDao;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.security.oauth2.OAuth2ClientService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OAuth2ClientServiceImpl extends AbstractAuditableCrudService<OAuth2Client>
		implements OAuth2ClientService, ClientDetailsService  {

	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

	@Autowired
	private OAuth2ClientDao oAuth2ClientDao;

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

	@Override
	public OAuth2Client create(final OAuth2Client client) throws ObjectNotFoundException,
			ValidationException {
		encodeSecret(client);
		return super.create(client);
	}

	@Override
	public OAuth2Client save(OAuth2Client client) throws ObjectNotFoundException, ValidationException {
		encodeSecret(client);
		return getDao().save(client);
	}

	private void encodeSecret(OAuth2Client client) {
		if ( client.isSecretChange() ) {
			client.setSecret(passwordEncoder.encode(client.getClientSecretChange()));
		}
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OAuth2Client client = ((OAuth2ClientDao)getDao()).findByUsername(clientId);
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
		clientDetails.setClientId(client.getUsername());
		clientDetails.setClientSecret(client.getSecret());
		clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
		clientDetails.setAuthorities(client.getAuthorities());
		clientDetails.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
		return clientDetails;
	}
}
