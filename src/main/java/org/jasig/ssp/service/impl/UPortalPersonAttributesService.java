package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.portlet.utils.rest.CrossContextRestApiInvoker;
import org.jasig.portlet.utils.rest.RestResponse;
import org.jasig.portlet.utils.rest.SimpleCrossContextRestApiInvoker;
import org.jasig.ssp.security.PersonAttributesResult;
import org.jasig.ssp.security.exception.UPortalSecurityException;
import org.jasig.ssp.security.uportal.RequestAndResponseAccessFilter;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UPortalPersonAttributesService implements PersonAttributesService {

	private static final String PARAM_USERNAME = "username";
	private static final String REST_URI_PERSON = "/ssp-platform/api/people/{"
			+ PARAM_USERNAME + "}.json";
	private static final String PARAM_SEARCH_TERMS = "searchTerms%5B%5D";
	private static final String REST_URI_SEARCH_PREFIX = "/ssp-platform/api/people.json?{"
			+ PARAM_SEARCH_TERMS + "}";
	private static final String PERSON_KEY = "person";
	private static final String PEOPLE_KEY = "people";
	private static final String USERNAME_KEY = "name";
	private static final String ATTRIBUTES_KEY = "attributes";

	//
	private static final String ATTRIBUTE_SCHOOLID = "schoolId";
	private static final String ATTRIBUTE_FIRSTNAME = "firstName";
	private static final String ATTRIBUTE_LASTNAME = "lastName";
	private static final String ATTRIBUTE_PRIMARYEMAILADDRESS = "primaryEmailAddress";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalPersonAttributesService.class);

	@Autowired
	private transient RequestAndResponseAccessFilter requestAndResponseAccessFilter;

	private Map<String, String> coachesQuery = Collections.singletonMap(
			"SSP_ROLES", "SSP_COACH");

	/**
	 * Allows the <code>coachesQuery</code> to be configured in Spring XML. The
	 * default ("SSP_ROLES":"COACH") matches the default permissions setup for
	 * coaches, and should be good for many or most adopters. Those who wish to
	 * define coaches in SSP in a non-default way -- such as through an AD group
	 * -- will need to adjust this setting to query for coaches appropriately.
	 * 
	 * @param coachesQuery
	 *            A Map of Strings, attribute name to attribute value
	 */
	public void setCoachesQuery(final Map<String, String> coachesQuery) {
		this.coachesQuery = coachesQuery;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonAttributesResult getAttributes(
			final HttpServletRequest req,
			final HttpServletResponse res, final String username)
			throws ObjectNotFoundException {

		LOGGER.debug("Fetching attributes for user '{}'", username);

		final Map<String, String[]> params = new HashMap<String, String[]>();
		params.put(PARAM_USERNAME, new String[] { username });

		final CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
		final RestResponse rr = rest.invoke(req, res, REST_URI_PERSON, params);

		final ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, Map<String, List<String>>>> value = null;
		try {
			value = mapper.readValue(rr.getWriterOutput(), Map.class);
		} catch (final Exception e) {
			final String msg = "Failed to access attributes for the specified person:  "
					+ username;
			throw new UPortalSecurityException(msg, e);
		}

		final Map<String, Map<String, List<String>>> person = value
				.get(PERSON_KEY);
		if (person == null) {
			// No match...
			throw new ObjectNotFoundException(
					"The specified person is unrecognized", username);
		}
		final Map<String, List<String>> rslt = person.get(ATTRIBUTES_KEY);
		if (rslt == null) {
			throw new ObjectNotFoundException(
					"No attributes are available for the specified person",
					username);
		}

		LOGGER.debug("Retrieved the following attributes for user {}:  {}",
				username, rslt.toString());

		return convertAttributes(rslt);
	}

	@Override
	public PersonAttributesResult getAttributes(final String username)
			throws ObjectNotFoundException {

		final HttpServletRequest req = requestAndResponseAccessFilter
				.getHttpServletRequest();
		final HttpServletResponse res = requestAndResponseAccessFilter
				.getHttpServletResponse();

		if ((req == null) || (res == null)) {
			throw new UnsupportedOperationException(
					"Uportal attributes may only be fetched when a HttpServletRequest and HttpServletResponse are available");
		} else {
			return getAttributes(req, res, username);
		}

	}

	@Override
	public PersonAttributesResult getAttributes(String username,
			PortletRequest portletRequest) {
		@SuppressWarnings("unchecked") Map<String,String> userInfo =
				(Map<String,String>) portletRequest.getAttribute(PortletRequest.USER_INFO);
		if ( userInfo == null ) {
			return new PersonAttributesResult();
		}
		return convertAttributesSingleValued(userInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> searchForUsers(
			final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, String> query) {

		LOGGER.debug("Searching for users with query terms '{}'", query);

		// Assemble searchTerms[] in expected way
		final List<String> searchTerms = new ArrayList<String>();
		final Map<String, String[]> params = new HashMap<String, String[]>();
		for (final Map.Entry<String, String> y : query.entrySet()) {
			searchTerms.add(y.getKey());
			params.put(y.getKey(), new String[] { y.getValue() });
		}

		// Build the URL
		final StringBuilder bld = new StringBuilder(REST_URI_SEARCH_PREFIX);
		for (final String key : params.keySet()) {
			bld.append("&{").append(key).append("}");
		}
		final String url = bld.toString();

		LOGGER.debug("Invoking REST enpoint with URL '{}'", url);

		// Add serchTerms[] to the params
		params.put(PARAM_SEARCH_TERMS, searchTerms.toArray(new String[0]));

		final CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
		final RestResponse rr = rest.invoke(req, res, url, params);

		final ObjectMapper mapper = new ObjectMapper();
		Map<String, List<Map<String, Object>>> value = null;
		try {
			value = mapper.readValue(rr.getWriterOutput(), Map.class);
		} catch (final Exception e) {
			final String msg = "Failed to search for users with the specified query:  "
					+ query;
			throw new PersonAttributesSearchException(msg, e);
		}

		final List<Map<String, Object>> rslt = value
				.get(PEOPLE_KEY);
		if (rslt == null) {
			// Odd... should at least be an empty list
			final String msg = "Search for users returned no list for the specified query:  "
					+ query;
			throw new PersonAttributesSearchException(msg);
		}

		LOGGER.debug("Retrieved the following people for query {}:  {}",
				query, rslt);

		return rslt;

	}

	/**
	 * <strong>NOTE:</strong> This method probably belongs somewhere else. It's
	 * at a different level of abstraction verses the other methods on this
	 * class. We should probably look to move it when we have more clarity as to
	 * where it could land.
	 */
	@Override
	public Collection<String> getCoaches() {

		final HttpServletRequest req = requestAndResponseAccessFilter
				.getHttpServletRequest();
		final HttpServletResponse res = requestAndResponseAccessFilter
				.getHttpServletResponse();

		// Get out if we can't fulfill the request
		if ((req == null) || (res == null)) {
			throw new UnsupportedOperationException(
					"Uportal attributes may only be fetched when a HttpServletRequest and HttpServletResponse are available");
		}

		final List<String> rslt = new ArrayList<String>();

		final List<Map<String, Object>> people = searchForUsers(req, res,
				coachesQuery);
		for (final Map<String, Object> person : people) {
			rslt.add((String) person.get(USERNAME_KEY));
		}

		return rslt;

	}

	/*
	 * Private Stuff
	 */

	private PersonAttributesResult convertAttributes(final Map<String,
			List<String>> attr) {

		final PersonAttributesResult person = new PersonAttributesResult();

		if (attr.containsKey(ATTRIBUTE_SCHOOLID)) {
			person.setSchoolId(attr.get(ATTRIBUTE_SCHOOLID).get(0));
		}
		if (attr.containsKey(ATTRIBUTE_FIRSTNAME)) {
			person.setFirstName(attr.get(ATTRIBUTE_FIRSTNAME).get(0));
		}
		if (attr.containsKey(ATTRIBUTE_LASTNAME)) {
			person.setLastName(attr.get(ATTRIBUTE_LASTNAME).get(0));
		}
		if (attr.containsKey(ATTRIBUTE_PRIMARYEMAILADDRESS)) {
			person.setPrimaryEmailAddress(attr.get(
					ATTRIBUTE_PRIMARYEMAILADDRESS).get(0));
		}

		return person;
	}

	private PersonAttributesResult convertAttributesSingleValued(
			final Map<String, String> attr) {
		final PersonAttributesResult person = new PersonAttributesResult();

		if (attr.containsKey(ATTRIBUTE_SCHOOLID)) {
			person.setSchoolId(attr.get(ATTRIBUTE_SCHOOLID));
		}
		if (attr.containsKey(ATTRIBUTE_FIRSTNAME)) {
			person.setFirstName(attr.get(ATTRIBUTE_FIRSTNAME));
		}
		if (attr.containsKey(ATTRIBUTE_LASTNAME)) {
			person.setLastName(attr.get(ATTRIBUTE_LASTNAME));
		}
		if (attr.containsKey(ATTRIBUTE_PRIMARYEMAILADDRESS)) {
			person.setPrimaryEmailAddress(attr.get(
					ATTRIBUTE_PRIMARYEMAILADDRESS));
		}

		return person;
	}
}