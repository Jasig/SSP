package edu.sinclair.ssp.service.tool.impl;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.model.PersonEducationLevel;
import edu.sinclair.ssp.model.PersonFundingSource;
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
	public boolean save(IntakeForm form) throws ObjectNotFoundException {
		return false;
	}

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
