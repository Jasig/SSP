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
	private PersonTOFactory personTOFactory;

	@Autowired
	private PersonDemographicsTOFactory personDemographicsTOFactory;

	@Autowired
	private PersonEducationGoalTOFactory personEducationGoalTOFactory;

	@Autowired
	private PersonEducationPlanTOFactory personEducationPlanTOFactory;

	@Autowired
	private PersonEducationLevelTOFactory personEducationLevelTOFactory;

	@Autowired
	private PersonFundingSourceTOFactory personFundingSourceTOFactory;

	@Autowired
	private PersonChallengeTOFactory personChallengeTOFactory;

	@Override
	public IntakeFormTO from(IntakeForm model) {
		return new IntakeFormTO(model);
	}

	@Override
	public IntakeForm from(IntakeFormTO tObject)
			throws ObjectNotFoundException {
		IntakeForm model = new IntakeForm();

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
				&& (tObject.getPersonEducationLevels().size() > 0)) {
			model.getPerson().setEducationLevels(
					personEducationLevelTOFactory.asSet(
							tObject.getPersonEducationLevels()));
		}

		if ((tObject.getPersonFundingSources() != null)
				&& (tObject.getPersonFundingSources().size() > 0)) {
			model.getPerson().setFundingSources(
					personFundingSourceTOFactory.asSet(
							tObject.getPersonFundingSources()));
		}

		if ((tObject.getPersonChallenges() != null)
				&& (tObject.getPersonChallenges().size() > 0)) {
			model.getPerson().setChallenges(
					personChallengeTOFactory.asSet(
							tObject.getPersonChallenges()));
		}

		return model;
	}
}
