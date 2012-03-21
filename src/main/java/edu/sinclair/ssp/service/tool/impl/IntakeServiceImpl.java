package edu.sinclair.ssp.service.tool.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonDemographicsService;
import edu.sinclair.ssp.service.PersonEducationGoalService;
import edu.sinclair.ssp.service.PersonEducationPlanService;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.tool.IntakeService;

@Service
public class IntakeServiceImpl implements IntakeService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(IntakeService.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonDemographicsService personDemographicsService;

	@Autowired
	private PersonEducationGoalService personEducationGoalService;

	@Autowired
	private PersonEducationPlanService personEducationPlanService;

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
		Person fPerson = form.getPerson();
		pPerson.setAddressLine1(fPerson.getAddressLine1());
		pPerson.setAddressLine2(fPerson.getAddressLine2());
		pPerson.setBirthDate(fPerson.getBirthDate());
		pPerson.setCellPhone(fPerson.getCellPhone());
		pPerson.setCity(fPerson.getCity());

		// No reason for IntakeForm to be able to mark a user
		// pPerson.setEnabled(fPerson.isEnabled());

		pPerson.setFirstName(fPerson.getFirstName());
		pPerson.setHomePhone(fPerson.getHomePhone());
		pPerson.setLastName(fPerson.getLastName());
		pPerson.setMiddleInitial(fPerson.getMiddleInitial());
		pPerson.setPhotoUrl(fPerson.getPhotoUrl());
		pPerson.setPrimaryEmailAddress(fPerson.getPrimaryEmailAddress());
		pPerson.setSchoolId(fPerson.getSchoolId());
		pPerson.setSecondaryEmailAddress(fPerson.getSecondaryEmailAddress());
		pPerson.setState(fPerson.getState());
		pPerson.setUsername(fPerson.getUsername());
		pPerson.setWorkPhone(fPerson.getWorkPhone());
		pPerson.setZipCode(fPerson.getZipCode());

		pPerson.setChallenges(fPerson.getChallenges());
		pPerson.setDemographics(fPerson.getDemographics());
		pPerson.setEducationGoal(fPerson.getEducationGoal());
		pPerson.setEducationLevels(fPerson.getEducationLevels());
		pPerson.setEducationPlan(fPerson.getEducationPlan());
		pPerson.setFundingSources(fPerson.getFundingSources());

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
