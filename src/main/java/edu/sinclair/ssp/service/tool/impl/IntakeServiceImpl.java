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
	 * Copy non-persisted model values, over to persisted values.
	 */
	@Override
	public boolean save(IntakeForm form) throws ObjectNotFoundException {
		if (form.getPerson() == null) {
			throw new ObjectNotFoundException("Missing person identifier.");
		}

		// Load current Person from storage
		Person pPerson = personService.get(form.getPerson().getId());
		
		// Walk through values copying mutable data into persistent version.
		pPerson.overwriteWithCollections(form);

		// Save changes to persistent storage.
		personService.save(pPerson);

		return true;
	}

	@Override
	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException {
		IntakeForm form = new IntakeForm();

		Person person = personService.get(studentId);
		form.setPerson(person);

		form.setPersonDemographics(person.getDemographics());
		form.setPersonEducationGoal(person.getEducationGoal());
		form.setPersonEducationPlan(person.getEducationPlan());
		form.setPersonEducationLevels(person.getEducationLevels());
		form.setPersonFundingSources(person.getFundingSources());
		form.setPersonChallenges(person.getChallenges());

		return form;
	}
}
