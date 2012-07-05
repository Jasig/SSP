package org.jasig.ssp.web.api;

import java.util.List;
import java.util.Map;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Allows the logged in user to get their profile.
 * <p>
 * Mapped to the URI path <code>/1/session/...</code>
 */
@Controller
@RequestMapping("/1/session")
public class SessionController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SessionController.class);

	@Autowired
	private transient SecurityService service;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient PersonTOFactory factory;

	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getMyServicePermissions() {

		final Map<String, Object> model = Maps.newHashMap();

		final List<String> permissions = permissions();

		model.put("success", true);
		model.put("permissions", permissions);

		return model;
	}

	private List<String> permissions() {
		final List<String> permissions = Lists.newArrayList();

		final SspUser user = service.currentlyAuthenticatedUser();

		if (user != null) {
			for (GrantedAuthority auth : user.getAuthorities()) {
				permissions.add(auth.getAuthority());
			}
		}

		return permissions;
	}

	@RequestMapping(value = "/confidentialityLevels", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getMyConfidentialityLevels() {

		final Map<String, Object> model = Maps.newHashMap();

		final SspUser user = service.currentlyAuthenticatedUser();

		model.put("success", true);
		model.put("levels", ReferenceLiteTO.toTOList(
				confidentialityLevelService
						.confidentialityLevelsForSspUser(user)));

		return model;
	}

	/**
	 * Gets the currently authenticated user.
	 * <p>
	 * Mapped to the URI path <code>.../getAuthenticatedPerson</code>
	 * 
	 * @return The currently authenticated {@link Person} info, or null (empty)
	 *         if not authenticated.
	 */
	@RequestMapping(value = "/getAuthenticatedPerson", method = RequestMethod.GET)
	public @ResponseBody
	PersonTO getAuthenticatedPerson() {
		if (service == null) {
			LOGGER.error("The security service was not wired by the Spring container correctly for the SessionController.");
			return null;
		}

		final SspUser user = service.currentlyAuthenticatedUser();

		if (user == null) {
			// User not authenticated, so return an empty result, but still with
			// an HTTP 200 response.
			return null;
		}

		// Return authenticated person transfer object
		final PersonTO personTO = factory.from(user.getPerson());

		personTO.setConfidentialityLevels(ReferenceLiteTO
				.toTOList(confidentialityLevelService
						.confidentialityLevelsForSspUser(user)));

		personTO.setPermissions(permissions());

		return personTO;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
