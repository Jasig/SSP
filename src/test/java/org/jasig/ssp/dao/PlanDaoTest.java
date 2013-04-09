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

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
	@Test
	public void testPlanDaoSaveWithChildren2() throws ObjectNotFoundException, CloneNotSupportedException {
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
		
		Plan clone = loadedPlan.clone();
		clone.setId(loadedPlan.getId());
		dao.save(clone);
		
		sessionFactory.getCurrentSession().flush();
		
		Plan loadedClone = dao.load(clone.getId());
		
		assertEquals(1, loadedClone.getPlanCourses().size());
		
		
		
	}
}