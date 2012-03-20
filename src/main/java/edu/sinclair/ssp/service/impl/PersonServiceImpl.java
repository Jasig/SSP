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

	//private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private PersonDao dao;
	
	@Override
	public List<Person> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public Person get(UUID id) throws ObjectNotFoundException {
		Person obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "Person");
		}
		return obj;
	}

	@Override
	public Person personFromUsername(String username) throws ObjectNotFoundException {
		Person obj = dao.fromUsername(username);
		if(null==obj){
			throw new ObjectNotFoundException("Could not find person with username: " + username);
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
		
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		if(obj.getFirstName()!=null){
			current.setFirstName(obj.getFirstName());
		}
		
		if(obj.getMiddleInitial()!=null){
			current.setMiddleInitial(obj.getMiddleInitial());
		}
		
		if(obj.getLastName()!=null){
			current.setLastName(obj.getLastName());
		}
		
		if(obj.getBirthDate()!=null){
			current.setBirthDate(obj.getBirthDate());
		}
		
		if(obj.getPrimaryEmailAddress()!=null){
			current.setPrimaryEmailAddress(obj.getPrimaryEmailAddress());
		}
		
		if(obj.getSecondaryEmailAddress()!=null){
			current.setSecondaryEmailAddress(obj.getSecondaryEmailAddress());
		}
		
		if(obj.getUsername()!=null){
			current.setUsername(obj.getUsername());
		}
		
		if(obj.getHomePhone()!=null){
			current.setHomePhone(obj.getHomePhone());
		}
		
		if(obj.getWorkPhone()!=null){
			current.setWorkPhone(obj.getWorkPhone());
		}
		
		if(obj.getCellPhone()!=null){
			current.setCellPhone(obj.getCellPhone());
		}
		
		if(obj.getAddressLine1()!=null){
			current.setAddressLine1(obj.getAddressLine1());
		}
		
		if(obj.getCity()!=null){
			current.setCity(obj.getCity());
		}
		
		if(obj.getState()!=null){
			current.setState(obj.getState());
		}
		
		if(obj.getZipCode()!=null){
			current.setZipCode(obj.getZipCode());
		}
		
		if(obj.getPhotoUrl()!=null){
			current.setPhotoUrl(obj.getPhotoUrl());
		}
		
		if(obj.getSchoolId()!=null){
			current.setSchoolId(obj.getSchoolId());
		}
		
		current.setEnabled(obj.isEnabled());
		
		
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Person current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(PersonDao dao){
		this.dao = dao;
	}

}

