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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.CharSequenceReader;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class AddressLabelsReportControllerIntegrationTest {

	@Autowired
	private transient AddressLabelsReportController controller;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient SessionFactory sessionFactory;

	/**
	 * Setup the security service with the administrator.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		controller.getAddressLabels(response, null, null, null, null, null,
				null, null, null, null, null, "csv");

		// by "body" the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("FIRST,,,MIDDLE,LAST,,,Student ,TYP,Address,,CITY,ST,PHONE(H),EMAIL(SCHOOL),EMAIL(HOME)");
		expectedReportBodyLines.add("James,,,A,Gosling,,,,ILP,444 West Third Street ,,San Francisco,CA,908-123-4567,test@sinclair.edu,test@sinclair.edu");
		expectedReportBodyLines.add("Dennis,,,M,Ritchie,,,,CAP,444 West Third Street ,,Berkeley Heights,NJ,908-123-4567,test@sinclair.edu,test@sinclair.edu");
		expectedReportBodyLines.add("Kenneth,,,L,Thompson,,,,CAP,444 West Third Street ,,Murray Hill,NJ,908-123-4567,test@sinclair.edu,test@sinclair.edu");

		final List<String> actualReportBodyLines = new ArrayList<String>(4);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("General Student Report")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body", expectedReportBodyLines, actualReportBodyLines);

	}

	@Test
	public void testGetAddressLabelsFiltersByCoachId()
			throws IOException, ObjectNotFoundException, JRException {

		final Person dennisRitchie =
				personService.get(UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		final Person kevinSmith =
				personService.get(UUID.fromString("f26d8f23-df20-40f1-bc98-83111be4a52a"));
		dennisRitchie.setCoach(kevinSmith);
		personService.save(dennisRitchie);
		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
		// Alan Turing, i.e. the coach assigned to our test student users
		// in our standard fixture
		final UUID coachId = UUID.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff");
		controller.getAddressLabels(response, null, coachId, null, null, null,
				null, null, null, null, null, "csv");

		// by "body" the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// same as in testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet(), but
		// Dennis Ritchie is missing
		expectedReportBodyLines.add("FIRST,,,MIDDLE,LAST,,,Student ,TYP,Address,,CITY,ST,PHONE(H),EMAIL(SCHOOL),EMAIL(HOME)");
		expectedReportBodyLines.add("James,,,A,Gosling,,,,ILP,444 West Third Street ,,San Francisco,CA,908-123-4567,test@sinclair.edu,test@sinclair.edu");
		expectedReportBodyLines.add("Kenneth,,,L,Thompson,,,,CAP,444 West Third Street ,,Murray Hill,NJ,908-123-4567,test@sinclair.edu,test@sinclair.edu");

		final List<String> actualReportBodyLines = new ArrayList<String>(4);
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean startAccumulatingActualBodyLines = false;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if (line.contains("General Student Report")) {
				startAccumulatingActualBodyLines = true;
				continue;
			}
			if ( startAccumulatingActualBodyLines ) {
				actualReportBodyLines.add(line);
			}
		}

		assertEquals("Unexpected report body", expectedReportBodyLines, actualReportBodyLines);

	}

}
