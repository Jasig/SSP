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
package org.jasig.ssp.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.array;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.internal.matchers.Matches;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CustomMatcher;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.CaseLoadSearchTO;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spockframework.util.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * Tests for the {@link CaseloadDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")

// many (most?) other DAO tests commit by default... here we're just testing
// reads, so we know that any fixture setup needs to be rolled back
@TransactionConfiguration(defaultRollback = false)

@Transactional
public class PlanDaoTest {

	@Autowired
	private transient PlanDao dao;
	 
	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient PersonService personService;
	
	@Autowired
	protected transient SessionFactory sessionFactory;

	/**
	 * Setup the security service for the tests
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testPlanDaoSave() throws ObjectNotFoundException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Plan plan = new Plan();
		plan.setPerson(person);
		plan.setName("TestPlan");
		plan.setOwner(person);
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(plan);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Plan loadedPlan = dao.load(plan.getId());
		assertEquals(loadedPlan.getName(), "TestPlan");
		assertEquals(loadedPlan.getObjectStatus(), ObjectStatus.ACTIVE);
		assertEquals(loadedPlan.getPerson().getId(),person.getId());
		
	}
	
	@Test
	public void testCloneAndSaveDao() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		final Session session = sessionFactory.getCurrentSession();
		
		Plan plan = new Plan();
		plan.setPerson(person);
		plan.setName("TestPlan");
		plan.setOwner(person);
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(plan);
		
		session.flush();
		
		Plan clonedPlan = dao.load(plan.getId()).clone();
		//save cloned plan
		dao.save(clonedPlan);
		
		session.flush();
		
		Plan clonedSavedPlan = dao.load(clonedPlan.getId());
		assertEquals(clonedSavedPlan.getName(), "TestPlan");
		assertEquals(clonedSavedPlan.getObjectStatus(), ObjectStatus.ACTIVE);
		assertEquals(clonedSavedPlan.getPerson().getId(),person.getId());
		
	}	

	@Test
	public void testPlanDaoSaveWithChildren() throws ObjectNotFoundException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Plan plan = new Plan();
		plan.setPerson(person);
		plan.setName("TestPlan");
		plan.setOwner(person);
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		
		PlanCourse course = new PlanCourse();
		course.setCourseCode("MAT");
		course.setCourseDescription("TEST");
		course.setCourseTitle("TEST");
		course.setFormattedCourse("TEST");
		course.setOrderInTerm(new Integer(1));
		course.setIsDev(true);
		course.setOrderInTerm(new Integer(1));
		course.setCreatedBy(person);
		course.setPlan(plan);
		course.setPerson(person);
		course.setCreditHours(3);
		plan.getPlanCourses().add(course);
		
		dao.save(plan);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Plan loadedPlan = dao.load(plan.getId());
		assertEquals(loadedPlan.getPlanCourses().size(), 1);
		PlanCourse planCourse = loadedPlan.getPlanCourses().get(0);
		assertEquals(planCourse.getCourseCode(),"MAT");
		assertEquals(planCourse.getCourseTitle(),"TEST");
		assertEquals(planCourse.getFormattedCourse(),"TEST");
		assertEquals(planCourse.getCourseDescription(),"TEST");
		assertEquals(planCourse.isDev(), true);
		assertEquals(planCourse.getOrderInTerm(), new Integer(1));
		assertEquals(planCourse.getOrderInTerm(), new Integer(1));
		
	}	

	@Test
	public void testPlanDaoSaveAndCloneWithChildren() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Plan plan = new Plan();
		plan.setPerson(person);
		plan.setName("TestPlan");
		plan.setOwner(person);
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		
		PlanCourse course = new PlanCourse();
		course.setCourseCode("MAT");
		course.setCourseDescription("TEST");
		course.setCourseTitle("TEST");
		course.setFormattedCourse("TEST");
		course.setOrderInTerm(1);
		course.setIsDev(true);
		course.setOrderInTerm(1);
		course.setCreatedBy(person);
		course.setPlan(plan);
		course.setPerson(person);
		course.setCreditHours(4);
		plan.getPlanCourses().add(course);
		
		dao.save(plan);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Plan loadedPlan = dao.load(plan.getId()).clone();
		assertEquals(loadedPlan.getPlanCourses().size(), 1);
		PlanCourse planCourse = loadedPlan.getPlanCourses().get(0);
		assertEquals(planCourse.getCourseCode(),"MAT");
		assertEquals(planCourse.getCourseTitle(),"TEST");
		assertEquals(planCourse.getFormattedCourse(),"TEST");
		assertEquals(planCourse.getCourseDescription(),"TEST");
		assertEquals(planCourse.isDev(), true);
		assertEquals(planCourse.getOrderInTerm(), new Integer(1));
		assertEquals(planCourse.getOrderInTerm(), new Integer(1));
		
	}
}