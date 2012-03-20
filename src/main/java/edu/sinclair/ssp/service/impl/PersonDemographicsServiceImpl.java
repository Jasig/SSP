package edu.sinclair.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.dao.PersonDemographicsDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonDemographicsService;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.reference.CitizenshipService;
import edu.sinclair.ssp.service.reference.EthnicityService;
import edu.sinclair.ssp.service.reference.MaritalStatusService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

@Service
public class PersonDemographicsServiceImpl implements PersonDemographicsService {

	@Autowired
	private PersonDemographicsDao dao;
	
	@Autowired
	private CitizenshipService citizenshipService;
	
	@Autowired
	private EthnicityService ethnicityService;

	@Autowired
	private MaritalStatusService maritalStatusService;

	@Autowired
	private PersonService personService;
	
	@Autowired
	private VeteranStatusService veteranStatusService;
	
	@Override
	public List<PersonDemographics> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public PersonDemographics get(UUID id) throws ObjectNotFoundException {
		PersonDemographics obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "Person");
		}
		return obj;
	}
	
	@Override
	public PersonDemographics forPerson(Person person){
		return dao.forPerson(person);
	}

	@Override
	public PersonDemographics create(PersonDemographics obj) {
		return dao.save(obj);
	}

	@Override
	public PersonDemographics save(PersonDemographics obj) throws ObjectNotFoundException{
		PersonDemographics current = get(obj.getId());
		
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		current.setAbilityToBenefit(obj.isAbilityToBenefit());
		current.setChildCareNeeded(obj.isChildCareNeeded());
		current.setEmployed(obj.isEmployed());
		current.setLocal(obj.isLocal());
		current.setPrimaryCaregiver(obj.isPrimaryCaregiver());
		current.setNumberOfChildren(obj.getNumberOfChildren());
		
		if(obj.getAnticipatedStartTerm()!=null){
			current.setAnticipatedStartTerm(obj.getAnticipatedStartTerm());
		}
		if(obj.getAnticipatedStartYear()!=null){
			current.setAnticipatedStartYear(obj.getAnticipatedStartYear());
		}
		if(obj.getCountryOfResidence()!=null){
			current.setCountryOfResidence(obj.getCountryOfResidence());
		}
		if(obj.getPaymentStatus()!=null){
			current.setPaymentStatus(obj.getPaymentStatus());
		}
		if(obj.getCountryOfCitizenship()!=null){
			current.setCountryOfCitizenship(obj.getCountryOfCitizenship());
		}
		if(obj.getChildAges()!=null){
			current.setChildAges(obj.getChildAges());
		}
		if(obj.getPlaceOfEmployment()!=null){
			current.setPlaceOfEmployment(obj.getPlaceOfEmployment());
		}
		if(obj.getWage()!=null){
			current.setWage(obj.getWage());
		}
		if(obj.getTotalHoursWorkedPerWeek()!=null){
			current.setTotalHoursWorkedPerWeek(obj.getTotalHoursWorkedPerWeek());
		}
		if(obj.getShift()!=null){
			current.setShift(obj.getShift());
		}
		if(obj.getGender()!=null){
			current.setGender(obj.getGender());
		}
		if(obj.getMaritalStatus()!=null){
			current.setMaritalStatus(maritalStatusService.get(obj.getMaritalStatus().getId()));
		}
		if(obj.getEthnicity()!=null){
			current.setEthnicity(ethnicityService.get(obj.getEthnicity().getId()));
		}
		if(obj.getCitizenship()!=null){
			current.setCitizenship(citizenshipService.get(obj.getCitizenship().getId()));
		}
		if(obj.getVeteranStatus()!=null){
			current.setVeteranStatus(veteranStatusService.get(obj.getVeteranStatus().getId()));
		}
		if(obj.getCoach()!=null){
			current.setCoach(personService.get(obj.getCoach().getId()));
		}
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonDemographics current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
