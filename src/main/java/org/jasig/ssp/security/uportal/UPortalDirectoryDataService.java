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
		return "unknown property";
	}

	@Override
	public String getFirstNameForUserId(final String userId) {
		return "Gen";
	}

	@Override
	public String getLastNameForUserId(final String userId) {
		return "Temporary";
	}

	@Override
	public String getPrimaryEmailAddressForUserId(final String userId) {
		return "unknown@test.com";
	}

	@Override
	public String getPhoneForUserId(final String userId) {
		return "111 867-5309";
	}

}
