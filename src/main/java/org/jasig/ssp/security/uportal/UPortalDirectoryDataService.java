package org.jasig.ssp.security.uportal;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.DirectoryDataService;

public class UPortalDirectoryDataService implements DirectoryDataService {

	@Override
	public String getProperty(final String propertyName, final Person user) {
		throw new NotImplementedException("Not Implemented");
	}

	@Override
	public String getProperty(final String propertyName, final String userId) {
		if ("schoolId".equals(propertyName)) {
			return userId;
		} else if ("firstName".equals(propertyName)) {
			return "Gen";
		} else if ("lastName".equals(propertyName)) {
			return "Temporary";
		} else if ("primaryEmailAddress".equals(propertyName)) {
			return "unknown@test.com";
		} else {
			return "unknown property";
		}
	}

}
