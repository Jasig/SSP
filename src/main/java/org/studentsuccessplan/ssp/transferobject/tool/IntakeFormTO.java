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

	private static final TransferObjectListFactory<PersonEducationLevelTO, PersonEducationLevel> PERSON_EDUCATION_LEVEL_FACTORY =
			TransferObjectListFactory.newFactory(PersonEducationLevelTO.class);

	private static final TransferObjectListFactory<PersonFundingSourceTO, PersonFundingSource> PERSON_FUNDING_SOURCE_FACTORY =
			TransferObjectListFactory.newFactory(PersonFundingSourceTO.class);

	private static final TransferObjectListFactory<PersonChallengeTO, PersonChallenge> PERSON_CHALLENGE_FACTORY =
			TransferObjectListFactory.newFactory(PersonChallengeTO.class);

	public IntakeFormTO() {
		// allowed
	}

	public IntakeFormTO(final IntakeForm model) {
		super();
		fromModel(model);
	}

	@Override
	public final void fromModel(final IntakeForm model) {
		if (model.getPerson() != null) {
			person = new PersonTO(model.getPerson());
		}

		if (model.getPerson().getDemographics() != null) {
			personDemographics = new PersonDemographicsTO(model.getPerson()
					.getDemographics());
		}

		if (model.getPerson().getEducationGoal() != null) {
			personEducationGoal = new PersonEducationGoalTO(model.getPerson()
					.getEducationGoal());
		}

		if (model.getPerson().getEducationPlan() != null) {
			personEducationPlan = new PersonEducationPlanTO(model.getPerson()
					.getEducationPlan());
		}

		if ((model.getPerson().getEducationLevels() != null)
				&& (model.getPerson().getEducationLevels().size() > 0)) {
			personEducationLevels = PERSON_EDUCATION_LEVEL_FACTORY
					.toTOList(model.getPerson().getEducationLevels());
		}

		if ((model.getPerson().getFundingSources() != null)
				&& (model.getPerson().getFundingSources().size() > 0)) {
			personFundingSources = PERSON_FUNDING_SOURCE_FACTORY.toTOList(model
					.getPerson().getFundingSources());
		}

		if ((model.getPerson().getChallenges() != null)
				&& (model.getPerson().getChallenges().size() > 0)) {
			personChallenges = PERSON_CHALLENGE_FACTORY.toTOList(model
					.getPerson().getChallenges());
		}
	}

	@Override
	public IntakeForm addToModel(final IntakeForm model) {
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
					PERSON_EDUCATION_LEVEL_FACTORY
							.toModelSet(getPersonEducationLevels()));
		}

		if ((getPersonFundingSources() != null)
				&& (getPersonFundingSources().size() > 0)) {
			model.getPerson().setFundingSources(
					PERSON_FUNDING_SOURCE_FACTORY
							.toModelSet(getPersonFundingSources()));
		}

		if ((getPersonChallenges() != null)
				&& (getPersonChallenges().size() > 0)) {
			model.getPerson().setChallenges(
					PERSON_CHALLENGE_FACTORY.toModelSet(getPersonChallenges()));
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

	public void setPerson(final PersonTO person) {
		this.person = person;
	}

	public PersonDemographicsTO getPersonDemographics() {
		return personDemographics;
	}

	public void setPersonDemographics(
			final PersonDemographicsTO personDemographics) {
		this.personDemographics = personDemographics;
	}

	public PersonEducationGoalTO getPersonEducationGoal() {
		return personEducationGoal;
	}

	public void setPersonEducationGoal(
			final PersonEducationGoalTO personEducationGoal) {
		this.personEducationGoal = personEducationGoal;
	}

	public List<PersonEducationLevelTO> getPersonEducationLevels() {
		return personEducationLevels;
	}

	public void setPersonEducationLevels(
			final List<PersonEducationLevelTO> personEducationLevels) {
		this.personEducationLevels = personEducationLevels;
	}

	public PersonEducationPlanTO getPersonEducationPlan() {
		return personEducationPlan;
	}

	public void setPersonEducationPlan(
			final PersonEducationPlanTO personEducationPlan) {
		this.personEducationPlan = personEducationPlan;
	}

	public List<PersonFundingSourceTO> getPersonFundingSources() {
		return personFundingSources;
	}

	public void setPersonFundingSources(
			final List<PersonFundingSourceTO> personFundingSources) {
		this.personFundingSources = personFundingSources;
	}

	public List<PersonChallengeTO> getPersonChallenges() {
		return personChallenges;
	}

	public void setPersonChallenges(
			final List<PersonChallengeTO> personChallenges) {
		this.personChallenges = personChallenges;
	}

	public Map<String, Object> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(final Map<String, Object> referenceData) {
		this.referenceData = referenceData;
	}
}
