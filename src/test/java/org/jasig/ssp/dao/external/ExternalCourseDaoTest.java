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
package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@Transactional
public class ExternalCourseDaoTest {

	@Autowired
	private transient ExternalCourseDao dao;

	@Test
	public void testGetByCourseCode() {
		ExternalCourse course = null;
		try {
			course = dao.getByCourseCode("MATH-101");
		} catch (final ObjectNotFoundException e) {
			fail("Course was not found");
		}
		assertEquals(course.getIsDev(), true);
		assertNotNull("Course was not found", course);

	}
	
	@Test
	public void testGetAll() {
		List<ExternalCourse> courses = null;
		courses = dao.getAll();
		assertEquals(20, courses.size());
	}	

}
