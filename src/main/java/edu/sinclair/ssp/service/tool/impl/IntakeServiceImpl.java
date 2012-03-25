package edu.sinclair.ssp.service.tool.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.tool.IntakeService;

@Service
public class IntakeServiceImpl implements IntakeService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(IntakeService.class);

	@Autowired
	private PersonService personService;

	/**
	 * Copy non-persisted, untrusted model values for a complete Person data
	 * tree, to persisted instances.
	 */
	@Override
	public boolean save(IntakeForm form) throws ObjectNotFoundException {
		if (form.getPerson() == null) {
			throw new ObjectNotFoundException("Missing person identifier.");
		}

		// Load current Person from storage
		Person pPerson = personService.get(form.getPerson().getId());

		// Walk through values copying mutable data into persistent version.
		/*
		 * TODO Somehow lookup and send Hibernate-loaded instances that are sent
		 * as only IDs or for instances that could create a dependency loop if
		 * we walked them.
		 */
		pPerson.overwriteWithCollections(form);

		// Save changes to persistent storage.
		personService.save(pPerson);

		return true;
	}

	/**
	 * Load the specified Person.
	 * 
	 * Be careful when walking the tree to avoid performance issues that can
	 * arise if eager fetching from the database layer is not used
	 * appropriately.
	 */
	@Override
	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException {
		IntakeForm form = new IntakeForm();

		Person person = personService.get(studentId);
		form.setPerson(person);

		return form;
	}
}
