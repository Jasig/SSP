package edu.sinclair.ssp.transferobject.tool;

import java.util.List;
import java.util.Map;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.transferobject.PersonChallengeTO;
import edu.sinclair.ssp.transferobject.PersonDemographicsTO;
import edu.sinclair.ssp.transferobject.PersonEducationGoalTO;
import edu.sinclair.ssp.transferobject.PersonEducationLevelTO;
import edu.sinclair.ssp.transferobject.PersonEducationPlanTO;
import edu.sinclair.ssp.transferobject.PersonFundingTO;
import edu.sinclair.ssp.transferobject.PersonTO;
import edu.sinclair.ssp.transferobject.TransferObject;

public class IntakeFormTO implements TransferObject<IntakeForm>{

	private PersonTO person;
	
	private PersonDemographicsTO personDemographics;
	
	private PersonEducationGoalTO personEducationGoal;
	
	private List<PersonEducationLevelTO> personEducationLevels;
	
	private PersonEducationPlanTO personEducationPlan;
	
	private List<PersonFundingTO> personFundingSources;
	
	private List<PersonChallengeTO> personChallenges;
	
	private Map<String, Object> referenceData;


	@Override
	public void fromModel(IntakeForm model) {
		if(model.getPerson()!=null){
			PersonTO person = new PersonTO();
			person.fromModel(model.getPerson());
			setPerson(person);
		}
	}

	@Override
	public void addToModel(IntakeForm model) {
		if(getPerson()!=null){
			Person person = new Person();
			getPerson().addToModel(person);
			model.setPerson(person);
		}
	}
	
	public PersonTO getPerson() {
		return person;
	}

	public void setPerson(PersonTO person) {
		this.person = person;
	}

	public PersonDemographicsTO getPersonDemographics() {
		return personDemographics;
	}

	public void setPersonDemographics(PersonDemographicsTO personDemographics) {
		this.personDemographics = personDemographics;
	}

	public PersonEducationGoalTO getPersonEducationGoal() {
		return personEducationGoal;
	}

	public void setPersonEducationGoal(PersonEducationGoalTO personEducationGoal) {
		this.personEducationGoal = personEducationGoal;
	}

	public List<PersonEducationLevelTO> getPersonEducationLevels() {
		return personEducationLevels;
	}

	public void setPersonEducationLevels(
			List<PersonEducationLevelTO> personEducationLevels) {
		this.personEducationLevels = personEducationLevels;
	}

	public PersonEducationPlanTO getPersonEducationPlan() {
		return personEducationPlan;
	}

	public void setPersonEducationPlan(PersonEducationPlanTO personEducationPlan) {
		this.personEducationPlan = personEducationPlan;
	}

	public List<PersonFundingTO> getPersonFundingSources() {
		return personFundingSources;
	}

	public void setPersonFundingSources(List<PersonFundingTO> personFundingSources) {
		this.personFundingSources = personFundingSources;
	}

	public List<PersonChallengeTO> getPersonChallenges() {
		return personChallenges;
	}

	public void setPersonChallenges(List<PersonChallengeTO> personChallenges) {
		this.personChallenges = personChallenges;
	}

	public Map<String, Object> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(Map<String, Object> referenceData) {
		this.referenceData = referenceData;
	}


	
}
