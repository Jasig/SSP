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
package org.jasig.ssp.transferobject;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jasig.ssp.model.security.PersistentGrantedAuthority;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;

import com.google.common.collect.Lists;

public class OAuth2ClientTO extends AbstractAuditableTO<OAuth2Client>
	implements TransferObject<OAuth2Client> {

	@NotNull
	private String clientId;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String primaryEmailAddress;

	private String secret;

	// So we can distinguish between not specifying a secret (the normal case)
	// and specifying a null secret which should delete the exising secret.
	private boolean secretChange;

	private List<String> authorities;

	private Integer accessTokenValiditySeconds;

	public OAuth2ClientTO() {
		super();
	}

	public OAuth2ClientTO(final OAuth2Client model) {
		super();
		from(model);
	}

	@Override
	public void from(final OAuth2Client model) {
		super.from(model);

		clientId = model.getUsername();
		firstName = model.getFirstName();
		lastName = model.getLastName();
		primaryEmailAddress = model.getPrimaryEmailAddress();
		// intentionally skip secret
		authorities = authoritiesFrom(model);
		accessTokenValiditySeconds = model.getAccessTokenValiditySeconds();
	}

	private List<String> authoritiesFrom(OAuth2Client model) {
		final List<PersistentGrantedAuthority> fromAuthorities =
				model.getAuthorities();

		if ( fromAuthorities == null ) {
			return null;
		}
		if ( fromAuthorities.isEmpty() ) {
			return Lists.newArrayListWithCapacity(0);
		}
		List<String> authorities = Lists.newArrayListWithCapacity(fromAuthorities.size());
		for ( PersistentGrantedAuthority pga : model.getAuthorities() ) {
			authorities.add(pga.getAuthority());
		}
		return authorities;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	// This field isn't mapped out of the model, but still, don't ever
	// broadcast secrets in API responses
	@JsonIgnore
	public String getSecret() {
		return secret;
	}

	/**
	 * Use this to set a secret change, including a {@link null} to delete the
	 * existing secret. But because we do support that sort of deletion, you
	 * also need to set {@link secretChange} otherwise whatever is set here
	 * will be ignored.
	 *
	 * @param secret
	 */
	@JsonProperty
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	// Only relevant on write operations
	@JsonIgnore
	public boolean isSecretChange() {
		return secretChange;
	}

	// Only relevant on write operations
	@JsonProperty
	public void setSecretChange(boolean secretChange) {
		this.secretChange = secretChange;
	}
}
