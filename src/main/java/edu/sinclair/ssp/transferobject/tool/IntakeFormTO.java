package edu.sinclair.ssp.transferobject.tool;

import java.util.List;
import java.util.Map;

import edu.sinclair.ssp.factory.TransferObjectListFactory;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.model.PersonEducationLevel;
import edu.sinclair.ssp.model.PersonFundingSource;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.transferobject.PersonChallengeTO;
import edu.sinclair.ssp.transferobject.PersonDemographicsTO;
import edu.sinclair.ssp.transferobject.PersonEducationGoalTO;
import edu.sinclair.ssp.transferobject.PersonEducationLevelTO;
import edu.sinclair.ssp.transferobject.PersonEducationPlanTO;
import edu.sinclair.ssp.transferobject.PersonFundingSourceTO;
import edu.sinclair.ssp.transferobject.PersonTO;
import edu.sinclair.ssp.transferobject.TransferObject;

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

	public IntakeFormTO(IntakeForm model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(IntakeForm model) {
		if (model.getPerson() != null) {
			setPerson(new PersonTO(model.getPerson()));
		}

		if (model.getPersonDemographics() != null) {
			setPersonDemographics(new PersonDemographicsTO(
					model.getPersonDemographics()));
		}

		if (model.getPersonEducationGoal() != null) {
			setPersonEducationGoal(new PersonEducationGoalTO(
					model.getPersonEducationGoal()));
		}

		if (model.getPersonEducationPlan() != null) {
			setPersonEducationPlan(new PersonEducationPlanTO(
					model.getPersonEducationPlan()));
		}

		if (model.getPersonEducationLevels() != null
				&& model.getPersonEducationLevels().size() > 0) {
			setPersonEducationLevels(personEducationLevelToFactory
					.toTOList(model.getPersonEducationLevels()));
		}

		if (model.getPersonFundingSources() != null
				&& model.getPersonFundingSources().size() > 0) {
			setPersonFundingSources(personFundingToFactory.toTOList(model
					.getPersonFundingSources()));
		}

		if (model.getPersonChallenges() != null
				&& model.getPersonChallenges().size() > 0) {
			setPersonChallenges(personChallengeToFactory.toTOList(model
					.getPersonChallenges()));
		}
	}

	@Override
	public IntakeForm pushAttributesToModel(IntakeForm model) {
		if (getPerson() != null) {
			model.setPerson(getPerson().asModel());
		}

		if (getPersonDemographics() != null) {
			model.setPersonDemographics(getPersonDemographics().asModel());
		}

		if (getPersonEducationGoal() != null) {
			model.setPersonEducationGoal(getPersonEducationGoal().asModel());
		}

		if (getPersonEducationPlan() != null) {
			model.setPersonEducationPlan(getPersonEducationPlan().asModel());
		}

		if (getPersonEducationLevels() != null
				&& getPersonEducationLevels().size() > 0) {
			model.setPersonEducationLevels(personEducationLevelToFactory
					.toModelSet(getPersonEducationLevels()));
		}

		if (getPersonFundingSources() != null
				&& getPersonFundingSources().size() > 0) {
			model.setPersonFundingSources(personFundingToFactory
					.toModelSet(getPersonFundingSources()));
		}

		if (getPersonChallenges() != null && getPersonChallenges().size() > 0) {
			model.setPersonChallenges(personChallengeToFactory
					.toModelSet(getPersonChallenges()));
		}

		return model;
	}

	@Override
	public IntakeForm asModel() {
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
