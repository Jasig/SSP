package org.studentsuccessplan.ssp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studentsuccessplan.ssp.dao.PersonDemographicsDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonDemographics;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonDemographicsService;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;
import org.studentsuccessplan.ssp.service.reference.VeteranStatusService;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
	public PagingWrapper<PersonDemographics> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonDemographics get(UUID id) throws ObjectNotFoundException {
		PersonDemographics obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Person");
		}
		return obj;
	}

	@Override
	public PersonDemographics forPerson(Person person) {
		return person.getDemographics();
	}

	@Override
	public PersonDemographics create(PersonDemographics obj) {
		return dao.save(obj);
	}

	@Override
	public PersonDemographics save(PersonDemographics obj)
			throws ObjectNotFoundException {
		PersonDemographics current = get(obj.getId());

		if (obj.getObjectStatus() != null) {
			current.setObjectStatus(obj.getObjectStatus());
		}

		current.setAbilityToBenefit(obj.isAbilityToBenefit());
		current.setChildCareNeeded(obj.isChildCareNeeded());
		current.setEmployed(obj.isEmployed());
		current.setLocal(obj.isLocal());
		current.setPrimaryCaregiver(obj.isPrimaryCaregiver());
		current.setNumberOfChildren(obj.getNumberOfChildren());

		current.setAnticipatedStartTerm(obj.getAnticipatedStartTerm());
		current.setAnticipatedStartYear(obj.getAnticipatedStartYear());
		current.setCountryOfResidence(obj.getCountryOfResidence());
		current.setPaymentStatus(obj.getPaymentStatus());
		current.setCountryOfCitizenship(obj.getCountryOfCitizenship());
		current.setChildAges(obj.getChildAges());
		current.setPlaceOfEmployment(obj.getPlaceOfEmployment());
		current.setWage(obj.getWage());
		current.setTotalHoursWorkedPerWeek(obj.getTotalHoursWorkedPerWeek());
		current.setShift(obj.getShift());
		current.setGender(obj.getGender());
		if (obj.getMaritalStatus() != null) {
			current.setMaritalStatus(maritalStatusService.get(obj
					.getMaritalStatus().getId()));
		}
		if (obj.getEthnicity() != null) {
			current.setEthnicity(ethnicityService.get(obj.getEthnicity()
					.getId()));
		}
		if (obj.getCitizenship() != null) {
			current.setCitizenship(citizenshipService.get(obj.getCitizenship()
					.getId()));
		}
		if (obj.getVeteranStatus() != null) {
			current.setVeteranStatus(veteranStatusService.get(obj
					.getVeteranStatus().getId()));
		}
		if (obj.getCoach() != null) {
			current.setCoach(personService.get(obj.getCoach().getId()));
		}
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		PersonDemographics current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

}
