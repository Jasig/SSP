package org.jasig.ssp.service.external.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PersonStaffDetailsService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

@Service
@Transactional
public class ExternalPersonServiceImpl
		extends AbstractExternalDataService<ExternalPerson>
		implements ExternalPersonService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalPersonServiceImpl.class);

	private static final int BATCH_SIZE_FOR_PERSON_ = 1000;

	@Autowired
	private transient ExternalPersonDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient PersonStaffDetailsService staffDetailsService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient MaritalStatusService maritalStatusService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Override
	public ExternalPerson getBySchoolId(final String schoolId)
			throws ObjectNotFoundException {
		return dao.getBySchoolId(schoolId);
	}

	@Override
	public ExternalPerson getByUsername(final String username)
			throws ObjectNotFoundException {
		return dao.getByUsername(username);
	}

	private transient int lastRecord = 0;

	@Override
	@Scheduled(fixedDelay = 300000)
	// run every 5 minutes
	public void syncWithPerson() {
		LOGGER.info(
				"BEGIN : Person and ExternalPerson Sync.  Selecting {} records starting at {}",
				BATCH_SIZE_FOR_PERSON_, lastRecord);

		final SortingAndPaging sAndP = SortingAndPaging.createForSingleSort(
				ObjectStatus.ACTIVE, lastRecord, BATCH_SIZE_FOR_PERSON_,
				"username",
				SortDirection.ASC.toString(), null);

		Long totalRows = Long.valueOf(0L);
		try {
			totalRows = syncWithPerson(sAndP);
		} catch (final Exception e) {
			LOGGER.error("Failed to sync Person table with ExternalPerson", e);
		} finally {
			lastRecord = lastRecord + BATCH_SIZE_FOR_PERSON_;
			if (lastRecord > totalRows.intValue()) {
				lastRecord = 0;
			}
		}

		LOGGER.info("END :  Person and ExternalPerson Sync");
	}

	@Override
	public long syncWithPerson(final SortingAndPaging sAndP) {
		final PagingWrapper<Person> people = personService.getAll(sAndP);

		// allow access to people by schoolId
		final Map<String, Person> peopleBySchoolId = Maps.newHashMap();
		final Map<String, Person> peopleBySchoolIdCaseInsensitive = Maps.newHashMap();
		for (final Person person : people) {
			peopleBySchoolId.put(person.getSchoolId(), person);
			peopleBySchoolIdCaseInsensitive.put(person.getSchoolId().toUpperCase(), person);
		}

		// fetch external people by schoolId
		final PagingWrapper<ExternalPerson> externalPeople =
				dao.getBySchoolIds(peopleBySchoolId.keySet(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		for (final ExternalPerson externalPerson : externalPeople) {
			// get the previously fetched person
			final Person person = peopleBySchoolIdCaseInsensitive.get(externalPerson
					.getSchoolId().toUpperCase());
			// upate person from external person
			updatePersonFromExternalPerson(person, externalPerson);
		}

		return people.getResults();
	}

	/**
	 * Modifies state of Person for values of ExternalPerson that are different.
	 * 
	 * @param person
	 *            person
	 */
	@Override
	public void updatePersonFromExternalPerson(final Person person,
			final ExternalPerson externalPerson) {

		LOGGER.debug(
				"BEGIN : Person and ExternalPerson Sync.  Person school id {}, username {}",
				person.getSchoolId(), person.getUsername());

		if (person.getSchoolId() == null) {
			person.setSchoolId(externalPerson.getSchoolId());
		}

		if (person.getUsername() == null) {
			person.setUsername(externalPerson.getUsername());
		}

		if ((person.getFirstName() == null) ||
				(!person.getFirstName().equals(externalPerson.getFirstName()))) {
			person.setFirstName(externalPerson.getFirstName());
		}

		if ((person.getLastName() == null) ||
				(!person.getLastName().equals(externalPerson.getLastName()))) {
			person.setLastName(externalPerson.getLastName());
		}

		if ((person.getMiddleName() == null)
				||
				(!person.getMiddleName().equals(externalPerson.getMiddleName()))) {
			person.setMiddleName(externalPerson.getMiddleName());
		}

		if ((person.getBirthDate() == null) ||
				(!person.getBirthDate().equals(externalPerson.getBirthDate()))) {
			person.setBirthDate(externalPerson.getBirthDate());
		}

		if ((person.getPrimaryEmailAddress() == null) ||
				(!person.getPrimaryEmailAddress().equals(
						externalPerson.getPrimaryEmailAddress()))) {
			person.setPrimaryEmailAddress(externalPerson
					.getPrimaryEmailAddress());
		}

		if ((person.getAddressLine1() == null)
				||
				(!person.getAddressLine1().equals(
						externalPerson.getAddressLine1()))) {
			person.setAddressLine1(externalPerson.getAddressLine1());
		}

		if ((person.getAddressLine2() == null)
				||
				(!person.getAddressLine2().equals(
						externalPerson.getAddressLine2()))) {
			person.setAddressLine2(externalPerson.getAddressLine2());
		}

		if ((person.getCity() == null) ||
				(!person.getCity().equals(externalPerson.getCity()))) {
			person.setCity(externalPerson.getCity());
		}

		if ((person.getState() == null) ||
				(!person.getState().equals(externalPerson.getState()))) {
			person.setState(externalPerson.getState());
		}

		if ((person.getZipCode() == null) ||
				(!person.getZipCode().equals(externalPerson.getZipCode()))) {
			person.setZipCode(externalPerson.getZipCode());
		}

		if ((person.getHomePhone() == null) ||
				(!person.getHomePhone().equals(externalPerson.getHomePhone()))) {
			person.setHomePhone(externalPerson.getHomePhone());
		}

		if ((person.getWorkPhone() == null) ||
				(!person.getWorkPhone().equals(externalPerson.getWorkPhone()))) {
			person.setWorkPhone(externalPerson.getWorkPhone());
		}

		if ((person.getActualStartTerm() == null) ||
				(!person.getActualStartTerm().equals(
						externalPerson.getActualStartTerm()))) {
			person.setActualStartTerm(externalPerson.getActualStartTerm());
		}

		if ((person.getActualStartYear() == null) ||
				(!person.getActualStartYear().equals(
						externalPerson.getActualStartYear()))) {
			person.setActualStartYear(externalPerson.getActualStartYear());
		}

		setCoachForPerson(person, externalPerson.getCoachSchoolId());

		if ((StringUtils.isBlank(externalPerson.getDepartmentName())
				&& StringUtils.isBlank(externalPerson.getOfficeHours())
				&& StringUtils.isBlank(externalPerson.getOfficeLocation()))) {
			// this is likely not a staff member
			if (person.getStaffDetails() != null) {
				try {
					staffDetailsService
							.delete(person.getStaffDetails().getId());
				} catch (final ObjectNotFoundException e) {
					LOGGER.info(
							"Strange that we found a staffDetails object a moment ago, but cannot find it again to delte it.",
							e);
				}
			}

		} else {
			// this is a staff member, add their details
			if (person.getStaffDetails() == null) {
				person.setStaffDetails(new PersonStaffDetails());
			}

			if ((person.getStaffDetails().getOfficeHours() == null) ||
					(!person.getStaffDetails().getOfficeHours()
							.equals(externalPerson.getOfficeHours()))) {
				person.getStaffDetails().setOfficeHours(
						externalPerson.getOfficeHours());
			}

			if ((person.getStaffDetails().getOfficeLocation() == null) ||
					(!person.getStaffDetails().getOfficeLocation()
							.equals(externalPerson.getOfficeLocation()))) {
				person.getStaffDetails().setOfficeLocation(
						externalPerson.getOfficeLocation());
			}

			if ((person.getStaffDetails().getDepartmentName() == null) ||
					(!person.getStaffDetails().getDepartmentName()
							.equals(externalPerson.getDepartmentName()))) {
				person.getStaffDetails().setDepartmentName(
						externalPerson.getDepartmentName());
			}

		}

		PersonDemographics demographics;
		if (person.getDemographics() == null) {
			demographics = new PersonDemographics();
			person.setDemographics(demographics);
		} else {
			demographics = person.getDemographics();
		}

		if (((demographics.getMaritalStatus() == null)
				&& !StringUtils.isBlank(externalPerson.getMaritalStatus()))
				||
				((demographics.getMaritalStatus() != null) &&
				!demographics.getMaritalStatus().getName().equals(
						externalPerson.getMaritalStatus()))) {
			try {
				demographics.setMaritalStatus(maritalStatusService
						.getByName(externalPerson.getMaritalStatus()));
			} catch (final ObjectNotFoundException e) {
				LOGGER.error("Marital status with name "
						+ externalPerson.getMaritalStatus() + " not found");
			}
		}

		if (((demographics.getEthnicity() == null)
				&& !StringUtils.isBlank(externalPerson.getEthnicity()))
				||
				((demographics.getEthnicity() != null) &&
				!demographics.getEthnicity().getName().equals(
						externalPerson.getEthnicity()))) {
			try {
				demographics.setEthnicity(ethnicityService
						.getByName(externalPerson.getEthnicity()));
			} catch (final ObjectNotFoundException e) {
				LOGGER.error("Ethnicity with name "
						+ externalPerson.getEthnicity() + " not found");
			}
		}

		if (((demographics.getGender() == null)
				&& !StringUtils.isBlank(externalPerson.getGender()))
				||
				((demographics.getGender() != null) &&
				!demographics.getGender().getCode().equals(
						externalPerson.getGender()))) {
			try {
				demographics.setGender(Genders.valueOf(externalPerson
						.getGender()));
			} catch (final IllegalArgumentException e) {
				LOGGER.error("Gender with code "
						+ externalPerson.getGender() + " not found");
			}
		}

		if (externalPerson.getIsLocal() == null) {
			demographics.setLocal(null);
		} else {
			demographics.setLocal(externalPerson.getIsLocal().equalsIgnoreCase(
					"Y"));
		}

		if (externalPerson.getBalanceOwed() == null) {
			demographics.setBalanceOwed(null);
		} else {
			if ((demographics.getBalanceOwed() == null) ||
					(!demographics.getBalanceOwed()
							.equals(externalPerson.getBalanceOwed()))) {
				demographics.setBalanceOwed(
						externalPerson.getBalanceOwed());
			}
		}

		try {
			personService.save(person);
		} catch (final ObjectNotFoundException e) {
			LOGGER.error("person failed to save", e);
		}

	}

	private void setCoachForPerson(final Person person, final String coachId) {
		if (configService.getByNameNullOrDefaultValue(
				"coachSetFromExternalData").equalsIgnoreCase("false")) {
			return;
		}

		if (person.getCoach() == null) {
			if (coachId != null) {
				person.setCoach(getCoach(coachId));
			}// else ignore
		} else {
			if (coachId == null) {
				person.setCoach(null);
			} else if (!coachId.equals(person.getCoach().getSchoolId())) {
				person.setCoach(getCoach(coachId));
			}// else equals, so ignore
		}
	}

	private Person getCoach(final String coachId) {
		try {
			return personService.getBySchoolId(coachId);
		} catch (final ObjectNotFoundException e) {
			LOGGER.warn(
					"Coach referenced in external table not available in system",
					e);
			return null;
		}
	}

	@Override
	protected ExternalDataDao<ExternalPerson> getDao() {
		return dao;
	}

}
