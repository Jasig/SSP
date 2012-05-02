package org.jasig.ssp.factory.tool.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.factory.PersonChallengeTOFactory;
import org.jasig.ssp.factory.PersonDemographicsTOFactory;
import org.jasig.ssp.factory.PersonEducationGoalTOFactory;
import org.jasig.ssp.factory.PersonEducationLevelTOFactory;
import org.jasig.ssp.factory.PersonEducationPlanTOFactory;
import org.jasig.ssp.factory.PersonFundingSourceTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.tool.IntakeFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.jasig.ssp.util.SetOps;
import org.jasig.ssp.web.api.validation.ValidationException;

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
			throws ObjectNotFoundException, ValidationException {
		final IntakeForm model = new IntakeForm();

		if (tObject.getPerson() != null) {
			model.setPerson(personTOFactory.from(tObject.getPerson()));
		} else {
			throw new ValidationException("Person is Required");
		}

		if (tObject.getPersonDemographics() != null) {
			model.getPerson()
					.setDemographics(
							personDemographicsTOFactory.from(tObject
									.getPersonDemographics()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getDemographics() != null)) {
				model.getPerson().getDemographics()
						.setObjectStatus(ObjectStatus.DELETED);
			}

		}

		if (tObject.getPersonEducationGoal() != null) {
			model.getPerson().setEducationGoal(
					personEducationGoalTOFactory.from(tObject
							.getPersonEducationGoal()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationPlan() != null)) {
				model.getPerson().getEducationPlan()
						.setObjectStatus(ObjectStatus.DELETED);
			}
		}

		if (tObject.getPersonEducationPlan() != null) {
			model.getPerson().setEducationPlan(
					personEducationPlanTOFactory.from(tObject
							.getPersonEducationPlan()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationPlan() != null)) {
				model.getPerson().getEducationPlan()
						.setObjectStatus(ObjectStatus.DELETED);
			}
		}

		if ((tObject.getPersonEducationLevels() != null)
				&& !tObject.getPersonEducationLevels().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getEducationLevels(),
					personEducationLevelTOFactory.asSet(
							tObject.getPersonEducationLevels()));
		} else {
			// clearing education levels
			SetOps.softDeleteSetItems(model.getPerson().getEducationLevels());
		}

		if ((tObject.getPersonFundingSources() != null)
				&& tObject.getPersonFundingSources().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getFundingSources(),
					personFundingSourceTOFactory.asSet(
							tObject.getPersonFundingSources()));
		} else {
			// clearing fundingSources
			SetOps.softDeleteSetItems(model.getPerson().getFundingSources());
		}

		if ((tObject.getPersonChallenges() != null)
				&& !tObject.getPersonChallenges().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getChallenges(),
					personChallengeTOFactory.asSet(
							tObject.getPersonChallenges()));
		} else {
			SetOps.softDeleteSetItems(model.getPerson().getChallenges());
		}

		return model;
	}
}
