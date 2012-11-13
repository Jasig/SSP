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
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.CharSequenceReader;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.util.service.stub.StubPersonAttributesService;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ReportControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class CaseloadReportControllerIntegrationTest {

	@Autowired
	private CaseloadReportController controller;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient StubPersonAttributesService personAttributesService;

	private transient List<String> origCoachUsernames;

	/**
	 * Setup the security service with the administrator.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		Collection<String> rawCoachUsernames = personAttributesService.getCoachUsernames();
		if ( rawCoachUsernames != null ) {
			origCoachUsernames = new ArrayList<String>(rawCoachUsernames);
		}
	}

	@After
	public void tearDown() {
		personAttributesService.setCoachUsernames(origCoachUsernames);
	}

	@Test
	public void testDefaultDataSet() throws ObjectNotFoundException, IOException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// by "body" the actual results and the header that describes its columns.
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

		final List<String> actualReportBodyLines = new ArrayList<String>(5);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("Case Load")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body" , expectedReportBodyLines, actualReportBodyLines);
	}

	@Test
	public void testIncludesCoachesWithoutAnyCaseload()
			throws ObjectNotFoundException, IOException, JRException {

		personAttributesService.getCoachUsernames()
				.add(Stubs.PersonFixture.KEVIN_SMITH.username());

		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getCaseLoad(response, null, "csv");

		// by "body" the actual results and the header that describes its columns.
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

		final List<String> actualReportBodyLines = new ArrayList<String>(5);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("Case Load")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body" , expectedReportBodyLines, actualReportBodyLines);
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

		// by "body" the actual results and the header that describes its columns.
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

		final List<String> actualReportBodyLines = new ArrayList<String>(5);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("Case Load")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body" , expectedReportBodyLines, actualReportBodyLines);
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

		// by "body" the actual results and the header that describes its columns.
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

		final List<String> actualReportBodyLines = new ArrayList<String>(5);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("Case Load")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body" , expectedReportBodyLines, actualReportBodyLines);
	}

}
