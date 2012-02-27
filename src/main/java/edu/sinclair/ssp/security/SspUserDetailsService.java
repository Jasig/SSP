package edu.sinclair.ssp.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.security.exception.EmailNotFoundException;
import edu.sinclair.ssp.security.exception.UserNotAuthorizedException;
import edu.sinclair.ssp.security.exception.UserNotEnabledException;

@Transactional(readOnly = true)
public class SspUserDetailsService implements UserDetailsService { 

	private Logger logger = LoggerFactory.getLogger(SspUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotAuthorizedException, EmailNotFoundException {

		logger.debug("BEGIN : loadUserByUsername()");
		
		Collection<GrantedAuthority> authorities = Lists.newArrayList();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		SspUser sspUser = new SspUser(username, "password", true, true, true, true, authorities);
		sspUser.setEmailAddress("test.user@infinum.com");

		if (sspUser == null) {
			logger.error("Unable to load user's record for: {}", username);
			throw new UserNotAuthorizedException("Unable to load user's record, support has been notified.");
		} else if (!sspUser.isEnabled()) {
			logger.error("User is disabled: {}", username);
			throw new UserNotEnabledException("User is disabled.");
		} else if (sspUser.getEmailAddress() == null) {
			logger.error("User {} has no email address", username);
			throw new EmailNotFoundException("User does not have an assigned email address, support has been notified.");
		}
		
		logger.debug("User: {}", sspUser.toString());
		
		logger.debug("END : loadUserByUsername()");
		
		return sspUser;
	}

}
