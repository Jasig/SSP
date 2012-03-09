package edu.sinclair.ssp.service;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

public interface PersonService extends AuditableCrudService<Person>{
	
	public List<Person> getAll(ObjectStatus status);
	
	public Person get(UUID id) throws ObjectNotFoundException;
	
	public Person personFromUsername(String username) throws ObjectNotFoundException;
	
	public Person create(Person obj);
	
	public Person save(Person obj) throws ObjectNotFoundException;
	
	public void delete(UUID id) throws ObjectNotFoundException;
}
