package org.jasig.ssp.security.uportal;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.DirectoryDataService;

/**
 * I respectfully sugest that you don't need any of this DirectoryDataService 
 * infrastructure.  I set up {@link PersonAttributesService} on 5/24, and I 
 * provided a nice writeup here:  
 * https://wiki.jasig.org/display/SSP/Person+Attributes.  I also sent an email 
 * on 5/24 to alert everyone about the available code and the writeup.
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
