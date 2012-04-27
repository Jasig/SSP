package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonDemographicsDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonDemographicsTOFactory;
import org.studentsuccessplan.ssp.model.PersonDemographics;
import org.studentsuccessplan.ssp.model.reference.EmploymentShifts;
import org.studentsuccessplan.ssp.model.reference.Genders;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;
import org.studentsuccessplan.ssp.service.reference.VeteranStatusService;
import org.studentsuccessplan.ssp.transferobject.PersonDemographicsTO;

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
		final PersonDemographics model = super.from(tObject);

		if (tObject.getCoachId() != null) {
			model.setCoach(personService.get(tObject.getCoachId()));
		}
		if (tObject.getMaritalStatusId() != null) {
			model.setMaritalStatus(maritalStatusService.get(tObject
					.getMaritalStatusId()));
		}
		if (tObject.getEthnicityId() != null) {
			model.setEthnicity(ethnicityService.get(tObject.getEthnicityId()));
		}
		if (tObject.getCitizenshipId() != null) {
			model.setCitizenship(citizenshipService.get(tObject
					.getCitizenshipId()));
		}
		if (tObject.getVeteranStatusId() != null) {
			model.setVeteranStatus(veteranStatusService.get(tObject
					.getVeteranStatusId()));
		}
		if (tObject.getChildCareArrangementId() != null) {
			model.setChildCareArrangement(childCareArrangementService
					.get(tObject.
							getChildCareArrangementId()));
		}
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
		if (tObject.getGender() != null) {
			model.setGender(Genders.valueOf(tObject.getGender()));
		}
		model.setCountryOfCitizenship(tObject.getCountryOfCitizenship());
		model.setChildAges(tObject.getChildAges());
		model.setPlaceOfEmployment(tObject.getPlaceOfEmployment());
		if (tObject.getShift() != null) {
			model.setShift(EmploymentShifts.valueOf(tObject.getShift()));
		}
		model.setWage(tObject.getWage());
		model.setTotalHoursWorkedPerWeek(tObject.getTotalHoursWorkedPerWeek());

		return model;
	}
}
