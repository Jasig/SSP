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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
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
@TransactionConfiguration(defaultRollback = true)
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
	
	@Autowired
	private 
	transient TermService termService;
	
	@Autowired
	private 
	transient PlanService planService;

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
	public void testPlanDaoSaveWithChildren() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = james gosling
		final Person person = personService.get(UUID
				.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff"));
		
		Plan plan = new Plan();
		plan.setPerson(person);
		plan.setName("TestPlan");
		plan.setOwner(person);
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		
		Collection<Term> all = getTermService().getAll(null).getRows();
		for (Term term : all) {
			
			for(int i = 1; i < 6; i++)
			{
				
				PlanCourse course = new PlanCourse();
				course.setCourseCode("MAT-"+i);
				course.setCourseDescription("TEST"+i);
				course.setCourseTitle("TEST"+i);
				course.setFormattedCourse("TEST"+i);
				course.setOrderInTerm(new Integer(i));
				course.setIsDev(false);
				course.setCreatedBy(new AuditPerson(person.getId()));
				course.setPlan(plan);
				course.setPerson(person);
				course.setCreditHours(new BigDecimal(3.0));
				course.setTermCode(term.getCode());
				plan.getPlanCourses().add(course);
			}
		}
		

		
		
		planService.copyAndSave(plan);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		
	}	
	@Test
	public void testPlanDaoSaveAndCloneWithChildren2() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = james gosling
		final Person person = personService.get(UUID
				.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff"));
		
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
		course.setTermCode("SP13");
		course.setIsDev(true);
		course.setOrderInTerm(1);
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setPlan(plan);
		course.setPerson(person);
		course.setCreditHours(new BigDecimal(4.0));
		
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
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setPlan(plan);
		course.setPerson(person);
		course.setCreditHours(new BigDecimal(4.0));
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
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setPlan(plan);
		course.setPerson(person);
		course.setCreditHours(new BigDecimal(3.0));
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
	
	@Test
	public void  testDaoReportMethodsNull(){
		SearchPlanTO form = new SearchPlanTO(null, null, null, null, null, null, null);
		List<PlanCourseCountTO>  planCount = dao.getPlanCourseCount(form);
		List<PlanAdvisorCountTO>  advisorsCount = dao.getOwnerPlanCount(form);
		List<PlanStudentStatusTO>  studentStatus = dao.getPlanStudentStatusByCourse(form);
		
		assertEquals(0, planCount.size());
		assertEquals(0, advisorsCount.size());
		assertEquals(0, studentStatus.size());
	}

	public TermService getTermService() {
		return termService;
	}

	public void setTermService(TermService termService) {
		this.termService = termService;
	}

	public PlanService getPlanService() {
		return planService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}
}