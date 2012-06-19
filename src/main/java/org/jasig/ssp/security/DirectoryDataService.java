package org.jasig.ssp.security;

import org.jasig.ssp.model.Person;

public interface DirectoryDataService {

	String getProperty(String propertyName, Person user);

	String getProperty(String propertyName, String userId);

	String getFirstNameForUserId(String userId);

	String getLastNameForUserId(String userId);

	String getPrimaryEmailAddressForUserId(String userId);

	String getPhoneForUserId(String userId);
}
