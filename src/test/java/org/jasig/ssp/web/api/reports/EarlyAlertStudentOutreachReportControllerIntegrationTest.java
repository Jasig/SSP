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

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs.EarlyAlertOutcomeFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class EarlyAlertStudentOutreachReportControllerIntegrationTest extends
		AbstractReportControllerIntegrationTest {


	@Autowired
	private transient EarlyAlertStudentOutreachReportController controller;


	@Test
	public void testGetEarlyAlertStudentOutreachReportWithFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getEarlyAlertStudentOutreachReport(response, 
				null, 
				null,
				Lists.newArrayList(EarlyAlertOutcomeFixture.WAITING_FOR_RESPONSE.id()), 
				TermFixture.FALL_2012.code(), 
				null, 
				null, 
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("COACH,TOTAL EARLY ,PHONE CALLS,EMAILS,LETTERS,TEXTS,IN PERSON");
		expectedReportBodyLines.add("test Mumford coach1,4,0,0,4,0,0");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetEarlyAlertStudentOutreachReportWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getEarlyAlertStudentOutreachReport(response, 
				null,
				null,
				null,
				null, 
				null,
				null, 
				"csv");;
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Understand why no filters does not bring back a result!
		expectedReportBodyLines.add("COACH,TOTAL EARLY ,PHONE CALLS,EMAILS,LETTERS,TEXTS,IN PERSON");
		expectedReportBodyLines.add("test Mumford coach1,5,0,0,4,0,1");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Early Alert Student Outreach Report");
	}

}
