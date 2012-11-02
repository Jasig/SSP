package org.jasig.ssp.util.service.stub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.security.PersonAttributesResult;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;

import com.google.common.collect.Lists;

public class StubPersonAttributesService implements PersonAttributesService {
	private Collection<String> coachUsernames;

	@Override
	public PersonAttributesResult getAttributes(HttpServletRequest req, HttpServletResponse res, String username) throws ObjectNotFoundException {
		throw new UnsupportedOperationException("Not needed yet, so not implemented yet");
	}

	@Override
	public PersonAttributesResult getAttributes(String username) throws ObjectNotFoundException {
		throw new UnsupportedOperationException("Not needed yet, so not implemented yet");
	}

	@Override
	public PersonAttributesResult getAttributes(String username, PortletRequest portletRequest) throws ObjectNotFoundException {
		throw new UnsupportedOperationException("Not needed yet, so not implemented yet");
	}

	@Override
	public List<Map<String, Object>> searchForUsers(HttpServletRequest req, HttpServletResponse res, Map<String, String> query) {
		throw new UnsupportedOperationException("Not needed yet, so not implemented yet");
	}

	@Override
	public Collection<String> getCoaches() {
		return coachUsernames == null ? new ArrayList<String>(0) : coachUsernames;
	}

	/**
	 * Assign a direct reference to the collection underlying
	 * {@link #getCoaches()}
	 * @param coachUsernames
	 */
	public void setCoachUsernames(Collection<String> coachUsernames) {
		this.coachUsernames = coachUsernames;
	}

	/**
	 * Get the collection assigned via
	 * {@link #setCoachUsernames(java.util.Collection)}. Same as
	 * {@link #getCoaches()} but with no special null handling.
	 *
	 * @return
	 */
	public Collection<String> getCoachUsernames() {
		return coachUsernames;
	}
}
