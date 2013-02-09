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
import org.jasig.ssp.util.sort.PagingWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Predicate;

public class DisabilityServicesReportControllerIntegrationTest
		extends AbstractReportControllerIntegrationTest  {

	@Autowired
	private transient DisabilityServicesReportController controller;
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

	//@Test
	public void testGetDisabilityeServicesReportFilters()
			throws IOException, ObjectNotFoundException, JRException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		final UUID coachId = Stubs.PersonFixture.ADVISOR_0.id();
		final UUID odsCoachId = Stubs.PersonFixture.ADVISOR_0.id();
		
		controller.getDisabilityServicesReport(response, 
				ObjectStatus.ACTIVE, 
				coachId, 
				odsCoachId, 
				getReferences(disabilityStatusService, 1).get(0), 
				null, 
				getReferences(programStatusService, 1).get(0), 
				getReferences(ssgService, 2), 
				getReferences(referralSourceService, 2), 
				getReferences(studentTypeService, 2), 
				null, 
				null, 2013, 
				"FA12", 
				null, 
				null, 
				"FA12",
				"csv");

		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		expectedReportBodyLines.add("FIRST,,,MIDDLE,LAST,,,Student ,TYP,Address,,CITY,ST,PHONE(H),EMAIL(SCHOOL),EMAIL(HOME)");
		expectedReportBodyLines.add("James,,,A,Gosling,,,,ILP,444 West Third Street ,,San Francisco,CA,908-123-4567,test@sinclair.edu,test@sinclair.edu");
		expectedReportBodyLines.add("Kenneth,,,L,Thompson,,,,CAP,444 West Third Street ,,Murray Hill,NJ,908-123-4567,test@sinclair.edu,test@sinclair.edu");
		expectReportBodyLines(expectedReportBodyLines, response, null);
	}

	@Test
	public void testGetDisablityServicesReportNoFilter()
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
		final UUID coachId = Stubs.PersonFixture.ADVISOR_0.id();
		final UUID odsCoachId = Stubs.PersonFixture.ADVISOR_0.id();
		controller.getDisabilityServicesReport(response, 
				ObjectStatus.ACTIVE, 
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
				2013, 
				null, 
				null, 
				null, 
				null,
				"csv");
		// "body" is the actual results and the header that describes its columns.
		// This is as opposed to rows which precede the header, which describe
		// the filtering criteria
		final List<String> expectedReportBodyLines = new ArrayList<String>(4);
		// same as in testGetAddressLabelsReturnsAllStudentsIfNoFiltersSet(), but
		// Dennis Ritchie is missing
		expectedReportBodyLines.add("STUDENT NAME,ID,ILP,DISABILITY,SSP STATUS,ODS STATUS,ODS REASON FOR INELIGIBILTY,ODS REG DATE,INTERPRETER,REG STATUS,MAJOR,VET STATUS,ETHNICITY,ASSIGNMENT DATES\t,AGENCY CONTACTS,SSP COACH");
		expectedReportBodyLines.add("test Mumford coach1student0,coach1student0,,ADD/ADHD,Active,Test Disability ,,,,,Physics,Dependent of ,American ,10/01/2012,MH -Other,test coach1");
		expectedReportBodyLines.add("test Mumford coach1student4,coach1student4,,LD,No-Show,Eligible,,,,,Biology,VEAP,Prefer Not To ,10/01/2012,VA -Test Disability ,test coach1");

		expectReportBodyLines(expectedReportBodyLines, response, null);
	}
	
	private List<UUID> getReferences(ReferenceService referenceService, int count){
		PagingWrapper<AbstractReference> references = referenceService.getAll(null);
		List<UUID> referenceIds = new ArrayList<UUID>();
		Integer index = new Integer(0);
		for(AbstractReference reference:references.getRows()){
			if(count == index++)
				break;
			
			referenceIds.add(reference.getId());
		}
		return referenceIds;
	}

	@Override
	protected Predicate<String> afterHeader() {
		return afterLineContaining("Disability Services Report");
	}

}