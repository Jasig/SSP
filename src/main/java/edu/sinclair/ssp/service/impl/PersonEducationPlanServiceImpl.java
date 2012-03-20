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

@Service
public class PersonEducationPlanServiceImpl implements PersonEducationPlanService {

	@Autowired
	private PersonEducationPlanDao dao;
	
	@Override
	public List<PersonEducationPlan> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public PersonEducationPlan get(UUID id) throws ObjectNotFoundException {
		PersonEducationPlan obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "PersonEducationPlan");
		}
		return obj;
	}
	
	@Override
	public PersonEducationPlan forPerson(Person person){
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationPlan create(PersonEducationPlan obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationPlan save(PersonEducationPlan obj) throws ObjectNotFoundException{
		PersonEducationPlan current = get(obj.getId());
		
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		
		
		
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonEducationPlan current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
