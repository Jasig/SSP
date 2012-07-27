package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonEducationPlanDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonEducationPlanTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.transferobject.PersonEducationPlanTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonEducationPlan transfer object factory implementation
 */
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
			final Person person = personService.get(tObject.getPersonId());
			final PersonEducationPlan unsetModel = person.getEducationPlan();
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

		model.setStudentStatus(tObject.getStudentStatusId() == null ? null
				: studentStatusService.get(tObject.getStudentStatusId()));

		return model;
	}
}
