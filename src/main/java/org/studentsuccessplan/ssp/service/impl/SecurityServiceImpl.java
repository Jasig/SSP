package org.studentsuccessplan.ssp.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;

@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityServiceImpl.class);

	@Autowired
	private PersonService personService;

	@Override
	public SspUser currentlyAuthenticatedUser() {
		SspUser sspUser = currentUser();

		// Check for missing data or the anonymous user
		return (sspUser == null)
				|| SspUser.ANONYMOUS_PERSON_USERNAME.equals(sspUser
						.getUsername()) || (sspUser.getPerson() == null) ? null
				: sspUser;
	}

	@Override
	public SspUser anonymousUser() {
		SspUser user = new SspUser(SspUser.ANONYMOUS_PERSON_USERNAME,
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

	@Override
	public SspUser currentUser() {
		SspUser sspUser = null;

		if (SecurityContextHolder.getContext().getAuthentication()
				.isAuthenticated()) {
			Object principal = SecurityContextHolder.getContext()
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
	public String getSessionId() {
		return RequestContextHolder.currentRequestAttributes().getSessionId();
	}
}
