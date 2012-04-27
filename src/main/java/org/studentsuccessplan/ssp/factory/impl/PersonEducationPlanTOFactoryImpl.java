package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonEducationPlanDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationPlanTOFactory;
import org.studentsuccessplan.ssp.model.PersonEducationPlan;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
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
	private PersonEducationPlanDao dao;

	@Autowired
	private StudentStatusService studentStatusService;

	@Override
	protected PersonEducationPlanDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationPlan from(PersonEducationPlanTO tObject)
			throws ObjectNotFoundException {
		PersonEducationPlan model = super.from(tObject);

		model.setNewOrientationComplete(tObject.isNewOrientationComplete());
		model.setRegisteredForClasses(tObject.isRegisteredForClasses());
		model.setCollegeDegreeForParents(tObject.isCollegeDegreeForParents());
		model.setSpecialNeeds(tObject.isSpecialNeeds());
		model.setGradeTypicallyEarned(tObject.getGradeTypicallyEarned());

		if (tObject.getStudentStatusId() != null) {
			model.setStudentStatus(studentStatusService.get(tObject
					.getStudentStatusId()));
		}

		return model;
	}
}
