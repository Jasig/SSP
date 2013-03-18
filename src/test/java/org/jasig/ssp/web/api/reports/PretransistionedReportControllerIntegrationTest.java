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
import java.util.UUID;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.service.stub.Stubs.PersonFixture;
import org.jasig.ssp.util.service.stub.Stubs.ProgramStatusFixture;
import org.jasig.ssp.util.service.stub.Stubs.ReferralSourceFixture;
import org.jasig.ssp.util.service.stub.Stubs.SpecialServiceGroupFixture;
import org.jasig.ssp.util.service.stub.Stubs.StudentTypeFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class PretransistionedReportControllerIntegrationTest extends
		AbstractReportControllerIntegrationTest {

	@Autowired
	private transient PreTransitionedReportController controller;

	@Test
	public void testGetPreTransitionedWithNoFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getPreTransitioned(response, null, null, null, null, null, null, null,
				null, null, null, null, null, "csv");

		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("STUDENT NAME,,,,ID,PHONE (HOME),PHONE (CELL)),,TYPE,STATUS,GPA,CURRENT REG,ACTUAL START TERM,ACTUAL START YEAR,SPECIAL SERVICE GROUPS,COACH");
		expectedReportBodyLines.add(",James,A,Gosling,student0,908-123-4567,,,ILP,Active,,N,,,Another Test Special Service Group - Test Special Service Group,Alan Turing");
		expectedReportBodyLines.add(",Dennis,M,Ritchie,dmr.1,908-123-4567,,,CAP,,,N,,,Another Test Special Service Group,Alan Turing");
		expectedReportBodyLines.add(",Kenneth,L,Thompson,ken.1,908-123-4567,,,CAP,Active,,N,,,Test Special Service Group,Alan Turing");
		expectedReportBodyLines.add(",test,Mumford,coach1student0,coach1student0,480-775-2345,,,ILP,Active,3.90,N,FA12,2013,Another Test Special Service Group,test coach1");
		expectedReportBodyLines.add(",test,Mumford,coach1student1,coach1student1,480-775-2345,,,CAP,Inactive,3.90,N,FA12,2013,Test Special Service Group,test coach1");
		expectedReportBodyLines.add(",test,Mumford,coach1student2,coach1student2,480-775-2345,,,EAL,Non-participating,3.90,N,FA12,2013,,test coach1");
		expectedReportBodyLines.add(",test,Mumford,coach1student3,coach1student3,480-775-2345,,,ILP,Transitioned,3.90,N,FA12,2013,,test coach1");
		expectedReportBodyLines.add("test,Mumford,coach1student4,coach1student4,480-775-2345,,CAP,No-Show,3.90,N,FA12,2013,Another Test Special Service Group,test coach1");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetPreTransitionedWith()
			throws IOException, ObjectNotFoundException, JRException {

		final Person dennisRitchie =
				personService.get(Stubs.PersonFixture.DMR.id());
		final Person kevinSmith =
				personService.get(Stubs.PersonFixture.KEVIN_SMITH.id());
		dennisRitchie.setCoach(kevinSmith);
		personService.save(dennisRitchie);
		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
		// Alan Turing, i.e. the coach assigned to our test student users
		// in our standard fixture
		controller.getPreTransitioned(response, 
				null, 
				PersonFixture.COACH_1.id(),
				ProgramStatusFixture.ACTIVE.id(), 
				Lists.newArrayList(SpecialServiceGroupFixture.ANOTHER_TEST_SSG.id()), 
				Lists.newArrayList(ReferralSourceFixture.TEST_REFERRAL_SOURCE.id()), 
				Lists.newArrayList(StudentTypeFixture.ILP.id()), 
				Lists.newArrayList(Stubs.ServiceReasonFixture.TEST_SERVICE_REASON.id()),
				null, 
				null, 
				TermFixture.FALL_2012.year(),
				TermFixture.FALL_2012.code(), 
				null,
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// same as in testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet(), but
		// Dennis Ritchie is missing
		expectedReportBodyLines.add("STUDENT NAME,,,,ID,PHONE (HOME),PHONE (CELL)),,TYPE,STATUS,GPA,CURRENT REG,ACTUAL START TERM,ACTUAL START YEAR,SPECIAL SERVICE GROUPS,COACH");
		expectedReportBodyLines.add(",,,,,,,,,,,N,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Test
	public void testGetPreTransitionedWithHomeDepartment()
			throws IOException, ObjectNotFoundException, JRException {
;
		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
		// Alan Turing, i.e. the coach assigned to our test student users
		// in our standard fixture
		controller.getPreTransitioned(response, 
				null, 
				null,
				ProgramStatusFixture.ACTIVE.id(), 
				Lists.newArrayList(SpecialServiceGroupFixture.ANOTHER_TEST_SSG.id()), 
				Lists.newArrayList(ReferralSourceFixture.TEST_REFERRAL_SOURCE.id()), 
				Lists.newArrayList(StudentTypeFixture.ILP.id()),
				Lists.newArrayList(Stubs.ServiceReasonFixture.TEST_SERVICE_REASON.id()),
				null, 
				null, 
				TermFixture.FALL_2012.year(),
				TermFixture.FALL_2012.code(), 
				Stubs.HomeDepartmentFixture.MATHEMATICS.title(),
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// same as in testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet(), but
		// Dennis Ritchie is missing
		expectedReportBodyLines.add("STUDENT NAME,,,,ID,PHONE (HOME),PHONE (CELL)),,TYPE,STATUS,GPA,CURRENT REG,ACTUAL START TERM,ACTUAL START YEAR,SPECIAL SERVICE GROUPS,COACH");
		expectedReportBodyLines.add(",,,,,,,,,,,N,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Counselor Case Management Report");
	}
}
