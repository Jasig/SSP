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