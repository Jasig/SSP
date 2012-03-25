package edu.sinclair.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.PersonDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.tool.IntakeService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonDao dao;

	/**
	 * Retrieve every Person instance in the database filtered by the supplied
	 * status.
	 * 
	 * @param status
	 *            Filter by this status, usually null or
	 *            {@link ObjectStatus#DELETED}.
	 * @return List of all people in the database filtered by the supplied
	 *         status.
	 */
	@Override
	public List<Person> getAll(ObjectStatus status) {
		return dao.getAll(status);
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

	@Override
	public Person create(Person obj) {
		return dao.save(obj);
	}

	/**
	 * Updates values of direct Person properties, but not any associated
	 * children or collections.
	 * 
	 * WARNING: Copies system-only (based on business logic rules) properties,
	 * so ensure that the incoming values have already been sanitized.
	 * 
	 * @see IntakeService
	 */
	@Override
	public Person save(Person obj) throws ObjectNotFoundException {
		Person current = get(obj.getId());

		current.setObjectStatus(obj.getObjectStatus());
		current.setFirstName(obj.getFirstName());
		current.setMiddleInitial(obj.getMiddleInitial());
		current.setLastName(obj.getLastName());
		current.setBirthDate(obj.getBirthDate());
		current.setPrimaryEmailAddress(obj.getPrimaryEmailAddress());
		current.setSecondaryEmailAddress(obj.getSecondaryEmailAddress());
		current.setUsername(obj.getUsername());
		current.setHomePhone(obj.getHomePhone());
		current.setWorkPhone(obj.getWorkPhone());
		current.setCellPhone(obj.getCellPhone());
		current.setAddressLine1(obj.getAddressLine1());
		current.setAddressLine2(obj.getAddressLine2());
		current.setCity(obj.getCity());
		current.setState(obj.getState());
		current.setZipCode(obj.getZipCode());
		current.setPhotoUrl(obj.getPhotoUrl());
		current.setSchoolId(obj.getSchoolId());

		current.setEnabled(obj.isEnabled());

		return dao.save(current);
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

}
