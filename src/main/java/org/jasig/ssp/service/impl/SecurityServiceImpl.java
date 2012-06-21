package org.jasig.ssp.service.impl;

import java.util.ArrayList;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityServiceImpl.class);

	@Autowired
	private transient PersonService personService;

	@Override
	public SspUser currentlyAuthenticatedUser() {
		final SspUser sspUser = currentUser();

		// Check for missing data or the anonymous user
		return (sspUser == null)
				|| SspUser.ANONYMOUS_PERSON_USERNAME.equals(sspUser
						.getUsername()) || (sspUser.getPerson() == null) ? null
				: sspUser;
	}

	@Override
	public SspUser anonymousUser() {
		final SspUser user = new SspUser(SspUser.ANONYMOUS_PERSON_USERNAME,
				"", true, true, true, true,
				new ArrayList<GrantedAuthority>(0));
		try {
			user.setPerson(personService
					.personFromUsername(SspUser.ANONYMOUS_PERSON_USERNAME));
		} catch (ObjectNotFoundException e) {
			LOGGER.error("Anonymous User appears not to be configured");
		}
		return user;
	}

	public SspUser noAuthAdminUser() {
		final SspUser user = new SspUser("no auth admin user", "", false,
				false, false, false, new ArrayList<GrantedAuthority>(0));

		try {
			user.setPerson(personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		} catch (ObjectNotFoundException e) {
			throw new ConfigException(
					"System Administrator not defined for Person Service", e);
		}

		return user;
	}

	@Override
	public SspUser currentUser() {
		SspUser sspUser = null;

		// assumption: SecurityContext only returns null for Authentication when
		// there is no actual web context

		if (null == SecurityContextHolder.getContext().getAuthentication()) {
			return noAuthAdminUser();
		}

		if (SecurityContextHolder.getContext().getAuthentication()
				.isAuthenticated()) {
			final Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			if (principal instanceof SspUser) {
				sspUser = (SspUser) principal;
			} else if (principal instanceof String) {
				if (SspUser.ANONYMOUS_PERSON_USERNAME.equals(principal)) {
					sspUser = anonymousUser();
				} else {
					LOGGER.error("Just tried to get an sspUser object from a user that is "
							+ principal);
					return null;
				}
			} else {
				LOGGER.error("Just tried to get an sspUser object from an object that is really a "
						+ principal.toString());
				return null;
			}
		} else {
			return null;
		}

		if (sspUser.getPerson() == null) {
			try {
				sspUser.setPerson(personService.personFromUsername(sspUser
						.getUsername()));
			} catch (ObjectNotFoundException e) {
				LOGGER.error("Did not find the person's domain object");
				return null;
			}
		}

		return sspUser;
	}

	@Override
	public boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication()
				.isAuthenticated()
				&& (currentUser() != null);
	}

	@Override
	public boolean hasAuthority(final String authority) {
		for (GrantedAuthority auth : currentUser().getAuthorities()) {
			if (auth.getAuthority().equalsIgnoreCase(authority)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getSessionId() {
		return RequestContextHolder.currentRequestAttributes().getSessionId();
	}
}
