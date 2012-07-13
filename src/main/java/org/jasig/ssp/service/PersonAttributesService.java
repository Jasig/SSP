package org.jasig.ssp.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.security.PersonAttributesResult;

/**
 * Allows SSP to access attributes for people in the community that may come
 * from other systems on campus.
 * 
 * @author awills
 */
public interface PersonAttributesService {

	PersonAttributesResult getAttributes(HttpServletRequest req,
			HttpServletResponse res, String username)
			throws ObjectNotFoundException;

	PersonAttributesResult getAttributes(String username)
			throws ObjectNotFoundException;
	
	List<Map<String, Object>> searchForUsers(HttpServletRequest req, HttpServletResponse res, 
			Map<String,String> query);

	/*
	 * @returns usernames of coaches
	 */
	Collection<String> getCoaches();

}
