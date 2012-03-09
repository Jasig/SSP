package edu.sinclair.ssp.factory.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.PersonTOFactory;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.transferobject.PersonTO;

@Service
public class PersonTOFactoryImpl 
			extends AbstractTransferObjectFactory<PersonTO, Person>
			implements PersonTOFactory{

	@Override
	public PersonTO toTO(Person from) {
		PersonTO to = new PersonTO();
		
		to.fromModel(from);
		
		return to;
	}

	@Override
	public Person toModel(PersonTO from) {
		Person model = new Person();
		
		from.addToModel(model);
		
		return model;
	}

}
