package edu.sinclair.ssp.service;

import edu.sinclair.ssp.security.SspUser;

public interface SecurityService {

	public SspUser currentlyLoggedInSspUser();
}
