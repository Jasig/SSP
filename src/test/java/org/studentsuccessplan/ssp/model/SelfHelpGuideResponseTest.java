package org.studentsuccessplan.ssp.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class SelfHelpGuideResponseTest {
	@Test
	public void testSettingPerson() {
		Person person = new Person(UUID.randomUUID());
		SelfHelpGuideResponse shgr = new SelfHelpGuideResponse();
		shgr.setPerson(person);

		assertEquals("Person does not match.", person, shgr.getPerson());
		assertEquals("Person UUIDs do not match.", person.getId(), shgr
				.getPerson().getId());
	}
}
