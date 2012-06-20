package org.jasig.ssp.security;

import org.jasig.ssp.model.Person;

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
public interface DirectoryDataService {

	String getProperty(String propertyName, Person user);

	String getProperty(String propertyName, String userId);

	String getFirstNameForUserId(String userId);

	String getLastNameForUserId(String userId);

	String getPrimaryEmailAddressForUserId(String userId);

	String getPhoneForUserId(String userId);
}
