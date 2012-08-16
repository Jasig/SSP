package org.jasig.ssp.security;

import java.util.Collection;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.exception.EmailNotFoundException;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * UserDetailsService that requires the authorities to already be determined via
 * ldap, uportal, etc...
 *
 */
public class UserDetailsService implements SspUserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDetailsService.class);

	@Autowired
	private transient PersonService personService;

	@Override
	public UserDetails loadUserDetails(final String username,
			final Collection<GrantedAuthority> authorities) {
		Person person = null;

		try {
			person = personService.personFromUsername(username);
		} catch (ObjectNotFoundException e) {

			LOGGER.info(
					"Unable to load {}'s record., creating user in ssp",
					username);
			try {
				person = personService.createUserAccount(username, authorities);
			} catch ( ObjectExistsException ee ) {
				try {
					person = personService.personFromUsername(username);
				} catch ( ObjectNotFoundException eee ) {
					throw new UnableToCreateAccountException(
							"Couldn't create account with username" + username
							+ " because an account with that username seemed"
							+ " to already exist, but was unable to load that"
							+ " existing account.", eee);
				}
			}
		}

		if (person.isDisabled()) {
			LOGGER.error("User is disabled: {}", username);
			throw new UserNotEnabledException("User is disabled.");
		}

		final SspUser sspUser = new SspUser(username, "password",
				true, true, true, true, authorities);

		sspUser.setPerson(person);

		if (StringUtils.isEmpty(person.getEmailAddressWithName())) {
			LOGGER.error("User {} has no email address", username);
			throw new EmailNotFoundException(
					"User does not have an assigned email address, support has been notified.");
		} else {
			sspUser.setEmailAddress(person.getEmailAddressWithName());
		}

		LOGGER.debug("Loaded User: {}", sspUser.toString());

		return sspUser;
	}

	@Override
	public UserDetails loadUserDetails(final Authentication token)
			throws UsernameNotFoundException {
		return loadUserDetails((String) token.getPrincipal(),
				token.getAuthorities());
	}

	@Override
	public UserDetails mapUserFromContext(final DirContextOperations ctx,
			final String username, final Collection<GrantedAuthority> authority) {
		return loadUserDetails(username, authority);
	}

	@Override
	public void mapUserToContext(final UserDetails user,
			final DirContextAdapter ctx) {
		throw new UnsupportedOperationException("Not applicable");
	}

}
