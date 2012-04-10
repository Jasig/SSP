package org.studentsuccessplan.ssp.service;

import org.studentsuccessplan.ssp.security.SspUser;

public interface SecurityService {

	public SspUser currentlyLoggedInSspUser();

	public boolean isAuthenticated();

	public String getSessionId();
}
