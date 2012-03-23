package edu.sinclair.ssp.model.tool;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;

public class IntakeFormTest {

	@Test
	public void testOverwrite() {
		String testString1 = "ts1";
		String testString2 = "ts2";

		Person person1 = new Person(UUID.randomUUID());
		person1.setFirstName(testString1);

		PersonDemographics pd1 = new PersonDemographics();
		pd1.setChildAges(testString2);

		IntakeForm pPersistent = new IntakeForm();
		pPersistent.setPerson(person1);
		pPersistent.setPersonDemographics(pd1);

		assertEquals("Field not set correctly.", testString1, pPersistent
				.getPerson().getFirstName());
		assertEquals("Field not set correctly.", testString2, pPersistent
				.getPersonDemographics().getChildAges());
	}
}
