package org.jasig.ssp.model.tool;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;

/**
 * Simple tests on the {@link IntakeForm} model.
 * 
 * @author jon.adams
 */
public class IntakeFormTest {

	/**
	 * Test that {@link IntakeForm#setPerson(Person)} correctly sets nested
	 * properties correctly.
	 */
	@Test
	public void testSetPerson() {
		String testString1 = "ts1";
		String testString2 = "ts2";

		Person person1 = new Person(UUID.randomUUID());
		person1.setFirstName(testString1);

		PersonDemographics pd1 = new PersonDemographics();
		pd1.setChildAges(testString2);

		IntakeForm pPersistent = new IntakeForm();
		pPersistent.setPerson(person1);
		pPersistent.getPerson().setDemographics(pd1);

		assertEquals("Field not set correctly.", testString1, pPersistent
				.getPerson().getFirstName());
		assertEquals("Field not set correctly.", testString2, pPersistent
				.getPerson().getDemographics().getChildAges());
	}
}
