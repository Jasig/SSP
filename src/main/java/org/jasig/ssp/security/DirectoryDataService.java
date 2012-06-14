package org.jasig.ssp.security;

import org.jasig.ssp.model.Person;

public interface DirectoryDataService {

	String getProperty(String propertyName, Person user);

	String getProperty(String propertyName, String userId);

}
