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
package org.jasig.ssp.dao; // NOPMD by jon.adams on 5/16/12 9:59 PM

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertDaoTest.class);

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID EARLY_ALERT_SUGGESTION_ID = UUID
			.fromString("b2d11141-5056-a51a-80c1-c1250ba820f8");

	private static final String EARLY_ALERT_SUGGESTION_NAME = "See Instructor";

	private static final String EARLY_ALERT_COURSE_NAME = "Complicated Science 101";

	private static final UUID EARLY_ALERT_SUGGESTION_DELETED_ID = UUID
			.fromString("881DF3DD-1AA6-4CB8-8817-E95DAF49227A");

	public final static UUID EARLY_ALERT_OUTCOME_STUDENTRESPONDED_ID = UUID
			.fromString("12a58804-45dc-40f2-b2f5-d7e4403acee1");

	@Autowired
	private transient EarlyAlertDao dao;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}
	
	private Date getDateSetByDayOffset(int dayOffset) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_MONTH, dayOffset);
		return new Date(today.getTimeInMillis());
	}
	
	private void assertList(final Collection<EarlyAlert> objects) {
		for (final EarlyAlert object : objects) {
			assertNotNull("List should not have contained any null objects.",
					object.getId());
		}
	}
	
	/**
	 * Create a new sample message to use for testing.
	 * 
	 * @return a new sample message to use for testing
	 * @throws ObjectNotFoundException
	 */
	private EarlyAlert createTestClosedEarlyAlert()
			throws ObjectNotFoundException {
		final EarlyAlert obj = new EarlyAlert();
		obj.setPerson(personService.get(PERSON_ID));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setClosedDate(new Date());
		obj.setClosedById(PERSON_ID);
		obj.setCourseName(EARLY_ALERT_COURSE_NAME);
		obj.setCampus(campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1")));

		final Set<EarlyAlertSuggestion> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestion(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME,
				"description", (short) 0)); // NOPMD by jon.adams on 5/21/12
		final EarlyAlertSuggestion deletedSuggestion = new EarlyAlertSuggestion(
				EARLY_ALERT_SUGGESTION_DELETED_ID,
				"EARLY_ALERT_SUGGESTION_DELETED_NAME", "description", (short) 0); // NOPMD
		deletedSuggestion.setObjectStatus(ObjectStatus.INACTIVE);
		earlyAlertSuggestionIds.add(deletedSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		return obj;
	}

	/**
	 * Test the {@link EarlyAlertDao#save(EarlyAlert)} and
	 * {@link EarlyAlertDao#delete(EarlyAlert)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             If any referenced objects could not be found. Since this is a
	 *             test, it should not be thrown.
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		final EarlyAlert saved = dao.save(createTestClosedEarlyAlert());

		assertNotNull("Id should have been automatically generated.",
				saved.getId());
		final UUID savedId = saved.getId();

		LOGGER.debug(saved.toString());

		try {
			final EarlyAlert obj = dao.get(savedId);

			assertNotNull("Saved message could not reloaded.", obj);
			assertEquals("Saved and reloaded IDs do not match.", savedId,
					obj.getId());

			final Collection<EarlyAlert> all = dao.getAll(ObjectStatus.ACTIVE)
					.getRows();
			assertNotNull("GetAll list should not have been null.", all);
			assertFalse("GetAll list should not have been empty.",
					all.isEmpty());
			assertList(all);

		} catch (final ObjectNotFoundException e) {
			fail("Saved message could not be found to reload.");
		} finally {
			dao.delete(saved);
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlert obj = dao.get(id);

		assertNull("Random ID should not have loaded any object.", obj);
	}

	@Test
	public void uuidGeneration() throws ObjectNotFoundException {
		final EarlyAlert obj = dao.save(createTestClosedEarlyAlert());
		final EarlyAlert obj2 = dao.save(createTestClosedEarlyAlert());

		assertNotNull("Generated ID should not have been null.", obj.getId());
		assertNotNull("Generated ID should not have been null.", obj2.getId());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void testHashCode() throws ObjectNotFoundException {
		final EarlyAlert obj = createTestClosedEarlyAlert();
		assertNotEquals("HashCodes should not have matched.", obj.hashCode(),
				new EarlyAlert().hashCode());
		assertEquals("HashCodes should have matched.", obj.hashCode(),
				obj.hashCode());
		assertEquals("HashCodes should have matched.",
				new EarlyAlert().hashCode(), new EarlyAlert().hashCode());
	}

	@Test
	public void getCountOfActiveAlertsForPeopleIds()
			throws ObjectNotFoundException {
		// arrange
		final UUID randomUuid = UUID.randomUUID();

		final EarlyAlert earlyAlert = createTestClosedEarlyAlert();
		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedById(null);
		final EarlyAlert saved = dao.save(earlyAlert);

		final EarlyAlert earlyAlert2 = createTestClosedEarlyAlert();
		earlyAlert2.setClosedDate(null);
		earlyAlert2.setClosedById(null);
		earlyAlert2.setObjectStatus(ObjectStatus.INACTIVE);
		final EarlyAlert saved2 = dao.save(earlyAlert2);
		sessionFactory.getCurrentSession().flush();

		final Collection<UUID> peopleIds = Lists.newArrayList();
		peopleIds.add(PERSON_ID);
		peopleIds.add(randomUuid);

		try {
			final Map<UUID, Number> result = dao
					.getCountOfActiveAlertsForPeopleIds(peopleIds);
			assertEquals("Count of ActiveAlertsForPeopleIds was not expected.",
					2, result.size());
			assertEquals("Count of PERSON_ID was not expected.", 2,
					result.get(PERSON_ID).intValue());
			assertEquals("Count of randomUuid was not expected.", 0, result
					.get(randomUuid).intValue());
		} finally {
			dao.delete(saved);
			dao.delete(saved2);
		}
	}

	@Test
	public void getCountOfActiveAlertsForPeopleIdsWithoutNew()
			throws ObjectNotFoundException {
		final Collection<UUID> peopleIds = Lists.newArrayList();
		peopleIds.add(PERSON_ID);

		final Map<UUID, Number> count = dao
				.getCountOfActiveAlertsForPeopleIds(peopleIds);
		assertEquals("Count of ActiveAlertsForPeopleIds was not expected.", 1,
				count.size());
		assertEquals("Count of PERSON_ID was not expected.", 1,
				count.get(PERSON_ID).intValue());
	}

	@Test
	public void getCountOfActiveAlertsForPeopleIdsEmpty()
			throws ObjectNotFoundException {
		final Collection<UUID> peopleIds = Lists.newArrayList();

		final Map<UUID, Number> count = dao
				.getCountOfActiveAlertsForPeopleIds(peopleIds);
		assertEquals("Count of ActiveAlertsForPeopleIds was not expected.", 0,
				count.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCountOfActiveAlertsForPeopleIdsNull()
			throws ObjectNotFoundException {
		dao.getCountOfActiveAlertsForPeopleIds(null);
		fail("Exception should have been thrown.");
	}

	@Test
	public void getCountOfEarlyAlertStudentsByDate()
			throws ObjectNotFoundException {

		final Date startDate = getDateSetByDayOffset(-1);
		final Date endDate = getDateSetByDayOffset(1);

		final EarlyAlert earlyAlert = createTestClosedEarlyAlert();
		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedById(null);
		final EarlyAlert saved = dao.save(earlyAlert);

		final EarlyAlert earlyAlert2 = createTestClosedEarlyAlert();
		final EarlyAlert saved2 = dao.save(earlyAlert2);
		sessionFactory.getCurrentSession().flush();

		Campus campus = campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1"));

		try {
			final Long result = dao.getCountOfEarlyAlertStudentsByDate(
					startDate, endDate, campus, null);
			assertEquals("Count of Students was not expected.", 1,
					result.intValue());
		} finally {
			dao.delete(saved);
			dao.delete(saved2);
		}
	}

	@Test
	public void getCountOfEarlyAlertsClosedByDate()
			throws ObjectNotFoundException {
		// arrange
		final Date startDate = getDateSetByDayOffset(-1);
		final Date endDate = getDateSetByDayOffset(1);
		final EarlyAlert earlyAlert = createTestClosedEarlyAlert();
		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedById(null);
		final EarlyAlert saved = dao.save(earlyAlert);

		final EarlyAlert earlyAlert2 = createTestClosedEarlyAlert();
		final EarlyAlert saved2 = dao.save(earlyAlert2);
		sessionFactory.getCurrentSession().flush();

		Campus campus = campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1"));

		try {
			final Long result = dao.getCountOfEarlyAlertsClosedByDate(
					startDate, endDate, campus, null);
			assertEquals("Count of Students was not expected.", 1,
					result.intValue());
		} finally {
			dao.delete(saved);
			dao.delete(saved2);
		}
	}

	@Test
	public void getCountOfEarlyAlertsByCreatedDate()
			throws ObjectNotFoundException {
		// arrange
		final Date startDate = getDateSetByDayOffset(-1);
		final Date endDate = getDateSetByDayOffset(1);
		final EarlyAlert earlyAlert = createTestClosedEarlyAlert();
		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedById(null);
		final EarlyAlert saved = dao.save(earlyAlert);

		final EarlyAlert earlyAlert2 = createTestClosedEarlyAlert();
		final EarlyAlert saved2 = dao.save(earlyAlert2);
		sessionFactory.getCurrentSession().flush();

		Campus campus = campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1"));

		try {
			final Long result = dao.getCountOfEarlyAlertsByCreatedDate(
					startDate, endDate, campus, null);
			assertEquals("Count of Students was not expected.", 2,
					result.intValue());
		} finally {
			dao.delete(saved);
			dao.delete(saved2);
		}
	}
	
	@Test
	public void getStudentsEarlyAlertCountSetForCritera()
			throws ObjectNotFoundException {
		
		final Date startDate = getDateSetByDayOffset(-1);
		final Date endDate = getDateSetByDayOffset(1);
		final EarlyAlert earlyAlert = createTestClosedEarlyAlert();
		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedById(null);
		final EarlyAlert saved = dao.save(earlyAlert);

		final EarlyAlert earlyAlert2 = createTestClosedEarlyAlert();
		final EarlyAlert saved2 = dao.save(earlyAlert2);
		sessionFactory.getCurrentSession().flush();


		final PersonSearchFormTO addressLabelSearchTO = new PersonSearchFormTO(
				null,
				null, null, null, null, null,
				null, null,
				null);
		
		final EarlyAlertStudentSearchTO searchForm = new EarlyAlertStudentSearchTO(addressLabelSearchTO,startDate, endDate);


		try {
			final PagingWrapper<EarlyAlertStudentReportTO> result = dao.getStudentsEarlyAlertCountSetForCritera(searchForm, null);
			List<EarlyAlertStudentReportTO> compressedReports = new ArrayList<EarlyAlertStudentReportTO>();
			for(EarlyAlertStudentReportTO reportTO: result.getRows())
			{
				Integer index = compressedReports.indexOf(reportTO);
				if(index >= 0){
					compressedReports.get(index).processDuplicate(reportTO);
				}else{
					compressedReports.add(reportTO);
				}
			}
			assertEquals("Count of Students was not expected.", 1,
					compressedReports.size());
			assertEquals("Count of Alerts Total was not expected.", (Long)2L,
					compressedReports.get(0).getTotal());
			assertEquals("Count of Alerts Closed was not expected.", (Long)1L,
					compressedReports.get(0).getClosed());
			assertEquals("Count of Alerts Pending was not expected.", (Long)0L,
					compressedReports.get(0).getPending());
			assertEquals("Count of Alerts Open was not expected.", (Long)1L,
					compressedReports.get(0).getOpen());
		} finally {
			dao.delete(saved);
			dao.delete(saved2);
		}
	}
}