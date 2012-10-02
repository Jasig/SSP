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
package org.jasig.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.jasig.ssp.factory.reference.ProgramStatusTOFactory;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ProgramStatusTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for {@link ProgramStatusTOFactoryImpl}
 * 
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../../service/service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class ProgramStatusTOFactoryImplTest {
	@Autowired
	private transient ProgramStatusTOFactory toFactory;

	/**
	 * Test method for ProgramStatusTOFactoryImpl#getDao().
	 * 
	 * @throws ObjectNotFoundException
	 *             If object not found, which is expected for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testGetDaoViaBaseClassFromCall() throws ObjectNotFoundException {
		assertNotNull("DAO should not have been null.",
				toFactory.from(UUID.randomUUID()));
	}

	@Test
	public void testNewProgramStatusTOFromValues() {
		final ProgramStatusTO obj = new ProgramStatusTO(UUID.randomUUID(),
				"some name", true);
		assertEquals("Name did not match.", "some name", obj.getName());
		assertTrue("Values did not match.",
				obj.isProgramStatusChangeReasonRequired());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewProgramStatusTOFromNullModel() {
		new ProgramStatusTO(null);
		fail("Exception should have been thrown.");
	}

	@Test
	public void testNewProgramStatusTOFromModel() {
		final ProgramStatus ps = createProgramStatus(true);
		final ProgramStatusTO obj = new ProgramStatusTO(ps);
		assertEquals("Name did not match.", "name", obj.getName());
		assertTrue("Values did not match.",
				obj.isProgramStatusChangeReasonRequired());
	}

	private ProgramStatus createProgramStatus(final boolean changeReasonValue) {
		final ProgramStatus ps = new ProgramStatus();
		ps.setName("name");
		ps.setProgramStatusChangeReasonRequired(changeReasonValue);
		return ps;
	}
}