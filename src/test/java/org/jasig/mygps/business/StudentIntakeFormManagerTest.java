package org.jasig.mygps.business; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jasig.mygps.model.transferobject.FormSectionTO;
import org.jasig.mygps.model.transferobject.FormTO;
import org.jasig.mygps.web.MyGpsChallengeController;
import org.jasig.ssp.TestUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.model.PersonEducationLevel;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.model.PersonFundingSource;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link MyGpsChallengeController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ssp/web/ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class StudentIntakeFormManagerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertManager.class);

	private static final String STUDENTINTAKEFORM_EMPTY = "studentintakeform_empty.json";

	private static final String STUDENTINTAKEFORM_COMPLETED = "studentintakeform_completed.json";

	private static final String STUDENTINTAKEFORM_COMPLETED_WITHOUTAGREEMENT = "studentintakeform_completed_withoutagreement.json";

	@Autowired
	private transient StudentIntakeFormManager formManager;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link StudentIntakeFormManager#create()} method.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 */
	@Test
	public void testCreate() throws ObjectNotFoundException {
		final FormTO form = formManager.create();

		assertNotNull("Form should not have been null.", form);

		final FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				section);

		assertEquals("Confidentiality section id does not match.",
				StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID,
				section.getId());
	}

	/**
	 * Test the {@link StudentIntakeFormManager#save(FormTO)} method with
	 * in-memory, code-based changes.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 */
	@Test
	public void testSaveInMemory() throws ObjectNotFoundException {
		// Setup
		final FormTO form = formManager.create();
		final FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				section);

		// Fill in values
		section.getFormQuestionById(
				StudentIntakeFormManager.SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID)
				.setValueBoolean(true);

		// Run
		final Person person = formManager.save(form);

		// Assert
		final Set<PersonConfidentialityDisclosureAgreement> agreements = person
				.getConfidentialityDisclosureAgreements();
		assertNotNull("Person agreements should not have been null.",
				agreements);
		assertFalse("Person should have some accepted agreements.",
				agreements.isEmpty());
	}

	/**
	 * Test the {@link StudentIntakeFormManager#save(FormTO)} method with
	 * pre-created but empty JSON data.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 */
	@Test
	public void testSaveEmpty() throws JsonParseException,
			JsonMappingException, IOException, ObjectNotFoundException {
		// Setup
		final FormTO form = loadJson(STUDENTINTAKEFORM_EMPTY);

		assertNotNull("FormTO should not have been null.", form);

		// Run
		final Person person = formManager.save(form);

		assertNotNull(
				"StudentIntakeFormManager.Save should have returned an updated Person instance.",
				person);

		// Assertions for Demographics

		final Set<PersonConfidentialityDisclosureAgreement> agreements = person
				.getConfidentialityDisclosureAgreements();
		assertTrue("Person agreements should have been null or empty.",
				(agreements == null) || agreements.isEmpty());

		final PersonDemographics demographics = person.getDemographics();
		assertNotNull("Missing demographics.", demographics);

		// Assertions for Education Levels

		final Set<PersonEducationLevel> levels = person.getEducationLevels();

		assertNotNull("Education Levels set should not have been null.", levels);

		assertTrue("Education levels should have been empty.", levels.isEmpty());

		// Assertions for Funding
		final Set<PersonFundingSource> sources = person.getFundingSources();

		assertNotNull("Funding Sources set should not have been null.", sources);

		assertTrue("Founding sources should have been empty.",
				sources.isEmpty());

		// Assertions for Challenges
		final Set<PersonChallenge> challenges = person.getChallenges();

		assertNotNull("Challenges set should not have been null.", challenges);

		assertTrue("Challenges should have been empty.", challenges.isEmpty());
	}

	/**
	 * Test the {@link StudentIntakeFormManager#save(FormTO)} method completed
	 * JSON data, except for missing disclosure agreement data because the
	 * student may have already agreed.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 */
	@Test
	public void testSaveCompletedWithoutAgreement() throws JsonParseException,
			JsonMappingException, IOException, ObjectNotFoundException {
		// Setup
		final FormTO form = loadJson(STUDENTINTAKEFORM_COMPLETED_WITHOUTAGREEMENT);

		assertNotNull("FormTO should not have been null.", form);

		final FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNull("Confidentiality section should have been null.", section);

		// Run
		final Person person = formManager.save(form);

		// Assert
		assertNotNull(
				"StudentIntakeFormManager.Save should have returned an updated Person instance.",
				person);
	}

	/**
	 * Test the {@link StudentIntakeFormManager#save(FormTO)} method completed
	 * JSON data.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 */
	@Test
	public void testSaveCompleted() throws JsonParseException, // NOPMD
			JsonMappingException, IOException, ObjectNotFoundException {
		// Setup
		final FormTO form = loadJson(STUDENTINTAKEFORM_COMPLETED);

		assertNotNull("FormTO should not have been null.", form);

		final FormSectionTO sectionConfidentiality = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				sectionConfidentiality);

		final FormSectionTO sectionDemographics = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_DEMOGRAPHICS_ID);

		assertNotNull("Demographics section should not have been null.",
				sectionDemographics);

		final String value = sectionDemographics
				.getFormQuestionById(
						StudentIntakeFormManager.SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID)
				.getValue();

		assertEquals(
				"Person demographics should have indicated a matching gender instead of: "
						+ value, Genders.M, Genders.valueOf(value));

		// Run
		final Person person = formManager.save(form);

		assertNotNull(
				"StudentIntakeFormManager.Save should have returned an updated Person instance.",
				person);

		// Assertions for Demographics
		final Set<PersonConfidentialityDisclosureAgreement> agreements = person
				.getConfidentialityDisclosureAgreements();
		assertNotNull("Person agreements should not have been null.",
				agreements);
		assertFalse("Person should have some accepted agreements.",
				agreements.isEmpty());

		final PersonDemographics demographics = person.getDemographics();
		assertNotNull("Missing demographics.", demographics);

		assertEquals("Marital status does not match.", "Married", demographics
				.getMaritalStatus().getName());

		assertEquals("Ethnicity does not match.", "Prefer Not To Answer",
				demographics.getEthnicity().getName());

		assertEquals("Person gender does not match.",
				Genders.M, demographics.getGender());

		assertEquals("Citizenship does not match.", "US Citizen", demographics
				.getCitizenship().getName());

		assertEquals("Country of Citizenship does not match.", "United States",
				demographics.getCountryOfCitizenship());

		assertEquals("Veteran Status does not match.",
				"Montgomery County Reservist", demographics.getVeteranStatus()
						.getName());

		assertTrue("Primary Caregiver option not match.",
				demographics.isPrimaryCaregiver());

		assertEquals("Number of children does not match.", 2,
				demographics.getNumberOfChildren());

		assertEquals("Ages of children does not match.", "4,4",
				demographics.getChildAges());

		assertTrue("Childcare needed option not match.",
				demographics.isChildCareNeeded());

		assertEquals("Childcare arrangements does not match.",
				"Need to make arrangements", demographics
						.getChildCareArrangement().getName());

		assertTrue("Employment option not match.", demographics.isEmployed());

		assertEquals("Shift option does not match.", EmploymentShifts.SECOND,
				demographics.getShift());

		// Assertions for Education Plan
		final PersonEducationPlan educationPlan = person.getEducationPlan();

		assertNotNull("Missing education plan.", educationPlan);

		assertEquals("Student Status option does not match.", "Former",
				educationPlan.getStudentStatus().getName());

		assertTrue("New Orientation option does not match.",
				educationPlan.isNewOrientationComplete());

		assertTrue("Registered for Classes option does not match.",
				educationPlan.isRegisteredForClasses());

		assertTrue("Parents' Degree option does not match.",
				educationPlan.isCollegeDegreeForParents());

		assertTrue("Special Needs option does not match.",
				educationPlan.isSpecialNeeds());

		assertEquals("Typical grade option does not match.", "2",
				educationPlan.getGradeTypicallyEarned());

		// Assertions for Education Goal
		final PersonEducationGoal goal = person.getEducationGoal();

		assertNotNull("Missing education goal.", goal);

		assertEquals("Career goal option does not match.", "Military", goal
				.getEducationGoal().getName());

		assertEquals("Miltary Branch option does not match.", "The Avengers",
				goal.getMilitaryBranchDescription());

		assertEquals("How Sure option does not match.", Integer.valueOf(3),
				goal.getHowSureAboutMajor());

		assertEquals("Planned Occupation option does not match.",
				"Chimney Sweep", goal.getPlannedOccupation());

		// Assertions for Education Levels

		final Set<PersonEducationLevel> levels = person.getEducationLevels();

		assertNotNull("Education Levels set should not have been null.", levels);

		assertEquals("Education levels should have included 2 items.", 2,
				levels.size());

		final PersonEducationLevel level = levels.iterator().next();

		assertNotNull("The first Education Level should not have been null.",
				level);

		assertTrue(
				"EducationLevel did not match.",
				"College Degree - 4 Year".equals(level.getEducationLevel()
						.getName())
						|| "No Diploma/No GED".equals(level.getEducationLevel()
								.getName()));

		// Assertions for Funding
		final Set<PersonFundingSource> sources = person.getFundingSources();

		assertNotNull("Funding Sources set should not have been null.", sources);

		assertEquals("Funding Sources should have included 2 items.", 2,
				sources.size());

		final Iterator<PersonFundingSource> iter = sources.iterator();
		iter.next();
		final PersonFundingSource source = iter.next();

		assertNotNull("The second Funding Source should not have been null.",
				source);

		assertTrue(
				"Funding Source did not match.",
				"Employer".equals(source.getFundingSource().getName())
						|| "Student Loan".equals(source.getFundingSource()
								.getName()));

		// Assertions for Challenges
		final Set<PersonChallenge> challenges = person.getChallenges();

		assertNotNull("Challenges set should not have been null.", challenges);

		assertEquals("Challenges should have included 3 items.", 3,
				challenges.size());

		final PersonChallenge challenge = challenges.iterator().next();

		assertNotNull("The first Challenge should not have been null.",
				challenge);

		assertNotNull(
				"The first Challenge reference should not have been null.",
				challenge.getChallenge());

		assertTrue(
				"Challenge did not match. Was: "
						+ challenge.getChallenge().getName(),
				"Social Support (Lack of Support)".equals(challenge
						.getChallenge().getName())
						|| "Finances - Education".equals(challenge
								.getChallenge().getName())
						|| "Test Challenge".equals(challenge.getChallenge()
								.getName()));
	}

	/**
	 * 
	 * @param file
	 *            the JSON file to load
	 * @return Loaded FormTO instance
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 */
	private FormTO loadJson(final String file) throws JsonParseException,
			JsonMappingException, IOException {
		LOGGER.debug("Loading and parsing JSON file {}...", file);
		return TestUtils.loadJson(getClass(), file, FormTO.class);
	}
}