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

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.service.stub.Stubs.PersonFixture;
import org.jasig.ssp.util.service.stub.Stubs.ProgramStatusFixture;
import org.jasig.ssp.util.service.stub.Stubs.StudentTypeFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class EarlyAlertStudentOutcomeReportControllerIntegrationTest extends
		AbstractReportControllerIntegrationTest {



	@Autowired
	private transient EarlyAlertStudentOutcomeReportController controller;

	/**
	 * {@link #testGetEarlyAlertStudentOutcomeReportWithFilters()}, 
	 * Test to make sure all the filters are implemented properly.
	 */
	@Test
	public void testGetEarlyAlertStudentOutcomeReportWithFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getEarlyAlertStudentOutcomeReport(response, 
				ObjectStatus.ACTIVE,//Object Status
				null,//roster status
				Stubs.HomeDepartmentFixture.MATHEMATICS.title(), //home department
				PersonFixture.COACH_1.id(), 
				Lists.newArrayList(StudentTypeFixture.ILP.id()),
				ProgramStatusFixture.ACTIVE.id(), 
				Lists.newArrayList(Stubs.SpecialServiceGroupFixture.TEST_SSG.id()), 
				TermFixture.FALL_2012.code(), 
				null, 
				null,
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("FIRST,MIDDLE,LAST,STUDENT ID,EMAIL(SCHOOL),EA TOT,RESPONSES,NO RESPONSE,,WAITING,NOT EA CLASS,DUPLICATE,COUNSELOR");
		expectedReportBodyLines.add(",,,,,,,,,,,,");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetEarlyAlertStudentOutcomeReportWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getEarlyAlertStudentOutcomeReport(response, 
				null,
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
		//TODO Understand why no filters does not bring back a result!
		expectedReportBodyLines.add("FIRST,MIDDLE,LAST,STUDENT ID,EMAIL(SCHOOL),EA TOT,RESPONSES,NO RESPONSE,,WAITING,NOT EA CLASS,DUPLICATE,COUNSELOR");
		expectedReportBodyLines.add("James,A,Gosling,student0,test@sinclair.edu,1,1,,1,0,0,0,Alan Turing");
		expectedReportBodyLines.add("test,Mumford,coach1student0,coach1student0,coach1student0@unicon.net,1,0,,0,0,0,0,test coach1");
		expectedReportBodyLines.add("test,Mumford,coach1student1,coach1student1,coach1student1@unicon.net,2,2,,0,0,1,1,test coach1");
		expectedReportBodyLines.add("test,Mumford,coach1student4,coach1student4,coach1student4@unicon.net,5,3,,3,0,0,0,test coach1");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Early Alert Student Outcome Report");
	}

}
