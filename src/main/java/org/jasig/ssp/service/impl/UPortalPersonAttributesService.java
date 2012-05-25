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
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UPortalPersonAttributesService implements PersonAttributesService {

	private static final String PARAM_USERNAME = "username";
	private static final String REST_URI = "/ssp-platform/api/people/{" 
										+ PARAM_USERNAME + "}.json";
	private static final String PERSON_KEY = "person";
	private static final String ATTRIBUTES_KEY = "attributes";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,List<String>> getAttributes(HttpServletRequest req, HttpServletResponse res, String username) throws ObjectNotFoundException {
		
		log.debug("Fetching attributes for user '{}'", username);

		final Map<String, String[]> params = new HashMap<String, String[]>();
		params.put(PARAM_USERNAME, new String[] {username});

		CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
		RestResponse rr = rest.invoke(req, res, REST_URI, params);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Map<String,Map<String,List<String>>>> value = null;
		try {
			value = mapper.readValue(rr.getWriterOutput(), Map.class);
		} catch (Exception e) {
			String msg = "Failed to access attributes for the specified person:  " 
																+ username;
			throw new RuntimeException(msg, e);
		}

		Map<String,Map<String,List<String>>> person = value.get(PERSON_KEY);
		if (person == null) {
			// No match...
			String msg = "The specified person is unrecognized";
			throw new ObjectNotFoundException(msg, username);
		}
		Map<String,List<String>> rslt = person.get(ATTRIBUTES_KEY);
		if (rslt == null) {
			String msg = "No attributes are available for the specified person";
			throw new ObjectNotFoundException(msg, username);
		}
		
		log.debug("Retrieved the following attributes for user {}:  {}", username, rslt.toString());

		return rslt;
		
	}

}
