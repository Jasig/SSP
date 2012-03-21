package edu.sinclair.ssp.security;

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

	private Logger logger = LoggerFactory
			.getLogger(SspUserDetailsContextMapper.class);

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<GrantedAuthority> authorities) {

		logger.debug("BEGIN : mapUserFromContext()");

		SspUser sspUser = new SspUser(username, "password", // Pwd not used,
															// stored in AD
				true, true, true, true, authorities);

		logger.debug("User: {}", sspUser.toString());

		logger.debug("END : mapUserFromContext()");

		return sspUser;
	}

	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		// TODO Auto-generated method stub
	}

}
