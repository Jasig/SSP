package edu.sinclair.mygps.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.mygps.dao.ConfidentialityDisclosureDao;
import edu.sinclair.mygps.dao.StudentToolsDao;
import edu.sinclair.mygps.model.transferobject.FormOptionTO;
import edu.sinclair.mygps.model.transferobject.FormQuestionTO;
import edu.sinclair.mygps.model.transferobject.FormSectionTO;
import edu.sinclair.mygps.model.transferobject.FormTO;
import edu.sinclair.mygps.util.Constants;
import edu.sinclair.mygps.util.MyGpsStringUtils;
import edu.sinclair.ssp.dao.PersonChallengeDao;
import edu.sinclair.ssp.dao.PersonDao;
import edu.sinclair.ssp.dao.PersonEducationGoalDao;
import edu.sinclair.ssp.dao.PersonEducationLevelDao;
import edu.sinclair.ssp.dao.PersonFundingSourceDao;
import edu.sinclair.ssp.dao.reference.ChallengeDao;
import edu.sinclair.ssp.dao.reference.ChildCareArrangementDao;
import edu.sinclair.ssp.dao.reference.CitizenshipDao;
import edu.sinclair.ssp.dao.reference.EducationGoalDao;
import edu.sinclair.ssp.dao.reference.EducationLevelDao;
import edu.sinclair.ssp.dao.reference.EthnicityDao;
import edu.sinclair.ssp.dao.reference.FundingSourceDao;
import edu.sinclair.ssp.dao.reference.StudentStatusDao;
import edu.sinclair.ssp.dao.reference.VeteranStatusDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.model.PersonDemographics;
import edu.sinclair.ssp.model.PersonEducationGoal;
import edu.sinclair.ssp.model.PersonEducationLevel;
import edu.sinclair.ssp.model.PersonEducationPlan;
import edu.sinclair.ssp.model.PersonFundingSource;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.model.reference.EmploymentShifts;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.model.reference.Genders;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.MaritalStatusService;

@Service
public class StudentIntakeFormManager {

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private ChildCareArrangementDao childcareArrangementDao;

	@Autowired
	private CitizenshipDao citizenshipDao;

	@Autowired
	private EducationLevelDao educationLevelDao;

	@Autowired
	private EducationGoalDao educationGoalDao;

	@Autowired
	private EthnicityDao ethnicityDao;

	@Autowired
	private FundingSourceDao fundingSourceDao;

	@Autowired
	private MaritalStatusService martitalStatusService;

	@Autowired
	private PersonChallengeDao studentChallengeDao;

	@Autowired
	private PersonDao studentDao;

	@Autowired
	private PersonEducationGoalDao studentEducationGoalDao;

	@Autowired
	private PersonEducationLevelDao studentEducationLevelDao;

	@Autowired
	private PersonFundingSourceDao studentFundingSourceDao;

	@Autowired
	private StudentStatusDao studentStatusDao;

	@Autowired
	private StudentToolsDao studentToolsDao;

	@Autowired
	private VeteranStatusDao veteranStatusDao;

	@Autowired
	private ConfidentialityDisclosureDao confidentialityDisclosureDao;

	@Autowired
	private SecurityService securityService;

	// Confidentialty disclosure
	private static UUID SECTION_CONFIDENTIALTY_ID = UUID
			.fromString("5e322321-c296-4d4d-94c8-ebf34e374df3");
	private static UUID SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID = UUID
			.fromString("794b587b-981d-41d9-9d80-bfc6b74d0997");

	// Personal
	private static UUID SECTION_PERSONAL_ID = UUID
			.fromString("ce8ddcd9-0bb4-4d54-b1bd-c0af72ee11ca");
	private static UUID SECTION_PERSONAL_QUESTION_FIRSTNAME_ID = UUID
			.fromString("a68b0fcf-888e-46bd-b867-a664db93f57e");
	private static UUID SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID = UUID
			.fromString("c881a6c3-2f8f-4ad0-9596-6327982491eb");
	private static UUID SECTION_PERSONAL_QUESTION_LASTNAME_ID = UUID
			.fromString("12487490-5206-4169-9d3f-1708fc6592dd");
	private static UUID SECTION_PERSONAL_QUESTION_STUDENTID_ID = UUID
			.fromString("6647b9ab-27a2-4173-8c75-a21140f8e73b");
	private static UUID SECTION_PERSONAL_QUESTION_BIRTHDATE_ID = UUID
			.fromString("1353f252-ddb4-40cf-bb4c-8da30d832722");
	private static UUID SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID = UUID
			.fromString("3e70fbff-95cd-4d10-ae18-fcd756dd749f");
	private static UUID SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID = UUID
			.fromString("c11e5428-2c1e-4126-a603-0c97dea5651b");
	private static UUID SECTION_PERSONAL_QUESTION_HOMEPHONE_ID = UUID
			.fromString("f4d880e7-ff98-431b-95f9-c973bfb74924");
	private static UUID SECTION_PERSONAL_QUESTION_WORKPHONE_ID = UUID
			.fromString("de35fada-d530-4df2-9428-3ce0162b832d");
	private static UUID SECTION_PERSONAL_QUESTION_CELLPHONE_ID = UUID
			.fromString("9b6f5bec-1ac4-4253-af5c-e52e2050e70f");
	private static UUID SECTION_PERSONAL_QUESTION_ADDRESS_ID = UUID
			.fromString("dcf977fb-8a64-46ee-8d82-0920a3d94560");
	private static UUID SECTION_PERSONAL_QUESTION_CITY_ID = UUID
			.fromString("872e15e9-0b90-4ac4-96a7-4af8fd1f187a");
	private static UUID SECTION_PERSONAL_QUESTION_STATE_ID = UUID
			.fromString("36a8a8ba-d0f7-4e0f-beda-6f539c4f65c3");
	private static UUID SECTION_PERSONAL_QUESTION_ZIPCODE_ID = UUID
			.fromString("253fec7e-c4c3-4fd2-92db-ebcf7ae70322");

	// Demographics
	private static UUID SECTION_DEMOGRAPHICS_ID = UUID
			.fromString("a4b1c09d-15e6-4f44-8cf8-565eea074bf5");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID = UUID
			.fromString("39e4740b-966e-40d2-bbb1-6915e63fe802");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID = UUID
			.fromString("c8c6889a-f07e-4f20-9aa1-2f169232578c");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID = UUID
			.fromString("e8674ff9-3ae5-48ac-a596-72ea7096295d");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID = UUID
			.fromString("44c3bd2e-a8f9-4a40-b761-d22e43f01c4f");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID = UUID
			.fromString("7e86e36d-12ff-4447-bd9c-e0716bfc3a3c");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID = UUID
			.fromString("8bd42e34-f4c6-4909-b540-38015440ba9f");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID = UUID
			.fromString("00b334c3-901e-46b9-ac70-6845114b63f4");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID = UUID
			.fromString("83e706c4-bbd4-4b59-a0b5-f6fe5094d5ab");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID = UUID
			.fromString("11221b47-d32d-4c30-9701-7072ab175483");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID = UUID
			.fromString("d2278348-1b16-466d-aa82-b88b58818dc9");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID = UUID
			.fromString("6517ad4d-28cc-4cf5-8d32-9eeeac101257");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID = UUID
			.fromString("9e07876c-47b9-4e68-8854-c48fd79527a9");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID = UUID
			.fromString("8fb34e96-1db7-4078-adc8-0e73b6ca73c9");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID = UUID
			.fromString("5d9a5b30-13c8-4498-80b8-2bba32ff8178");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID = UUID
			.fromString("9d818a23-7d99-4189-9131-cfef3253a7b1");
	private static UUID SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID = UUID
			.fromString("316a04e4-6735-47b2-875b-fc2b56a95ca3");

	// EducationalPlan
	private static UUID SECTION_EDUCATIONPLAN_ID = UUID
			.fromString("28235c89-214c-40bd-9181-bc2daaa8441d");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID = UUID
			.fromString("73f95353-ed79-4402-afbb-b0e744e07d5d");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID = UUID
			.fromString("b5a9d91e-b95b-4a7b-b485-7d69cf83b786");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID = UUID
			.fromString("42d36849-2095-444c-81a4-6d7a38226a65");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID = UUID
			.fromString("5ce514a2-45db-4b8a-b054-26df63420d22");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMODATIONS_ID = UUID
			.fromString("67bcc088-3fc4-4d0f-aa88-4c34d36acc8b");
	private static UUID SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID = UUID
			.fromString("30def0f2-d89a-47e4-a8f3-61471c570f3b");

	// Education Levels
	private static UUID SECTION_EDUCATIONLEVEL_ID = UUID
			.fromString("45ca75b1-e9b5-4595-928b-07be382475cd");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID = UUID
			.fromString("e4557e29-8aff-4f03-94f1-d7a3475673d7");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID = UUID
			.fromString("798a886c-c4a2-408d-9b32-299cd5b8bffe");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID = UUID
			.fromString("f412ca4c-ddac-40e3-8338-854c6c2eb29b");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID = UUID
			.fromString("cef96a6d-85fb-4b5a-8a51-f6d1443ca830");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID = UUID
			.fromString("a0657f72-9650-4dec-b146-dafee6c6e59c");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID = UUID
			.fromString("9fb9c0c3-9df5-45a9-998f-986c6c23f1e9");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID = UUID
			.fromString("d6cc72f9-57ff-4e39-8568-67750395618d");
	private static UUID SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID = UUID
			.fromString("ac1c43c0-5705-479e-8236-bb6dca4744c9");

	// Education Goal
	private static UUID SECTION_EDUCATIONGOAL_ID = UUID
			.fromString("dad92c0e-c185-4baa-b114-9cbedebeacd5");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID = UUID
			.fromString("8055f756-afc1-487f-a67f-1e66d5db3907");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID = UUID
			.fromString("a222b26f-c325-443e-8df8-5db3079353c3");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID = UUID
			.fromString("98abe279-9cb0-4df1-859b-a7b96ec82b3c");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID = UUID
			.fromString("4e3eceb7-a832-43b9-bccd-151e77fd3a84");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID = UUID
			.fromString("6dda82d4-5f6a-4187-bf56-4d26730be054");
	private static UUID SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID = UUID
			.fromString("ca44bce9-3e12-46e4-b83d-51a60878bb8c");

	// Funding
	private static UUID SECTION_FUNDING_ID = UUID
			.fromString("e14e5879-fb06-47e4-93af-ed4dfb273821");
	private static UUID SECTION_FUNDING_QUESTION_FUNDING_ID = UUID
			.fromString("6178c564-4247-4d79-9f6a-075e2689e7e0");
	private static UUID SECTION_FUNDING_QUESTION_OTHER_ID = UUID
			.fromString("f6f60253-e62c-4f6c-898b-0392b43bf2d5");
	private static UUID SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID = UUID
			.fromString("B2D05DEC-5056-A51A-8001FE8BDD379C5B");

	// Challenge
	private static UUID SECTION_CHALLENGE_ID = UUID
			.fromString("47ebe563-1b68-42aa-ad23-93153cb99cce");
	private static UUID SECTION_CHALLENGE_QUESTION_CHALLENGE_ID = UUID
			.fromString("3f8d0dc4-4506-4cd6-95e6-3d74c0a07f80");
	private static UUID SECTION_CHALLENGE_QUESTION_OTHER_ID = UUID
			.fromString("839dc532-1aec-4580-8294-8c97bb72fa72");

	public FormTO create() {

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

	public FormTO populate() {

		FormTO formTO = create();

		Person student = securityService.currentlyLoggedInSspUser().getPerson();

		/* Confidentiality disclosure */

		FormSectionTO formSectionTO = FormUtil.getFormSectionById(
				SECTION_CONFIDENTIALTY_ID, formTO);
		if (null != formSectionTO) {
			FormUtil.getFormQuestionById(
					SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID, formSectionTO)
					.setValue(
							confidentialityDisclosureDao
									.getAgreementByStudent(student));
		}

		/* Personal */

		formSectionTO = FormUtil
				.getFormSectionById(SECTION_PERSONAL_ID, formTO);

		// First Name
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_FIRSTNAME_ID,
				formSectionTO).setValue(student.getFirstName());

		// Middle Initial
		FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID, formSectionTO)
				.setValue(student.getMiddleInitial());

		// Last Name
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_LASTNAME_ID,
				formSectionTO).setValue(student.getLastName());

		// Student Id
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_STUDENTID_ID,
				formSectionTO).setValue(student.getId());

		// Birthdate
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_BIRTHDATE_ID,
				formSectionTO).setValue(format.format(student.getBirthDate()));

		// School Email
		/*
		 * FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID,
		 * formSectionTO).setValue(student.getSchoolEmailAddress());
		 * 
		 * // Home Email
		 * FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID,
		 * formSectionTO).setValue(student.getHomeEmailAddress());
		 */

		// Home Phone
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_HOMEPHONE_ID,
				formSectionTO).setValue(student.getHomePhone());

		// Work Phone
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_WORKPHONE_ID,
				formSectionTO).setValue(student.getWorkPhone());

		// Cell Phone
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_CELLPHONE_ID,
				formSectionTO).setValue(student.getCellPhone());

		// Address
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_ADDRESS_ID,
				formSectionTO).setValue(student.getAddressLine1());

		// City
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_CITY_ID,
				formSectionTO).setValue(student.getCity());

		// State
		if (student.getState() == null) {
			FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_STATE_ID,
					formSectionTO)
					.getOptions()
					.add(0,
							new FormOptionTO(UUID.randomUUID(),
									Constants.DEFAULT_DROPDOWN_LIST_LABEL,
									Constants.DEFAULT_DROPDOWN_LIST_VALUE));
			FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_STATE_ID,
					formSectionTO).setValue(
					Constants.DEFAULT_DROPDOWN_LIST_VALUE);
		} else {
			FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_STATE_ID,
					formSectionTO).setValue(student.getState());
		}

		// Zip Code
		FormUtil.getFormQuestionById(SECTION_PERSONAL_QUESTION_ZIPCODE_ID,
				formSectionTO).setValue(student.getZipCode());

		/* Demographics */

		if (student.getDemographics() != null) {
			formSectionTO = FormUtil.getFormSectionById(
					SECTION_DEMOGRAPHICS_ID, formTO);

			// Marital Status
			if (student.getDemographics().getMaritalStatus() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID,
						formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID,
						formSectionTO).setValue(
						Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID,
						formSectionTO).setValue(
						student.getDemographics().getMaritalStatus().getId()
								.toString());
			}

			// Ethnicity
			if (student.getDemographics().getEthnicity() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID,
						formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID,
						formSectionTO).setValue(
						Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID,
						formSectionTO).setValue(
						student.getDemographics().getEthnicity().getId()
								.toString());
			}

			// Gender
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID, formSectionTO)
					.setValue(student.getDemographics().getGender().getCode());

			// Citizenship
			if (student.getDemographics().getCitizenship() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID,
						formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID,
						formSectionTO).setValue(
						Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID,
						formSectionTO).setValue(
						student.getDemographics().getCitizenship().getId()
								.toString());
			}

			// Country of Citizenship -> Shown if Citizenship is "International"
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID,
					formSectionTO).setValue(
					student.getDemographics().getCountryOfCitizenship());

			// Veteran Status
			if (student.getDemographics().getVeteranStatus() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID,
						formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID,
						formSectionTO).setValue(
						Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID,
						formSectionTO).setValue(
						student.getDemographics().getVeteranStatus().getId());
			}

			// Primary Caregiver
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID,
					formSectionTO).setValue(
					student.getDemographics().isPrimaryCaregiver());

			// How Many Children
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID,
					formSectionTO).setValue(
					String.valueOf(student.getDemographics()
							.getNumberOfChildren()));

			// Chidren Ages
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID,
					formSectionTO).setValue(
					student.getDemographics().getChildAges());

			// Childcare Needed
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID,
					formSectionTO).setValue(
					student.getDemographics().isChildCareNeeded());

			// Childcare Arrangements
			if (student.getDemographics().getChildCareArrangement() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID,
						formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID,
						formSectionTO).setValue(
						Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID,
						formSectionTO).setValue(
						student.getDemographics().getChildCareArrangement()
								.getId());
			}

			// Employed
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID, formSectionTO)
					.setValue(student.getDemographics().isEmployed());

			// Employer
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID, formSectionTO)
					.setValue(student.getDemographics().getPlaceOfEmployment());

			// Shift
			if (student.getDemographics().getShift() == null) {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID, formSectionTO)
						.getOptions()
						.add(0,
								new FormOptionTO(UUID.randomUUID(),
										Constants.DEFAULT_DROPDOWN_LIST_LABEL,
										Constants.DEFAULT_DROPDOWN_LIST_VALUE));
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID, formSectionTO)
						.setValue(Constants.DEFAULT_DROPDOWN_LIST_VALUE);
			} else {
				FormUtil.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID, formSectionTO)
						.setValue(
								student.getDemographics().getShift().getCode());
			}

			// Wage
			FormUtil.getFormQuestionById(SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID,
					formSectionTO)
					.setValue(student.getDemographics().getWage());

			// Hours worked per week
			FormUtil.getFormQuestionById(
					SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID,
					formSectionTO).setValue(
					student.getDemographics().getTotalHoursWorkedPerWeek());

		}

		/* Education Plan */
		if (student.getEducationPlan() != null) {

			formSectionTO = FormUtil.getFormSectionById(
					SECTION_EDUCATIONPLAN_ID, formTO);

			// Student Status
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID,
					formSectionTO).setValue(
					student.getEducationPlan().getStudentStatus());

			// New Student Orientation
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID,
					formSectionTO).setValue(
					student.getEducationPlan().isNewOrientationComplete());

			// Registered for Classes
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID,
					formSectionTO).setValue(
					student.getEducationPlan().isRegisteredForClasses());

			// Have Parents Obtained a College Degree
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID,
					formSectionTO).setValue(
					student.getEducationPlan().isCollegeDegreeForParents());

			// Special Needs or Accomodations
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMODATIONS_ID,
					formSectionTO).setValue(
					student.getEducationPlan().isSpecialNeeds());

			// Grade at Highest Education Level
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID,
					formSectionTO).setValue(
					student.getEducationPlan().getGradeTypicallyEarned());
		}

		/* Education Level */

		FormSectionTO educationLevelFormSection = FormUtil.getFormSectionById(
				SECTION_EDUCATIONLEVEL_ID, formTO);

		FormQuestionTO educationLevelFormQuestion = FormUtil
				.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID,
						educationLevelFormSection);
		List<String> educationLevelFormQuestionValues = new ArrayList<String>();

		for (PersonEducationLevel studentEducationLevel : student
				.getEducationLevels()) {

			educationLevelFormQuestionValues.add(FormUtil.getFormOptionById(
					studentEducationLevel.getEducationLevel().getId(),
					educationLevelFormQuestion).getValue());

			if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.NO_DIPLOMA_NO_GED_ID)) {

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID,
						educationLevelFormSection).setValue(
						MyGpsStringUtils.stringFromYear(studentEducationLevel
								.getLastYearAttended()));

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID,
						educationLevelFormSection).setValue(
						String.valueOf(studentEducationLevel
								.getHighestGradeCompleted()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.GED_ID)) {

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID,
						educationLevelFormSection).setValue(
						MyGpsStringUtils.stringFromYear(studentEducationLevel
								.getGraduatedYear()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.HIGH_SCHOOL_GRADUATION_ID)) {

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID,
						educationLevelFormSection).setValue(
						MyGpsStringUtils.stringFromYear(studentEducationLevel
								.getGraduatedYear()));

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID,
						educationLevelFormSection).setValue(
						studentEducationLevel.getSchoolName());

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.SOME_COLLEGE_CREDITS_ID)) {

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID,
						educationLevelFormSection).setValue(
						MyGpsStringUtils.stringFromYear(studentEducationLevel
								.getLastYearAttended()));

			} else if (studentEducationLevel.getEducationLevel().getId()
					.equals(EducationLevel.OTHER_ID)) {

				FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID,
						educationLevelFormSection).setValue(
						studentEducationLevel.getDescription());

			}
		}

		educationLevelFormQuestion.setValues(educationLevelFormQuestionValues);

		/* Education Goal */

		if (student.getEducationGoal() != null) {

			formSectionTO = FormUtil.getFormSectionById(
					SECTION_EDUCATIONGOAL_ID, formTO);

			// Goal
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID, formSectionTO)
					.setValue(student.getEducationGoal().getEducationGoal());

			// Goal Description
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID,
					formSectionTO).setValue(
					student.getEducationGoal().getDescription());

			// Other Description
			/*
			 * FormUtil.getFormQuestionById(
			 * SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID,
			 * formSectionTO).setValue(
			 * student.getEducationGoal().getOtherDescription());
			 */

			// Sure of Major
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID,
					formSectionTO).setValue(
					student.getEducationGoal().getHowSureAboutMajor());

			// Career Goal
			FormUtil.getFormQuestionById(
					SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID, formSectionTO)
					.setValue(student.getEducationGoal().getPlannedOccupation());

			// Military Branch Description
			/*
			 * FormUtil.getFormQuestionById(
			 * SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID,
			 * formSectionTO).setValue( student.getEducationGoal()
			 * .getMilitaryBranchDescription());
			 */
		}

		/* Funding Sources */

		FormSectionTO fundingFormSection = FormUtil.getFormSectionById(
				SECTION_FUNDING_ID, formTO);

		FormQuestionTO fundingQuestion = FormUtil.getFormQuestionById(
				SECTION_FUNDING_QUESTION_FUNDING_ID, fundingFormSection);
		List<String> fundingQuestionValues = new ArrayList<String>();

		for (PersonFundingSource studentFundingSource : student
				.getFundingSources()) {
			fundingQuestionValues.add(studentFundingSource.getFundingSource()
					.getId().toString());

			if (studentFundingSource.getFundingSource().getId()
					.equals(SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID)) {

				FormQuestionTO fundingOtherQuestion = FormUtil
						.getFormQuestionById(SECTION_FUNDING_QUESTION_OTHER_ID,
								fundingFormSection);

				fundingOtherQuestion.setValue(studentFundingSource
						.getDescription());
			}
		}

		fundingQuestion.setValues(fundingQuestionValues);

		/* Challenges */

		FormSectionTO challengeFormSection = FormUtil.getFormSectionById(
				SECTION_CHALLENGE_ID, formTO);

		FormQuestionTO challengeQuestion = FormUtil.getFormQuestionById(
				SECTION_CHALLENGE_QUESTION_CHALLENGE_ID, challengeFormSection);
		List<String> challengeQuestionValues = new ArrayList<String>();

		for (PersonChallenge studentChallenge : student.getChallenges()) {
			challengeQuestionValues.add(studentChallenge.getChallenge().getId()
					.toString());
		}

		challengeQuestion.setValues(challengeQuestionValues);

		return formTO;
	}

	public void save(FormTO formTO) throws ObjectNotFoundException {

		Person student = securityService.currentlyLoggedInSspUser().getPerson();

		/* Add intake form to student's record */
		studentToolsDao.addIntakeToolToStudent(student);

		/* Confidentiality disclosure */

		FormSectionTO confidentialitySection = FormUtil.getFormSectionById(
				SECTION_CONFIDENTIALTY_ID, formTO);

		if (null != confidentialitySection) {
			Boolean agreed = MyGpsStringUtils.booleanFromString(FormUtil
					.getFormQuestionById(
							SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID,
							confidentialitySection).getValue());
			confidentialityDisclosureDao.saveAgreementByStudent(student,
					agreed, student.getUsername());
		}

		/* Personal */

		FormSectionTO personalSection = FormUtil.getFormSectionById(
				SECTION_PERSONAL_ID, formTO);

		// First Name
		student.setFirstName(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_FIRSTNAME_ID, personalSection)
				.getValue());

		// Middle Initial
		student.setMiddleInitial(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID, personalSection)
				.getValue());

		// Last Name
		student.setLastName(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_LASTNAME_ID, personalSection)
				.getValue());

		// Student Id
		student.setUserId(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_STUDENTID_ID, personalSection)
				.getValue());

		// Birthdate
		FormQuestionTO birthDateQuestion = FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_BIRTHDATE_ID, personalSection);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		try {
			student.setBirthDate(format.parse(birthDateQuestion.getValue()));
		} catch (ParseException pe) {
			// just leave the birthDate alone
		}

		// School Email
		/*
		 * student.setSchoolEmailAddress(FormUtil.getFormQuestionById(
		 * SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID, personalSection)
		 * .getValue());
		 * 
		 * // Home Email
		 * student.setHomeEmailAddress(FormUtil.getFormQuestionById(
		 * SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID, personalSection)
		 * .getValue());
		 */

		// Home Phone
		student.setHomePhone(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_HOMEPHONE_ID, personalSection)
				.getValue());

		// Work Phone
		student.setWorkPhone(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_WORKPHONE_ID, personalSection)
				.getValue());

		// Cell Phone
		student.setCellPhone(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_CELLPHONE_ID, personalSection)
				.getValue());

		// Address
		student.setAddressLine1(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_ADDRESS_ID, personalSection)
				.getValue());

		// City
		student.setCity(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_CITY_ID, personalSection).getValue());

		// State
		FormQuestionTO stateQuestion = FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_STATE_ID, personalSection);

		if (Constants.DEFAULT_DROPDOWN_LIST_VALUE.equals(stateQuestion
				.getValue())) {
			student.setState(null);
		} else {
			student.setState(stateQuestion.getValue());
		}

		// ZIP Code
		student.setZipCode(FormUtil.getFormQuestionById(
				SECTION_PERSONAL_QUESTION_ZIPCODE_ID, personalSection)
				.getValue());

		/* Demographics */

		FormSectionTO demographicsSection = FormUtil.getFormSectionById(
				SECTION_DEMOGRAPHICS_ID, formTO);

		PersonDemographics demographics = student.getDemographics();

		if (demographics == null) {
			demographics = new PersonDemographics();
		}

		demographics.setChildAges(FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID,
				demographicsSection).getValue());

		// Childcare Arrangements
		FormQuestionTO childCareArrangementQuestion = FormUtil
				.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID,
						demographicsSection);

		if (childCareArrangementQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setChildCareArrangement(null);
		} else {
			demographics.setChildCareArrangement(childcareArrangementDao
					.get(UUID.fromString(childCareArrangementQuestion
							.getValue())));
		}

		// Childcare Needed
		FormQuestionTO childCareNeededQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_CHILDCARENEEDED_ID,
				demographicsSection);

		if (childCareNeededQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setChildCareNeeded(false);
		} else {
			demographics.setChildCareNeeded(MyGpsStringUtils
					.booleanFromString(childCareNeededQuestion.getValue()));
		}

		// Citizenship
		FormQuestionTO citizenshipQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID,
				demographicsSection);

		if (citizenshipQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setCitizenship(null);
		} else {
			demographics.setCitizenship(citizenshipDao.get(UUID
					.fromString(citizenshipQuestion.getValue())));
		}

		demographics.setCountryOfCitizenship(FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID,
				demographicsSection).getValue());

		// Employed
		FormQuestionTO employedQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID, demographicsSection);

		if (Constants.DEFAULT_DROPDOWN_LIST_VALUE.equals(employedQuestion
				.getValue())) {
			demographics.setEmployed(false);
		} else {
			demographics.setEmployed(MyGpsStringUtils
					.booleanFromString(employedQuestion.getValue()));
		}

		demographics.setPlaceOfEmployment(FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID, demographicsSection)
				.getValue());

		// Ethnicity
		FormQuestionTO ethnicityQuestion = FormUtil
				.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID,
						demographicsSection);

		if (ethnicityQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setEthnicity(null);
		} else {
			demographics.setEthnicity(ethnicityDao.get(UUID
					.fromString(ethnicityQuestion.getValue())));
		}

		// Gender
		FormQuestionTO genderQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID, demographicsSection);

		if (genderQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setGender(null);
		} else {
			demographics.setGender(Genders.valueOf(genderQuestion.getValue()));
		}

		demographics.setTotalHoursWorkedPerWeek(FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_HOURSWORKEDPERWEEK_ID,
				demographicsSection).getValue());

		// Marital Status
		FormQuestionTO maritalStatusQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_MARITALSTATUS_ID,
				demographicsSection);

		if (maritalStatusQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setMaritalStatus(null);
		} else {
			demographics.setMaritalStatus(martitalStatusService.get(UUID
					.fromString(maritalStatusQuestion.getValue())));
		}

		demographics.setNumberOfChildren(Integer.parseInt(FormUtil
				.getFormQuestionById(
						SECTION_DEMOGRAPHICS_QUESTION_HOWMANYCHILDREN_ID,
						demographicsSection).getValue()));

		// Primary caregiver
		FormQuestionTO primaryCaregiverQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_PRIMARYCAREGIVER_ID,
				demographicsSection);

		if (primaryCaregiverQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setPrimaryCaregiver(false);
		} else {
			demographics.setPrimaryCaregiver(MyGpsStringUtils
					.booleanFromString(primaryCaregiverQuestion.getValue()));
		}

		// Shift
		FormQuestionTO shiftQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_SHIFT_ID, demographicsSection);

		if (shiftQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			demographics.setShift(null);
		} else {
			demographics.setShift(EmploymentShifts.valueOf(shiftQuestion
					.getValue()));
		}

		// Veteran Status
		FormQuestionTO veteranStatusQuestion = FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID,
				demographicsSection);

		if (veteranStatusQuestion.getValue() == null) {
			demographics.setVeteranStatus(null);
		} else {
			demographics.setVeteranStatus(veteranStatusDao.get(UUID
					.fromString(veteranStatusQuestion.getValue())));
		}

		demographics.setWage(FormUtil.getFormQuestionById(
				SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID, demographicsSection)
				.getValue());

		/* Education Plan */

		FormSectionTO educationPlanSection = FormUtil.getFormSectionById(
				SECTION_EDUCATIONPLAN_ID, formTO);

		PersonEducationPlan educationPlan = student.getEducationPlan();

		if (educationPlan == null) {
			educationPlan = new PersonEducationPlan();
		}

		educationPlan.setNewOrientationComplete(MyGpsStringUtils
				.booleanFromString(FormUtil.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_COMPLETEDORIENTATION_ID,
						educationPlanSection).getValue()));

		educationPlan.setGradeTypicallyEarned(FormUtil.getFormQuestionById(
				SECTION_EDUCATIONPLAN_QUESTION_GRADEATHIGHESTEDUCATIONLEVEL_ID,
				educationPlanSection).getValue());

		// Parents Have College Degree
		FormQuestionTO parentsHaveCollegeDegreeQuestion = FormUtil
				.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_PARENTSHAVECOLLEGEDEGREE_ID,
						educationPlanSection);

		if (parentsHaveCollegeDegreeQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			educationPlan.setCollegeDegreeForParents(false);
		} else {
			educationPlan.setCollegeDegreeForParents(MyGpsStringUtils
					.booleanFromString(parentsHaveCollegeDegreeQuestion
							.getValue()));
		}

		educationPlan.setRegisteredForClasses(MyGpsStringUtils
				.booleanFromString(FormUtil.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_REGISTEREDFORCLASSES_ID,
						educationPlanSection).getValue()));

		// Require Special Accomodation
		FormQuestionTO requireSpecialAccomodationQuestion = FormUtil
				.getFormQuestionById(
						SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMODATIONS_ID,
						educationPlanSection);

		if (requireSpecialAccomodationQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			educationPlan.setSpecialNeeds(false);
		} else {
			educationPlan.setSpecialNeeds(MyGpsStringUtils
					.booleanFromString(requireSpecialAccomodationQuestion
							.getValue()));
		}

		// Student Status
		FormQuestionTO studentStatusQuestion = FormUtil.getFormQuestionById(
				SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID,
				educationPlanSection);

		if (studentStatusQuestion.getValue() == Constants.DEFAULT_DROPDOWN_LIST_VALUE) {
			educationPlan.setStudentStatus(null);
		} else {
			educationPlan.setStudentStatus(studentStatusDao.get(UUID
					.fromString(studentStatusQuestion.getValue())));
		}

		/* Education Level */

		FormSectionTO educationLevelSection = FormUtil.getFormSectionById(
				SECTION_EDUCATIONLEVEL_ID, formTO);

		FormQuestionTO educationLevelQuestion = FormUtil.getFormQuestionById(
				SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID,
				educationLevelSection);

		String educationLevelQuestionValue = null;

		// Delete all existing education levels
		Set<PersonEducationLevel> studentEducationLevels = student
				.getEducationLevels();

		for (PersonEducationLevel studentEducationLevel : studentEducationLevels) {
			studentEducationLevelDao.delete(studentEducationLevel);
		}

		// int counter = 1;

		for (FormOptionTO formOption : educationLevelQuestion.getOptions()) {

			educationLevelQuestionValue = null;

			PersonEducationLevel studentEducationLevel = new PersonEducationLevel();

			// studentEducationLevel.setCounter(counter);
			studentEducationLevel.setEducationLevel(new EducationLevel(
					formOption.getId()));
			studentEducationLevel.setPerson(student);

			// We only continue if this option has been selected by the user
			boolean optionSelected = false;
			for (String selectedLevel : educationLevelQuestion.getValues()) {
				if (formOption.getValue().equals(selectedLevel)) {
					optionSelected = true;
					break;
				}
			}
			if (!optionSelected) {
				continue;
			}

			if (formOption.getId().equals(EducationLevel.NO_DIPLOMA_NO_GED_ID)) {

				educationLevelQuestionValue = FormUtil
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID,
								educationLevelSection).getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setLastYearAttended(Integer
							.parseInt(educationLevelQuestionValue));
				}

				educationLevelQuestionValue = FormUtil
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMAHIGHESTGRADECOMPLETED_ID,
								educationLevelSection).getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setHighestGradeCompleted(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(EducationLevel.GED_ID)) {

				educationLevelQuestionValue = FormUtil.getFormQuestionById(
						SECTION_EDUCATIONLEVEL_QUESTION_GEDYEAROFGED_ID,
						educationLevelSection).getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setGraduatedYear(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(
					EducationLevel.HIGH_SCHOOL_GRADUATION_ID)) {

				educationLevelQuestionValue = FormUtil
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLYEARGRADUATED_ID,
								educationLevelSection).getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setGraduatedYear(Integer
							.parseInt(educationLevelQuestionValue));
				}

				studentEducationLevel
						.setSchoolName(FormUtil
								.getFormQuestionById(
										SECTION_EDUCATIONLEVEL_QUESTION_HIGHSCHOOLATTENDED_ID,
										educationLevelSection).getValue());

			} else if (formOption.getId().equals(
					EducationLevel.SOME_COLLEGE_CREDITS_ID)) {

				educationLevelQuestionValue = FormUtil
						.getFormQuestionById(
								SECTION_EDUCATIONLEVEL_QUESTION_SOMECOLLEGECREDITSLASTYEARATTENDED_ID,
								educationLevelSection).getValue();

				if (educationLevelQuestionValue != null) {
					studentEducationLevel.setLastYearAttended(Integer
							.parseInt(educationLevelQuestionValue));
				}

			} else if (formOption.getId().equals(EducationLevel.OTHER_ID)) {

				studentEducationLevel
						.setDescription(FormUtil
								.getFormQuestionById(
										SECTION_EDUCATIONLEVEL_QUESTION_OTHERPLEASEEXPLAIN_ID,
										educationLevelSection).getValue());

			}

			studentEducationLevelDao.save(studentEducationLevel);

			// counter++;
		}

		/* Education Goal */
		FormSectionTO educationGoalSection = FormUtil.getFormSectionById(
				SECTION_EDUCATIONGOAL_ID, formTO);

		PersonEducationGoal studentEducationGoal = student.getEducationGoal();

		if (studentEducationGoal == null) {
			studentEducationGoal = new PersonEducationGoal();
		}

		studentEducationGoal.setPlannedOccupation(FormUtil.getFormQuestionById(
				SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID,
				educationGoalSection).getValue());
		studentEducationGoal.setEducationGoal(educationGoalDao.get(UUID
				.fromString(FormUtil.getFormQuestionById(
						SECTION_EDUCATIONGOAL_QUESTION_GOAL_ID,
						educationGoalSection).getValue())));
		studentEducationGoal.setDescription(FormUtil.getFormQuestionById(
				SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID,
				educationGoalSection).getValue());

		/*
		 * studentEducationGoal .setMilitaryBranchDescription(FormUtil
		 * .getFormQuestionById(
		 * SECTION_EDUCATIONGOAL_QUESTION_MILITARYBRANCHDESCRIPTION_ID,
		 * educationGoalSection).getValue());
		 * studentEducationGoal.setOtherDescription
		 * (FormUtil.getFormQuestionById(
		 * SECTION_EDUCATIONGOAL_QUESTION_OTHERDESCRIPTION_ID,
		 * educationGoalSection).getValue());
		 */
		studentEducationGoal.setHowSureAboutMajor(Integer.valueOf(FormUtil
				.getFormQuestionById(
						SECTION_EDUCATIONGOAL_QUESTION_SUREOFMAJOR_ID,
						educationGoalSection).getValue()));

		studentEducationGoalDao.save(studentEducationGoal);

		/* Funding */
		FormSectionTO fundingSection = FormUtil.getFormSectionById(
				SECTION_FUNDING_ID, formTO);
		FormQuestionTO fundingQuestion = FormUtil.getFormQuestionById(
				SECTION_FUNDING_QUESTION_FUNDING_ID, fundingSection);

		// Delete all funding sources
		Set<PersonFundingSource> studentFundingSources = student
				.getFundingSources();

		for (PersonFundingSource studentFundingSource : studentFundingSources) {
			studentFundingSourceDao.delete(studentFundingSource);
		}

		for (String value : fundingQuestion.getValues()) {

			FormOptionTO formOptionTO = FormUtil.getFormOptionByValue(value,
					fundingQuestion);

			if (formOptionTO != null) {

				PersonFundingSource studentFundingSource = new PersonFundingSource();

				studentFundingSource.setFundingSource(new FundingSource(
						formOptionTO.getId()));

				if (formOptionTO.getId().equals(
						SECTION_FUNDING_OTHER_FUNDING_SOURCE_ID)) {
					studentFundingSource.setDescription(FormUtil
							.getFormQuestionById(
									SECTION_FUNDING_QUESTION_OTHER_ID,
									fundingSection).getValue());
				} else {
					studentFundingSource
							.setDescription(formOptionTO.getLabel());
				}

				studentFundingSource.setPerson(student);

				studentFundingSourceDao.save(studentFundingSource);
			}
		}

		/* Challenges */
		FormSectionTO challengeSection = FormUtil.getFormSectionById(
				SECTION_CHALLENGE_ID, formTO);
		FormQuestionTO challengeQuestion = FormUtil.getFormQuestionById(
				SECTION_CHALLENGE_QUESTION_CHALLENGE_ID, challengeSection);

		// Delete all student challenges
		Set<PersonChallenge> studentChallenges = student.getChallenges();

		for (PersonChallenge studentChallenge : studentChallenges) {
			studentChallengeDao.delete(studentChallenge);
		}

		for (String value : challengeQuestion.getValues()) { // Alcohol
			// Issues/Concerns

			FormOptionTO formOptionTO = FormUtil.getFormOptionByValue(value,
					challengeQuestion);

			if (formOptionTO != null) {

				PersonChallenge studentChallenge = new PersonChallenge();

				studentChallenge.setChallenge(new Challenge(formOptionTO
						.getId()));
				// studentChallenge.setOtherDescription(formOptionTO.getValue());
				studentChallenge.setPerson(student);
			}

		}

		studentDao.save(student);

	}

	private FormSectionTO buildConfidentialitySection() {
		Person student = securityService.currentlyLoggedInSspUser().getPerson();
		if (confidentialityDisclosureDao.getAgreementByStudent(student)) {
			// if already agreed to, we don't need to see it again.
			return null;
		}

		FormSectionTO confidentialitySection = new FormSectionTO();
		List<FormQuestionTO> confidentialitySectionQuestions = new ArrayList<FormQuestionTO>();

		confidentialitySection.setId(SECTION_CONFIDENTIALTY_ID);
		confidentialitySection.setLabel("Confidentiality Disclosure");

		// Agree
		FormQuestionTO agreeQuestion = new FormQuestionTO();

		agreeQuestion.setId(SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID);
		agreeQuestion.setLabel(confidentialityDisclosureDao
				.getDisclosureAgreementText());
		agreeQuestion.setRequired(true);
		agreeQuestion.setValidationExpression("isChecked()");
		agreeQuestion.setType(Constants.FORM_TYPE_AGREEMENT);

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
		firstNameQuestionTO.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(firstNameQuestionTO);

		// Middle Initial
		FormQuestionTO middleInitialQuestionTO = new FormQuestionTO();

		middleInitialQuestionTO.setReadOnly(true);
		middleInitialQuestionTO
				.setId(SECTION_PERSONAL_QUESTION_MIDDLEINITIAL_ID);
		middleInitialQuestionTO.setLabel("Middle Initial");
		middleInitialQuestionTO.setMaximumLength("1");
		middleInitialQuestionTO.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(middleInitialQuestionTO);

		// Last Name
		FormQuestionTO lastNameQuestionTO = new FormQuestionTO();

		lastNameQuestionTO.setReadOnly(true);
		lastNameQuestionTO.setId(SECTION_PERSONAL_QUESTION_LASTNAME_ID);
		lastNameQuestionTO.setLabel("Last");
		lastNameQuestionTO.setMaximumLength("30");
		lastNameQuestionTO.setRequired(true);
		lastNameQuestionTO.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(lastNameQuestionTO);

		// Student ID
		FormQuestionTO studentIdQuestion = new FormQuestionTO();

		studentIdQuestion.setReadOnly(true);
		studentIdQuestion.setId(SECTION_PERSONAL_QUESTION_STUDENTID_ID);
		studentIdQuestion.setLabel("Student ID");
		studentIdQuestion.setMaximumLength("50");
		studentIdQuestion.setRequired(true);
		studentIdQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(studentIdQuestion);

		// Birthdate
		FormQuestionTO birthDateQuestion = new FormQuestionTO();

		birthDateQuestion.setReadOnly(true);
		birthDateQuestion.setId(SECTION_PERSONAL_QUESTION_BIRTHDATE_ID);
		birthDateQuestion.setLabel("Birthdate");
		birthDateQuestion.setMaximumLength("10");
		birthDateQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(birthDateQuestion);

		// School Email
		FormQuestionTO schoolEmailQuestion = new FormQuestionTO();

		schoolEmailQuestion.setReadOnly(true);
		schoolEmailQuestion.setId(SECTION_PERSONAL_QUESTION_SCHOOLEMAIL_ID);
		schoolEmailQuestion.setLabel("Email (School)");
		schoolEmailQuestion.setMaximumLength("50");
		schoolEmailQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(schoolEmailQuestion);

		// Home Email
		FormQuestionTO homeEmailQuestion = new FormQuestionTO();

		homeEmailQuestion.setId(SECTION_PERSONAL_QUESTION_HOMEEMAIL_ID);
		homeEmailQuestion.setLabel("Email (Home)");
		homeEmailQuestion.setMaximumLength("50");
		homeEmailQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(homeEmailQuestion);

		// Home Phone
		FormQuestionTO homePhoneQuestion = new FormQuestionTO();

		homePhoneQuestion.setReadOnly(true);
		homePhoneQuestion.setId(SECTION_PERSONAL_QUESTION_HOMEPHONE_ID);
		homePhoneQuestion.setLabel("Home Phone");
		homePhoneQuestion.setMaximumLength("12");
		homePhoneQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(homePhoneQuestion);

		// Work Phone
		FormQuestionTO workPhoneQuestion = new FormQuestionTO();

		workPhoneQuestion.setReadOnly(true);
		workPhoneQuestion.setId(SECTION_PERSONAL_QUESTION_WORKPHONE_ID);
		workPhoneQuestion.setLabel("Work Phone");
		workPhoneQuestion.setMaximumLength("12");
		workPhoneQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(workPhoneQuestion);

		// Cell Phone
		FormQuestionTO cellPhoneQuestion = new FormQuestionTO();

		cellPhoneQuestion.setId(SECTION_PERSONAL_QUESTION_CELLPHONE_ID);
		cellPhoneQuestion.setLabel("Cell Phone");
		cellPhoneQuestion.setMaximumLength("12");
		cellPhoneQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(cellPhoneQuestion);

		// Address
		FormQuestionTO addressQuestion = new FormQuestionTO();

		addressQuestion.setReadOnly(true);
		addressQuestion.setId(SECTION_PERSONAL_QUESTION_ADDRESS_ID);
		addressQuestion.setLabel("Address");
		addressQuestion.setMaximumLength("50");
		addressQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

		personalSectionQuestions.add(addressQuestion);

		// City
		FormQuestionTO cityQuestion = new FormQuestionTO();

		cityQuestion.setReadOnly(true);
		cityQuestion.setId(SECTION_PERSONAL_QUESTION_CITY_ID);
		cityQuestion.setLabel("City");
		cityQuestion.setMaximumLength("50");
		cityQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

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
		stateQuestion.setType(Constants.FORM_TYPE_SELECT);

		personalSectionQuestions.add(stateQuestion);

		// Zip Code
		FormQuestionTO zipCodeQuestion = new FormQuestionTO();

		zipCodeQuestion.setReadOnly(true);
		zipCodeQuestion.setId(SECTION_PERSONAL_QUESTION_ZIPCODE_ID);
		zipCodeQuestion.setLabel("Zip Code");
		zipCodeQuestion.setMaximumLength("10");
		zipCodeQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

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
		maritalStatusQuestion.setType(Constants.FORM_TYPE_SELECT);

		demographicSectionQuestions.add(maritalStatusQuestion);

		// Ethnicity
		FormQuestionTO ethnicityQuestion = new FormQuestionTO();
		List<FormOptionTO> ethnicityQuestionOptions = new ArrayList<FormOptionTO>();

		ethnicityQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_ETHNICITY_ID);
		ethnicityQuestion.setLabel("Ethnicity");

		for (Ethnicity ethnicity : ethnicityDao.getAll(ObjectStatus.ACTIVE)) {
			ethnicityQuestionOptions.add(new FormOptionTO(UUID.randomUUID(),
					ethnicity.getName(), ethnicity.getName()));
		}

		ethnicityQuestion.setOptions(ethnicityQuestionOptions);
		ethnicityQuestion.setType(Constants.FORM_TYPE_SELECT);

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
		genderQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		citizenshipQuestion.setType(Constants.FORM_TYPE_SELECT);

		demographicSectionQuestions.add(citizenshipQuestion);

		// Country of Citizenship -> Shown if Citizenship is "International"
		FormQuestionTO countryOfCitizenshipQuestion = new FormQuestionTO();

		countryOfCitizenshipQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_COUNTRYOFCITIZENSHIP_ID);
		countryOfCitizenshipQuestion.setLabel("Country of Citizenship");
		countryOfCitizenshipQuestion.setMaximumLength("50");
		countryOfCitizenshipQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
		// DEPENDENCY -> countryOfCitizenshipQuestion should be shown only when
		// citizenshipQuestion selection matches 'International'
		countryOfCitizenshipQuestion
				.setVisibilityExpression("hasValueForQuestionId('8BFA49CE-4D8E-43C1-AA2B-0F5E66319168', '"
						+ SECTION_DEMOGRAPHICS_QUESTION_CITIZENSHIP_ID + "')");

		demographicSectionQuestions.add(countryOfCitizenshipQuestion);

		// Veteran Status
		FormQuestionTO veteranStatusQuestion = new FormQuestionTO();
		List<FormOptionTO> veteranStatusQuestionOptions = new ArrayList<FormOptionTO>();

		for (VeteranStatus veteranStatus : veteranStatusDao
				.getAll(ObjectStatus.ACTIVE)) {
			veteranStatusQuestionOptions
					.add(new FormOptionTO(veteranStatus.getId(), veteranStatus
							.getName(), veteranStatus.getName()));
		}

		veteranStatusQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_VETERANSTATUS_ID);
		veteranStatusQuestion.setLabel("Veteran Status");
		veteranStatusQuestion.setOptions(veteranStatusQuestionOptions);
		veteranStatusQuestion.setType(Constants.FORM_TYPE_SELECT);

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
		primaryCaregiverQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		howManyChildrenQuestion.setType(Constants.FORM_TYPE_SELECT);

		demographicSectionQuestions.add(howManyChildrenQuestion);

		// Chidren Ages
		FormQuestionTO childrenAgesQuestion = new FormQuestionTO();

		childrenAgesQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_CHILDRENAGES_ID);
		childrenAgesQuestion.setLabel("Ages? (Separated by commas)");
		childrenAgesQuestion.setMaximumLength("50");
		childrenAgesQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

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
		childCareNeededQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(childCareNeededQuestion);

		// Childcare Arrangement
		FormQuestionTO childCareArrangementQuestion = new FormQuestionTO();
		List<FormOptionTO> childCareArrangementQuestionOptions = new ArrayList<FormOptionTO>();

		for (ChildCareArrangement childcareArrangement : childcareArrangementDao
				.getAll(ObjectStatus.ACTIVE)) {
			childCareArrangementQuestionOptions.add(new FormOptionTO(
					childcareArrangement.getId(), childcareArrangement
							.getName(), childcareArrangement.getName()));
		}

		childCareArrangementQuestion
				.setId(SECTION_DEMOGRAPHICS_QUESTION_CHILDCAREARRANGEMENT_ID);
		childCareArrangementQuestion.setLabel("Childcare arrangement");
		childCareArrangementQuestion
				.setOptions(childCareArrangementQuestionOptions);
		childCareArrangementQuestion.setType(Constants.FORM_TYPE_SELECT);
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
		employedQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

		demographicSectionQuestions.add(employedQuestion);

		// Employer
		FormQuestionTO employerQuestion = new FormQuestionTO();

		employerQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_EMPLOYER_ID);
		employerQuestion.setLabel("Place of employment");
		employerQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		shiftQuestion.setType(Constants.FORM_TYPE_SELECT);
		// DEPENDENCY -> shiftQuestion shown when employedQuestion selection
		// matches "Yes"
		shiftQuestion.setVisibilityExpression("hasValueForQuestionId('Y', '"
				+ SECTION_DEMOGRAPHICS_QUESTION_EMPLOYED_ID + "')");

		demographicSectionQuestions.add(shiftQuestion);

		// Wage
		FormQuestionTO wageQuestion = new FormQuestionTO();

		wageQuestion.setId(SECTION_DEMOGRAPHICS_QUESTION_WAGE_ID);
		wageQuestion.setLabel("Wages");
		wageQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		hoursWorkedPerWeekQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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

		for (StudentStatus studentStatus : studentStatusDao
				.getAll(ObjectStatus.ACTIVE)) {
			studentStatusQuestionOptions
					.add(new FormOptionTO(studentStatus.getId(), studentStatus
							.getName(), studentStatus.getName()));
		}

		studentStatusQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_STUDENTSTATUS_ID);
		studentStatusQuestion.setLabel("Student Status");
		studentStatusQuestion.setOptions(studentStatusQuestionOptions);
		studentStatusQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		newStudentOrientationQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		registeredForClassesQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		parentsObtainedCollegeDegreeQuestion
				.setType(Constants.FORM_TYPE_RADIOLIST);

		eduPlanSectionQuestions.add(parentsObtainedCollegeDegreeQuestion);

		// Special Needs/Special Accomodation
		FormQuestionTO specialAccomodationQuestion = new FormQuestionTO();
		List<FormOptionTO> specialAccomodationQuestionOptions = new ArrayList<FormOptionTO>();

		specialAccomodationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "Yes", "Y"));
		specialAccomodationQuestionOptions.add(new FormOptionTO(UUID
				.randomUUID(), "No", "N"));

		specialAccomodationQuestion
				.setId(SECTION_EDUCATIONPLAN_QUESTION_REQUIRESPECIALACCOMODATIONS_ID);
		specialAccomodationQuestion
				.setLabel("Special needs or require special accomodation?");
		specialAccomodationQuestion
				.setOptions(specialAccomodationQuestionOptions);
		specialAccomodationQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

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
		gradeEarnedHighestLevelEducationQuestion
				.setType(Constants.FORM_TYPE_RADIOLIST);

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

		for (EducationLevel educationLevel : educationLevelDao
				.getAll(ObjectStatus.ACTIVE)) {
			educationLevelCompletedQuestionOptions.add(new FormOptionTO(
					educationLevel.getId(), educationLevel.getName(),
					educationLevel.getId().toString()));
		}

		educationLevelCompletedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_EDUCATIONLEVELCOMPLETED_ID);
		educationLevelCompletedQuestion.setLabel("Education Level Completed");
		educationLevelCompletedQuestion
				.setOptions(educationLevelCompletedQuestionOptions);
		educationLevelCompletedQuestion.setType(Constants.FORM_TYPE_CHECKLIST);

		eduLevelSectionQuestions.add(educationLevelCompletedQuestion);

		// No Diploma/No GED - Last Year Attended
		FormQuestionTO noDiplomaLastYearAttendedQuestion = new FormQuestionTO();

		noDiplomaLastYearAttendedQuestion
				.setId(SECTION_EDUCATIONLEVEL_QUESTION_NODIPLOMALASTYEARATTENDED_ID);
		noDiplomaLastYearAttendedQuestion.setLabel("Last Year Attended");
		noDiplomaLastYearAttendedQuestion.setMaximumLength("4");
		noDiplomaLastYearAttendedQuestion
				.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		noDiplomaHighestGradeCompletedQuestion
				.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		gedYearOfGedQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		highSchoolYearGraduatedQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		highSchoolAttendedQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		someCollegeCreditsLastYearAttendedQuestion
				.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		otherPleaseExplainQuestion.setType(Constants.FORM_TYPE_TEXTAREA);
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
		educationCareerGoalQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

		eduGoalSectionQuestions.add(educationCareerGoalQuestion);

		// Goal Description
		FormQuestionTO bachelorsDegreeQuestion = new FormQuestionTO();

		bachelorsDegreeQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_GOALDESCRIPTION_ID);
		bachelorsDegreeQuestion.setLabel("Bachelor's Degree Major");
		bachelorsDegreeQuestion.setMaximumLength("255");
		bachelorsDegreeQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		militaryBranchQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		otherQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
		majorCertaintyQuestion.setType(Constants.FORM_TYPE_RADIOLIST);

		eduGoalSectionQuestions.add(majorCertaintyQuestion);

		// Planned Occupation
		FormQuestionTO plannedOccupationQuestion = new FormQuestionTO();

		plannedOccupationQuestion
				.setId(SECTION_EDUCATIONGOAL_QUESTION_CAREERGOAL_ID);
		plannedOccupationQuestion.setLabel("What is your planned occupation?");
		plannedOccupationQuestion.setMaximumLength("50");
		plannedOccupationQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);

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

		for (FundingSource fundingSource : fundingSourceDao
				.getAll(ObjectStatus.ACTIVE)) {
			fundingSourceOptions.add(new FormOptionTO(fundingSource.getId(),
					fundingSource.getName(), fundingSource.getId().toString()));
		}

		fundingQuestionTO.setId(SECTION_FUNDING_QUESTION_FUNDING_ID);
		fundingQuestionTO.setLabel("How will you pay for college");
		fundingQuestionTO.setOptions(fundingSourceOptions);
		fundingQuestionTO.setType(Constants.FORM_TYPE_CHECKLIST);

		fundingSectionQuestions.add(fundingQuestionTO);

		// Other Description
		FormQuestionTO otherQuestion = new FormQuestionTO();

		otherQuestion.setId(SECTION_FUNDING_QUESTION_OTHER_ID);
		otherQuestion.setLabel("Other");
		otherQuestion.setMaximumLength("255");
		otherQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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

		FormSectionTO challengeSection = new FormSectionTO();
		List<FormQuestionTO> challengeSectionQuestions = new ArrayList<FormQuestionTO>();

		challengeSection.setId(SECTION_CHALLENGE_ID);
		challengeSection.setLabel("Challenges");

		// Challenges
		FormQuestionTO challengeQuestionTO = new FormQuestionTO();
		List<FormOptionTO> challengeOptions = new ArrayList<FormOptionTO>();

		for (Challenge challenge : challengeDao.selectForStudentIntake()) {
			challengeOptions.add(new FormOptionTO(challenge.getId(), challenge
					.getName(), challenge.getId().toString()));
		}

		challengeQuestionTO.setId(SECTION_CHALLENGE_QUESTION_CHALLENGE_ID);
		challengeQuestionTO
				.setLabel("Select all challenges that may be barriers to your academic success");
		challengeQuestionTO.setOptions(challengeOptions);
		challengeQuestionTO.setType(Constants.FORM_TYPE_CHECKLIST);

		challengeSectionQuestions.add(challengeQuestionTO);

		// Other Description
		FormQuestionTO otherQuestion = new FormQuestionTO();

		otherQuestion.setId(SECTION_CHALLENGE_QUESTION_OTHER_ID);
		otherQuestion.setLabel("Other");
		otherQuestion.setMaximumLength("255");
		otherQuestion.setType(Constants.FORM_TYPE_TEXTINPUT);
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
