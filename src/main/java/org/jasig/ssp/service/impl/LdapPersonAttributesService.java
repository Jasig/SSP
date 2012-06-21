package org.jasig.ssp.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;

public class LdapPersonAttributesService implements PersonAttributesService {

	public LdapPersonAttributesService() {
		

	}

	@Override
	public Map<String, List<String>> getAttributes(HttpServletRequest req,
			HttpServletResponse res, String username)
			throws ObjectNotFoundException {

		return getAttributes(username);

	}

	@Override
	public Map<String, List<String>> getAttributes(String username)
			throws ObjectNotFoundException {

		final Map<String, List<String>> rslt = new HashMap<String, List<String>>();
		rslt.put("schoolId", Arrays.asList(new String[] { username }));
		rslt.put("firstName", Arrays.asList(new String[] { "Gen" }));
		rslt.put("lastName", Arrays.asList(new String[] { "Temporary" }));
		rslt.put("primaryEmailAddress", Arrays.asList(new String[] { "unknown@test.com" }));
		
		return rslt;

	}

}
