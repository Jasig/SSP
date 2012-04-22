package org.studentsuccessplan.mygps.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studentsuccessplan.mygps.model.transferobject.FormOptionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormQuestionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormSectionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;
import org.studentsuccessplan.mygps.web.MyGpsStudentIntakeController;
import org.studentsuccessplan.ssp.dao.PersonChallengeDao;
import org.studentsuccessplan.ssp.dao.PersonEducationGoalDao;
import org.studentsuccessplan.ssp.dao.PersonEducationLevelDao;
import org.studentsuccessplan.ssp.dao.PersonFundingSourceDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.EducationLevelDao;
import org.studentsuccessplan.ssp.dao.reference.FundingSourceDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.model.PersonDemographics;
import org.studentsuccessplan.ssp.model.PersonEducationGoal;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.model.PersonEducationPlan;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.model.reference.EmploymentShifts;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.model.reference.Genders;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.model.tool.Tools;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonConfidentialityDisclosureAgreementService;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;
import org.studentsuccessplan.ssp.service.reference.VeteranStatusService;
import org.studentsuccessplan.ssp.service.tool.PersonToolService;
import org.studentsuccessplan.ssp.util.SspStringUtils;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Sets;

@Service
public class StudentIntakeFormManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsStudentIntakeController.class);

	@Autowired
	private transient ChallengeDao challengeDao;

	@Autowired
	private transient ChildCareArrangementService childCareArrangementService;

	@Autowired
	private transient CitizenshipService citizenshipService;

	@Autowired
	private transient EducationLevelDao educationLevelDao;

	@Autowired
	private transient EducationGoalService educationGoalService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Autowired
	private transient FundingSourceDao fundingSourceDao;

	@Autowired
	private MaritalStatusService maritalStatusService;

	@Autowired
	private PersonChallengeDao studentChallengeDao;

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonEducationGoalDao studentEducationGoalDao;

	@Autowired
	private PersonEducationLevelDao studentEducationLevelDao;

	@Autowired
	private PersonFundingSourceDao studentFundingSourceDao;

	@Autowired
	private PersonConfidentialityDisclosureAgreementService studentConfidentialityDisclosureAgreementService;

	@Autowired
	private PersonToolService personToolService;

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private VeteranStatusService veteranStatusService;

	@Autowired
	private SecurityService securityService;

	// Default value for unfilled drop-down
	public static final String DEFAULT_DROPDOWN_LIST_LABEL = "-- Select One --";
	public static final String DEFAULT_DROPDOWN_LIST_VALUE = "";

	// Form Types
	public static final String FORM_TYPE_AGREEMENT = "agreement";
	public static final String FORM_TYPE_CHECKLIST = "checklist";
	public static final String FORM_TYPE_LABEL = "label";
	public static final String FORM_TYPE_RADIOLIST = "radiolist";
	public static final String FORM_TYPE_SELECT = "select";
	public static final String FORM_TYPE_TEXTAREA = "textarea";
	public static final String FORM_TYPE_TEXTINPUT = "textinput";

	// Confidentiality disclosure
	public static final UUID SECTION_CONFIDENTIALITY_ID = UUID
			.fromString("5e322321-c296-4d4d-94c8-ebf34e374df3");
	public static final UUID SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID = UUID
			.fromString("794b587b-981d-41d9-9d80-bfc6b74d0997");

	// Personal
	public static final UUID SECTION_PERSONAL_ID = UUID
			.fromString("ce8ddcd9-0bb4-4d54-b1bd-c0af72ee11ca");
	public static final UUID SECTION_PERSONAL_QUESTION_FIRSTNAME_ID = UUID
			.fromString("a68b0fcf-888e-46bd-b867-a664db93f57e");
	public static final UUID SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID = UUID
			.fromString("c881a6c3-2f8f-4ad0-9596-6327982491eb");
	public static final UUID SECTION_PERSONAL_QUESTION_LASTNAME_ID = UUID
			.fromString("12487490-5206-4169-9d3f-1708fc6592dd");
	public static final UUID SECTION_PERSONAL_QUESTION_STUDENTID_ID = UUID
			.fromString("6647b9ab-27a2-4173-8c75-a21140f8e73b");
	public static final UUID SECTION_PERSONAL_QUESTION_BIRTHDATE_ID = UUID
			.fromString("1353f252-ddb4-40cf-bb4c-8da30d832722");
	public static final UUID SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID = UUID
			.fromString("3e70fbff-95cd-4d10-ae18-fcd756dd749f");
	public static final UUID SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID = UUID
			.fromString("c11e5428-2c1e-4126-a603-0c97dea5651b");
	public static final UUID SECTION_PERSONAL_QUESTION_HOMEPHONE_ID = UUID
			.fromString("f4d880e7-ff98-431b-95f9-c973bfb74924");
	public static final UUID SECTION_PERSONAL_QUESTION_WORKPHONE_ID = UUID
			.fromString("de35fada-d530-4df2-9428-3ce0162b832d");
	public static final UUID SECTION_PERSONAL_QUESTION_CELLPHONE_ID = UUID
			.fromString("9b6f5bec-1ac4-4253-af5c-e52e2050e70f");
	public static final UUID SECTION_PERSONAL_QUESTION_ADDRESS_ID = UUID
			.fromString("dcf977fb-8a64-46ee-8d82-0920a3d94560");
	public static final UUID SECTION_PERSONAL_QUESTION_CITY_ID = UUID
			.fromString("872e15e9-0b90-4ac4-96a7-4af8fd1f187a");
	public static final UUID SECTION_PERSONAL_QUESTION_STATE_ID = UUID
			.fromString("36a8a8ba-d0f7-4e0f-beda-6f539c4f65c3");
	public static final UUID SECTION_PERSONAL_QUESTION_ZIPCODE_ID = UUID
			.fromString("253fec7e-c4c3-4fd2-92db-ebcf7ae70322");

	// Demographics
	public static final UUID SECTION_DEMOGRAPHICS_ID = UUID
			.fromString("a4b1c09d-15e6-4f44-8cf8-565eea074bf5");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID = UUID
			.fromString("39e4740b-966e-40d2-bbb1-6915e63fe802");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID = UUID
			.fromString("c8c6889a-f07e-4f20-9aa1-2f169232578c");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID = UUID
			.fromString("e8674ff9-3ae5-48ac-a596-72ea7096295d");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID = UUID
			.fromString("44c3bd2e-a8f9-4a40-b761-d22e43f01c4f");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID = UUID
			.fromString("7e86e36d-12ff-4447-bd9c-e0716bfc3a3c");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID = UUID
			.fromString("8bd42e34-f4c6-4909-b540-38015440ba9f");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID = UUID
			.fromString("00b334c3-901e-46b9-ac70-6845114b63f4");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID = UUID
			.fromString("83e706c4-bbd4-4b59-a0b5-f6fe5094d5ab");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID = UUID
			.fromString("11221b47-d32d-4c30-9701-7072ab175483");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID = UUID
			.fromString("d2278348-1b16-466d-aa82-b88b58818dc9");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID = UUID
			.fromString("6517ad4d-28cc-4cf5-8d32-9eeeac101257");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID = UUID
			.fromString("9e07876c-47b9-4e68-8854-c48fd79527a9");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID = UUID
			.fromString("8fb34e96-1db7-4078-adc8-0e73b6ca73c9");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID = UUID
			.fromString("5d9a5b30-13c8-4498-80b8-2bba32ff8178");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID = UUID
			.fromString("9d818a23-7d99-4189-9131-cfef3253a7b1");
	public static final UUID SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID = UUID
			.fromString("316a04e4-6735-47b2-875b-fc2b56a95ca3");

	// EducationalPlan
	public static final UUID SECTION_EDUCATIONPLAN_ID = UUID
			.fromString("28235c89-214c-40bd-9181-bc2daaa8441d");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID = UUID
			.fromString("73f95353-ed79-4402-afbb-b0e744e07d5d");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID = UUID
			.fromString("b5a9d91e-b95b-4a7b-b485-7d69cf83b786");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID = UUID
			.fromString("42d36849-2095-444c-81a4-6d7a38226a65");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID = UUID
			.fromString("5ce514a2-45db-4b8a-b054-26df63420d22");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMMODATIONS_ID = UUID
			.fromString("67bcc088-3fc4-4d0f-aa88-4c34d36acc8b");
	public static final UUID SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID = UUID
			.fromString("30def0f2-d89a-47e4-a8f3-61471c570f3b");

	// Education Levels
	public static final UUID SECTION_EDUCATIONLEVEL_ID = UUID
			.fromString("45ca75b1-e9b5-4595-928b-07be382475cd");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID = UUID
			.fromString("e4557e29-8aff-4f03-94f1-d7a3475673d7");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID = UUID
			.fromString("798a886c-c4a2-408d-9b32-299cd5b8bffe");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID = UUID
			.fromString("f412ca4c-ddac-40e3-8338-854c6c2eb29b");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID = UUID
			.fromString("cef96a6d-85fb-4b5a-8a51-f6d1443ca830");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID = UUID
			.fromString("a0657f72-9650-4dec-b146-dafee6c6e59c");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID = UUID
			.fromString("9fb9c0c3-9df5-45a9-998f-986c6c23f1e9");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID = UUID
			.fromString("d6cc72f9-57ff-4e39-8568-67750395618d");
	public static final UUID SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID = UUID
			.fromString("ac1c43c0-5705-479e-8236-bb6dca4744c9");

	// Education Goal
	public static final UUID SECTION_EDUCATIONGOAL_ID = UUID
			.fromString("dad92c0e-c185-4baa-b114-9cbedebeacd5");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID = UUID
			.fromString("8055f756-afc1-487f-a67f-1e66d5db3907");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID = UUID
			.fromString("a222b26f-c325-443e-8df8-5db3079353c3");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID = UUID
			.fromString("98abe279-9cb0-4df1-859b-a7b96ec82b3c");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID = UUID
			.fromString("4e3eceb7-a832-43b9-bccd-151e77fd3a84");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID = UUID
			.fromString("6dda82d4-5f6a-4187-bf56-4d26730be054");
	public static final UUID SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID = UUID
			.fromString("ca44bce9-3e12-46e4-b83d-51a60878bb8c");

	// Funding
	public static final UUID SECTION_FUNDING_ID = UUID
			.fromString("e14e5879-fb06-47e4-93af-ed4dfb273821");
	public static final UUID SECTION_FUNDING_QUESTION_FUNDING_ID = UUID
			.fromString("6178c564-4247-4d79-9f6a-075e2689e7e0");
	public static final UUID SECTION_FUNDING_QUESTION_OTHER_ID = UUID
			.fromString("f6f60253-e62c-4f6c-898b-0392b43bf2d5");
	public static final UUID SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID = UUID
			.fromString("B2D05DEC-5056-A51A-8001-FE8BDD379C5B");

	// Challenge
	public static final UUID SECTION_CHALLENGE_ID = UUID
			.fromString("47ebe563-1b68-42aa-ad23-93153cb99cce");
	public static final UUID SECTION_CHALLENGE_QUESTION_CHALLENGE_ID = UUID
			.fromString("3f8d0dc4-4506-4cd6-95e6-3d74c0a07f80");
	public static final UUID SECTION_CHALLENGE_QUESTION_OTHER_ID = UUID
			.fromString("839dc532-1aec-4580-8294-8c97bb72fa72");

	public FormTO create() throws ObjectNotFoundException {

		FormTO formTO = new FormTO();
		List<FormSectionTO> formSections = new ArrayList<FormSectionTO>();

		FormSectionTO confidentialitySection = buildConfidentialitySection();
		if (null != confidentialitySection) {
			formSections.add(confidentialitySection);
		}

		formSections.add(buildPersonalSection());
		formSections.add(buildDemographicsSection());
		formSections.add(buildEducationPlanSection());
		formSections.add(buildEducationLevelsSection());
		formSections.add(buildEducationGoalSection());
		formSections.add(buildFundingSection());
		formSections.add(buildChallengesSection());

		// FormTO
		formTO.setId(UUID.randomUUID());
		formTO.setLabel("Student Intake");
		formTO.setSections(formSections);

		return formTO;
	}

	/**
	 * 
	 * @throws ObjectNotFoundException
	 *             If currently authenticated user data could not be refreshed.
	 * @return A form transfer object filled with current user data.
	 */
	public FormTO populate() throws ObjectNotFoundException {
		FormTO formTO = create();
		Person student = securityService.currentUser().getPerson();

		// now refresh Person from Hibernate so lazy-loading works in case the
		// person instance was loaded in a previous request
		student = personService.get(student.getId());

		/* Confidentiality disclosure */

		FormSectionTO formSectionTO = formTO
				.getFormSectionById(SECTION_CONFIDENTIALITY_ID);
		if (null != formSectionTO) {
			formSectionTO.getFormQuestionById(
					SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID).setValueBoolean(
					(null == studentConfidentialityDisclosureAgreementService
							.hasStudentAgreedToOne(student)));
		}

		/* Personal */

		formSectionTO = formTO.getFormSectionById(SECTION_PERSONAL_ID);

		// First Name
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_FIRSTNAME_ID).setValue(
				student.getFirstName());

		// Middle Initial
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID).setValue(
				student.getMiddleInitial());

		// Last Name
		formSectionTO
				.getFormQuestionById(SECTION_PERSONAL_QUESTION_LASTNAME_ID)
				.setValue(student.getLastName());

		// Student Id
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_STUDENTID_ID).setValue(
				student.getUserId());

		// Birthdate
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_BIRTHDATE_ID).setValue(
				student.getBirthDate() == null ? null : new SimpleDateFormat(
						"MM/dd/yyyy").format(student.getBirthDate()));

		// School Email
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID).setValue(
				student.getPrimaryEmailAddress());

		// Home Email
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID).setValue(
				student.getSecondaryEmailAddress());

		// Home Phone
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_HOMEPHONE_ID).setValue(
				student.getHomePhone());

		// Work Phone
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_WORKPHONE_ID).setValue(
				student.getWorkPhone());

		// Cell Phone
		formSectionTO.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_CELLPHONE_ID).setValue(
				student.getCellPhone());

		// Address
		formSectionTO.getFormQuestionById(SECTION_PERSONAL_QUESTION_ADDRESS_ID)
				.setValue(student.getAddressLine1());

		// City
		formSectionTO.getFormQuestionById(SECTION_PERSONAL_QUESTION_CITY_ID)
				.setValue(student.getCity());

		// State
		if (student.getState() == null) {
			formSectionTO
					.getFormQuestionById(SECTION_PERSONAL_QUESTION_STATE_ID)
					.getOptions()
					.add(0,
							new FormOptionTO(UUID.randomUUID(),
									DEFAULT_DROPDOWN_LIST_LABEL,
									DEFAULT_DROPDOWN_LIST_VALUE));
			formSectionTO.getFormQuestionById(
					SECTION_PERSONAL_QUESTION_STATE_ID).setValue(
					DEFAULT_DROPDOWN_LIST_VALUE);
		} else {
			formSectionTO.getFormQuestionById(
					SECTION_PERSONAL_QUESTION_STATE_ID).setValue(
					student.getState());
		}

		// ZIP Code
		formSectionTO.getFormQuestionById(SECTION_PERSONAL_QUESTION_ZIPCODE_ID)
				.setValue(student.getZipCode());

		/* Demographics */

		if (student.getDemographics() != null) {
			formSectionTO = formTO.getFormSectionById(SECTION_DEMOGRAPHICS_ID);

			// Marital Status
			if (student.getDemographics().getMaritalStatus() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID)
						.setValue(DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID)
						.setValue(
								student.getDemographics().getMaritalStatus()
										.getId().toString());
			}

			// Ethnicity
			if (student.getDemographics().getEthnicity() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID).setValue(
						DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID).setValue(
						student.getDemographics().getEthnicity().getId()
								.toString());
			}

			// Gender
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID).setValue(
					student.getDemographics().getGender().getCode());

			// Citizenship
			if (student.getDemographics().getCitizenship() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID).setValue(
						DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID).setValue(
						student.getDemographics().getCitizenship().getId()
								.toString());
			}

			// Country of Citizenship -> Shown if Citizenship is "International"
			formSectionTO
					.getFormQuestionById(
							SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID)
					.setValue(
							student.getDemographics().getCountryOfCitizenship());

			// Veteran Status
			if (student.getDemographics().getVeteranStatus() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID)
						.setValue(DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID)
						.setValueUUID(
								student.getDemographics().getVeteranStatus()
										.getId());
			}

			// Primary Caregiver
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID)
					.setValueBoolean(
							student.getDemographics().isPrimaryCaregiver());

			// How Many Children
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID).setValue(
					String.valueOf(student.getDemographics()
							.getNumberOfChildren()));

			// Children Ages
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID).setValue(
					student.getDemographics().getChildAges());

			// Childcare Needed
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID)
					.setValueBoolean(
							student.getDemographics().isChildCareNeeded());

			// Childcare Arrangements
			if (student.getDemographics().getChildCareArrangement() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID)
						.setValue(DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID)
						.setValueAbstractReference(
								student.getDemographics()
										.getChildCareArrangement());
			}

			// Employed
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID).setValueBoolean(
					student.getDemographics().isEmployed());

			// Employer
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID).setValue(
					student.getDemographics().getPlaceOfEmployment());

			// Shift
			if (student.getDemographics().getShift() == null) {
				formSectionTO
						.getFormQuestionById(
								SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										DEFAULT_DROPDOWN_LIST_LABEL,
										DEFAULT_DROPDOWN_LIST_VALUE));
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID).setValue(
						DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				formSectionTO.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID).setValue(
						student.getDemographics().getShift().getCode());
			}

			// Wage
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID).setValue(
					student.getDemographics().getWage());

			// Hours worked per week
			formSectionTO.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID)
					.setValue(
							student.getDemographics()
									.getTotalHoursWorkedPerWeek());

		}

		/* Education Plan */
		if (student.getEducationPlan() != null) {

			formSectionTO = formTO.getFormSectionById(SECTION_EDUCATIONPLAN_ID);

			// Student Status
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID)
					.setValueAbstractReference(
							student.getEducationPlan().getStudentStatus());

			// New Student Orientation
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID)
					.setValueBoolean(
							student.getEducationPlan()
									.isNewOrientationComplete());

			// Registered for Classes
			formSectionTO
					.getFormQuestionById(
							SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID)
					.setValueBoolean(
							student.getEducationPlan().isRegisteredForClasses());

			// Have Parents Obtained a College Degree
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID)
					.setValueBoolean(
							student.getEducationPlan()
									.isCollegeDegreeForParents());

			// Special Needs or Accommodations
			formSectionTO
					.getFormQuestionById(
							SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMMODATIONS_ID)
					.setValueBoolean(
							student.getEducationPlan().isSpecialNeeds());

			// Grade at Highest Education Level
			formSectionTO
					.getFormQuestionById(
							SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID)
					.setValue(
							student.getEducationPlan()
									.getGradeTypicallyEarned());
		}

		/* Education Level */

		FormSectionTO educationLevelFormSection = formTO
				.getFormSectionById(SECTION_EDUCATIONLEVEL_ID);

		FormQuestionTO educationLevelFormQuestion = educationLevelFormSection
				.getFormQuestionById(SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID);

		List<String> educationLevelFormQuestionValues = new ArrayList<String>();

		for (PersonEducationLevel studentEducationLevel : student
				.getEducationLevels()) {

			educationLevelFormQuestionValues.add(educationLevelFormQuestion
					.getFormOptionById(
							studentEducationLevel.getEducationLevel().getId())
					.getValue());

			if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.NO_DIPLOMA_NO_GED_ID)) {

				educationLevelFormSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID)
						.setValue(
								SspStringUtils
										.stringFromYear(studentEducationLevel
												.getLastYearAttended()));

				educationLevelFormSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID)
						.setValue(
								String.valueOf(studentEducationLevel
										.getHighestGradeCompleted()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.GED_ID)) {

				educationLevelFormSection.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID)
						.setValue(
								SspStringUtils
										.stringFromYear(studentEducationLevel
												.getGraduatedYear()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.HIGH_SCHOOL_GRADUATION_ID)) {

				educationLevelFormSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID)
						.setValue(
								SspStringUtils
										.stringFromYear(studentEducationLevel
												.getGraduatedYear()));

				educationLevelFormSection.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID)
						.setValue(studentEducationLevel.getSchoolName());

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.SOME_COLLEGE_CREDITS_ID)) {

				educationLevelFormSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID)
						.setValue(
								SspStringUtils
										.stringFromYear(studentEducationLevel
												.getLastYearAttended()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.OTHER_ID)) {

				educationLevelFormSection.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID)
						.setValue(studentEducationLevel.getDescription());

			}
		}

		educationLevelFormQuestion.setValues(educationLevelFormQuestionValues);

		/* Education Goal */

		if (student.getEducationGoal() != null) {

			formSectionTO = formTO.getFormSectionById(SECTION_EDUCATIONGOAL_ID);

			// Goal
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID)
					.setValueAbstractReference(
							student.getEducationGoal().getEducationGoal());

			// Goal Description
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID)
					.setValue(student.getEducationGoal().getDescription());

			// Other Description
			/*
			 * :TODO formSectionTO.getFormQuestionById(
			 * SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID,
			 * formSectionTO).setValue(
			 * student.getEducationGoal().getOtherDescription());
			 */

			// Sure of Major
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID).setValueInt(
					student.getEducationGoal().getHowSureAboutMajor());

			// Career Goal
			formSectionTO.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID).setValue(
					student.getEducationGoal().getPlannedOccupation());

			// Military Branch Description
			formSectionTO
					.getFormQuestionById(
							SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID)
					.setValue(
							student.getEducationGoal()
									.getMilitaryBranchDescription());
		}

		/* Funding Sources */

		FormSectionTO fundingFormSection = formTO
				.getFormSectionById(SECTION_FUNDING_ID);

		FormQuestionTO fundingQuestion = fundingFormSection
				.getFormQuestionById(SECTION_FUNDING_QUESTION_FUNDING_ID);

		List<String> fundingQuestionValues = new ArrayList<String>();

		for (PersonFundingSource studentFundingSource : student
				.getFundingSources()) {
			fundingQuestionValues.add(studentFundingSource.getFundingSource()
					.getId().toString());

			if (studentFundingSource.getFundingSource().getId()
					.equals(SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID)) {

				FormQuestionTO fundingOtherQuestion = fundingFormSection
						.getFormQuestionById(SECTION_FUNDING_QUESTION_OTHER_ID);

				fundingOtherQuestion.setValue(studentFundingSource
						.getDescription());
			}
		}

		fundingQuestion.setValues(fundingQuestionValues);

		/* Challenges */

		FormSectionTO challengeFormSection = formTO
				.getFormSectionById(SECTION_CHALLENGE_ID);

		FormQuestionTO challengeQuestion = challengeFormSection
				.getFormQuestionById(SECTION_CHALLENGE_QUESTION_CHALLENGE_ID);
		List<String> challengeQuestionValues = new ArrayList<String>();

		for (PersonChallenge studentChallenge : student.getChallenges()) {
			challengeQuestionValues.add(studentChallenge.getChallenge().getId()
					.toString());
		}

		challengeQuestion.setValues(challengeQuestionValues);

		return formTO;
	}

	public Person save(final FormTO formTO) throws ObjectNotFoundException {

		// Refresh Person from Hibernate so lazy-loading works in case the
		// person instance was loaded in a previous request
		Person student = personService.get(securityService.currentUser()
				.getPerson().getId());

		/* Add intake form to student's record */
		personToolService.addToolToStudent(student, Tools.INTAKE);

		/* Confidentiality disclosure */

		FormSectionTO confidentialitySection = formTO
				.getFormSectionById(SECTION_CONFIDENTIALITY_ID);

		if (null != confidentialitySection
				&& confidentialitySection.getFormQuestionById(
						SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID).getValue() != null) {
			Boolean agreed = SspStringUtils
					.booleanFromString(confidentialitySection
							.getFormQuestionById(
									SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID)
							.getValue());
			if (agreed) {
				studentConfidentialityDisclosureAgreementService
						.studentAgreed(student);
			}
		}

		/* Personal */

		FormSectionTO personalSection = formTO
				.getFormSectionById(SECTION_PERSONAL_ID);

		// First Name
		student.setFirstName(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_FIRSTNAME_ID).getValue());

		// Middle Initial
		student.setMiddleInitial(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID).getValue());

		// Last Name
		student.setLastName(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_LASTNAME_ID).getValue());

		// Student Id
		student.setUserId(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_STUDENTID_ID).getValue());

		// Birthdate
		FormQuestionTO birthDateQuestion = personalSection
				.getFormQuestionById(SECTION_PERSONAL_QUESTION_BIRTHDATE_ID);

		try {
			student.setBirthDate(birthDateQuestion.getValue() == null ? null
					: new SimpleDateFormat("MM/dd/yyyy")
							.parse(birthDateQuestion.getValue()));
		} catch (ParseException pe) {
			// just leave the birthDate alone
			LOGGER.warn("Could not parse birth date from MyGPS StudentIntake: "
					+ birthDateQuestion.getValue());
		}

		// School Email
		student.setPrimaryEmailAddress(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID).getValue());

		// Home Email
		student.setSecondaryEmailAddress(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID).getValue());

		// Home Phone
		student.setHomePhone(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_HOMEPHONE_ID).getValue());

		// Work Phone
		student.setWorkPhone(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_WORKPHONE_ID).getValue());

		// Cell Phone
		student.setCellPhone(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_CELLPHONE_ID).getValue());

		// Address
		student.setAddressLine1(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_ADDRESS_ID).getValue());

		// City
		student.setCity(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_CITY_ID).getValue());

		// State
		FormQuestionTO stateQuestion = personalSection
				.getFormQuestionById(SECTION_PERSONAL_QUESTION_STATE_ID);

		if (DEFAULT_DROPDOWN_LIST_VALUE.equals(stateQuestion.getValue())) {
			student.setState(null);
		} else {
			student.setState(stateQuestion.getValue());
		}

		// ZIP Code
		student.setZipCode(personalSection.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_ZIPCODE_ID).getValue());

		/* Demographics */

		FormSectionTO demographicsSection = formTO
				.getFormSectionById(SECTION_DEMOGRAPHICS_ID);

		PersonDemographics demographics = student.getDemographics();

		if (demographics == null) {
			demographics = new PersonDemographics();
			student.setDemographics(demographics);
		}

		demographics.setChildAges(demographicsSection.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID).getValue());

		// Childcare Arrangements
		FormQuestionTO childCareArrangementQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID);

		if (childCareArrangementQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setChildCareArrangement(null);
		} else {
			// get matching FormOption since IDs do not line up with database
			List<ChildCareArrangement> childCareArrangements = (List<ChildCareArrangement>) childCareArrangementService
					.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
					.getRows();
			for (ChildCareArrangement childCareArrangement : childCareArrangements) {
				if (childCareArrangement.getName().equals(
						childCareArrangementQuestion.getValue())) {
					demographics.setChildCareArrangement(childCareArrangement);
					break;
				}
			}
		}

		// Childcare Needed
		FormQuestionTO childCareNeededQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID);

		if ((childCareNeededQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (childCareNeededQuestion.getValue() == null)) {
			demographics.setChildCareNeeded(false);
		} else {
			demographics.setChildCareNeeded(SspStringUtils
					.booleanFromString(childCareNeededQuestion.getValue()));
		}

		// Citizenship
		FormQuestionTO citizenshipQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID);

		if (citizenshipQuestion.getValue() == null
				|| citizenshipQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setCitizenship(null);
		} else {
			// get matching FormOption since IDs do not line up with database
			String citizenshipNameSelected = citizenshipQuestion
					.getFormOptionByValue(citizenshipQuestion.getValue())
					.getLabel();
			Collection<Citizenship> citizenships = citizenshipService.getAll(
					new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
			for (Citizenship citizenship : citizenships) {
				if (citizenship.getName().equals(citizenshipNameSelected)) {
					demographics.setCitizenship(citizenship);
					break;
				}
			}
		}

		demographics.setCountryOfCitizenship(demographicsSection
				.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID)
				.getValue());

		// Employed
		FormQuestionTO employedQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID);

		if (DEFAULT_DROPDOWN_LIST_VALUE.equals(employedQuestion.getValue())
				|| (employedQuestion.getValue() == null)) {
			demographics.setEmployed(false);
		} else {
			demographics.setEmployed(SspStringUtils
					.booleanFromString(employedQuestion.getValue()));
		}

		demographics.setPlaceOfEmployment(demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID)
				.getValue());

		// Ethnicity
		FormQuestionTO ethnicityQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID);

		if ((ethnicityQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (ethnicityQuestion.getValue() == null)) {
			demographics.setEthnicity(null);
		} else {
			Collection<Ethnicity> ethnicities = ethnicityService.getAll(
					new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
			for (Ethnicity ethnicity : ethnicities) {
				if (ethnicity.getName().equals(ethnicityQuestion.getValue())) {
					demographics.setEthnicity(ethnicity);
					break;
				}
			}
		}

		// Gender
		FormQuestionTO genderQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID);

		if ((genderQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (genderQuestion.getValue() == null)) {
			demographics.setGender(null);
		} else {
			demographics.setGender(Genders.valueOf(genderQuestion.getValue()));
		}

		demographics.setTotalHoursWorkedPerWeek(demographicsSection
				.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID)
				.getValue());

		// Marital Status
		FormQuestionTO maritalStatusQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID);

		if ((maritalStatusQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (maritalStatusQuestion.getValue() == null)) {
			demographics.setMaritalStatus(null);
		} else {
			Collection<MaritalStatus> maritalStatuses = maritalStatusService
					.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
					.getRows();
			for (MaritalStatus ms : maritalStatuses) {
				if (ms.getName().equals(maritalStatusQuestion.getValue())) {
					demographics.setMaritalStatus(ms);
					break;
				}
			}
		}

		if (demographicsSection.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID).getValue() != null) {
			demographics.setNumberOfChildren(Integer
					.parseInt(demographicsSection.getFormQuestionById(
							SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID)
							.getValue()));
		}

		// Primary caregiver
		FormQuestionTO primaryCaregiverQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID);

		if ((primaryCaregiverQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (primaryCaregiverQuestion.getValue() == null)) {
			demographics.setPrimaryCaregiver(false);
		} else {
			demographics.setPrimaryCaregiver(SspStringUtils
					.booleanFromString(primaryCaregiverQuestion.getValue()));
		}

		// Shift
		FormQuestionTO shiftQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID);

		if ((shiftQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (shiftQuestion.getValue() == null)) {
			demographics.setShift(null);
		} else {
			demographics
					.setShift(EmploymentShifts.getEnumByValue(shiftQuestion
							.getFormOptionByValue(shiftQuestion.getValue())
							.getLabel()));
		}

		// Veteran Status
		FormQuestionTO veteranStatusQuestion = demographicsSection
				.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID);

		if (veteranStatusQuestion.getValue() == null) {
			demographics.setVeteranStatus(null);
		} else {
			demographics.setVeteranStatus(veteranStatusService
					.get(veteranStatusQuestion.getFormOptionByValue(
							veteranStatusQuestion.getValue()).getId()));
		}

		demographics.setWage(demographicsSection.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID).getValue());

		/* Education Plan */

		FormSectionTO educationPlanSection = formTO
				.getFormSectionById(SECTION_EDUCATIONPLAN_ID);

		PersonEducationPlan educationPlan = student.getEducationPlan();

		if (educationPlan == null) {
			educationPlan = new PersonEducationPlan();
			student.setEducationPlan(educationPlan);
		}

		educationPlan.setNewOrientationComplete(educationPlanSection
				.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID)
				.getValue() == null ? false : SspStringUtils
				.booleanFromString(educationPlanSection.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID)
						.getValue()));

		FormQuestionTO gradeAtHighestEducationLevelQuestion = educationPlanSection
				.getFormQuestionById(SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID);
		if (StringUtils
				.isEmpty(gradeAtHighestEducationLevelQuestion.getValue())) {
			educationPlan.setGradeTypicallyEarned(null);
		} else {
			for (FormOptionTO option : gradeAtHighestEducationLevelQuestion
					.getOptions()) {
				if (gradeAtHighestEducationLevelQuestion.getValue().equals(
						option.getValue())) {
					educationPlan.setGradeTypicallyEarned(option.getLabel());
					break;
				}
			}
		}

		// Parents Have College Degree
		FormQuestionTO parentsHaveCollegeDegreeQuestion = educationPlanSection
				.getFormQuestionById(SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID);

		if ((parentsHaveCollegeDegreeQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (parentsHaveCollegeDegreeQuestion.getValue() == null)) {
			educationPlan.setCollegeDegreeForParents(false);
		} else {
			educationPlan.setCollegeDegreeForParents(SspStringUtils
					.booleanFromString(parentsHaveCollegeDegreeQuestion
							.getValue()));
		}

		educationPlan.setRegisteredForClasses(educationPlanSection
				.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID)
				.getValue() == null ? false : SspStringUtils
				.booleanFromString(educationPlanSection.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID)
						.getValue()));

		// Require Special Accommodation
		FormQuestionTO requireSpecialAccomodationQuestion = educationPlanSection
				.getFormQuestionById(SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMMODATIONS_ID);

		if ((requireSpecialAccomodationQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (requireSpecialAccomodationQuestion.getValue() == null)) {
			educationPlan.setSpecialNeeds(false);
		} else {
			educationPlan.setSpecialNeeds(SspStringUtils
					.booleanFromString(requireSpecialAccomodationQuestion
							.getValue()));
		}

		// Student Status
		FormQuestionTO studentStatusQuestion = educationPlanSection
				.getFormQuestionById(SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID);

		if ((studentStatusQuestion.getValue() == DEFAULT_DROPDOWN_LIST_VALUE)
				|| (studentStatusQuestion.getValue() == null)) {
			educationPlan.setStudentStatus(null);
		} else {
			Collection<StudentStatus> studentStatuses = studentStatusService
					.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
					.getRows();
			for (StudentStatus ss : studentStatuses) {
				if (ss.getName().equals(studentStatusQuestion.getValue())) {
					educationPlan.setStudentStatus(studentStatusService.get(ss
							.getId()));
				}
			}
		}

		/* Education Level */

		FormSectionTO educationLevelSection = formTO
				.getFormSectionById(SECTION_EDUCATIONLEVEL_ID);

		FormQuestionTO educationLevelQuestion = educationLevelSection
				.getFormQuestionById(SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID);

		String educationLevelQuestionValue = null;

		// Delete all existing education levels
		Set<PersonEducationLevel> studentEducationLevels = student
				.getEducationLevels();

		for (PersonEducationLevel studentEducationLevel : studentEducationLevels) {
			studentEducationLevelDao.delete(studentEducationLevel);
		}

		studentEducationLevels.clear();

		// Create/recreate all new ones
		for (FormOptionTO formOption : educationLevelQuestion.getOptions()) {

			educationLevelQuestionValue = null;

			PersonEducationLevel studentEducationLevel = new PersonEducationLevel();
			studentEducationLevel.setEducationLevel(educationLevelDao
					.load(formOption.getId()));
			studentEducationLevel.setPerson(student);

			// We only continue if this option has been selected by the user
			boolean optionSelected = false;
			if (educationLevelQuestion.getValues() != null) {
				for (String selectedLevel : educationLevelQuestion.getValues()) {
					if (formOption.getValue().equals(selectedLevel)) {
						optionSelected = true;
						break;
					}
				}
			}

			if (!optionSelected) {
				continue;
			}

			if (formOption.getId().equals(EducationLevel.NO_DIPLOMA_NO_GED_ID)) {

				educationLevelQuestionValue = educationLevelSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID)
						.getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setLastYearAttended(Integer
							.parseInt(educationLevelQuestionValue));
				}

				educationLevelQuestionValue = educationLevelSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID)
						.getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setHighestGradeCompleted(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(EducationLevel.GED_ID)) {

				educationLevelQuestionValue = educationLevelSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID)
						.getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setGraduatedYear(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(
					EducationLevel.HIGH_SCHOOL_GRADUATION_ID)) {

				educationLevelQuestionValue = educationLevelSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID)
						.getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setGraduatedYear(Integer
							.parseInt(educationLevelQuestionValue));
				}

				studentEducationLevel
						.setSchoolName(educationLevelSection
								.getFormQuestionById(
										SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID)
								.getValue());

			} else if (formOption.getId().equals(
					EducationLevel.SOME_COLLEGE_CREDITS_ID)) {

				educationLevelQuestionValue = educationLevelSection
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID)
						.getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setLastYearAttended(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(EducationLevel.OTHER_ID)) {

				studentEducationLevel
						.setDescription(educationLevelSection
								.getFormQuestionById(
										SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID)
								.getValue());

			}

			// TODO: Is this save necessary, or does add to the set below
			// persist the new instance?
			studentEducationLevelDao.save(studentEducationLevel);

			// Add new object to the student levels set, so it can be returned.
			student.getEducationLevels().add(studentEducationLevel);
		}

		/* Education Goal */
		FormSectionTO educationGoalSection = formTO
				.getFormSectionById(SECTION_EDUCATIONGOAL_ID);

		PersonEducationGoal studentEducationGoal = student.getEducationGoal();

		FormQuestionTO educationGoalQuestion = educationGoalSection
				.getFormQuestionById(SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID);

		if (studentEducationGoal == null
				&& educationGoalQuestion.getValue() != null) {
			studentEducationGoal = new PersonEducationGoal();
			student.setEducationGoal(studentEducationGoal);
		}

		if (studentEducationGoal != null) {
			Collection<EducationGoal> educationGoals = educationGoalService
					.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
					.getRows();
			for (EducationGoal eg : educationGoals) {
				if (eg.getName().equals(
						educationGoalQuestion.getFormOptionByValue(
								educationGoalQuestion.getValue()).getLabel())) {
					studentEducationGoal.setEducationGoal(educationGoalService
							.get(eg.getId()));
				}
			}

			studentEducationGoal.setPlannedOccupation(educationGoalSection
					.getFormQuestionById(
							SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID)
					.getValue());

			studentEducationGoal.setDescription(educationGoalSection
					.getFormQuestionById(
							SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID)
					.getValue());

			studentEducationGoal
					.setMilitaryBranchDescription(educationGoalSection
							.getFormQuestionById(
									SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID)
							.getValue());

			// :TODO
			// studentEducationGoal.setOtherDescription(educationGoalSection.getFormQuestionById(SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID).getValue());

			studentEducationGoal.setHowSureAboutMajor(Integer
					.valueOf(educationGoalSection.getFormQuestionById(
							SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID)
							.getValue()));

			studentEducationGoalDao.save(studentEducationGoal);
		}

		/* Funding */
		FormSectionTO fundingSection = formTO
				.getFormSectionById(SECTION_FUNDING_ID);
		FormQuestionTO fundingQuestion = fundingSection
				.getFormQuestionById(SECTION_FUNDING_QUESTION_FUNDING_ID);

		// Delete all funding sources
		Set<PersonFundingSource> studentFundingSources = student
				.getFundingSources();

		if (studentFundingSources == null) {
			studentFundingSources = Sets.newHashSet();
			student.setFundingSources(studentFundingSources);
		}

		for (PersonFundingSource studentFundingSource : studentFundingSources) {
			studentFundingSourceDao.delete(studentFundingSource);
		}

		studentFundingSources.clear();

		if (fundingQuestion.getValues() != null) {
			for (String value : fundingQuestion.getValues()) {
				FormOptionTO formOptionTO = fundingQuestion
						.getFormOptionByValue(value);

				if (formOptionTO != null) {
					PersonFundingSource studentFundingSource = new PersonFundingSource();
					studentFundingSource.setPerson(student);

					studentFundingSource.setFundingSource(fundingSourceDao
							.get(formOptionTO.getId()));

					if (formOptionTO.getId().equals(
							SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID)) {
						studentFundingSource.setDescription(fundingSection
								.getFormQuestionById(
										SECTION_FUNDING_QUESTION_OTHER_ID)
								.getValue());
					} else {
						studentFundingSource.setDescription(formOptionTO
								.getLabel());
					}

					studentFundingSourceDao.save(studentFundingSource);
					studentFundingSources.add(studentFundingSource);
				}
			}
		}

		/* Challenges */
		FormSectionTO challengeSection = formTO
				.getFormSectionById(SECTION_CHALLENGE_ID);
		FormQuestionTO challengeQuestion = challengeSection
				.getFormQuestionById(SECTION_CHALLENGE_QUESTION_CHALLENGE_ID);

		// Delete all student challenges
		Set<PersonChallenge> studentChallenges = student.getChallenges();

		if (studentChallenges == null) {
			studentChallenges = Sets.newHashSet();
			student.setChallenges(studentChallenges);
		}

		for (PersonChallenge studentChallenge : studentChallenges) {
			studentChallengeDao.delete(studentChallenge);
		}

		studentChallenges.clear();

		if (challengeQuestion.getValues() != null) {
			for (String value : challengeQuestion.getValues()) {
				FormOptionTO formOptionTO = challengeQuestion
						.getFormOptionByValue(value);

				if (formOptionTO != null) {
					PersonChallenge studentChallenge = new PersonChallenge();
					studentChallenge.setPerson(student);
					studentChallenge.setChallenge(challengeDao.get(formOptionTO
							.getId()));
					// studentChallenge.setOtherDescription(formOptionTO.getValue());

					studentChallengeDao.save(studentChallenge);
					studentChallenges.add(studentChallenge);
				}
			}
		}

		return personService.save(student);
	}

	private FormSectionTO buildConfidentialitySection()
			throws ObjectNotFoundException {
		Person student = securityService.currentUser().getPerson();
		if (null != studentConfidentialityDisclosureAgreementService
				.hasStudentAgreedToOne(student)) {
			// if already agreed to, we don't need to see it again.
			return null;
		}

		FormSectionTO confidentialitySection = new FormSectionTO();
		List<FormQuestionTO> confidentialitySectionQuestions = new ArrayList<FormQuestionTO>();

		confidentialitySection.setId(SECTION_CONFIDENTIALITY_ID);
		confidentialitySection.setLabel("Confidentiality Disclosure");

		// Agree
		FormQuestionTO agreeQuestion = new FormQuestionTO();

		agreeQuestion.setId(SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID);
		agreeQuestion.setLabel(studentConfidentialityDisclosureAgreementService
				.latestAgreement().getText());
		agreeQuestion.setRequired(true);
		agreeQuestion.setValidationExpression("isChecked()");
		agreeQuestion.setType(FORM_TYPE_AGREEMENT);

		confidentialitySectionQuestions.add(agreeQuestion);

		confidentialitySection.setQuestions(confidentialitySectionQuestions);

		return confidentialitySection;
	}

	private FormSectionTO buildPersonalSection() {

		FormSectionTO personalSection = new FormSectionTO();
		List<FormQuestionTO> personalSectionQuestions = new ArrayList<FormQuestionTO>();

		personalSection.setId(SECTION_PERSONAL_ID);
		personalSection.setLabel("Personal");

		// First Name
		FormQuestionTO firstNameQuestionTO = new FormQuestionTO();

		firstNameQuestionTO.setReadOnly(true);
		firstNameQuestionTO.setId(SECTION_PERSONAL_QUESTION_FIRSTNAME_ID);
		firstNameQuestionTO.setLabel("First");
		firstNameQuestionTO.setMaximumLength("30");
		firstNameQuestionTO.setRequired(true);
		firstNameQuestionTO.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(firstNameQuestionTO);

		// Middle Initial
		FormQuestionTO middleInitialQuestionTO = new FormQuestionTO();

		middleInitialQuestionTO.setReadOnly(true);
		middleInitialQuestionTO
				.setId(SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID);
		middleInitialQuestionTO.setLabel("Middle Initial");
		middleInitialQuestionTO.setMaximumLength("1");
		middleInitialQuestionTO.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(middleInitialQuestionTO);

		// Last Name
		FormQuestionTO lastNameQuestionTO = new FormQuestionTO();

		lastNameQuestionTO.setReadOnly(true);
		lastNameQuestionTO.setId(SECTION_PERSONAL_QUESTION_LASTNAME_ID);
		lastNameQuestionTO.setLabel("Last");
		lastNameQuestionTO.setMaximumLength("30");
		lastNameQuestionTO.setRequired(true);
		lastNameQuestionTO.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(lastNameQuestionTO);

		// Student ID
		FormQuestionTO studentIdQuestion = new FormQuestionTO();

		studentIdQuestion.setReadOnly(true);
		studentIdQuestion.setId(SECTION_PERSONAL_QUESTION_STUDENTID_ID);
		studentIdQuestion.setLabel("Student ID");
		studentIdQuestion.setMaximumLength("50");
		studentIdQuestion.setRequired(true);
		studentIdQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(studentIdQuestion);

		// Birthdate
		FormQuestionTO birthDateQuestion = new FormQuestionTO();

		birthDateQuestion.setReadOnly(true);
		birthDateQuestion.setId(SECTION_PERSONAL_QUESTION_BIRTHDATE_ID);
		birthDateQuestion.setLabel("Birthdate");
		birthDateQuestion.setMaximumLength("10");
		birthDateQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(birthDateQuestion);

		// School Email
		FormQuestionTO schoolEmailQuestion = new FormQuestionTO();

		schoolEmailQuestion.setReadOnly(true);
		schoolEmailQuestion.setId(SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID);
		schoolEmailQuestion.setLabel("Email (School)");
		schoolEmailQuestion.setMaximumLength("50");
		schoolEmailQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(schoolEmailQuestion);

		// Home Email
		FormQuestionTO homeEmailQuestion = new FormQuestionTO();

		homeEmailQuestion.setId(SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID);
		homeEmailQuestion.setLabel("Email (Home)");
		homeEmailQuestion.setMaximumLength("50");
		homeEmailQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(homeEmailQuestion);

		// Home Phone
		FormQuestionTO homePhoneQuestion = new FormQuestionTO();

		homePhoneQuestion.setReadOnly(true);
		homePhoneQuestion.setId(SECTION_PERSONAL_QUESTION_HOMEPHONE_ID);
		homePhoneQuestion.setLabel("Home Phone");
		homePhoneQuestion.setMaximumLength("12");
		homePhoneQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(homePhoneQuestion);

		// Work Phone
		FormQuestionTO workPhoneQuestion = new FormQuestionTO();

		workPhoneQuestion.setReadOnly(true);
		workPhoneQuestion.setId(SECTION_PERSONAL_QUESTION_WORKPHONE_ID);
		workPhoneQuestion.setLabel("Work Phone");
		workPhoneQuestion.setMaximumLength("12");
		workPhoneQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(workPhoneQuestion);

		// Cell Phone
		FormQuestionTO cellPhoneQuestion = new FormQuestionTO();

		cellPhoneQuestion.setId(SECTION_PERSONAL_QUESTION_CELLPHONE_ID);
		cellPhoneQuestion.setLabel("Cell Phone");
		cellPhoneQuestion.setMaximumLength("12");
		cellPhoneQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(cellPhoneQuestion);

		// Address
		FormQuestionTO addressQuestion = new FormQuestionTO();

		addressQuestion.setReadOnly(true);
		addressQuestion.setId(SECTION_PERSONAL_QUESTION_ADDRESS_ID);
		addressQuestion.setLabel("Address");
		addressQuestion.setMaximumLength("50");
		addressQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(addressQuestion);

		// City
		FormQuestionTO cityQuestion = new FormQuestionTO();

		cityQuestion.setReadOnly(true);
		cityQuestion.setId(SECTION_PERSONAL_QUESTION_CITY_ID);
		cityQuestion.setLabel("City");
		cityQuestion.setMaximumLength("50");
		cityQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(cityQuestion);

		// State
		FormQuestionTO stateQuestion = new FormQuestionTO();
		List<FormOptionTO> stateQuestionOptions = new ArrayList<FormOptionTO>();

		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "AK", "AK"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "AL", "AL"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "AS", "AS"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "AZ", "AZ"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "BC", "BC"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "CA", "CA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "CO", "CO"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "CT", "CT"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "CZ", "CZ"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "DC", "DC"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "DE", "DE"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "FL", "FL"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "FM", "FM"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "GA", "GA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "GU", "GU"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "HI", "HI"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "IA", "IA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "ID", "ID"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "IL", "IL"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "IN", "IN"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "KS", "KS"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "KY", "KY"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "LA", "LA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MA", "MA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MD", "MD"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "ME", "ME"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MH", "MH"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MN", "MN"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MO", "MO"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MP", "MP"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MS", "MS"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "MT", "MT"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NC", "NC"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "ND", "ND"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NE", "NE"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NH", "NH"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NJ", "NJ"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NM", "NM"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NV", "NV"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "NY", "NY"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "OH", "OH"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "OK", "OK"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "ON", "ON"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "OR", "OR"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "OS", "OS"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "PA", "PA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "PR", "PR"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "RI", "RI"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "SC", "SC"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "SD", "SD"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "TN", "TN"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "TX", "TX"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "UT", "UT"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "VA", "VA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "VI", "VI"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "WA", "WA"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "WI", "WI"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "WV", "WV"));
		stateQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "WY", "WY"));

		stateQuestion.setReadOnly(true);
		stateQuestion.setId(SECTION_PERSONAL_QUESTION_STATE_ID);
		stateQuestion.setLabel("State");
		stateQuestion.setMaximumLength("2");
		stateQuestion.setOptions(stateQuestionOptions);
		stateQuestion.setType(FORM_TYPE_SELECT);

		personalSectionQuestions.add(stateQuestion);

		// Zip Code
		FormQuestionTO zipCodeQuestion = new FormQuestionTO();

		zipCodeQuestion.setReadOnly(true);
		zipCodeQuestion.setId(SECTION_PERSONAL_QUESTION_ZIPCODE_ID);
		zipCodeQuestion.setLabel("Zip Code");
		zipCodeQuestion.setMaximumLength("10");
		zipCodeQuestion.setType(FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(zipCodeQuestion);

		personalSection.setQuestions(personalSectionQuestions);

		return personalSection;
	}

	private FormSectionTO buildDemographicsSection() {

		FormSectionTO demographicSection = new FormSectionTO();
		List<FormQuestionTO> demographicSectionQuestions = new ArrayList<FormQuestionTO>();

		demographicSection.setId(SECTION_DEMOGRAPHICS_ID);
		demographicSection.setLabel("Demographics");

		// Marital Status
		FormQuestionTO maritalStatusQuestion = new FormQuestionTO();
		List<FormOptionTO> maritalStatusQuestionOptions = new ArrayList<FormOptionTO>();

		maritalStatusQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID);
		maritalStatusQuestion.setLabel("Marital Status");

		maritalStatusQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Single", "Single"));
		maritalStatusQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Married", "Married"));
		maritalStatusQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Divorced", "Divorced"));
		maritalStatusQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Separated", "Separated"));
		maritalStatusQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Widowed", "Widowed"));

		maritalStatusQuestion.setOptions(maritalStatusQuestionOptions);
		maritalStatusQuestion.setType(FORM_TYPE_SELECT);

		demographicSectionQuestions.add(maritalStatusQuestion);

		// Ethnicity
		FormQuestionTO ethnicityQuestion = new FormQuestionTO();
		List<FormOptionTO> ethnicityQuestionOptions = new ArrayList<FormOptionTO>();

		ethnicityQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID);
		ethnicityQuestion.setLabel("Ethnicity");

		for (Ethnicity ethnicity : ethnicityService.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()) {
			ethnicityQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
					ethnicity.getName(), ethnicity.getName()));
		}

		ethnicityQuestion.setOptions(ethnicityQuestionOptions);
		ethnicityQuestion.setType(FORM_TYPE_SELECT);

		demographicSectionQuestions.add(ethnicityQuestion);

		// Gender
		FormQuestionTO genderQuestion = new FormQuestionTO();
		List<FormOptionTO> genderQuestionOptions = new ArrayList<FormOptionTO>();

		genderQuestionOptions.add(new FormOptionTO(UUID.randomUUID(), "Male",
				"M"));
		genderQuestionOptions.add(new FormOptionTO(UUID.randomUUID(), "Female",
				"F"));

		genderQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID);
		genderQuestion.setLabel("Gender");
		genderQuestion.setOptions(genderQuestionOptions);
		genderQuestion.setType(FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(genderQuestion);

		// Citizenship
		FormQuestionTO citizenshipQuestion = new FormQuestionTO();
		List<FormOptionTO> citizenshipQuestionOptions = new ArrayList<FormOptionTO>();

		citizenshipQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"US Citizen", "DD1A644C-58A9-4A77-A263-76C2ECB6B152"));
		citizenshipQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Permanent Resident", "1B870957-9165-46AB-9BA8-47085DD4B561"));
		citizenshipQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Naturalized Citizen", "8DDB5013-1D9F-441D-8C6B-71C52F839570"));
		citizenshipQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"International", "8BFA49CE-4D8E-43C1-AA2B-0F5E66319168"));

		citizenshipQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID);
		citizenshipQuestion.setLabel("Citizenship");
		citizenshipQuestion.setOptions(citizenshipQuestionOptions);
		citizenshipQuestion.setType(FORM_TYPE_SELECT);

		demographicSectionQuestions.add(citizenshipQuestion);

		// Country of Citizenship -> Shown if Citizenship is "International"
		FormQuestionTO countryOfCitizenshipQuestion = new FormQuestionTO();

		countryOfCitizenshipQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID);
		countryOfCitizenshipQuestion.setLabel("Country of Citizenship");
		countryOfCitizenshipQuestion.setMaximumLength("50");
		countryOfCitizenshipQuestion.setType(FORM_TYPE_TEXTINPUT);
		// DEPENDENCY -> countryOfCitizenshipQuestion should be shown only when
		// citizenshipQuestion selection matches 'International'
		countryOfCitizenshipQuestion
				.setVisibilityExpression("hasValueForQuestionId('8BFA49CE-4D8E-43C1-AA2B-0F5E66319168', '"
						+ SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID + "')");

		demographicSectionQuestions.add(countryOfCitizenshipQuestion);

		// Veteran Status
		FormQuestionTO veteranStatusQuestion = new FormQuestionTO();
		List<FormOptionTO> veteranStatusQuestionOptions = new ArrayList<FormOptionTO>();

		for (VeteranStatus veteranStatus : veteranStatusService.getAll(
				new SortingAndPaging(ObjectStatus.ALL)).getRows()) {
			veteranStatusQuestionOptions
					.add(new FormOptionTO(veteranStatus.getId(), veteranStatus
							.getName(), veteranStatus.getName()));
		}

		veteranStatusQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID);
		veteranStatusQuestion.setLabel("Veteran Status");
		veteranStatusQuestion.setOptions(veteranStatusQuestionOptions);
		veteranStatusQuestion.setType(FORM_TYPE_SELECT);

		demographicSectionQuestions.add(veteranStatusQuestion);

		// Primary Caregiver
		FormQuestionTO primaryCaregiverQuestion = new FormQuestionTO();
		List<FormOptionTO> primaryCaregiverQuestionOptions = new ArrayList<FormOptionTO>();

		primaryCaregiverQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Yes", "Y"));
		primaryCaregiverQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"No", "N"));

		primaryCaregiverQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID);
		primaryCaregiverQuestion.setLabel("Are you a primary Caregiver?");
		primaryCaregiverQuestion.setOptions(primaryCaregiverQuestionOptions);
		primaryCaregiverQuestion.setType(FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(primaryCaregiverQuestion);

		// How Many Children
		FormQuestionTO howManyChildrenQuestion = new FormQuestionTO();
		List<FormOptionTO> howManyChildrenQuestionOptions = new ArrayList<FormOptionTO>();

		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"0", "0"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"1", "1"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"2", "2"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"3", "3"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"4", "4"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"5", "5"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"6", "6"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"7", "7"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"8", "8"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"9", "9"));
		howManyChildrenQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"10", "10"));

		howManyChildrenQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID);
		howManyChildrenQuestion.setLabel("If you have children, how many?");
		howManyChildrenQuestion.setOptions(howManyChildrenQuestionOptions);
		howManyChildrenQuestion.setType(FORM_TYPE_SELECT);

		demographicSectionQuestions.add(howManyChildrenQuestion);

		// Children Ages
		FormQuestionTO childrenAgesQuestion = new FormQuestionTO();

		childrenAgesQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID);
		childrenAgesQuestion.setLabel("Ages? (Separated by commas)");
		childrenAgesQuestion.setMaximumLength("50");
		childrenAgesQuestion.setType(FORM_TYPE_TEXTINPUT);

		demographicSectionQuestions.add(childrenAgesQuestion);

		// Childcare Needed
		FormQuestionTO childCareNeededQuestion = new FormQuestionTO();
		List<FormOptionTO> childCareNeededQuestionOptions = new ArrayList<FormOptionTO>();

		childCareNeededQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Yes", "Y"));
		childCareNeededQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"No", "N"));

		childCareNeededQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID);
		childCareNeededQuestion.setLabel("Childcare needed?");
		childCareNeededQuestion.setOptions(childCareNeededQuestionOptions);
		childCareNeededQuestion.setType(FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(childCareNeededQuestion);

		// Childcare Arrangement
		FormQuestionTO childCareArrangementQuestion = new FormQuestionTO();
		List<FormOptionTO> childCareArrangementQuestionOptions = new ArrayList<FormOptionTO>();

		for (ChildCareArrangement childcareArrangement : childCareArrangementService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()) {
			childCareArrangementQuestionOptions.add(new FormOptionTO(
					childcareArrangement.getId(), childcareArrangement
							.getName(), childcareArrangement.getName()));
		}

		childCareArrangementQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID);
		childCareArrangementQuestion.setLabel("Childcare arrangement");
		childCareArrangementQuestion
				.setOptions(childCareArrangementQuestionOptions);
		childCareArrangementQuestion.setType(FORM_TYPE_SELECT);
		// DEPENDENCY -> childCareArrangementQuestion shown when
		// childCareNeededQuestion selection matches "Yes"
		childCareArrangementQuestion
				.setVisibilityExpression("hasValueForQuestionId('Y', '"
						+ SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID
						+ "')");

		demographicSectionQuestions.add(childCareArrangementQuestion);

		// Employed
		FormQuestionTO employedQuestion = new FormQuestionTO();
		List<FormOptionTO> employedQuestionOptions = new ArrayList<FormOptionTO>();

		employedQuestionOptions.add(new FormOptionTO(UUID.randomUUID(), "Yes",
				"Y"));
		employedQuestionOptions.add(new FormOptionTO(UUID.randomUUID(), "No",
				"N"));

		employedQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID);
		employedQuestion.setLabel("Are you employed");
		employedQuestion.setOptions(employedQuestionOptions);
		employedQuestion.setType(FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(employedQuestion);

		// Employer
		FormQuestionTO employerQuestion = new FormQuestionTO();

		employerQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID);
		employerQuestion.setLabel("Place of employment");
		employerQuestion.setType(FORM_TYPE_TEXTINPUT);
		// DEPENDENCY -> employerQuestion shown when employedQuestion selection
		// matches "Yes"
		employerQuestion.setVisibilityExpression("hasValueForQuestionId('Y', '"
				+ SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID + "')");

		demographicSectionQuestions.add(employerQuestion);

		// Shift
		FormQuestionTO shiftQuestion = new FormQuestionTO();
		List<FormOptionTO> shiftQuestionOptions = new ArrayList<FormOptionTO>();

		shiftQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "1st", "1"));
		shiftQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "2nd", "2"));
		shiftQuestionOptions
				.add(new FormOptionTO(UUID.randomUUID(), "3rd", "3"));
		shiftQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Not Applicable", "4"));

		shiftQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID);
		shiftQuestion.setLabel("Shift");
		shiftQuestion.setOptions(shiftQuestionOptions);
		shiftQuestion.setType(FORM_TYPE_SELECT);
		// DEPENDENCY -> shiftQuestion shown when employedQuestion selection
		// matches "Yes"
		shiftQuestion.setVisibilityExpression("hasValueForQuestionId('Y', '"
				+ SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID + "')");

		demographicSectionQuestions.add(shiftQuestion);

		// Wage
		FormQuestionTO wageQuestion = new FormQuestionTO();

		wageQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID);
		wageQuestion.setLabel("Wages");
		wageQuestion.setType(FORM_TYPE_TEXTINPUT);
		// DEPENDENCY -> wageQuestion shown when employedQuestion selection
		// matches "Yes"
		wageQuestion.setVisibilityExpression("hasValueForQuestionId('Y', '"
				+ SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID + "')");

		demographicSectionQuestions.add(wageQuestion);

		// Hours worked per week
		FormQuestionTO hoursWorkedPerWeekQuestion = new FormQuestionTO();

		hoursWorkedPerWeekQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID);
		hoursWorkedPerWeekQuestion.setLabel("Hours Worked per Week");
		hoursWorkedPerWeekQuestion.setType(FORM_TYPE_TEXTINPUT);
		// DEPENDENCY -> hoursWorkedPerWeekQuestion shown when employedQuestion
		// selection matches "Yes"
		hoursWorkedPerWeekQuestion
				.setVisibilityExpression("hasValueForQuestionId('Y', '"
						+ SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID + "')");

		demographicSectionQuestions.add(hoursWorkedPerWeekQuestion);

		// Add questions to section
		demographicSection.setQuestions(demographicSectionQuestions);

		return demographicSection;
	}

	private FormSectionTO buildEducationPlanSection() {

		FormSectionTO eduPlanSection = new FormSectionTO();
		List<FormQuestionTO> eduPlanSectionQuestions = new ArrayList<FormQuestionTO>();

		eduPlanSection.setId(SECTION_EDUCATIONPLAN_ID);
		eduPlanSection.setLabel("EduPlan");

		// Student Status
		FormQuestionTO studentStatusQuestion = new FormQuestionTO();
		List<FormOptionTO> studentStatusQuestionOptions = new ArrayList<FormOptionTO>();

		for (StudentStatus studentStatus : studentStatusService.getAll(
				new SortingAndPaging(ObjectStatus.ALL)).getRows()) {
			studentStatusQuestionOptions
					.add(new FormOptionTO(studentStatus.getId(), studentStatus
							.getName(), studentStatus.getName()));
		}

		studentStatusQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID);
		studentStatusQuestion.setLabel("Student Status");
		studentStatusQuestion.setOptions(studentStatusQuestionOptions);
		studentStatusQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(studentStatusQuestion);

		// New Student Orientation
		FormQuestionTO newStudentOrientationQuestion = new FormQuestionTO();
		List<FormOptionTO> newStudentOrientationQuestionOptions = new ArrayList<FormOptionTO>();

		newStudentOrientationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Yes", "Y"));
		newStudentOrientationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "No", "N"));

		newStudentOrientationQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID);
		newStudentOrientationQuestion
				.setLabel("Have you completed new student orientation?");
		newStudentOrientationQuestion
				.setOptions(newStudentOrientationQuestionOptions);
		newStudentOrientationQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(newStudentOrientationQuestion);

		// Registered for Classes
		FormQuestionTO registeredForClassesQuestion = new FormQuestionTO();
		List<FormOptionTO> registeredForClassesQuestionOptions = new ArrayList<FormOptionTO>();

		registeredForClassesQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Yes", "Y"));
		registeredForClassesQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "No", "N"));

		registeredForClassesQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID);
		registeredForClassesQuestion
				.setLabel("Have you registered for classes?");
		registeredForClassesQuestion
				.setOptions(registeredForClassesQuestionOptions);
		registeredForClassesQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(registeredForClassesQuestion);

		// Parents Obtained College Degree
		FormQuestionTO parentsObtainedCollegeDegreeQuestion = new FormQuestionTO();
		List<FormOptionTO> parentsObtainedCollegeDegreeQuestionOptions = new ArrayList<FormOptionTO>();

		parentsObtainedCollegeDegreeQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Yes", "Y"));
		parentsObtainedCollegeDegreeQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "No", "N"));

		parentsObtainedCollegeDegreeQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID);
		parentsObtainedCollegeDegreeQuestion
				.setLabel("Have your parents obtained a college degree?");
		parentsObtainedCollegeDegreeQuestion
				.setOptions(newStudentOrientationQuestionOptions);
		parentsObtainedCollegeDegreeQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(parentsObtainedCollegeDegreeQuestion);

		// Special Needs/Special Accomodation
		FormQuestionTO specialAccomodationQuestion = new FormQuestionTO();
		List<FormOptionTO> specialAccomodationQuestionOptions = new ArrayList<FormOptionTO>();

		specialAccomodationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Yes", "Y"));
		specialAccomodationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "No", "N"));

		specialAccomodationQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMMODATIONS_ID);
		specialAccomodationQuestion
				.setLabel("Special needs or require special accomodation?");
		specialAccomodationQuestion
				.setOptions(specialAccomodationQuestionOptions);
		specialAccomodationQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(specialAccomodationQuestion);

		// Grade Earned Highest Level of Education
		FormQuestionTO gradeEarnedHighestLevelEducationQuestion = new FormQuestionTO();
		List<FormOptionTO> gradeEarnedHighestLevelEducationQuestionOptions = new ArrayList<FormOptionTO>();

		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "A", "1"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "A-B", "2"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "B", "3"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "B-C", "4"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "C", "5"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "C-D", "6"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "D", "7"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "D-F", "8"));
		gradeEarnedHighestLevelEducationQuestionOptions.add(new FormOptionTO(
				UUID.randomUUID(), "F", "9"));

		gradeEarnedHighestLevelEducationQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID);
		gradeEarnedHighestLevelEducationQuestion
				.setLabel("What grade did you typically earn at your highest level of education?");
		gradeEarnedHighestLevelEducationQuestion
				.setOptions(gradeEarnedHighestLevelEducationQuestionOptions);
		gradeEarnedHighestLevelEducationQuestion.setType(FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(gradeEarnedHighestLevelEducationQuestion);

		// Add questions to section
		eduPlanSection.setQuestions(eduPlanSectionQuestions);

		return eduPlanSection;
	}

	private FormSectionTO buildEducationLevelsSection() {

		FormSectionTO eduLevelSection = new FormSectionTO();
		List<FormQuestionTO> eduLevelSectionQuestions = new ArrayList<FormQuestionTO>();

		eduLevelSection.setId(SECTION_EDUCATIONLEVEL_ID);
		eduLevelSection.setLabel("EduLevels");

		// Education Level Completed
		FormQuestionTO educationLevelCompletedQuestion = new FormQuestionTO();
		List<FormOptionTO> educationLevelCompletedQuestionOptions = new ArrayList<FormOptionTO>();

		for (EducationLevel educationLevel : educationLevelDao.getAll(
				ObjectStatus.ACTIVE).getRows()) {
			educationLevelCompletedQuestionOptions.add(new FormOptionTO(
					educationLevel.getId(), educationLevel.getName(),
					educationLevel.getId().toString()));
		}

		educationLevelCompletedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID);
		educationLevelCompletedQuestion.setLabel("Education Level Completed");
		educationLevelCompletedQuestion
				.setOptions(educationLevelCompletedQuestionOptions);
		educationLevelCompletedQuestion.setType(FORM_TYPE_CHECKLIST);

		eduLevelSectionQuestions.add(educationLevelCompletedQuestion);

		// No Diploma/No GED - Last Year Attended
		FormQuestionTO noDiplomaLastYearAttendedQuestion = new FormQuestionTO();

		noDiplomaLastYearAttendedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID);
		noDiplomaLastYearAttendedQuestion.setLabel("Last Year Attended");
		noDiplomaLastYearAttendedQuestion.setMaximumLength("4");
		noDiplomaLastYearAttendedQuestion.setType(FORM_TYPE_TEXTINPUT);
		noDiplomaLastYearAttendedQuestion.setRequired(true);
		// DEPENDENCY -> noDiplomaLastYearAttendedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05BB9-5056-A51A-80FDFE0D53E6EB07
		noDiplomaLastYearAttendedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05BB9-5056-A51A-80FDFE0D53E6EB07', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(noDiplomaLastYearAttendedQuestion);

		// No Diploma/No GED - Highest Grade Completed
		FormQuestionTO noDiplomaHighestGradeCompletedQuestion = new FormQuestionTO();

		noDiplomaHighestGradeCompletedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID);
		noDiplomaHighestGradeCompletedQuestion
				.setLabel("Highest Grade Completed");
		noDiplomaHighestGradeCompletedQuestion.setType(FORM_TYPE_TEXTINPUT);
		noDiplomaHighestGradeCompletedQuestion.setRequired(true);
		// DEPENDENCY -> noDiplomaHighestGradeCompletedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05BB9-5056-A51A-80FDFE0D53E6EB07
		noDiplomaHighestGradeCompletedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05BB9-5056-A51A-80FDFE0D53E6EB07', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(noDiplomaHighestGradeCompletedQuestion);

		// GED - Year of GED
		FormQuestionTO gedYearOfGedQuestion = new FormQuestionTO();

		gedYearOfGedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID);
		gedYearOfGedQuestion.setLabel("Year of GED");
		gedYearOfGedQuestion.setMaximumLength("4");
		gedYearOfGedQuestion.setType(FORM_TYPE_TEXTINPUT);
		gedYearOfGedQuestion.setRequired(true);
		// DEPENDENCY -> gedYearOfGedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05BF8-5056-A51A-8053E140B84D65A4
		gedYearOfGedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05BF8-5056-A51A-8053E140B84D65A4', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(gedYearOfGedQuestion);

		// High School Graduation - Year Graduated
		FormQuestionTO highSchoolYearGraduatedQuestion = new FormQuestionTO();

		highSchoolYearGraduatedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID);
		highSchoolYearGraduatedQuestion.setLabel("Year Graduated");
		highSchoolYearGraduatedQuestion.setMaximumLength("4");
		highSchoolYearGraduatedQuestion.setType(FORM_TYPE_TEXTINPUT);
		highSchoolYearGraduatedQuestion.setRequired(true);
		// DEPENDENCY -> highSchoolYearGraduatedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05C27-5056-A51A-80D26A4742E0AB64
		highSchoolYearGraduatedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05C27-5056-A51A-80D26A4742E0AB64', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(highSchoolYearGraduatedQuestion);

		// High School Graduation - High School Attended
		FormQuestionTO highSchoolAttendedQuestion = new FormQuestionTO();

		highSchoolAttendedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID);
		highSchoolAttendedQuestion.setLabel("High School Attended");
		highSchoolAttendedQuestion.setMaximumLength("250");
		highSchoolAttendedQuestion.setType(FORM_TYPE_TEXTINPUT);
		highSchoolAttendedQuestion.setRequired(true);
		// DEPENDENCY -> highSchoolAttendedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05C27-5056-A51A-80D26A4742E0AB64
		highSchoolAttendedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05C27-5056-A51A-80D26A4742E0AB64', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(highSchoolAttendedQuestion);

		// Some College Credits - Last Year Attended
		FormQuestionTO someCollegeCreditsLastYearAttendedQuestion = new FormQuestionTO();

		someCollegeCreditsLastYearAttendedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID);
		someCollegeCreditsLastYearAttendedQuestion
				.setLabel("Last Year Attended");
		someCollegeCreditsLastYearAttendedQuestion.setMaximumLength("4");
		someCollegeCreditsLastYearAttendedQuestion.setType(FORM_TYPE_TEXTINPUT);
		someCollegeCreditsLastYearAttendedQuestion.setRequired(true);
		// DEPENDENCY -> someCollegeCreditsLastYearAttendedQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05C36-5056-A51A-80E7C017F4882593
		someCollegeCreditsLastYearAttendedQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05C36-5056-A51A-80E7C017F4882593', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions
				.add(someCollegeCreditsLastYearAttendedQuestion);

		// Other - Please Explain
		FormQuestionTO otherPleaseExplainQuestion = new FormQuestionTO();

		otherPleaseExplainQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID);
		otherPleaseExplainQuestion.setLabel("Please Explain");
		otherPleaseExplainQuestion.setMaximumLength("250");
		otherPleaseExplainQuestion.setType(FORM_TYPE_TEXTAREA);
		otherPleaseExplainQuestion.setRequired(true);
		// DEPENDENCY -> otherPleaseExplainQuestion shown when
		// educationLevelCompletedQuestion value is
		// B2D05C65-5056-A51A-8024DC8A118A585C
		otherPleaseExplainQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05C65-5056-A51A-8024DC8A118A585C', '"
						+ SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID
						+ "')");

		eduLevelSectionQuestions.add(otherPleaseExplainQuestion);

		// Add questions to section
		eduLevelSection.setQuestions(eduLevelSectionQuestions);

		return eduLevelSection;
	}

	private FormSectionTO buildEducationGoalSection() {

		FormSectionTO eduGoalSection = new FormSectionTO();
		List<FormQuestionTO> eduGoalSectionQuestions = new ArrayList<FormQuestionTO>();

		eduGoalSection.setId(SECTION_EDUCATIONGOAL_ID);
		eduGoalSection.setLabel("EduGoal");

		// Education/Career Goal
		FormQuestionTO educationCareerGoalQuestion = new FormQuestionTO();
		List<FormOptionTO> educationCareerGoalQuestionOptions = new ArrayList<FormOptionTO>();

		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Uncertain", "uncertain"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Associates Degree", "associate"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Certificate", "certificate"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Short Term Certificate", "short-term"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Bachelor's Degree", "bachelor"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Workforce", "workforce"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Tech School", "tech-school"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Military", "military"));
		educationCareerGoalQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Other", "other"));

		educationCareerGoalQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID);
		educationCareerGoalQuestion.setLabel("Education/Career Goal");
		educationCareerGoalQuestion
				.setOptions(educationCareerGoalQuestionOptions);
		educationCareerGoalQuestion.setType(FORM_TYPE_RADIOLIST);

		eduGoalSectionQuestions.add(educationCareerGoalQuestion);

		// Goal Description
		FormQuestionTO bachelorsDegreeQuestion = new FormQuestionTO();

		bachelorsDegreeQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID);
		bachelorsDegreeQuestion.setLabel("Bachelor's Degree Major");
		bachelorsDegreeQuestion.setMaximumLength("255");
		bachelorsDegreeQuestion.setType(FORM_TYPE_TEXTINPUT);
		bachelorsDegreeQuestion.setRequired(true);
		// DEPENDENCY -> bachelorsDegreeQuestion shown when
		// educationCareerGoalQuestion.option.value value is 'bachelor'
		bachelorsDegreeQuestion
				.setVisibilityExpression("hasValueForQuestionId('bachelor', '"
						+ SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID + "')");

		eduGoalSectionQuestions.add(bachelorsDegreeQuestion);

		// Military Branch Description
		FormQuestionTO militaryBranchQuestion = new FormQuestionTO();

		militaryBranchQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID);
		militaryBranchQuestion.setLabel("Military Branch");
		militaryBranchQuestion.setMaximumLength("255");
		militaryBranchQuestion.setType(FORM_TYPE_TEXTINPUT);
		militaryBranchQuestion.setRequired(true);
		// DEPENDENCY -> militaryBranchQuestion shown when
		// educationCareerGoalQuestion.option.value value is 'military'
		militaryBranchQuestion
				.setVisibilityExpression("hasValueForQuestionId('military', '"
						+ SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID + "')");

		eduGoalSectionQuestions.add(militaryBranchQuestion);

		// Other Description
		FormQuestionTO otherQuestion = new FormQuestionTO();

		otherQuestion.setId(SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID);
		otherQuestion.setLabel("Other Goal");
		otherQuestion.setMaximumLength("255");
		otherQuestion.setType(FORM_TYPE_TEXTINPUT);
		otherQuestion.setRequired(true);
		// DEPENDENCY -> otherQuestion shown when
		// educationCareerGoalQuestion.option.value value is 'other'
		otherQuestion
				.setVisibilityExpression("hasValueForQuestionId('other', '"
						+ SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID + "')");

		eduGoalSectionQuestions.add(otherQuestion);

		// Major Certainty
		FormQuestionTO majorCertaintyQuestion = new FormQuestionTO();
		List<FormOptionTO> majorCertaintyQuestionOptions = new ArrayList<FormOptionTO>();

		majorCertaintyQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Very Unsure", "1"));
		majorCertaintyQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Unsure", "2"));
		majorCertaintyQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"None", "3"));
		majorCertaintyQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Sure", "4"));
		majorCertaintyQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
				"Very Sure", "5"));

		majorCertaintyQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID);
		majorCertaintyQuestion.setLabel("How sure are you about your major?");
		majorCertaintyQuestion.setOptions(majorCertaintyQuestionOptions);
		majorCertaintyQuestion.setType(FORM_TYPE_RADIOLIST);

		eduGoalSectionQuestions.add(majorCertaintyQuestion);

		// Planned Occupation
		FormQuestionTO plannedOccupationQuestion = new FormQuestionTO();

		plannedOccupationQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID);
		plannedOccupationQuestion.setLabel("What is your planned occupation?");
		plannedOccupationQuestion.setMaximumLength("50");
		plannedOccupationQuestion.setType(FORM_TYPE_TEXTINPUT);

		eduGoalSectionQuestions.add(plannedOccupationQuestion);

		// Add questions to section
		eduGoalSection.setQuestions(eduGoalSectionQuestions);

		return eduGoalSection;
	}

	private FormSectionTO buildFundingSection() {

		FormSectionTO fundingSection = new FormSectionTO();
		List<FormQuestionTO> fundingSectionQuestions = new ArrayList<FormQuestionTO>();

		fundingSection.setId(SECTION_FUNDING_ID);
		fundingSection.setLabel("Funding");

		// Funding
		FormQuestionTO fundingQuestionTO = new FormQuestionTO();
		List<FormOptionTO> fundingSourceOptions = new ArrayList<FormOptionTO>();

		for (FundingSource fundingSource : fundingSourceDao.getAll(
				ObjectStatus.ACTIVE).getRows()) {
			fundingSourceOptions.add(new FormOptionTO(fundingSource.getId(),
					fundingSource.getName(), fundingSource.getId().toString()));
		}

		fundingQuestionTO.setId(SECTION_FUNDING_QUESTION_FUNDING_ID);
		fundingQuestionTO.setLabel("How will you pay for college");
		fundingQuestionTO.setOptions(fundingSourceOptions);
		fundingQuestionTO.setType(FORM_TYPE_CHECKLIST);

		fundingSectionQuestions.add(fundingQuestionTO);

		// Other Description
		FormQuestionTO otherQuestion = new FormQuestionTO();

		otherQuestion.setId(SECTION_FUNDING_QUESTION_OTHER_ID);
		otherQuestion.setLabel("Other");
		otherQuestion.setMaximumLength("255");
		otherQuestion.setType(FORM_TYPE_TEXTINPUT);
		otherQuestion.setRequired(true);
		// DEPENDENCY -> otherQuestion shown when fundingQuestionTO value is
		// 'B2D05DEC-5056-A51A-8001FE8BDD379C5B'
		otherQuestion
				.setVisibilityExpression("hasValueForQuestionId('B2D05DEC-5056-A51A-8001FE8BDD379C5B', '"
						+ SECTION_FUNDING_QUESTION_FUNDING_ID + "')");

		fundingSectionQuestions.add(otherQuestion);

		// Add questions to section
		fundingSection.setQuestions(fundingSectionQuestions);

		return fundingSection;
	}

	private FormSectionTO buildChallengesSection() {

		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);

		FormSectionTO challengeSection = new FormSectionTO();
		List<FormQuestionTO> challengeSectionQuestions = new ArrayList<FormQuestionTO>();

		challengeSection.setId(SECTION_CHALLENGE_ID);
		challengeSection.setLabel("Challenges");

		// Challenges
		FormQuestionTO challengeQuestionTO = new FormQuestionTO();
		List<FormOptionTO> challengeOptions = new ArrayList<FormOptionTO>();

		for (Challenge challenge : challengeDao.getAllInStudentIntake(sAndP)) {
			challengeOptions.add(new FormOptionTO(challenge.getId(), challenge
					.getName(), challenge.getId().toString()));
		}

		challengeQuestionTO.setId(SECTION_CHALLENGE_QUESTION_CHALLENGE_ID);
		challengeQuestionTO
				.setLabel("Select all challenges that may be barriers to your academic success");
		challengeQuestionTO.setOptions(challengeOptions);
		challengeQuestionTO.setType(FORM_TYPE_CHECKLIST);

		challengeSectionQuestions.add(challengeQuestionTO);

		// Other Description
		FormQuestionTO otherQuestion = new FormQuestionTO();

		otherQuestion.setId(SECTION_CHALLENGE_QUESTION_OTHER_ID);
		otherQuestion.setLabel("Other");
		otherQuestion.setMaximumLength("255");
		otherQuestion.setType(FORM_TYPE_TEXTINPUT);
		otherQuestion.setRequired(true);
		// DEPENDENCY -> otherQuestion shown when challengeQuestionTO value is
		// '90CD580D-58F8-489F-AC3E-8522B929B24B'
		otherQuestion
				.setVisibilityExpression("hasValueForQuestionId('90CD580D-58F8-489F-AC3E-8522B929B24B', '"
						+ SECTION_CHALLENGE_QUESTION_CHALLENGE_ID + "')");

		challengeSectionQuestions.add(otherQuestion);

		// Add questions to section
		challengeSection.setQuestions(challengeSectionQuestions);

		return challengeSection;
	}
}
