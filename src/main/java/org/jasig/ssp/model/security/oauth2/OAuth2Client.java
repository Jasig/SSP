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

	private static final String BEARER_GRANT_TYPE = "Bearer";
	private static final Set<String> authorizedGrantTypes = Collections.singleton(BEARER_GRANT_TYPE);

	@Column(nullable = true, length = 256)
	@Size(max = 256)
	private String secret;

	@Transient
	private String secretChange;

	@Transient
	private boolean isSecretChange;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="person_authority", joinColumns=@JoinColumn(name="person_id"))
	@Column(name="authority")
	@Cascade(value =  { CascadeType.ALL })
	private List<PersistentGrantedAuthority> authorities;

	@Nullable
	@Column(name = "oauth2_client_access_token_validity_seconds")
	private Integer accessTokenValiditySeconds;

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

	public boolean isSecretChange() {
		return isSecretChange;
	}

	public String getClientSecretChange() {
		return secretChange;
	}

	public void setSecretChange(String secretChange) {
		this.isSecretChange = !(secretChange == null && secret == null);
		this.secretChange = secretChange;
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
