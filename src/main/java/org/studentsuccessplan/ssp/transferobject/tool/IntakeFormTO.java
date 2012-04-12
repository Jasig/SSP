package org.studentsuccessplan.ssp.transferobject.tool;

import java.util.List;
import java.util.Map;

import org.studentsuccessplan.ssp.factory.TransferObjectListFactory;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.transferobject.PersonChallengeTO;
import org.studentsuccessplan.ssp.transferobject.PersonDemographicsTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationGoalTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationLevelTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationPlanTO;
import org.studentsuccessplan.ssp.transferobject.PersonFundingSourceTO;
import org.studentsuccessplan.ssp.transferobject.PersonTO;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public class IntakeFormTO implements TransferObject<IntakeForm> {

	private PersonTO person;

	private PersonDemographicsTO personDemographics;

	private PersonEducationGoalTO personEducationGoal;

	private PersonEducationPlanTO personEducationPlan;

	private List<PersonEducationLevelTO> personEducationLevels;

	private List<PersonFundingSourceTO> personFundingSources;

	private List<PersonChallengeTO> personChallenges;

	private Map<String, Object> referenceData;

	private TransferObjectListFactory<PersonEducationLevelTO, PersonEducationLevel> personEducationLevelToFactory = new TransferObjectListFactory<PersonEducationLevelTO, PersonEducationLevel>(
			PersonEducationLevelTO.class);

	private TransferObjectListFactory<PersonFundingSourceTO, PersonFundingSource> personFundingToFactory = new TransferObjectListFactory<PersonFundingSourceTO, PersonFundingSource>(
			PersonFundingSourceTO.class);

	private TransferObjectListFactory<PersonChallengeTO, PersonChallenge> personChallengeToFactory = new TransferObjectListFactory<PersonChallengeTO, PersonChallenge>(
			PersonChallengeTO.class);

	public IntakeFormTO() {
	}

	public IntakeFormTO(IntakeForm model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(IntakeForm model) {
		if (model.getPerson() != null) {
			setPerson(new PersonTO(model.getPerson()));
		}

		if (model.getPerson().getDemographics() != null) {
			setPersonDemographics(new PersonDemographicsTO(model.getPerson()
					.getDemographics()));
		}

		if (model.getPerson().getEducationGoal() != null) {
			setPersonEducationGoal(new PersonEducationGoalTO(model.getPerson()
					.getEducationGoal()));
		}

		if (model.getPerson().getEducationPlan() != null) {
			setPersonEducationPlan(new PersonEducationPlanTO(model.getPerson()
					.getEducationPlan()));
		}

		if ((model.getPerson().getEducationLevels() != null)
				&& (model.getPerson().getEducationLevels().size() > 0)) {
			setPersonEducationLevels(personEducationLevelToFactory
					.toTOList(model.getPerson().getEducationLevels()));
		}

		if ((model.getPerson().getFundingSources() != null)
				&& (model.getPerson().getFundingSources().size() > 0)) {
			setPersonFundingSources(personFundingToFactory.toTOList(model
					.getPerson().getFundingSources()));
		}

		if ((model.getPerson().getChallenges() != null)
				&& (model.getPerson().getChallenges().size() > 0)) {
			setPersonChallenges(personChallengeToFactory.toTOList(model
					.getPerson().getChallenges()));
		}
	}

	@Override
	public IntakeForm addToModel(IntakeForm model) {
		if (getPerson() != null) {
			model.setPerson(getPerson().asModel());
		}

		if (getPersonDemographics() != null) {
			model.getPerson()
					.setDemographics(getPersonDemographics().asModel());
		}

		if (getPersonEducationGoal() != null) {
			model.getPerson().setEducationGoal(
					getPersonEducationGoal().asModel());
		}

		if (getPersonEducationPlan() != null) {
			model.getPerson().setEducationPlan(
					getPersonEducationPlan().asModel());
		}

		if ((getPersonEducationLevels() != null)
				&& (getPersonEducationLevels().size() > 0)) {
			model.getPerson().setEducationLevels(
					personEducationLevelToFactory
							.toModelSet(getPersonEducationLevels()));
		}

		if ((getPersonFundingSources() != null)
				&& (getPersonFundingSources().size() > 0)) {
			model.getPerson().setFundingSources(
					personFundingToFactory
							.toModelSet(getPersonFundingSources()));
		}

		if ((getPersonChallenges() != null)
				&& (getPersonChallenges().size() > 0)) {
			model.getPerson().setChallenges(
					personChallengeToFactory.toModelSet(getPersonChallenges()));
		}

		return model;
	}

	@Override
	public IntakeForm asModel() {
		return addToModel(new IntakeForm());
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

	public List<PersonFundingSourceTO> getPersonFundingSources() {
		return personFundingSources;
	}

	public void setPersonFundingSources(
			List<PersonFundingSourceTO> personFundingSources) {
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
