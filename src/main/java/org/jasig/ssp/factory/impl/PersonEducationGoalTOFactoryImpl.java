package org.jasig.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.PersonEducationGoalDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonEducationGoalTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.EducationGoalService;
import org.jasig.ssp.transferobject.PersonEducationGoalTO;

@Service
@Transactional(readOnly = true)
public class PersonEducationGoalTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonEducationGoalTO, PersonEducationGoal>
		implements PersonEducationGoalTOFactory {

	public PersonEducationGoalTOFactoryImpl() {
		super(PersonEducationGoalTO.class, PersonEducationGoal.class);
	}

	@Autowired
	private transient PersonEducationGoalDao dao;

	@Autowired
	private transient EducationGoalService educationGoalService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonEducationGoalDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationGoal from(final PersonEducationGoalTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			Person person = personService.get(tObject.getPersonId());
			PersonEducationGoal unsetModel = person.getEducationGoal();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonEducationGoal model = super.from(tObject);

		model.setHowSureAboutMajor(tObject.getHowSureAboutMajor());
		model.setDescription(tObject.getDescription());
		model.setPlannedOccupation(tObject.getPlannedOccupation());
		model.setEducationGoal((tObject.getEducationGoalId() == null) ? null :
				educationGoalService.get(tObject.getEducationGoalId()));

		return model;
	}
}
