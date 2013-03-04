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
package org.jasig.ssp.web.api.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTestTO;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ExternalStudentRecordsControllerIntegrationTest {
	
	@Autowired
	private transient ExternalStudentRecordsController controller;

	@Autowired
	private transient SessionFactory sessionFactory;
	
	/**
	 * Test the {@link ExternalStudentRecordsController#loadLite(String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerLoadLite()
			throws ObjectNotFoundException{
		
		ExternalStudentRecordsLiteTO records = controller.loadSummaryStudentRecords(getStudent1Id());
		assertNotNull(
				"Returned FacultyCourseTO from the controller should not have been null.",
				records);
		
	}
	
	/**
	 * Test the {@link ExternalStudentRecordsController#loadLite(String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerLoadFull()
			throws ObjectNotFoundException{
		
		ExternalStudentRecordsTO records = controller.loadFullStudentRecords(getStudent1Id());
		assertNotNull(
				"Returned FacultyCourseTO from the controller should not have been null.",
				records);
		
	}
	
	/**
	 * Test the {@link ExternalStudentRecordsController#loadLite(String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerLoadStudentTests()
			throws ObjectNotFoundException{
		
		List<ExternalStudentTestTO> records = controller.loadStudentTestRecords(getStudent1Id());
		assertNotNull(
				"Returned FacultyCourseTO from the controller should not have been null.",
				records);
		assertEquals("Student Tests", 3, records.size());
		
	}
	
	private UUID getStudent1Id(){
		return Stubs.PersonFixture.COACH1_STUDENT0.id();
	}
	
	

}
