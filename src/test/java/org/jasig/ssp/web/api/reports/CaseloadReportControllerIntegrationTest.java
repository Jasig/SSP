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


public class CaseloadReportControllerIntegrationTest
		extends AbstractReportControllerIntegrationTest {

	@Autowired
	private CaseloadReportController controller;

	@Test
	public void testDefaultDataSet() throws ObjectNotFoundException, IOException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add(",FIRST,,LAST,DEPARTMENT,TOTAL COUNT,,,ACTIVE COUNT,,A,,IA,,T,,NP,,NS");
		expectedReportBodyLines.add(",Alan,,Turing,Not Available Yet,2,,,2,,2,,0,,0,,0,,0");
		// not sure why totals render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",,,,,,,2,,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,,,,2,,0,,0,,0,,0,");
		expectedReportBodyLines.add(",TOTAL:,,,,2,,,,,,,,,,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Test
	public void testIncludesCoachesWithoutAnyCaseload()
			throws ObjectNotFoundException, IOException, JRException {

		personAttributesService.getCoachUsernames()
				.add(Stubs.PersonFixture.KEVIN_SMITH.username());

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add(",FIRST,,LAST,DEPARTMENT,TOTAL COUNT,,,ACTIVE COUNT,,A,,IA,,T,,NP,,NS");
		expectedReportBodyLines.add(",Kevin,,Smith,Not Available Yet,0,,,0,,0,,0,,0,,0,,0");
		expectedReportBodyLines.add(",Alan,,Turing,Not Available Yet,2,,,2,,2,,0,,0,,0,,0");
		// not sure why totals render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",,,,,,,2,,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,,,,2,,0,,0,,0,,0,");
		expectedReportBodyLines.add(",TOTAL:,,,,2,,,,,,,,,,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Test
	public void testNewEarlyAlertForValidCoachReflectedOnReport()
			throws ObjectNotFoundException, IOException, JRException, ValidationException {

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
		earlyAlertService.create(earlyAlert);

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add(",FIRST,,LAST,DEPARTMENT,TOTAL COUNT,,,ACTIVE COUNT,,A,,IA,,T,,NP,,NS");
		expectedReportBodyLines.add(",Kevin,,Smith,Not Available Yet,1,,,1,,1,,0,,0,,0,,0");
		expectedReportBodyLines.add(",Alan,,Turing,Not Available Yet,2,,,2,,2,,0,,0,,0,,0");
		// not sure why totals render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",,,,,,,3,,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,,,,3,,0,,0,,0,,0,");
		expectedReportBodyLines.add(",TOTAL:,,,,3,,,,,,,,,,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	/**
	 * Same as {@link #testNewEarlyAlertForValidCoachReflectedOnReport()}, but
	 * we don't register one of the coaches with the PersonAttributesService.
	 * This failed in early impls b/c the coach list returned from the uPortal-
	 * backed PersonAttributesService didn't match the list of users with actual
	 * caseloads in the SSP database.
	 */
	@Test
	public void testNewEarlyAlertForMissingCoachReflectedOnReport()
			throws ObjectNotFoundException, ValidationException, IOException, JRException {
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
		earlyAlertService.create(earlyAlert);

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add(",FIRST,,LAST,DEPARTMENT,TOTAL COUNT,,,ACTIVE COUNT,,A,,IA,,T,,NP,,NS");
		expectedReportBodyLines.add(",Kevin,,Smith,Not Available Yet,1,,,1,,1,,0,,0,,0,,0");
		expectedReportBodyLines.add(",Alan,,Turing,Not Available Yet,2,,,2,,2,,0,,0,,0,,0");
		// not sure why totals render this way, but they do... csv formatting
		// needs to be fixed up
		expectedReportBodyLines.add(",,,,,,,3,,,,,,,,,,,");
		expectedReportBodyLines.add(",,,,,,,,,3,,0,,0,,0,,0,");
		expectedReportBodyLines.add(",TOTAL:,,,,3,,,,,,,,,,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, afterHeader());
	}

	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Case Load");
	}

}
