package org.studentsuccessplan.ssp.factory.tool.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.factory.PersonChallengeTOFactory;
import org.studentsuccessplan.ssp.factory.PersonDemographicsTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationGoalTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationLevelTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationPlanTOFactory;
import org.studentsuccessplan.ssp.factory.PersonFundingSourceTOFactory;
import org.studentsuccessplan.ssp.factory.PersonTOFactory;
import org.studentsuccessplan.ssp.factory.tool.IntakeFormTOFactory;
import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.tool.IntakeFormTO;

@Service
@Transactional(readOnly = true)
public class IntakeFormTOFactoryImpl implements IntakeFormTOFactory {

	@Autowired
	private transient PersonTOFactory personTOFactory;

	@Autowired
	private transient PersonDemographicsTOFactory personDemographicsTOFactory;

	@Autowired
	private transient PersonEducationGoalTOFactory personEducationGoalTOFactory;

	@Autowired
	private transient PersonEducationPlanTOFactory personEducationPlanTOFactory;

	@Autowired
	private transient PersonEducationLevelTOFactory personEducationLevelTOFactory;

	@Autowired
	private transient PersonFundingSourceTOFactory personFundingSourceTOFactory;

	@Autowired
	private transient PersonChallengeTOFactory personChallengeTOFactory;

	@Override
	public IntakeFormTO from(final IntakeForm model) {
		return new IntakeFormTO(model);
	}

	@Override
	public IntakeForm from(final IntakeFormTO tObject)
			throws ObjectNotFoundException {
		final IntakeForm model = new IntakeForm();

		if (tObject.getPerson() != null) {
			model.setPerson(personTOFactory.from(tObject.getPerson()));
		}

		if (tObject.getPersonDemographics() != null) {
			model.getPerson()
					.setDemographics(
							personDemographicsTOFactory.from(tObject
									.getPersonDemographics()));
		}

		if (tObject.getPersonEducationGoal() != null) {
			model.getPerson().setEducationGoal(
					personEducationGoalTOFactory.from(tObject
							.getPersonEducationGoal()));
		}

		if (tObject.getPersonEducationPlan() != null) {
			model.getPerson().setEducationPlan(
					personEducationPlanTOFactory.from(tObject
							.getPersonEducationPlan()));
		}

		if ((tObject.getPersonEducationLevels() != null)
				&& !tObject.getPersonEducationLevels().isEmpty()) {
			model.getPerson().getEducationLevels()
					.addAll(personEducationLevelTOFactory.asSet(
							tObject.getPersonEducationLevels()));
		}

		if ((tObject.getPersonFundingSources() != null)
				&& tObject.getPersonFundingSources().isEmpty()) {
			model.getPerson().getFundingSources().addAll(
					personFundingSourceTOFactory.asSet(
							tObject.getPersonFundingSources()));
		}

		if ((tObject.getPersonChallenges() != null)
				&& !tObject.getPersonChallenges().isEmpty()) {
			model.getPerson().getChallenges().addAll(
					personChallengeTOFactory.asSet(
							tObject.getPersonChallenges()));
		}

		return model;
	}
}
