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
	public List<PersonDemographics> getAll(ObjectStatus status,
			int firstResult, int maxResults, String sortExpression) {
		return dao.getAll(status, firstResult, maxResults, sortExpression);
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
		return dao.forPerson(person);
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
