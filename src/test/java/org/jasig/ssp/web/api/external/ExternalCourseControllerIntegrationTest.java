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
package org.jasig.ssp.web.api.external; // NOPMD

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalCourseTO;
import org.jasig.ssp.transferobject.external.ExternalCourseTermResponseTO;
import org.jasig.ssp.transferobject.external.TermTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link TermController} tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ExternalCourseControllerIntegrationTest {

	@Autowired
	private transient ExternalCourseController controller;



	/**
	 * Test the {@link ExternalCourseController#get(String)} action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ExternalCourseTO obj = controller.get("MATH-101");

		assertNotNull(
				"Returned TermTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Course.title did not match.", "MATH-101",
				obj.getTitle());
	}
	
	/**
	 * Test the {@link ExternalCourseController#get(String)} action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCourseTerm() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ExternalCourseTermResponseTO obj = controller.validateTerm("MATH-101", "FA15");

		assertNotNull(
				"Returned TermTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Course.title did not match.", true,
				obj.isValid());
	}	

	/**
	 * Test that the {@link ExternalCourseController#get(String)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ExternalCourseTO obj = controller.get("invalid id");

		assertNull(
				"Returned TermTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link ExternalCourseController#getAll(Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<ExternalCourseTO> list = controller.getAllCourses();

		assertNotNull("List should not have been null.", list);
		assertNotEmpty("List action should have returned some objects.", list);
	}

	/**
	 * Test the {@link TermController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final Collection<ExternalCourseTO> list = controller.getAllCourses();


		ExternalCourseTO obj = list.iterator().next();
		assertTrue("FormattedCourse should have been longer than 0 characters.", obj
				.getFormattedCourse().length() > 0);

		obj = list.iterator().next();
		assertNotNull("SubjectAbbreviation should not have been null.", obj.getSubjectAbbreviation());
	}

	
}