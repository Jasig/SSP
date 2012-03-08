package edu.sinclair.ssp.service;

import java.util.UUID;

import edu.sinclair.ssp.model.Person;

public interface PersonService {

	Person personFromId(UUID id);
	
	Person personFromUsername(String username);
}
