package org.jasig.ssp.security;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.model.Person;

public class LdapDirectoryDataService implements DirectoryDataService {

	@Override
	public String getProperty(final String propertyName, final Person user) {
		throw new NotImplementedException();
	}

	@Override
	public String getProperty(final String propertyName, final String userId) {
		throw new NotImplementedException();
	}

}
