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
package org.jasig.ssp.web.api.reports;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.sort.PagingWrapper;
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
@ContextConfiguration("../../../dao/dao-testConfig.xml")
// many (most?) other DAO tests commit by default... here we're just testing
// reads, so we know that any fixture setup needs to be rolled back
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SearchParametersTest {

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient StudentTypeService studentTypeService;

	@Autowired
	private transient SpecialServiceGroupService ssgService;
	
	@Autowired
	private transient ReferralSourceService referralSourceService;
	
	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;
	

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSpecialGroupsNamesToMap() throws ObjectNotFoundException {
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<UUID> specialServiceGroupIds = new ArrayList<UUID>();

		PagingWrapper<SpecialServiceGroup> ssgs = ssgService.getAll(null);
		for (SpecialServiceGroup ssg : ssgs) {
			specialServiceGroupIds.add(ssg.getId());
		}

		SearchParameters.addSpecialGroupsNamesToMap(specialServiceGroupIds,
				parameters, ssgService);

		assertEquals("Unable to find add SpecialServiceGroups to Map",
				parameters.size(), 1);
	}
	
	@Test
	public void testReferralSourceNamesToMap() throws ObjectNotFoundException {
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<UUID> referralSourceIds = new ArrayList<UUID>();

		PagingWrapper<ReferralSource> referalSources = referralSourceService.getAll(null);
		for (ReferralSource referralSource : referalSources) {
			referralSourceIds.add(referralSource.getId());
		}

		SearchParameters.addReferralSourcesToMap(referralSourceIds,
				parameters, referralSourceService);

		assertEquals("Unable to add Referral source to Map",
				parameters.size(), 1);
	}
	
	@Test
	public void testStudentTypeNamesToMap() throws ObjectNotFoundException {
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<UUID> studentTypeIds = new ArrayList<UUID>();

		PagingWrapper<StudentType> studentTypes = studentTypeService.getAll(null);
		for (StudentType studentType : studentTypes) {
			studentTypeIds.add(studentType.getId());
		}

		SearchParameters.addStudentTypesToMap(studentTypeIds, parameters, studentTypeService);

		assertEquals("Unable to add Student Type to Map",
				parameters.size(), 1);
	}
	
	@Test
	public void testEarlyAlertOutcomeNamesToMap() throws ObjectNotFoundException {
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<UUID> outcomeIds = new ArrayList<UUID>();

		PagingWrapper<EarlyAlertOutcome> earlyAlertOutcomes = earlyAlertOutcomeService.getAll(null);
		for (EarlyAlertOutcome outcome : earlyAlertOutcomes) {
			outcomeIds.add(outcome.getId());
		}

		SearchParameters.addEarlyAlertOutcomesToMap(outcomeIds, parameters, earlyAlertOutcomeService);

		assertEquals("Unable to add Early Alert Outcome to Map",
				parameters.size(), 1);
	}


}