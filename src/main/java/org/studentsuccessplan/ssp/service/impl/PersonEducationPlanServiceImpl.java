package edu.sinclair.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.dao.PersonEducationPlanDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationPlan;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonEducationPlanService;
import edu.sinclair.ssp.service.reference.StudentStatusService;

@Service
public class PersonEducationPlanServiceImpl implements
		PersonEducationPlanService {

	@Autowired
	private PersonEducationPlanDao dao;

	@Autowired
	private StudentStatusService studentStatusService;

	@Override
	public List<PersonEducationPlan> getAll(ObjectStatus status,
			Integer firstResult, Integer maxResults, String sort,
			String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
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
