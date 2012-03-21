package edu.sinclair.ssp.service.tool.impl;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean save(IntakeForm form) throws ObjectNotFoundException {
		return false;
	}

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException {
		IntakeForm form = new IntakeForm();

		form.setPerson(personService.get(studentId));

		form.setPersonDemographics(personDemographicsService.forPerson(form
				.getPerson()));
		form.setPersonEducationGoal(personEducationGoalService.forPerson(form
				.getPerson()));
		form.setPersonEducationPlan(personEducationPlanService.forPerson(form
				.getPerson()));
		form.setPersonEducationLevels(new ArrayList<PersonEducationLevel>());
		form.setPersonFundingSources(new ArrayList<PersonFundingSource>());
		form.setPersonChallenges(new ArrayList<PersonChallenge>());

		return form;
	}
}
