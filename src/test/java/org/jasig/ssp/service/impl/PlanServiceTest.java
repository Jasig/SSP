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
package org.jasig.ssp.service.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.PlanDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.PlanCourseTO;
import org.jasig.ssp.transferobject.PlanTO;
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
 * {@link PlanServiceImpl} test suite
 * 
 * @author tony.arland
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PlanServiceTest {

	private transient PlanServiceImpl service;
	
	private transient PlanDao dao;
	
	  
	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient TermService termService;

	@Before
	public void setUp() {
		service = new PlanServiceImpl();
		dao = createMock(PlanDao.class);
		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<Plan> daoAll = new ArrayList<Plan>();
		daoAll.add(new Plan());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Plan>(daoAll));
		
		replay(dao);

		final Collection<Plan> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll() result should not have returned an empty list.",
				all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Plan daoOne = new Plan(id);
        daoOne.setObjectStatus(ObjectStatus.ACTIVE);
		expect(dao.get(id)).andReturn(daoOne);
		
		replay(dao);

		assertNotNull("Get() result should not have been null.",
				service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final Plan daoOne = new Plan(id);

		expect(dao.save(daoOne)).andReturn(daoOne);
		
		replay(dao);

		assertNotNull("Save() result should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException { // NOPMD
		final UUID id = UUID.randomUUID();
		final Plan daoOne = new Plan(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		service.delete(id);

		verify(dao);
	}
	

	@Test
	public void createMapPlanPrintOutTest() throws ObjectNotFoundException {
		
		PlanTO plan = new PlanTO();
		plan.setName("TestPlan");
		plan.setObjectStatus(ObjectStatus.ACTIVE);
		
		Collection<Term> all = termService.getAll(null).getRows();
		for (Term term : all) {
			if(term.getReportYear() < 2008 || term.getReportYear() > 2013)
				continue;
			
			for(int i = 1; i < 6; i++)
			{
				
				PlanCourseTO course = new PlanCourseTO();
				course.setCourseCode("MAT-"+i);
				course.setCourseDescription("TEST"+i);
				course.setCourseTitle("TEST"+i);
				course.setFormattedCourse("TEST"+i);
				course.setOrderInTerm(new Integer(i));
				course.setIsDev(false);
				course.setCreditHours(new BigDecimal(3.0));
				course.setTermCode(term.getCode());
				plan.getPlanCourses().add(course);
			}
		}
		

		//final SubjectAndBody messageText = service.createMatrixOutput(plan, MessageTemplate.OUTPUT_MAP_PLAN_MATRIX_ID);
		//assertNotNull("Save() result should not have been null.", messageText);

	}

}