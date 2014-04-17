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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
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
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TemplateDaoTest {

	@Autowired
	private transient TemplateDao dao;
	   
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
	transient TemplateService templateService;

	/**
	 * Setup the security service for the tests
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	
	public void testTemplateDaoSave() throws ObjectNotFoundException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(template);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Template loadedTemplate = dao.load(template.getId());
		assertEquals(loadedTemplate.getName(), "TestTemplate");
		assertEquals(loadedTemplate.getObjectStatus(), ObjectStatus.ACTIVE);
		
	}
	
	
	public void testCloneAndSaveDao() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		final Session session = sessionFactory.getCurrentSession();
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(template);
		
		session.flush();
		
		Template clonedTemplate = dao.load(template.getId()).clone();
		//save cloned template
		dao.save(clonedTemplate);
		
		session.flush();
		
		Template clonedSavedTemplate = dao.load(clonedTemplate.getId());
		assertEquals(clonedSavedTemplate.getName(), "TestTemplate");
		assertEquals(clonedSavedTemplate.getObjectStatus(), ObjectStatus.ACTIVE);
		
	}	

	
	public void testTemplateDaoSaveWithChildren() throws ObjectNotFoundException, CloneNotSupportedException, ValidationException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		template.setIsPrivate(false);
		Collection<Term> all = getTermService().getAll(null).getRows();
		for (Term term : all) {
			
			for(int i = 1; i < 6; i++)
			{
				
				TemplateCourse course = new TemplateCourse();
				course.setCourseCode("MAT-"+i);
				course.setCourseDescription("TEST"+i);
				course.setCourseTitle("TEST"+i);
				course.setFormattedCourse("TEST"+i);
				course.setOrderInTerm(new Integer(i));
				course.setIsDev(false);
				course.setCreatedBy(new AuditPerson(person.getId()));
				course.setTemplate(template);
				course.setCreditHours(new BigDecimal(3.0));
				course.setTermCode(term.getCode());
				template.getTemplateCourses().add(course);
			}
		}
		templateService.save(template);
        
		
		
		templateService.copyAndSave(template,person);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		
	}	
	
	public void testTemplateDaoSaveAndCloneWithChildren2() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = james gosling
		final Person person = personService.get(UUID
				.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff"));
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		
		TemplateCourse course = new TemplateCourse();
		course.setCourseCode("MAT");
		course.setCourseDescription("TEST");
		course.setCourseTitle("TEST");
		course.setFormattedCourse("TEST");
		course.setOrderInTerm(1);
		course.setTermCode("SP13");
		course.setIsDev(true);
		course.setOrderInTerm(1);
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setTemplate(template);
		course.setCreditHours(new BigDecimal(4.0));
		
		template.getTemplateCourses().add(course);
		
		dao.save(template);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Template loadedTemplate = dao.load(template.getId()).clone();
		assertEquals(loadedTemplate.getTemplateCourses().size(), 1);
		TemplateCourse templateCourse = loadedTemplate.getTemplateCourses().get(0);
		assertEquals(templateCourse.getCourseCode(),"MAT");
		assertEquals(templateCourse.getCourseTitle(),"TEST");
		assertEquals(templateCourse.getFormattedCourse(),"TEST");
		assertEquals(templateCourse.getCourseDescription(),"TEST");
		assertEquals(templateCourse.isDev(), true);
		assertEquals(templateCourse.getOrderInTerm(), new Integer(1));
		assertEquals(templateCourse.getOrderInTerm(), new Integer(1));
		
	}
	
	
	public void testTemplateDaoSaveAndCloneWithChildren() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		
		TemplateCourse course = new TemplateCourse();
		course.setCourseCode("MAT");
		course.setCourseDescription("TEST");
		course.setCourseTitle("TEST");
		course.setFormattedCourse("TEST");
		course.setOrderInTerm(1);
		course.setIsDev(true);
		course.setOrderInTerm(1);
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setTemplate(template);
		course.setCreditHours(new BigDecimal(4.0));
		template.getTemplateCourses().add(course);
		
		dao.save(template);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Template loadedTemplate = dao.load(template.getId()).clone();
		assertEquals(loadedTemplate.getTemplateCourses().size(), 1);
		TemplateCourse templateCourse = loadedTemplate.getTemplateCourses().get(0);
		assertEquals(templateCourse.getCourseCode(),"MAT");
		assertEquals(templateCourse.getCourseTitle(),"TEST");
		assertEquals(templateCourse.getFormattedCourse(),"TEST");
		assertEquals(templateCourse.getCourseDescription(),"TEST");
		assertEquals(templateCourse.isDev(), true);
		assertEquals(templateCourse.getOrderInTerm(), new Integer(1));
		assertEquals(templateCourse.getOrderInTerm(), new Integer(1));
		
	}
	
	public void testGetAll()
	{
		PagingWrapper<Template> all = dao.getAll(SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ALL , null,
						null, null, null, null), null);
		assertTrue(all.getRows().size() > 0);
	}
	public void testTemplateDaoSaveWithChildren2() throws ObjectNotFoundException, CloneNotSupportedException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		Template template = new Template();
		template.setName("TestTemplate");
		template.setOwner(person);
		template.setObjectStatus(ObjectStatus.ACTIVE);
		
		TemplateCourse course = new TemplateCourse();
		course.setCourseCode("MAT");
		course.setCourseDescription("TEST");
		course.setCourseTitle("TEST");
		course.setFormattedCourse("TEST");
		course.setOrderInTerm(new Integer(1));
		course.setIsDev(true);
		course.setOrderInTerm(new Integer(1));
		course.setCreatedBy(new AuditPerson(person.getId()));
		course.setTemplate(template);
		course.setCreditHours(new BigDecimal(3.0));
		template.getTemplateCourses().add(course);
		
		dao.save(template);
		
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		Template loadedTemplate = dao.load(template.getId());
		
		Template clone = loadedTemplate.clone();
		clone.setId(loadedTemplate.getId());
		dao.save(clone);
		
		sessionFactory.getCurrentSession().flush();
		
		Template loadedClone = dao.load(clone.getId());
		
		assertEquals(1, loadedClone.getTemplateCourses().size());
		
		
		
	}

	public TermService getTermService() {
		return termService;
	}

	public void setTermService(TermService termService) {
		this.termService = termService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
}