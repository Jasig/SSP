package edu.sinclair.ssp.service.tool;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;

@Service
public class IntakeService {

	//private static final Logger logger = LoggerFactory.getLogger(StudentIntakeService.class);

	@Autowired
	private PersonService personService;
	
	public boolean save(IntakeForm form){
		return false;
	}
	
	public IntakeForm loadForPerson(UUID studentId) throws ObjectNotFoundException {
		IntakeForm form = new IntakeForm();
		
		form.setPerson(personService.get(studentId));
		/*form.setPersonDemographics(new PersonDemographics());
		form.setPersonEducationGoal(new PersonEducationGoal());
		form.setPersonEducationLevels(new ArrayList<PersonEducationLevel>());
		form.setPersonEducationPlan(new PersonEducationPlan());
		form.setPersonFundingSources(new ArrayList<PersonFunding>());
		form.setPersonChallenges(new ArrayList<PersonChallenge>());
		*/
		return form;
	}
}
