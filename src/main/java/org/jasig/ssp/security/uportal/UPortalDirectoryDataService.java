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
		throw new NotImplementedException("Not Implemented");
	}

}
