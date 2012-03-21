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

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonDao dao;

	@Override
	public List<Person> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

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
		current.setCity(obj.getCity());
		current.setState(obj.getState());
		current.setZipCode(obj.getZipCode());
		current.setPhotoUrl(obj.getPhotoUrl());
		current.setSchoolId(obj.getSchoolId());
		current.setEnabled(obj.isEnabled());

		return dao.save(current);
	}

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
