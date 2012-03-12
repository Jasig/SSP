package edu.sinclair.ssp.transferobject.tool;

import java.util.List;
import java.util.Map;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;
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

	public IntakeFormTO(IntakeForm model){
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(IntakeForm model) {
		if(model.getPerson()!=null){
			setPerson(new PersonTO(model.getPerson()));
		}
		if(model.getPersonDemographics()!=null){
			setPersonDemographics(new PersonDemographicsTO(model.getPersonDemographics()));
		}
	}

	@Override
	public IntakeForm pushAttributesToModel(IntakeForm model) {
		if(getPerson()!=null){
			model.setPerson(getPerson().pushAttributesToModel(new Person()));
		}
		if(getPersonDemographics()!=null){
			model.setPersonDemographics(
					getPersonDemographics().pushAttributesToModel(new PersonDemographics()));
		}
		return model;
	}

	@Override
	public IntakeForm asModel(){
		return pushAttributesToModel(new IntakeForm());
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
