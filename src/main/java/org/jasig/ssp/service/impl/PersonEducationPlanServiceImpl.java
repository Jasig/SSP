package org.jasig.ssp.service.impl;

import java.util.UUID;

import org.jasig.ssp.dao.PersonEducationPlanDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEducationPlanService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonEducationPlanServiceImpl implements
		PersonEducationPlanService {

	@Autowired
	private PersonEducationPlanDao dao;

	@Autowired
	private StudentStatusService studentStatusService;

	@Override
	public PagingWrapper<PersonEducationPlan> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonEducationPlan get(UUID id) throws ObjectNotFoundException {
		return dao.get(id);
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
