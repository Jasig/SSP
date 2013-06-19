/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.external.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PersonStaffDetailsService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private transient StudentTypeService studentTypeService;

	
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
	public void syncWithPerson() {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning syncWithPerson because of thread interruption");
			return;
		}

		LOGGER.info(
				"BEGIN : Person and ExternalPerson Sync.  Selecting {} records starting at {}",
				BATCH_SIZE_FOR_PERSON_, lastRecord);

		final SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(
				ObjectStatus.ACTIVE, lastRecord, BATCH_SIZE_FOR_PERSON_,
				"username",
				SortDirection.ASC.toString(), null);

		Long totalRows = Long.valueOf(0L);
		try {
			totalRows = syncWithPerson(sAndP);
		} catch ( InterruptedException e ) {
			LOGGER.info("Abandoning syncWithPerson because of thread interruption");
			Thread.currentThread().interrupt(); // reassert
		} catch (final Exception e) {
			LOGGER.error("Failed to sync Person table with ExternalPerson", e);
		} finally {
			if ( !(Thread.currentThread().isInterrupted()) ) {
				lastRecord = lastRecord + BATCH_SIZE_FOR_PERSON_;
				if (lastRecord > totalRows.intValue()) {
					lastRecord = 0;
				}
			}
		}

		LOGGER.info("END :  Person and ExternalPerson Sync");
	}

	@Override
	public long syncWithPerson(final SortingAndPaging sAndP) throws InterruptedException {

		// Use InterruptedExceptions instead of manipulating return value b/c
		// the return value actually means "total number of possible processable"
		// rows, so if it is returned gracefully, the caller will likely
		// incorrectly update its internal state, as is the case with the
		// scheduled job call site.

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning syncWithPerson because of thread interruption");
			throw new InterruptedException();
		}

		final PagingWrapper<Person> people = personService.getAll(sAndP);

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning syncWithPerson because of thread interruption");
			throw new InterruptedException();
		}

		// allow access to people by schoolId
		final Map<String, Person> peopleBySchoolId = Maps.newHashMap();
		for (final Person person : people) {
			peopleBySchoolId.put(person.getSchoolId(), person);
		}

		Set<String> internalPeopleSchoolIds = peopleBySchoolId.keySet();
		if ( LOGGER.isDebugEnabled() ) {
			LOGGER.debug(
					"Candidate internal person schoolIds for sync with external persons {}",
					internalPeopleSchoolIds);
		}

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning syncWithPerson because of thread interruption");
			throw new InterruptedException();
		}

		// fetch external people by schoolId
		final PagingWrapper<ExternalPerson> externalPeople =
				dao.getBySchoolIds(internalPeopleSchoolIds,SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ACTIVE, 0, BATCH_SIZE_FOR_PERSON_,
						"username",
						SortDirection.ASC.toString(), null));

		for (final ExternalPerson externalPerson : externalPeople) {

			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning syncWithPerson because of thread interruption on person {}", externalPerson.getUsername());
				throw new InterruptedException();
			}

			LOGGER.debug(
					"Looking for internal person by external person schoolId {}",
					externalPerson.getSchoolId());
			// get the previously fetched person
			final Person person = peopleBySchoolId.get(externalPerson
					.getSchoolId());
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
				"Person and ExternalPerson Sync.  Person school id {}, username {}",
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

		if ((person.getCellPhone() == null) ||
				(!person.getCellPhone().equals(externalPerson.getCellPhone()))) {
			person.setCellPhone(externalPerson.getCellPhone());
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
		
		if ((person.getNonLocalAddress() == null) ||
				(!person.getNonLocalAddress().equals(
						externalPerson.getNonLocalAddress()))) {
			person.setNonLocalAddress(externalPerson.getNonLocalAddress());
		}
		
		if ((person.getResidencyCounty()== null) ||
				(!person.getResidencyCounty().equals(
						externalPerson.getResidencyCounty()))) {
			person.setResidencyCounty(externalPerson.getResidencyCounty());
		}
		
		if ((person.getF1Status()== null) ||
				(!person.getF1Status().equals(
						externalPerson.getF1Status()))) {
			person.setF1Status(externalPerson.getF1Status());
		}

		setCoachForPerson(person, externalPerson.getCoachSchoolId());
		
		setStudentTypeForPerson(person, externalPerson.getStudentType());

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
			LOGGER.debug("Skipping all coach assignment processing for person "
					+ "schoolId '{}' because that operation has been disabled "
					+ "via configuration.", person.getSchoolId());
			return;
		}

		if (person.getCoach() == null) {
			if (coachId != null) {
				LOGGER.debug("Assigning coach schoolId '{}' to person " +
						"schoolId '{}'", coachId, person.getSchoolId());
				person.setCoach(getCoach(coachId));
			}// else ignore
		} else {
			if (coachId == null) {
				if ( configService.getByNameNullOrDefaultValue(
						"coachUnsetFromExternalData")
						.equalsIgnoreCase("true") ) {
					LOGGER.debug("Deleting coach assignment for person schoolId '{}'",
							person.getSchoolId());
					person.setCoach(null);
				} else {
					LOGGER.debug("Skipping coach assignment deletion for "
							+ "person schoolId '{}' because that operation has "
							+ "been disabled via configuration.",
							person.getSchoolId());
				}
			} else if (!coachId.equals(person.getCoach().getSchoolId())) {
				Person coach = getCoach(coachId);
				if ( coach == null ) {
					// lookup problem already logged
					LOGGER.debug("Coach with schoolId '{}' does not exist so "
							+ "skipping coach assignment for person schoolId '{}'",
							person.getSchoolId());
				} else {
					person.setCoach(coach);
				}
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
	
	private void setStudentTypeForPerson(final Person person, final String externStudentType)
	{
		if (configService.getByNameNullOrDefaultValue(
				"studentTypeSetFromExternalData").equalsIgnoreCase("false")) {
			LOGGER.debug("Skipping all student type assignment processing for person "
					+ "schoolId '{}' because that operation has been disabled "
					+ "via configuration.", person.getSchoolId());
			return;
		}
		
		if (person.getStudentType() == null) {   
			if (externStudentType != null) {
				LOGGER.debug("Assigning student_type '{}' to person " +
						"schoolId '{}'", externStudentType, person.getSchoolId());
				person.setStudentType(getInternalStudentType(externStudentType));
			}// else ignore
		} else {
			if (externStudentType == null) {
				if ( configService.getByNameNullOrDefaultValue(
						"studentTypeUnsetFromExternalData")
						.equalsIgnoreCase("true") ) {
					LOGGER.debug("Deleting student type assignment for person schoolId '{}'",
							person.getSchoolId());
					person.setStudentType(null);
				} else {
					LOGGER.debug("Skipping student type assignment deletion for "
							+ "person schoolId '{}' because that operation has "
							+ "been disabled via configuration.",
							person.getSchoolId());
				}
			} else if (!externStudentType.equals(person.getStudentType().getName())) {
				StudentType studentType = getInternalStudentType(externStudentType);
				if ( studentType == null ) {
					// lookup problem already logged
					LOGGER.debug("Student Type with name '{}' does not exist so "
							+ "skipping student_type assignment for person schoolId '{}'",
							externStudentType, person.getSchoolId());
				} else {
					person.setStudentType(getInternalStudentType(externStudentType));
				}
			}// else equals, so ignore
		}
	}
	
	private StudentType getInternalStudentType(final String studentType) {
		try {
			return studentTypeService.getByName(studentType);
		} catch (final ObjectNotFoundException e) {
			LOGGER.warn(
					"Student_Type referenced in external table not available in system",
					e);
			return null;
		}
	}

	@Override
	protected ExternalDataDao<ExternalPerson> getDao() {
		return dao;
	}
	
	@Override
	public List<String> getAllDepartmentNames(){
		return dao.getAllDepartmentNames();
	}

}
