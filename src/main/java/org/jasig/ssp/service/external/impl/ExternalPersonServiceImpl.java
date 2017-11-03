/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.external.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.reference.*;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PersonStaffDetailsService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.*;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ExternalPersonServiceImpl
		extends AbstractExternalDataService<ExternalPerson>
		implements ExternalPersonService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalPersonServiceImpl.class);

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
	private transient RaceService raceService;
	
	@Autowired
	private transient StudentTypeService studentTypeService;

	@Autowired
	private transient CampusService campusService;

	@Autowired
    private transient PersonProgramStatusService personProgramStatusService;

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

	@Override
	public void updatePersonFromExternalPerson(final Person person, final boolean isStudent) {
		ExternalPerson externalPerson = null;
		final String schoolId = person.getSchoolId();
		final String username = person.getUsername();

		try {
			externalPerson = getBySchoolId(schoolId);
		} catch ( ObjectNotFoundException e ) {
			try {
				externalPerson = getByUsername(username);
			} catch ( ObjectNotFoundException ee ) {
				// see below
			}
		}

		if ( externalPerson != null ) {
			updatePersonFromExternalPerson(person, externalPerson,true, isStudent);
		} else {
			LOGGER.debug("Skipping external data sync for "
					+ "person [id: {}] [schoolId: {}] [username: {}]  because "
					+ "there is no corresponding external record under either key.",
					new Object[] {
							person.getId(), schoolId, username
					});
		}

	}

	/**
	 * Modifies state of Person for values of ExternalPerson that are different.
	 * 
	 * @param person
	 *            person
	 */
	@Override
	public void updatePersonFromExternalPerson(final Person person, final ExternalPerson externalPerson, boolean commit,
                                               boolean isStudent) {

		LOGGER.debug(
				"Person and ExternalPerson Sync.  Person school id {}, username {}",
				person.getSchoolId(), person.getUsername());

		// Allow ExternalPerson.schoolId to override that Person field since
		// the ExternalPerson might have been looked up by username rather than
		// schoolId and Person.schoolId is not necessarily trustworthy on 1st
		// time login (https://issues.jasig.org/browse/SSP-1750).
		//
		// Comparison here is intentionally case-sensitive pending
		// https://issues.jasig.org/browse/SSP-1735
		if (person.getSchoolId() == null ||
				(!person.getSchoolId().equals(externalPerson.getSchoolId()))) {
			person.setSchoolId(externalPerson.getSchoolId());
		}

		if (person.getUsername() == null ||
				(!person.getUsername().equalsIgnoreCase(externalPerson.getUsername()))) {
			person.setUsername(StringUtils.lowerCase(externalPerson.getUsername()));
		}

		if ((person.getFirstName() == null) ||
				(!person.getFirstName().equals(externalPerson.getFirstName()))) {
			person.setFirstName(externalPerson.getFirstName());
		}

		if ((person.getLastName() == null) ||
				(!person.getLastName().equals(externalPerson.getLastName()))) {
			person.setLastName(externalPerson.getLastName());
		}

		if ((person.getMiddleName() == null) ||
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

		if ((person.getAddressLine1() == null) ||
				(!person.getAddressLine1().equals(
						externalPerson.getAddressLine1()))) {
			person.setAddressLine1(externalPerson.getAddressLine1());
		}

		if ((person.getAddressLine2() == null) ||
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

        if ((person.getPhotoUrl() == null) ||
                (!person.getPhotoUrl().equals(externalPerson.getPhotoUrl()))) {
            person.setPhotoUrl(externalPerson.getPhotoUrl());
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

		setHomeCampusForPerson(person, externalPerson.getCampusCode());

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
							"Strange that we found a staffDetails object a moment ago, but cannot find it again to delete it.",
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
 
        /*  As per SSP-874, how to handle external data overwrites:
		 *	Based on jasig-ssp discussions, the rules for the updates to the person_demographics are:
		 *	1. Null/Empty/Blank value from external table: over-write (they may be intentionally desiring the existing value overwritten.
		 *	2. Valid value from external table that matches entry in reference table: over-write
		 *	3. Invalid value from external table that has no matching entry in reference table: don't over-write
		 */
        if (StringUtils.isBlank(externalPerson.getMaritalStatus())) {
            demographics.setMaritalStatus(null);
        } else {
            MaritalStatus maritalStatus = maritalStatusService.getByName(externalPerson.getMaritalStatus());
            demographics.setMaritalStatus(maritalStatus == null ? demographics.getMaritalStatus() : maritalStatus);
        }

        if (StringUtils.isBlank(externalPerson.getEthnicity())) {
            demographics.setEthnicity(null);
        } else {
            Ethnicity ethnicity =ethnicityService.getByName(externalPerson.getEthnicity());
            demographics.setEthnicity(ethnicity == null ? demographics.getEthnicity() : ethnicity);
        }

        if (StringUtils.isBlank(externalPerson.getRace())) {
            demographics.setRace(null);
        } else {
            Race race =raceService.getByCode(externalPerson.getRace());
            demographics.setRace(race == null ? demographics.getRace() : race);
        }

        if (StringUtils.isBlank(externalPerson.getGender())) {
            demographics.setGender(null);
        } else {
            try {
                Genders gender =Genders.valueOf(externalPerson.getGender());
                demographics.setGender(gender);
            } catch (IllegalArgumentException e) {
                demographics.setGender(null);
            }
        }

		if (externalPerson.getIsLocal() == null) {
			demographics.setLocal(null);
		} else {
			demographics.setLocal(externalPerson.getIsLocal().equalsIgnoreCase("Y"));
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
			if (commit) {
				final Person savedPerson = personService.save(person);

				if (isStudent && savedPerson != null) {
                    PersonProgramStatus personProgramStatus = null;
                    try {
                        personProgramStatus = personProgramStatusService.getCurrent(savedPerson.getId());

                    } catch (ObjectNotFoundException | ValidationException e) {
                        //swallow
                    }

                    try {
                        if (personProgramStatus == null) {
                            personProgramStatusService.setActiveForStudent(savedPerson);
                        }
                    } catch (ObjectNotFoundException | ValidationException onfve) {
                        LOGGER.info("Couldn't set program status for schoolId: {} error is ", person.getSchoolId(), onfve.getStackTrace());
                    }
                }
			}

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
			if (coachId != null && !coachId.trim().isEmpty()) {
				LOGGER.debug("Assigning coach schoolId '{}' to person " +
						"schoolId '{}'", coachId, person.getSchoolId());
				person.setCoach(getCoach(coachId));
			}// else ignore
		} else {
			if (coachId == null || coachId.trim().isEmpty()) {
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
			return personService.getInternalOrExternalPersonBySchoolId(coachId,true, false); //this adds the coach if only exists externally, otherwise retrieves the internal record
		} catch (final ObjectNotFoundException e) {
			LOGGER.warn("Coach referenced in external table not available in system", e);
			return null;
		}
	}
	
	private void setStudentTypeForPerson(final Person person, final String externStudentType) {
		if (configService.getByNameNullOrDefaultValue(
				"studentTypeSetFromExternalData").equalsIgnoreCase("false")) {
			LOGGER.debug("Skipping all student type assignment processing for person "
					+ "schoolId '{}' because that operation has been disabled "
					+ "via configuration.", person.getSchoolId());
			return;
		}
		
		if (person.getStudentType() == null) {   
			if (externStudentType != null && !externStudentType.trim().isEmpty()) {
				LOGGER.debug("Assigning student_type '{}' to person " +
						"schoolId '{}'", externStudentType, person.getSchoolId());
				person.setStudentType(getInternalStudentTypeCode(externStudentType));
			}// else ignore
		} else {
			if (externStudentType == null || externStudentType.trim().isEmpty()) {
				if ( configService.getByNameNullOrDefaultValue(
						"studentTypeUnsetFromExternalData")
						.equalsIgnoreCase("true") ) {
					LOGGER.debug("Deleting student_type assignment for person schoolId '{}'",
							person.getSchoolId());
					person.setStudentType(null);
				} else {
					LOGGER.debug("Skipping student_type assignment deletion for "
							+ "person schoolId '{}' because that operation has "
							+ "been disabled via configuration.",
							person.getSchoolId());
				}
			} else if (!externStudentType.equals(person.getStudentType().getCode())) {
				StudentType studentType = getInternalStudentTypeCode(externStudentType);
				if ( studentType == null ) {
					// lookup problem already logged
					LOGGER.debug("Student Type with name '{}' does not exist so "
							+ "skipping student_type assignment for person schoolId '{}'",
							externStudentType, person.getSchoolId());
				} else {
					person.setStudentType(studentType);
				}
			}// else equals, so ignore
		}
	}
	
	private StudentType getInternalStudentTypeCode(final String studentTypeCode) {
		try {
			return studentTypeService.getByCode(studentTypeCode);
		} catch (final ObjectNotFoundException e) {
			LOGGER.warn("Student_Type " +studentTypeCode +" referenced in external table not "
					+ "available in system. ", e);
			return null;
		}				
	}

	private void setHomeCampusForPerson(final Person person, final String externalCampusCode) {
		if (person.getHomeCampus() == null) {
			if (externalCampusCode != null && !externalCampusCode.trim().isEmpty()) {
				LOGGER.debug("Assigning campus_code '{}' to person " +
						"schoolId '{}'", externalCampusCode, person.getSchoolId());
				person.setHomeCampus(getInternalCampus(externalCampusCode));
			}
		} else {
			if (externalCampusCode == null || externalCampusCode.trim().isEmpty()) {
				LOGGER.debug("Assigning a null Campus to person schoolId '{}'", person.getSchoolId());
				person.setHomeCampus(null);
			} else if (!externalCampusCode.equals(person.getHomeCampus().getCode())) {
				Campus campus = getInternalCampus(externalCampusCode);
				if ( campus == null ) {
					// lookup problem already logged
					LOGGER.debug("Campus with name '{}' does not exist so "
									+ "skipping home_campus assignment for person schoolId '{}'",
							externalCampusCode, person.getSchoolId());
				} else {
					LOGGER.debug("Assigning campus_code '{}' to person " +
							"schoolId '{}'", externalCampusCode, person.getSchoolId());
					person.setHomeCampus(campus);
				}
			}// else equals, so ignore
		}
	}

	private Campus getInternalCampus(final String campusCode) {
		try {
			return campusService.getByCode(campusCode);
		} catch (final ObjectNotFoundException e) {
			LOGGER.warn("Campus_code " + campusCode +" referenced in external table not "
					+ "available in system. ", e);
			return null;
		}
	}

	@Override
	protected ExternalDataDao<ExternalPerson> getDao() {
		return dao;
	}
	
	@Override
	public List<String> getAllDepartmentNames() {
		return dao.getAllDepartmentNames();
	}
}
