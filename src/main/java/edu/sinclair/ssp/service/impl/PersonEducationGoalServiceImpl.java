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
public class PersonEducationGoalServiceImpl implements PersonEducationGoalService {

	@Autowired
	private PersonEducationGoalDao dao;
	
	@Autowired
	private EducationGoalService educationGoalService;
	
	@Override
	public List<PersonEducationGoal> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public PersonEducationGoal get(UUID id) throws ObjectNotFoundException {
		PersonEducationGoal obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "PersonEducationGoal");
		}
		return obj;
	}
	
	@Override
	public PersonEducationGoal forPerson(Person person){
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationGoal create(PersonEducationGoal obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationGoal save(PersonEducationGoal obj) throws ObjectNotFoundException{
		PersonEducationGoal current = get(obj.getId());
		
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		if(obj.getDescription()!=null){
			current.setDescription(obj.getDescription());
		}
		if(obj.getPlannedOccupation()!=null){
			current.setPlannedOccupation(obj.getPlannedOccupation());
		}
		current.setHowSureAboutMajor(obj.getHowSureAboutMajor());
		if(obj.getEducationGoal()!=null){
			current.setEducationGoal(educationGoalService.get(obj.getEducationGoal().getId()));
		}
		
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonEducationGoal current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
