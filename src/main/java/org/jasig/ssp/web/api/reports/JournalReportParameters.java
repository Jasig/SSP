/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reports;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JournalReportParameters {
	
	private static String JOURNAL_SESSION_DETAILS = "journalSessionDetails";
	private static String JOURNAL_SOURCES = "journalSourceNames";

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient SpecialServiceGroupService ssgService;
	@Autowired
	private transient ReferralSourceService referralSourcesService;
	@Autowired
	private transient TermService termService;
	@Autowired
	private transient ProgramStatusService programStatusService;
	@Autowired
	protected transient StudentTypeService studentTypeService;	
	@Autowired
	protected transient ServiceReasonService serviceReasonService;	
	
	@Autowired
	protected transient JournalStepDetailService journalEntryStepDetailService;

	@Autowired
	protected transient JournalSourceService journalSourceService;
	
	@Autowired
	protected transient JournalEntryService journalEntryService;	
	
	protected void setParameters(ObjectStatus status,
			Map<String, Object> parameters, 
			JournalStepSearchFormTO personSearchForm,
			final UUID coachId,
			final UUID programStatus,	
			final Boolean hasStepDetails,
			final List<UUID> specialServiceGroupIds,
			final List<UUID> studentTypeIds,
			final List<UUID> serviceReasonIds,
			final List<UUID> journalStepDetailIds,
			final List<UUID> journalSourceIds,
			final Date createJounalEntryDateFrom,
			final Date createJournalEntryDateTo,
			final String termCode,
			final String homeDepartment) throws ObjectNotFoundException{
		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);
		
		SearchParameters.addReferenceLists(studentTypeIds,
				null,
				specialServiceGroupIds, 
				null,
				serviceReasonIds,
				parameters, 
				personSearchForm, 
				studentTypeService,
				null,
				ssgService, 
				referralSourcesService,
				serviceReasonService);
		
		addJournalEntryDateRange(createJounalEntryDateFrom, 
				createJournalEntryDateTo, 
				termCode, 
				parameters, 
				personSearchForm, 
				termService);
		
		SearchParameters.addReferenceTypes(programStatus,
				null, 
				false,
				null,
				homeDepartment,
				parameters, 
				personSearchForm, 
				programStatusService, 
				null);

		List<UUID> cleanJournalSourceIds = SearchParameters.cleanUUIDListOfNulls(journalSourceIds);
		SearchParameters.addUUIDSToMap(JOURNAL_SOURCES,
				SearchParameters.NOT_USED,
				cleanJournalSourceIds,
				parameters,
				journalSourceService);
		personSearchForm.setJournalSourceIds(cleanJournalSourceIds);

		List<UUID> cleanJournalStepDetailIds = SearchParameters.cleanUUIDListOfNulls(journalStepDetailIds);
		SearchParameters.addUUIDSToMap(JOURNAL_SESSION_DETAILS,
				SearchParameters.ALL, 
				cleanJournalStepDetailIds,
				parameters,
				journalEntryStepDetailService);
		
		if((cleanJournalStepDetailIds == null || cleanJournalStepDetailIds.isEmpty())){
			cleanJournalStepDetailIds = new ArrayList<UUID>();
			PagingWrapper<JournalStepDetail> details = journalEntryStepDetailService.getAll(SortingAndPaging.createForSingleSortAll(status, "sortOrder", "ASC"));
			for(JournalStepDetail detail:details){
				cleanJournalStepDetailIds.add(detail.getId());
			}
		}
		personSearchForm.setJournalStepDetailIds(cleanJournalStepDetailIds);

		personSearchForm.setHasStepDetails(hasStepDetails);
	}
	
	static final void addJournalEntryDateRange(final Date createDateFrom,
			final Date createDateTo,
			final String termCode,
			final Map<String, Object> parameters,
			final JournalStepSearchFormTO personSearchForm,
			final TermService termService
			
			) throws ObjectNotFoundException{
		DateTerm dateTerm = new DateTerm(createDateFrom, createDateTo, termCode, termService);
		SearchParameters.addDateTermToMap(dateTerm, parameters);
		
		personSearchForm.setJournalCreateDateFrom(dateTerm.getStartDate());
		personSearchForm.setJournalCreateDateTo(dateTerm.getEndDate());
	}

}
