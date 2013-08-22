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
package org.jasig.ssp.model.security.oauth2;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.security.PersistentGrantedAuthority;
import org.jasig.ssp.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Entity
@DiscriminatorValue("oauth2-client")
public class OAuth2Client extends Person {

	private static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

	// We only support client creds at the moment... eventually this should
	// become a configurable field, but at this point there's no point.
	private static final Set<String> authorizedGrantTypes = Collections.singleton(CLIENT_CREDENTIALS_GRANT_TYPE);

	@Column(nullable = true, length = 256)
	@Size(max = 256)
	private String secret;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="person_authority", joinColumns=@JoinColumn(name="person_id"))
	@Column(name="authority")
	@Cascade(value =  { CascadeType.ALL })
	private List<PersistentGrantedAuthority> authorities;

	@Nullable
	@Column(name = "oauth2_client_access_token_validity_seconds")
	private Integer accessTokenValiditySeconds;


	public String getClientId() {
		return getUsername();
	}

	public void setClientId(String clientId) {
		setUsername(clientId);
	}

	// Bit of a hack, but keeps this mapping nicely centralized here in this
	// class. Specifically it avoids duplicating the mapping in the DAO.
	//
	// Some background... the OAuth2 spec refers to a Client's identifier as its
	// ClientId. So it's nice and symmetric to support that concept and
	// vocabulary directly on our corresponding model. But because doing this
	// involves slightly more than a simple getter and setter, you could argue
	// the mapping should be in the owning service in order to keep our model
	// POJOs as dumb as possible. But it's hard to keep that abstraction from
	// leaking, e.g. because the TO class, following conventions, would also
	// directly implement the username->clientId mapping rather than routing it
	// through the service. So, for nice, obvious correlation with the OAuth2
	// spec, and to avoid mapping leakage, we decided to put the mapping here.
	//
	// Of course... it still leaks anyway since the front-end needs to know
	// the physical column name in order for SortingAndPaging to work against
	// this field without writing extra mapping code.
	public static String getPhysicalClientIdColumnName() {
		return "username";
	}

	public String getSecret() {
		return secret;
	}

	/**
	 * Should only be invoked by {@link OAuth2ClientService}, which knows how
	 * to properly encode the value.
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public List<PersistentGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<PersistentGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(@Nullable Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public Integer getRefreshTokenValiditySeconds() {
		return null; // We don't support refresh tokens right now
					 // BaseClientDetails and JsbcClientDetailsService allow for nulls here
	}

}
