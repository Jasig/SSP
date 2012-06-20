package org.jasig.ssp.security.uportal;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.DirectoryDataService;

/**
 * I respectfully suggest that you don't need any of this DirectoryDataService 
 * infrastructure.  I put out {@link PersonAttributesService} on 5/24 as well as 
 * a wiki page (https://wiki.jasig.org/display/SSP/Person+Attributes) and an 
 * email.  This class can fill this need.
 * 
 * I am happy to rethink/refactor the {@link PersonAttributesService} as our 
 * needs become clearer and evolve.  Please get me on email, on IM, or on 
 * the phone, and I'll be very happy to work it out. 
 */
@Deprecated
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
