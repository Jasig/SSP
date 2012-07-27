package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonEducationLevelDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonEducationLevelTOFactory;
import org.jasig.ssp.model.PersonEducationLevel;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.jasig.ssp.transferobject.PersonEducationLevelTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private transient PersonEducationLevelDao dao;

	@Autowired
	private transient EducationLevelService educationLevelService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonEducationLevelDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationLevel from(final PersonEducationLevelTO tObject)
			throws ObjectNotFoundException {
		final PersonEducationLevel model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		model.setEducationLevel(tObject.getEducationLevelId() == null ? null
				: educationLevelService.get(tObject.getEducationLevelId()));

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
