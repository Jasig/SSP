package org.studentsuccessplan.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.studentsuccessplan.ssp.dao.PersonEducationPlanDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationPlan;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonEducationPlanService;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
public class PersonEducationPlanServiceImpl implements
		PersonEducationPlanService {

	@Autowired
	private PersonEducationPlanDao dao;

	@Autowired
	private StudentStatusService studentStatusService;

	@Override
	public List<PersonEducationPlan> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonEducationPlan get(UUID id) throws ObjectNotFoundException {
		PersonEducationPlan obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "PersonEducationPlan");
		}
		return obj;
	}

	@Override
	public PersonEducationPlan forPerson(Person person) {
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationPlan create(PersonEducationPlan obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationPlan save(PersonEducationPlan obj)
			throws ObjectNotFoundException {
		PersonEducationPlan current = get(obj.getId());

		current.setObjectStatus(obj.getObjectStatus());
		if (obj.getStudentStatus() != null) {
			current.setStudentStatus(studentStatusService.get(obj
					.getStudentStatus().getId()));
		}
		current.setNewOrientationComplete(obj.isNewOrientationComplete());
		current.setRegisteredForClasses(obj.isRegisteredForClasses());
		current.setCollegeDegreeForParents(obj.isCollegeDegreeForParents());
		current.setSpecialNeeds(obj.isSpecialNeeds());
		current.setGradeTypicallyEarned(obj.getGradeTypicallyEarned());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonEducationPlan current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
