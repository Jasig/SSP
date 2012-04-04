package edu.sinclair.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.dao.PersonEducationGoalDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationGoal;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonEducationGoalService;
import edu.sinclair.ssp.service.reference.EducationGoalService;

@Service
public class PersonEducationGoalServiceImpl implements
		PersonEducationGoalService {

	@Autowired
	private PersonEducationGoalDao dao;

	@Autowired
	private EducationGoalService educationGoalService;

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database filtered by the supplied status.
	 */
	@Override
	public List<PersonEducationGoal> getAll(ObjectStatus status,
			int firstResult, int maxResults, String sortExpression) {
		return dao.getAll(status, firstResult, maxResults, sortExpression);
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
