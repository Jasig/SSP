package org.jasig.ssp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Allows SSP to access attributes for people in the community that may come 
 * from other systems on campus. 
 * 
 * @author awills
 */
public interface PersonAttributesService {
	
	Map<String,List<String>> getAttributes(HttpServletRequest req, 
			HttpServletResponse res, String username) 
					throws ObjectNotFoundException;

}
