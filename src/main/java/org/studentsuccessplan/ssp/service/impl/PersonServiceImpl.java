package org.studentsuccessplan.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.tool.IntakeService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao dao;

	@Override
	public List<Person> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	/**
	 * Retrieves the specified Person.
	 * 
	 * @param id
	 *            Required identifier for the Person to retrieve. Can not be
	 *            null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	@Override
	public Person get(UUID id) throws ObjectNotFoundException {
		Person obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Person");
		}

		return obj;
	}

	@Override
	public Person personFromUsername(String username)
			throws ObjectNotFoundException {
		Person obj = dao.fromUsername(username);
		if (null == obj) {
			throw new ObjectNotFoundException(
					"Could not find person with username: " + username);
		}
		return obj;
	}

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 */
	@Override
	public Person create(Person obj) {
		return dao.save(obj);
	}

	/**
	 * @param obj
	 *            Model instance to save
	 * @see IntakeService
	 */
	@Override
	public Person save(Person obj) throws ObjectNotFoundException {
		return dao.save(obj);
	}

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#DELETED}.
	 */
	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Person current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(PersonDao dao) {
		this.dao = dao;
	}

	@Override
	public Person personFromUserId(String userId)
			throws ObjectNotFoundException {
		return dao.fromUserId(userId);
	}

	@Override
	public List<Person> peopleFromListOfIds(List<UUID> personIds,
			SortingAndPaging sAndP) {
		return dao.getPeopleInList(personIds, sAndP);
	}
}
