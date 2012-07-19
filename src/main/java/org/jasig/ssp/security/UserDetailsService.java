package org.jasig.ssp.security;

import java.util.Collection;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.exception.EmailNotFoundException;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
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

	@Autowired
	private transient PersonAttributesService personAttributesService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDetailsService.class);

	public static final boolean ALL_AUTHENTICATED_USERS_CAN_CREATE_ACCOUNT = true;
	public static final String PERMISSION_TO_CREATE_ACCOUNT = "ROLE_CAN_CREATE";

	@Autowired
	private transient PersonService personService;

	private boolean hasAccountCreationPermission(
			final Collection<GrantedAuthority> authorities) {
		boolean permission = ALL_AUTHENTICATED_USERS_CAN_CREATE_ACCOUNT;

		// if already true, skip permission check
		if (permission) {
			return true;
		}

		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equals(PERMISSION_TO_CREATE_ACCOUNT)) {
				permission = true;
				break;
			}
		}

		return permission;
	}

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

			if (hasAccountCreationPermission(authorities)) {
				// At this point, we should already have authentication through
				// ldap or uportal go ahead and create the user.
				person = new Person();
				person.setEnabled(true);
				person.setUsername(username);

				try {
					final PersonAttributesResult attr = personAttributesService
							.getAttributes(username);
					person.setSchoolId(attr.getSchoolId());
					person.setFirstName(attr.getFirstName());
					person.setLastName(attr.getLastName());
					person.setPrimaryEmailAddress(attr.getPrimaryEmailAddress());
					// :TODO Set additional user attributes
					person = personService.create(person);
				} catch (ObjectNotFoundException onfe) {
					throw new UnableToCreateAccountException( // NOPMD - stack
																// passed to new
																// exception
							"Unable to pull required attributes", onfe);
				}

			} else {
				throw new UnableToCreateAccountException( // NOPMD already know
															// the account was
															// not found
						"Insufficient Permissions to create Account");
			}
		}

		final boolean enabled = person.getEnabled();
		if (!enabled) {
			LOGGER.error("User is disabled: {}", username);
			throw new UserNotEnabledException("User is disabled.");
		}

		final SspUser sspUser = new SspUser(username, "password",
				enabled, enabled, enabled, enabled, authorities);

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

	@Override
	public void setPersonAttributesService(
			final PersonAttributesService personAttributesService) {
		this.personAttributesService = personAttributesService;
	}

}
