package org.jasig.ssp.service;

import java.io.IOException;
import java.util.Map;

public interface ServerService {

	Map<String,Object> getDateTimeProfile();
	Map<String,Object> getVersionProfile() throws IOException;
	Map<String,Object> getClientTimeout();
}
