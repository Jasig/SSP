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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.service.stub.Stubs.CampusFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class JournalSessionDetailsReportIntegrationTest extends
		AbstractReportControllerIntegrationTest {

	@Autowired
	private transient JournalSessionDetailsReportController controller;

	
	/**
	 * {@link #testGetEarlyAlertClassReportWithFilters()}, 
	 * Test to make sure all the filters are implemented properly.
	 */
	@Test
	public void testJournalSessionDetailsReportWithFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getJournalSessionDetails(response, 
						ObjectStatus.ACTIVE, 
						Stubs.PersonFixture.COACH_1.id(),
						null,
						Stubs.ProgramStatusFixture.ACTIVE.id(),
						new Boolean(true),
						Lists.newArrayList(Stubs.SpecialServiceGroupFixture.TEST_SSG.id()),
						Lists.newArrayList(Stubs.StudentTypeFixture.ILP.id()),
						Lists.newArrayList(Stubs.ServiceReasonFixture.TEST_SERVICE_REASON.id()),
						Lists.newArrayList(Stubs.JournalStepDetail.TEST_JOURNAL_STEP_DETAIL.id()),
						Stubs.DateFixture.START_FALL_2012.date(),
						Stubs.DateFixture.END_FALL_2012.date(),
						Stubs.TermFixture.FALL_2012.code(),
						null,
						Stubs.HomeDepartmentFixture.MATHEMATICS.name(),
						"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Eliminate ,, from code
		expectedReportBodyLines.add("SCHOOL ID,FIRST,MIDDLE,LAST,COUNSELOR,JOURNAL DETAIL");
		expectedReportBodyLines.add(",,,,,");
		expectReportBodyLines(expectedReportBodyLines, response, null);
		}

	@Test
	public void testGetJournalSessionDetailsReportWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getJournalSessionDetails(response,
				null,
				null,
				null,
				null,
				true,
				null,
				null,
				null,
				null, 
				null,
				null,
				null,
				null,
				null,
				"csv");
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Eliminate ,, from code
		expectedReportBodyLines.add("SCHOOL ID,FIRST,MIDDLE,LAST,COUNSELOR,JOURNAL DETAIL");
		expectedReportBodyLines.add(",,,,,");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Test
	public void testGetJournalSessionDetailsReportNotStepsWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getJournalSessionDetails(response,
				null,
				null,
				null,
				null,
				false,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				"csv");
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Eliminate ,, from code
		expectedReportBodyLines.add("SCHOOL ID,FIRST,MIDDLE,LAST,COUNSELOR,JOURNAL DETAIL");
		expectedReportBodyLines.add("coach1student1,test,Mumford,coach1student1,test coach1,");
		expectedReportBodyLines.add("coach1student2,test,Mumford,coach1student2,test coach1,");
		expectedReportBodyLines.add("coach1student3,test,Mumford,coach1student3,test coach1,");
		expectedReportBodyLines.add("coach1student4,test,Mumford,coach1student4,test coach1,");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	@Override
	protected Predicate<String> afterHeader() {
		// TODO Auto-generated method stub
		return  afterLineContaining("Journal Session Detail Report");
	}

}
