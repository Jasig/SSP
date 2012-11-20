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
package org.jasig.ssp.util.service.stub;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.web.api.validation.ValidationException;

import com.google.common.collect.Sets;

/**
 * Static factories for "standard" dummy object configurations, i.e. the sorts
 * of in-memory fixtures you'd usually inject into "stub" services or use as
 * inputs to tests. In order to be useful to the latter, object state usually
 * corresponds to database fixture state as initialized by Liquibase.
 *
 * <p>Also collects "script" methods of sorts for centralizing commonly
 * executed domain manipulations, e.g. setting a program status for a user
 * or generating an early alert.</p>
 */
public class Stubs {

	/**
	 * Each element represents a person records created by test Liquibase
	 * scripts, but enum is not necessarily guaranteed to represent all
	 * person records created by those tests. Usually, though, an attempt
	 * should be made to keep it up to date.
	 */
	public static enum PersonFixture {
		STUDENT_0 ( "1010e4a0-1001-0110-1011-4ffc02fe81ff", "student0", "student0", "James", "A", "Gosling", null, "test@sinclair.edu", "123-456-7654" ),
		JAMES_DOE ( "d2320b58-4d54-4532-a0cd-1f4409c9fc38", "james.doe", "uu351764", "James", null, "Doe", null, "james.ryan80@university.edu", null ),
		SYSTEM ( "58ba5ee3-734e-4ae9-b9c5-943774b4de41", "system", "userid_4", "System", null, "Administrator", null, "test@sinclair.edu", "000-000-0000" ),
		DMR ( "7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194", "dmr", "dmr.1", "Dennis", "M", "Ritchie", null, "test@sinclair.edu", "123-456-7654" ),
		KEN ( "f549ecab-5110-4cc1-b2bb-369cac854dea", "ken", "ken.1", "Kenneth", "L", "Thompson", null, "test@sinclair.edu", "123-456-7654" ),
		KEVIN_SMITH ( "f26d8f23-df20-40f1-bc98-83111be4a52a", "kevin.smith", "uu112233", "Kevin", null, "Smith", null, "ksmith12@university.edu", "123-456-7654" ),
		ADVISOR_0 ( "252de4a0-7c06-4254-b7d8-4ffc02fe81ff", "advisor0", "turing.1", "Alan", "M", "Turing", null, "test@sinclair.edu", "123-456-7654" ),
		MARK_GALAFRION ( "8005ad07-9111-457d-9896-f15a0dc8bbfb", "mark.galafrion", "uu358123", "Mark", null, "Galafrion", null, "mark.g@university.edu", null),
		BOB_REYNOLDS ( "a5a23d8a-f0d3-4ef6-9a75-21f3775bac0f", "bob.reynolds", "uu462875", "Bob", null, "Reynolds", null, "bob1@university.edu", null),
		FACULTY_0 ( "5eceefd1-257c-421f-99bf-a3622815bfee", "faculty0", "uf928711", "Douglas", null, "Toya", null, "douglas.toya@sinclair.edu", null),
		FACULTY_1 ( "0d6163de-90fd-46d1-a9ca-7dbaa850924b", "faculty1", "uf123456", "Mary", null, "Webber", null, "mary.webber@sinclair.edu", null);

		private final UUID id;
		private final String username;
		private final String schoolId;
		private final String firstName;
		private final String middleName;
		private final String lastName;
		private final String departmentName;
		private final String primaryEmailAddress;
		private final String workPhone;

		PersonFixture(String id, String username, String schoolId,
					  String firstName, String middleName, String lastName,
					  String departmentName, String primaryEmailAddress,
					  String workPhone) {
			this.id = UUID.fromString(id);
			this.username = username;
			this.schoolId = schoolId;
			this.firstName = firstName;
			this.middleName = middleName;
			this.lastName = lastName;
			this.departmentName = departmentName;
			this.primaryEmailAddress = primaryEmailAddress;
			this.workPhone = workPhone;
		}

		public UUID id() { return id; }
		public String username() { return username; }
		public String schoolId() { return schoolId; }
		public String firstName() { return firstName; }
		public String middleName() { return middleName; }
		public String lastName() { return lastName; }
		public String fullName() {
			String fname = StringUtils.trimToEmpty(firstName);
			String lname = StringUtils.trimToEmpty(lastName);
			boolean eitherNameEmpty = "".equals(fname) || "".equals(lname);
			return fname + (eitherNameEmpty ? "" : " ") + lname;
		}
		public String formalFullName() {
			String fname = StringUtils.trimToEmpty(firstName);
			String mname = StringUtils.trimToEmpty(middleName);
			String lname = StringUtils.trimToEmpty(lastName);
			boolean fnameBlank = "".equals(fname);
			boolean mnameBlank = "".equals(mname);
			boolean lnameBlank = "".equals(lname);
			return (fname + (fnameBlank || mnameBlank ? "" : " ")
					+ mname + (fnameBlank && mnameBlank ? "" : " ") + lname)
					.trim();
		}
		public String departmentName() { return departmentName; }
		public String workPhone() { return workPhone; }
		public String primaryEmailAddress() { return primaryEmailAddress; }
	}

	public static enum CampusFixture {
		TEST ( "901E104B-4DC7-43F5-A38E-581015E204E1" );

		private final UUID id;

		CampusFixture(String id) {
			this.id = UUID.fromString(id);
		}

		public UUID id() { return id; }
	}

	public static enum EarlyAlertReasonFixture {
		OTHER ( "b2d11335-5056-a51a-80ea-074f8fef94ea", "Other" );

		private final UUID id;
		// "name" is reserved for enums
		private final String title;

		EarlyAlertReasonFixture(String id, String title) {
			this.id = UUID.fromString(id);
			this.title = title;
		}

		public UUID id() { return id; }
		public String title() { return title; }
	}

	public static enum EarlyAlertSuggestionFixture {
		SEE_INSTRUCTOR ( "b2d11141-5056-a51a-80c1-c1250ba820f8", "See Instructor" ),
		TEST_DELETED_SUGGESTION ( "881df3dd-1aa6-4cb8-8817-e95daf49227a", "Test deleted suggestion - should never be seen" );

		private final UUID id;
		// "name" is reserved for enums
		private final String title;

		EarlyAlertSuggestionFixture(String id, String title) {
			this.id = UUID.fromString(id);
			this.title = title;
		}

		public UUID id() { return id; }
		public String title() { return title; }
	}

	public static enum ProgramStatusFixture {
		ACTIVE (ProgramStatus.ACTIVE_ID, "Active"),
		INACTIVE (ProgramStatus.INACTIVE_ID, "Inactive"),
		TRANSITIONED (ProgramStatus.TRANSITIONED_ID, "Transitioned"),
		NON_PARTICIPATING (ProgramStatus.NON_PARTICIPATING_ID, "Non-participating"),
		NO_SHOW (ProgramStatus.NO_SHOW, "No-Show");

		private final UUID id;
		// "name" is reserved for enums
		private final String title;

		ProgramStatusFixture(String id, String title) {
			this.id = UUID.fromString(id);
			this.title = title;
		}

		ProgramStatusFixture(UUID id, String title) {
			this.id = id;
			this.title = title;
		}

		public UUID id() { return id; }
		public String title() { return title; }
	}

	public static enum StudentTypeFixture {

		ILP ("b2d058eb-5056-a51a-80a7-8a20c30d1e91", "ILP"),
		CAP ("b2d05919-5056-a51a-80bd-03e5288de771", "CAP"),
		EAL ("b2d05939-5056-a51a-8004-d803265d2645", "EAL");

		private final UUID id;
		// "name" is reserved for enums
		private final String title;

		StudentTypeFixture(String id, String title) {
			this.id = UUID.fromString(id);
			this.title = title;
		}

		StudentTypeFixture(UUID id, String title) {
			this.id = id;
			this.title = title;
		}

		public UUID id() { return id; }
		public String title() { return title; }

	}

	/**
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public static EarlyAlert arrangeEarlyAlert(PersonService personService,
											   CampusService campusService)
			throws ObjectNotFoundException {
		final EarlyAlert obj = new EarlyAlert();
		obj.setPerson(personService.get(PersonFixture.STUDENT_0.id()));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setClosedById(PersonFixture.STUDENT_0.id());
		obj.setCourseName("Complicated Science 101");
		obj.setCampus(campusService.get(CampusFixture.TEST.id()));

		final Set<EarlyAlertSuggestion> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestion(
				EarlyAlertSuggestionFixture.SEE_INSTRUCTOR.id(),
				EarlyAlertSuggestionFixture.SEE_INSTRUCTOR.title(),
				"description", (short) 0)); // NOPMD by jon.adams on 5/21/12
		final EarlyAlertSuggestion deletedSuggestion = new EarlyAlertSuggestion(
				EarlyAlertSuggestionFixture.TEST_DELETED_SUGGESTION.id(),
				EarlyAlertSuggestionFixture.TEST_DELETED_SUGGESTION.title(),
				"description",
				(short) 0); // NOPMD
		deletedSuggestion.setObjectStatus(ObjectStatus.INACTIVE);
		earlyAlertSuggestionIds.add(deletedSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		final Set<EarlyAlertReason> earlyAlertReasonIds = Sets
				.newHashSet();
		earlyAlertReasonIds.add(new EarlyAlertReason(
				EarlyAlertReasonFixture.OTHER.id(),
				EarlyAlertReasonFixture.OTHER.title,
				"description", (short) 0)); // NOPMD by jon.adams
		obj.setEarlyAlertReasonIds(earlyAlertReasonIds);

		return obj;
	}

	/**
	 * @return An EarlyAlertRouting with Campus and EarlyAlertReason, but no
	 *         Person or group information set (left null).
	 * @throws ObjectNotFoundException
	 */
	public static EarlyAlertRouting arrangeEarlyAlertRouting(CampusService campusService)
			throws ObjectNotFoundException {
		final EarlyAlertRouting obj = new EarlyAlertRouting();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setCampus(campusService.get(CampusFixture.TEST.id()));
		obj.setEarlyAlertReason(new EarlyAlertReason(EarlyAlertReasonFixture.OTHER.id(),
				EarlyAlertReasonFixture.OTHER.title(),
				"description", (short) 0)); // NOPMD by jon.adams
		return obj;
	}

	public static void activateInProgram(PersonService personService,
										 PersonProgramStatusService personProgramStatusService,
										 ProgramStatusService programStatusService,
										 Person... persons)
			throws ObjectNotFoundException, ValidationException {
		setCurrentProgramStatus(personService, personProgramStatusService,
				programStatusService, Stubs.ProgramStatusFixture.ACTIVE,
				persons);
	}

	public static void deactivateInProgram(PersonService personService,
										   PersonProgramStatusService personProgramStatusService,
										   ProgramStatusService programStatusService,
										   Person... persons)
			throws ObjectNotFoundException, ValidationException {
		setCurrentProgramStatus(personService, personProgramStatusService,
				programStatusService, Stubs.ProgramStatusFixture.INACTIVE,
				persons);
	}

	public static void setCurrentProgramStatus(PersonService personService,
											   PersonProgramStatusService personProgramStatusService,
											   ProgramStatusService programStatusService,
											   Stubs.ProgramStatusFixture programStatus,
											   Person... persons)
			throws ObjectNotFoundException, ValidationException {
		setProgramStatus(personService, personProgramStatusService,
				programStatusService, programStatus, new Date(), null, true,
				persons);
	}

	public static void setProgramStatus(PersonService personService,
										PersonProgramStatusService personProgramStatusService,
										ProgramStatusService programStatusService,
										Stubs.ProgramStatusFixture programStatus,
										Date effectiveOn,
										Date expiredOn,
										boolean makeCurrent,
										Person... persons)
			throws ObjectNotFoundException, ValidationException {
		for ( Person person : persons ) {
			Set<PersonProgramStatus> programStatuses =
					person.getProgramStatuses();

			PersonProgramStatus personProgramStatus = new PersonProgramStatus();
			personProgramStatus.setEffectiveDate(effectiveOn);
			personProgramStatus.setExpirationDate(expiredOn);
			personProgramStatus.setProgramStatus(programStatus(programStatus,
					programStatusService));
			personProgramStatus.setPerson(person);
			programStatuses.add(personProgramStatus);
			person.setProgramStatuses(programStatuses);
			if ( makeCurrent ) {
				// person save should cascade, but make sure custom create logic fires,
				// which includes expiring whatever status might have already
				// been present
				personProgramStatusService.create(personProgramStatus);
			}
		}
	}

	public static Person person(PersonFixture personFixture,
								PersonService personService)
			throws ObjectNotFoundException {
		return personService.get(personFixture.id());
	}

	public static ProgramStatus programStatus(
			ProgramStatusFixture programStatusFixture,
			ProgramStatusService programStatusService)
			throws ObjectNotFoundException {
		return programStatusService.get(programStatusFixture.id());
	}

	public static StudentType studentType(StudentTypeFixture typeFixture,
										  StudentTypeService studentTypeService)
			throws ObjectNotFoundException {
		return studentTypeService.get(typeFixture.id());
	}
}
