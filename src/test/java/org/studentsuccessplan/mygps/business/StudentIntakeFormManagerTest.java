package org.studentsuccessplan.mygps.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.studentsuccessplan.mygps.model.transferobject.FormSectionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;
import org.studentsuccessplan.mygps.web.MyGpsChallengeController;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.model.reference.Genders;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

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

	private static String STUDENTINTAKEFORM_EMPTY = "studentintakeform_empty.json";

	private static String STUDENTINTAKEFORM_COMPLETED = "studentintakeform_completed.json";

	private static String STUDENTINTAKEFORM_COMPLETED_WITHOUTAGREEMENT = "studentintakeform_completed_withoutagreement.json";

	@Autowired
	private transient StudentIntakeFormManager formManager;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private transient static ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertManager.class);

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
		FormTO form = formManager.create();

		assertNotNull("Form should not have been null.", form);

		FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				section);

		assertEquals("Confidentiality section id did not match.",
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
		FormTO form = formManager.create();

		assertNotNull("FormTO should not have been null.", form);

		FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				section);

		// Fill in values
		// TODO

		section.getFormQuestionById(
				StudentIntakeFormManager.SECTION_CONFIDENTIALITY_QUESTION_AGREE_ID)
				.setValueBoolean(true);

		// Run
		Person person = formManager.save(form);

		// Assert
		assertNotNull(
				"StudentIntakeFormManager.Save should have returned an updated Person instance.",
				person);

		Set<PersonConfidentialityDisclosureAgreement> agreements = person
				.getConfidentialityDisclosureAgreements();
		assertNotNull("Person agreements should not have been null.",
				agreements);
		assertFalse("Person should have some accepted agreements.",
				agreements.isEmpty());

		// TODO: More assertions
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
		FormTO form = loadJson(STUDENTINTAKEFORM_EMPTY);

		assertNotNull("FormTO should not have been null.", form);

		// Run
		Person person = formManager.save(form);
		assertNotNull(person);
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
		FormTO form = loadJson(STUDENTINTAKEFORM_COMPLETED_WITHOUTAGREEMENT);

		assertNotNull("FormTO should not have been null.", form);

		FormSectionTO section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNull("Confidentiality section should have been null.", section);

		// Run
		Person person = formManager.save(form);

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
	public void testSaveCompleted() throws JsonParseException,
			JsonMappingException, IOException, ObjectNotFoundException {
		// Setup
		FormTO form = loadJson(STUDENTINTAKEFORM_COMPLETED);
		FormSectionTO section = null;
		String value = null;

		assertNotNull("FormTO should not have been null.", form);

		section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_CONFIDENTIALITY_ID);

		assertNotNull("Confidentiality section should not have been null.",
				section);

		section = form
				.getFormSectionById(StudentIntakeFormManager.SECTION_DEMOGRAPHICS_ID);

		assertNotNull("Demographics section should not have been null.",
				section);

		value = section
				.getFormQuestionById(
						StudentIntakeFormManager.SECTION_DEMOGRAPHICS_QUESTION_GENDER_ID)
				.getValue();

		assertEquals(
				"Person demographics should have indicated a matching gender instead of: "
						+ value, Genders.M, Genders.valueOf(value));

		// Run
		Person person = formManager.save(form);

		// Assert
		assertNotNull(
				"StudentIntakeFormManager.Save should have returned an updated Person instance.",
				person);

		Set<PersonConfidentialityDisclosureAgreement> agreements = person
				.getConfidentialityDisclosureAgreements();
		assertNotNull("Person agreements should not have been null.",
				agreements);
		assertFalse("Person should have some accepted agreements.",
				agreements.isEmpty());

		assertNotNull("Missing demographics.", person.getDemographics());

		assertTrue("Persion gender did not match.",
				Genders.M.equals(person.getDemographics().getGender()));

		// TODO: More assertions
	}

	/**
	 * 
	 * @param path
	 *            to the JSON file to load
	 * @return Loaded FormTO instance
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 */
	private FormTO loadJson(String file) throws JsonParseException,
			JsonMappingException, IOException {
		// Load file
		BufferedReader in = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream(file)));

		String json = in.readLine();
		LOGGER.warn(json);

		FormTO form = objectMapper.readValue(json, FormTO.class);

		if (in != null) {
			in.close();
		}

		in = null;

		assertNotNull("File data could not be parsed: " + file, form);

		return form;
	}
}
