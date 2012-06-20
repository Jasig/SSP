package org.jasig.ssp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.portlet.utils.rest.CrossContextRestApiInvoker;
import org.jasig.portlet.utils.rest.RestResponse;
import org.jasig.portlet.utils.rest.SimpleCrossContextRestApiInvoker;
import org.jasig.ssp.security.exception.UPortalSecurityException;
import org.jasig.ssp.security.uportal.UPortalRequestContextFilter;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UPortalPersonAttributesService implements PersonAttributesService {

	private static final String PARAM_USERNAME = "username";
	private static final String REST_URI = "/ssp-platform/api/people/{"
			+ PARAM_USERNAME + "}.json";
	private static final String PERSON_KEY = "person";
	private static final String ATTRIBUTES_KEY = "attributes";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalPersonAttributesService.class);
	
	@Autowired
	private UPortalRequestContextFilter uPortalRequestContextFilter;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> getAttributes(
			final HttpServletRequest req,
			final HttpServletResponse res, final String username)
			throws ObjectNotFoundException {

		LOGGER.debug("Fetching attributes for user '{}'", username);

		final Map<String, String[]> params = new HashMap<String, String[]>();
		params.put(PARAM_USERNAME, new String[] { username });

		final CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
		final RestResponse rr = rest.invoke(req, res, REST_URI, params);

		final ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, Map<String, List<String>>>> value = null;
		try {
			value = mapper.readValue(rr.getWriterOutput(), Map.class);
		} catch (Exception e) {
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

		return rslt;

	}

	@Override
	public Map<String, List<String>> getAttributes(String username)
			throws ObjectNotFoundException {
		
		final HttpServletRequest req = uPortalRequestContextFilter
										.getHttpServletRequest();
		final HttpServletResponse res = uPortalRequestContextFilter
										.getHttpServletResponse();
		
		return getAttributes(req, res, username);
		
	}

}
