package org.jasig.ssp.service.impl;

import java.util.UUID;

import org.jasig.ssp.dao.PersonDemographicsDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonDemographicsService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDemographicsServiceImpl implements PersonDemographicsService {

	@Autowired
	private transient PersonDemographicsDao dao;

	@Autowired
	private transient CitizenshipService citizenshipService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Autowired
	private transient MaritalStatusService maritalStatusService;

	@Autowired
	private transient VeteranStatusService veteranStatusService;

	@Override
	public PagingWrapper<PersonDemographics> getAll(final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonDemographics get(final UUID id) throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonDemographics forPerson(final Person person) {
		return person.getDemographics();
	}

	@Override
	public PersonDemographics create(final PersonDemographics obj) {
		return dao.save(obj);
	}

	@Override
	public PersonDemographics save(final PersonDemographics obj)
			throws ObjectNotFoundException {
		final PersonDemographics current = get(obj.getId());

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

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonDemographics current = get(id);

		if (null != current
				&& !ObjectStatus.DELETED.equals(current.getObjectStatus())) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}
}
