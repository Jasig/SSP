package org.jasig.ssp.security;

import java.util.Collection;

import org.jasig.ssp.service.PersonAttributesService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public interface SspUserDetailsService extends
		AuthenticationUserDetailsService,
		UserDetailsContextMapper {

	UserDetails loadUserDetails(final String username,
			final Collection<GrantedAuthority> authorities);

	void setPersonAttributesService(
			final PersonAttributesService personAttributesService);
}
