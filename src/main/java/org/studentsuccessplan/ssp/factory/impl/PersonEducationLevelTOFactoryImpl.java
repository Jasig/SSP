package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonEducationLevelDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonEducationLevelTOFactory;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.EducationLevelService;
import org.studentsuccessplan.ssp.transferobject.PersonEducationLevelTO;

@Service
@Transactional(readOnly = true)
public class PersonEducationLevelTOFactoryImpl
		extends
		AbstractAuditableTOFactory<PersonEducationLevelTO, PersonEducationLevel>
		implements PersonEducationLevelTOFactory {

	public PersonEducationLevelTOFactoryImpl() {
		super(PersonEducationLevelTO.class, PersonEducationLevel.class);
	}

	@Autowired
	private PersonEducationLevelDao dao;

	@Autowired
	private EducationLevelService educationLevelService;

	@Autowired
	private PersonService personService;

	@Override
	protected PersonEducationLevelDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationLevel from(PersonEducationLevelTO tObject)
			throws ObjectNotFoundException {
		PersonEducationLevel model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		if (tObject.getEducationLevelId() != null) {
			model.setEducationLevel(educationLevelService.get(tObject
					.getEducationLevelId()));
		}

		model.setGraduatedYear(tObject.getGraduatedYear());
		model.setHighestGradeCompleted(tObject.getHighestGradeCompleted());
		model.setLastYearAttended(tObject.getLastYearAttended());

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		model.setSchoolName(tObject.getSchoolName());

		return model;
	}
}
