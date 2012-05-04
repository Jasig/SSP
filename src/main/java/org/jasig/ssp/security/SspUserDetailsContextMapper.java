package org.jasig.ssp.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SspUserDetailsContextMapper implements UserDetailsContextMapper {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SspUserDetailsContextMapper.class);

	@Override
	public UserDetails mapUserFromContext(final DirContextOperations ctx,
			final String username,
			final Collection<GrantedAuthority> authorities) {

		LOGGER.debug("BEGIN : mapUserFromContext()");

		final SspUser sspUser = new SspUser(username, "password", // Pwd not
																	// used,
				// stored in AD
				true, true, true, true, authorities);

		LOGGER.debug("User: {}", sspUser.toString());
		LOGGER.debug("END : mapUserFromContext()");

		return sspUser;
	}

	@Override
	public void mapUserToContext(final UserDetails arg0,
			final DirContextAdapter arg1) {
		// TODO Auto-generated method stub
	}
}
