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
/**
 * 
 */
package org.jasig.ssp.factory.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.jasig.ssp.factory.PersonDisabilityTOFactory;
import org.jasig.ssp.model.PersonDisability;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonDisabilityTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for {@link PersonDisabilityTOFactoryImpl}
 * 
 * @author shawn.gormley
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../service/service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class PersonDisabilityTOFactoryImplTest {
	@Autowired
	private transient PersonDisabilityTOFactory toFactory;

	/**
	 * Test method for PersonDisabilityTOFactoryImpl#getDao().
	 * 
	 * @throws ObjectNotFoundException
	 *             If object not found, which is expected for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testGetDaoViaBaseClassFromCall() throws ObjectNotFoundException {
		assertNotNull("DAO should not have been null.",
				toFactory.from(UUID.randomUUID()));
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.factory.impl.PersonDisabilityTOFactoryImpl#from(org.jasig.ssp.transferobject.PersonDisabilityTO)}
	 * .
	 */
	@Test
	public void testFromPersonDisabilityTOWithMissingData() {
		final PersonDisabilityTO obj = toFactory
				.from(new PersonDisability());
		assertNull("Disability Status should have been null.",
				obj.getDisabilityStatusId());
		assertNull("Person should have been null.", obj.getPersonId());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.factory.impl.PersonDisabilityTOFactoryImpl#from(org.jasig.ssp.transferobject.PersonDisabilityTO)}
	 * .
	 */
	@Test
	public void testFromPersonDisabilityTO() {
		final PersonDisability obj = new PersonDisability();
		boolean testBool = Boolean.TRUE;
		obj.setContactName("TEST");
		obj.setReleaseSigned(testBool);
		obj.setDisabilityStatus(new DisabilityStatus());

		final PersonDisabilityTO to = toFactory.from(obj);
		assertEquals("Contact Name did not match.", "TEST",
				to.getContactName());
		assertEquals("Release Signed did not match.",
				testBool,
				to.getReleaseSigned());
	}
}