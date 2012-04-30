package org.studentsuccessplan.ssp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studentsuccessplan.ssp.dao.PersonEducationGoalDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationGoal;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonEducationGoalService;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
public class PersonEducationGoalServiceImpl implements
		PersonEducationGoalService {

	@Autowired
	private PersonEducationGoalDao dao;

	@Autowired
	private EducationGoalService educationGoalService;

	@Override
	public PagingWrapper<PersonEducationGoal> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonEducationGoal get(UUID id) throws ObjectNotFoundException {
		PersonEducationGoal obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "PersonEducationGoal");
		}
		return obj;
	}

	@Override
	public PersonEducationGoal forPerson(Person person) {
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationGoal create(PersonEducationGoal obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationGoal save(PersonEducationGoal obj)
			throws ObjectNotFoundException {
		PersonEducationGoal current = get(obj.getId());

		current.setObjectStatus(obj.getObjectStatus());
		current.setDescription(obj.getDescription());
		current.setPlannedOccupation(obj.getPlannedOccupation());
		current.setHowSureAboutMajor(obj.getHowSureAboutMajor());

		if (obj.getEducationGoal() != null
				&& obj.getEducationGoal().getId() != null) {
			current.setEducationGoal(educationGoalService.get(obj
					.getEducationGoal().getId()));
		}

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonEducationGoal current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
