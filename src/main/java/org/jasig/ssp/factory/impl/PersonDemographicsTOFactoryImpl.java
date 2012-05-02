package org.jasig.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.PersonDemographicsDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonDemographicsTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.transferobject.PersonDemographicsTO;

@Service
@Transactional(readOnly = true)
public class PersonDemographicsTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonDemographicsTO, PersonDemographics>
		implements PersonDemographicsTOFactory {

	public PersonDemographicsTOFactoryImpl() {
		super(PersonDemographicsTO.class, PersonDemographics.class);
	}

	@Autowired
	private transient PersonDemographicsDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient MaritalStatusService maritalStatusService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Autowired
	private transient CitizenshipService citizenshipService;

	@Autowired
	private transient ChildCareArrangementService childCareArrangementService;

	@Autowired
	private transient VeteranStatusService veteranStatusService;

	@Override
	protected PersonDemographicsDao getDao() {
		return dao;
	}

	@Override
	public PersonDemographics from(final PersonDemographicsTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			Person person = personService.get(tObject.getPersonId());
			PersonDemographics unsetModel = person.getDemographics();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonDemographics model = super.from(tObject);

		model.setCoach((tObject.getCoachId() == null) ? null : personService
				.get(tObject.getCoachId()));

		model.setMaritalStatus((tObject.getMaritalStatusId() == null) ? null :
				maritalStatusService.get(tObject.getMaritalStatusId()));

		model.setEthnicity((tObject.getEthnicityId() == null) ? null :
				ethnicityService.get(tObject.getEthnicityId()));

		model.setCitizenship((tObject.getCitizenshipId() == null) ? null :
				citizenshipService.get(tObject.getCitizenshipId()));

		model.setVeteranStatus((tObject.getVeteranStatusId() == null) ? null :
				veteranStatusService.get(tObject.getVeteranStatusId()));

		model.setChildCareArrangement((tObject.getChildCareArrangementId() == null) ? null
				: childCareArrangementService.get(tObject
						.getChildCareArrangementId()));

		model.setGender((tObject.getGender() == null) ? null :
				Genders.valueOf(tObject.getGender()));

		model.setShift((tObject.getShift() == null) ? null :
				EmploymentShifts.valueOf(tObject.getShift()));

		model.setAbilityToBenefit(tObject.isAbilityToBenefit());
		model.setLocal(tObject.isLocal());
		model.setPrimaryCaregiver(tObject.isPrimaryCaregiver());
		model.setChildCareNeeded(tObject.isChildCareNeeded());
		model.setEmployed(tObject.isEmployed());
		model.setNumberOfChildren(tObject.getNumberOfChildren());
		model.setAnticipatedStartTerm(tObject.getAnticipatedStartTerm());
		model.setAnticipatedStartYear(tObject.getAnticipatedStartYear());
		model.setCountryOfResidence(tObject.getCountryOfResidence());
		model.setPaymentStatus(tObject.getPaymentStatus());
		model.setCountryOfCitizenship(tObject.getCountryOfCitizenship());
		model.setChildAges(tObject.getChildAges());
		model.setPlaceOfEmployment(tObject.getPlaceOfEmployment());
		model.setWage(tObject.getWage());
		model.setTotalHoursWorkedPerWeek(tObject.getTotalHoursWorkedPerWeek());

		return model;
	}
}
