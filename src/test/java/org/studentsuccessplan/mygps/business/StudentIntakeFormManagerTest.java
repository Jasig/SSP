package org.studentsuccessplan.mygps.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	 * Test the {@link StudentIntakeFormManager#save(FormTO)} method.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 */
	@Test
	public void testSave() throws ObjectNotFoundException {
		// Setup
		FormTO form = formManager.create();

		assertNotNull("Form should not have been null.", form);

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
}
