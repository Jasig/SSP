package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonEducationPlanDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationPlanTOFactory;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationPlan;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;
import org.studentsuccessplan.ssp.transferobject.PersonEducationPlanTO;

@Service
@Transactional(readOnly = true)
public class PersonEducationPlanTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonEducationPlanTO, PersonEducationPlan>
		implements PersonEducationPlanTOFactory {

	public PersonEducationPlanTOFactoryImpl() {
		super(PersonEducationPlanTO.class, PersonEducationPlan.class);
	}

	@Autowired
	private transient PersonEducationPlanDao dao;

	@Autowired
	private transient StudentStatusService studentStatusService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonEducationPlanDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationPlan from(final PersonEducationPlanTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			Person person = personService.get(tObject.getPersonId());
			PersonEducationPlan unsetModel = person.getEducationPlan();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonEducationPlan model = super.from(tObject);

		model.setNewOrientationComplete(tObject.isNewOrientationComplete());
		model.setRegisteredForClasses(tObject.isRegisteredForClasses());
		model.setCollegeDegreeForParents(tObject.isCollegeDegreeForParents());
		model.setSpecialNeeds(tObject.isSpecialNeeds());
		model.setGradeTypicallyEarned(tObject.getGradeTypicallyEarned());

		model.setStudentStatus((tObject.getStudentStatusId() == null) ? null :
				studentStatusService.get(tObject.getStudentStatusId()));

		return model;
	}
}
