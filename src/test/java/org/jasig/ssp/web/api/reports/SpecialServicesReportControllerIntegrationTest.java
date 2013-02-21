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
import org.jasig.ssp.util.service.stub.Stubs.SpecialServiceGroupFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class SpecialServicesReportControllerIntegrationTest extends
		AbstractReportControllerIntegrationTest {


	@Autowired
	private transient SpecialServicesReportController controller;


	@Test
	public void testGetEarlyAlertClassReportWithFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getSpecialServices(response, 
				null, Lists.newArrayList(SpecialServiceGroupFixture.ANOTHER_TEST_SSG.id(), SpecialServiceGroupFixture.ANOTHER_TEST_SSG.id()), "csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("STUDENT ID,FIRST NAME,MIDDLE NAME,LAST NAME,STUDENT TYPE,SPECIAL SERVICES");
		expectedReportBodyLines.add("student0,James,A,Gosling,ILP,Another Test Special Service Group");
		expectedReportBodyLines.add("dmr.1,Dennis,M,Ritchie,CAP,Another Test Special Service Group");
		expectedReportBodyLines.add("coach1student0,test,Mumford,coach1student0,ILP,Another Test Special Service Group");
		expectedReportBodyLines.add("coach1student4,test,Mumford,coach1student4,CAP,Another Test Special Service Group");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetEarlyAlertClassReportWithNoFilter()
			throws IOException, ObjectNotFoundException, JRException {

		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();

		controller.getSpecialServices(response, 
				null, 
				null, 
				"csv");;
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		//TODO Understand why no filters does not bring back a result!
		expectedReportBodyLines.add("STUDENT ID,FIRST NAME,MIDDLE NAME,LAST NAME,STUDENT TYPE,SPECIAL SERVICES");
		expectedReportBodyLines.add("student0,James,A,Gosling,ILP,Another Test Special Service Group -Test Special Service Group");
		expectedReportBodyLines.add("dmr.1,Dennis,M,Ritchie,CAP,Another Test Special Service Group");
		expectedReportBodyLines.add("ken.1,Kenneth,L,Thompson,CAP,Test Special Service Group");
		expectedReportBodyLines.add( "coach1student0,test,Mumford,coach1student0,ILP,Another Test Special Service Group");
		expectedReportBodyLines.add( "coach1student1,test,Mumford,coach1student1,CAP,Test Special Service Group");
		expectedReportBodyLines.add("coach1student2,test,Mumford,coach1student2,EAL,");
		expectedReportBodyLines.add("coach1student3,test,Mumford,coach1student3,ILP,");
		expectedReportBodyLines.add("coach1student4,test,Mumford,coach1student4,CAP,Another Test Special Service Group");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Special Services Group Report");
	}

}
