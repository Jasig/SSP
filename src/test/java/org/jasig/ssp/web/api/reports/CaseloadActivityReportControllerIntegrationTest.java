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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;

import net.sf.jasperreports.engine.JRException;


public class CaseloadActivityReportControllerIntegrationTest
		extends AbstractReportControllerIntegrationTest {

	@Autowired
	private transient CaseloadActivityReportController controller;

	@Test
	public void testDefaultDataSet()
			throws ObjectNotFoundException, IOException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoadActivity(response, null, null, null, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// not sure why lines render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",FIRST,,LAST,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,JOURNAL ENTRIES CREATED,STUDENT JOURNAL ENTRY COUNT,,ACTION TASKS CREATED,STUDENT ACTION TASKS COUNT,EARLY ALERTS CREATED,STUDENTS EARLY ALERT COUNT,EARLY ALERTS RESPONDED");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Alan,,Turing,,,0,0,,,0,0,0,0");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Test
	public void testIncludesAllOfficialCoaches()
			throws ObjectNotFoundException, IOException, JRException {

		personAttributesService.getCoachUsernames()
				.add(Stubs.PersonFixture.KEVIN_SMITH.username());

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoadActivity(response, null, null, null, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// not sure why lines render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",FIRST,,LAST,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,JOURNAL ENTRIES CREATED,STUDENT JOURNAL ENTRY COUNT,,ACTION TASKS CREATED,STUDENT ACTION TASKS COUNT,EARLY ALERTS CREATED,STUDENTS EARLY ALERT COUNT,EARLY ALERTS RESPONDED");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Kevin,,Smith,,,0,0,,,0,0,0,0");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Alan,,Turing,,,0,0,,,0,0,0,0");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());

	}

	/**
	 * Same as {@link #testIncludesAllOfficialCoaches()}, but
	 * we don't register one of the coaches with the PersonAttributesService.
	 * This failed in early impls b/c the coach list returned from the uPortal-
	 * backed PersonAttributesService didn't match the list of users with actual
	 * caseloads in the SSP database.
	 */
	@Test
	public void testIncludesBothOfficialAndNonOfficialCoaches()
			throws ObjectNotFoundException, ValidationException, IOException, JRException {
		final Person jamesDoe =
				personService.get(Stubs.PersonFixture.JAMES_DOE.id());
		final Person kevinSmith =
				personService.get(Stubs.PersonFixture.KEVIN_SMITH.id());
		jamesDoe.setCoach(kevinSmith);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoadActivity(response, null, null, null, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// not sure why lines render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",FIRST,,LAST,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,JOURNAL ENTRIES CREATED,STUDENT JOURNAL ENTRY COUNT,,ACTION TASKS CREATED,STUDENT ACTION TASKS COUNT,EARLY ALERTS CREATED,STUDENTS EARLY ALERT COUNT,EARLY ALERTS RESPONDED");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Kevin,,Smith,,,0,0,,,0,0,0,0");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Alan,,Turing,,,0,0,,,0,0,0,0");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Test
	public void testNewCoachInitiatedEarlyAlertReflectedOnReport()
			throws ObjectNotFoundException, IOException, JRException, ValidationException {

		// adding kevinSmith to the personAttributesService stub effectively
		// duplicates what testIncludesAllOfficialCoaches() tests, but we have
		// almost exactly the same thing going on in
		// CaseloadReportControllerIntegrationTest, and there's at least
		// some (very small) unit of value in verifying that early alert
		// counting does work for *any* user found in the attributes service,
		// not just the default

		personAttributesService.getCoachUsernames()
				.add(Stubs.PersonFixture.KEVIN_SMITH.username());

		final Person jamesDoe =
				personService.get(Stubs.PersonFixture.JAMES_DOE.id());
		final Person kevinSmith =
				personService.get(Stubs.PersonFixture.KEVIN_SMITH.id());
		jamesDoe.setCoach(kevinSmith);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final EarlyAlert earlyAlert =
				Stubs.arrangeEarlyAlert(personService, campusService);
		earlyAlert.setPerson(jamesDoe);
		earlyAlert.setClosedById(null);
		earlyAlert.setClosedDate(null);
		earlyAlert.setCreatedBy(kevinSmith); //otherwise will be the system user
		earlyAlertService.create(earlyAlert);

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoadActivity(response, null, null, null, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// not sure why lines render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",FIRST,,LAST,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,JOURNAL ENTRIES CREATED,STUDENT JOURNAL ENTRY COUNT,,ACTION TASKS CREATED,STUDENT ACTION TASKS COUNT,EARLY ALERTS CREATED,STUDENTS EARLY ALERT COUNT,EARLY ALERTS RESPONDED");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Kevin,,Smith,,,0,0,,,0,1,1,0");
		expectedReportBodyLines.add(",,,,,,,,,0,,,,");
		expectedReportBodyLines.add(",Alan,,Turing,,,0,0,,,0,0,0,0");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Case Load Activity");
	}

}
