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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.service.stub.Stubs.CampusFixture;
import org.jasig.ssp.util.service.stub.Stubs.TermFixture;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class EarlyAlertCaseCountsReportControllerIntegrationTest 
	extends AbstractReportControllerIntegrationTest{

	@Autowired
	private transient EarlyAlertCaseCountsReportController controller;
	@Autowired
	private transient PersonService personService;
	@Autowired
	protected transient SecurityService securityService;
	@Autowired
	protected transient ProgramStatusService programStatusService;
	@Autowired
	protected transient SpecialServiceGroupService ssgService;
	@Autowired
	protected transient StudentTypeService studentTypeService;
	@Autowired
	protected transient DisabilityStatusService disabilityStatusService;
	@Autowired
	protected transient ReferralSourceService referralSourceService;

	/**
	 * {@link #testGetCaseloadWithFilters()}, 
	 * Test to make sure all the filters are implemented properly.
	 */
	@Test
	public void testGetEarlyAlertCaseCountsReportWtihFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		controller.getEarlyAlertCaseCountsReport(response, 
				CampusFixture.TEST.id(),null, 
				Lists.newArrayList(TermFixture.FALL_2012.code()), 
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(3);
		expectedReportBodyLines.add("TERM,TOTAL STUDENTS,TOTAL CASES,TOTAL RESPONDED ,,TOTAL CLOSED,,");
		expectedReportBodyLines.add("FA12,6,16,9,(56.2)%,,2,(12.5)%");
		expectedReportBodyLines.add(",6,16,9,(56.2)%,,2,(12.5)%");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetEarlyAlertCaseCountsReportNoFilters()
			throws IOException, ObjectNotFoundException, JRException {

		final Person dennisRitchie =
				personService.get(Stubs.PersonFixture.DMR.id());
		final Person kevinSmith =
				personService.get(Stubs.PersonFixture.KEVIN_SMITH.id());
		dennisRitchie.setCoach(kevinSmith);
		personService.save(dennisRitchie);
		sessionFactory.getCurrentSession().flush();

		final MockHttpServletResponse response = new MockHttpServletResponse();
	
		controller.getEarlyAlertCaseCountsReport(response, 
				null, 
				null,
				null, 
				"csv");;
		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// same as in testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet(), but
		// Dennis Ritchie is missing
		expectedReportBodyLines.add("TERM,TOTAL STUDENTS,TOTAL CASES,TOTAL RESPONDED ,,TOTAL CLOSED,,");
		expectedReportBodyLines.add( "All,6,16,9,(56.2)%,,2,(12.5)%");
		expectedReportBodyLines.add(",6,16,9,(56.2)%,,2,(12.5)%");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}


	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Early Alert Case Counts Report");
	}
}
