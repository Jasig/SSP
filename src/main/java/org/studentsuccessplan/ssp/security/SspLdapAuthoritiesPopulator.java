package edu.sinclair.ssp.security;

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class SspLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	private UserDetailsService userDetailsService;

	public SspLdapAuthoritiesPopulator(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Collection<GrantedAuthority> getGrantedAuthorities(
			DirContextOperations userData, String username) {
		return (Collection<GrantedAuthority>) userDetailsService
				.loadUserByUsername(username).getAuthorities();
	}

}
