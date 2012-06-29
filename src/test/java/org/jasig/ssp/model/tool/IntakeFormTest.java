package org.jasig.ssp.model.tool;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.junit.Test;

/**
 * Simple tests on the {@link IntakeForm} model.
 * 
 * @author jon.adams
 */
public class IntakeFormTest {

	private static final String TEST_STRING1 = "ts1";
	private static final String TEST_STRING2 = "ts2";

	/**
	 * Test that {@link IntakeForm#setPerson(Person)} correctly sets nested
	 * properties correctly.
	 */
	@Test
	public void testSetPerson() {
		final Person person1 = new Person(UUID.randomUUID());
		person1.setFirstName(TEST_STRING1);

		final PersonDemographics pd1 = new PersonDemographics();
		pd1.setChildAges(TEST_STRING2);

		final IntakeForm pPersistent = new IntakeForm();
		pPersistent.setPerson(person1);
		pPersistent.getPerson().setDemographics(pd1);

		assertEquals("Field not set correctly.", TEST_STRING1, pPersistent
				.getPerson().getFirstName());
		assertEquals("Field not set correctly.", TEST_STRING2, pPersistent
				.getPerson().getDemographics().getChildAges());
	}
}