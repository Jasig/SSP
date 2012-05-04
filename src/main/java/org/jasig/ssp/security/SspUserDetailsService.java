package org.jasig.ssp.security;

import java.util.Collection;

import org.jasig.ssp.security.exception.EmailNotFoundException;
import org.jasig.ssp.security.exception.UserNotAuthorizedException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * SSP User details service
 */
@Transactional(readOnly = true)
public class SspUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SspUserDetailsService.class);

	@Autowired
	private transient PersonService personService;

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UserNotAuthorizedException, EmailNotFoundException {

		LOGGER.debug("BEGIN : loadUserByUsername()");

		final Collection<GrantedAuthority> authorities = Lists.newArrayList();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));

		final SspUser sspUser = new SspUser(username, "password", true, true,
				true,
				true, authorities);
		sspUser.setEmailAddress("test.user@infinum.com");

		try {
			sspUser.setPerson(personService.personFromUsername(username));
		} catch (ObjectNotFoundException e) {
			LOGGER.error("Did not find the person's domain object");
		}

		if (sspUser.getPerson() == null) {
			LOGGER.error("Unable to load user's record for: {}", username);
			throw new UserNotAuthorizedException(
					"Unable to load user's record, support has been notified.");
		} else if (!sspUser.isEnabled()) {
			LOGGER.error("User is disabled: {}", username);
			throw new UserNotEnabledException("User is disabled.");
		} else if (sspUser.getEmailAddress() == null) {
			LOGGER.error("User {} has no email address", username);
			throw new EmailNotFoundException(
					"User does not have an assigned email address, support has been notified.");
		}

		LOGGER.debug("User: {}", sspUser.toString());
		LOGGER.debug("END : loadUserByUsername()");

		return sspUser;
	}
}
