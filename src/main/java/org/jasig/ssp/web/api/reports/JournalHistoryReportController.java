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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.reference.BlurbTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.BlurbService;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.reports.PersonReportTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/1/person")
public class JournalHistoryReportController extends ReportBaseController<Map<String, Collection<JournalEntryTO>>>{
	
	private static final String REPORT_URL = "/reports/journalHistoryReport.jasper";
	private static final String REPORT_FILE_TITLE = "Journal_History_Report-";
	
	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient JournalEntryService journalEntryService;
	
	@Autowired
	private transient JournalEntryTOFactory journalEntryTOFactory;
	
	@Autowired
	protected transient SecurityService securityService;
	
	@Autowired
	private BlurbService blurbService;
	@Autowired
	private BlurbTOFactory blurbTOFactory;
	
	@RequestMapping(value = "/{personId}/journalhistory/print", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	void getJournalDetailHistoryReport(
			final HttpServletResponse response,
			final @PathVariable UUID personId,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		Person person = personService.get(personId);
		final PersonReportTO personTO = new PersonReportTO(person);
		SortingAndPaging sAndP = SortingAndPaging.allActiveSorted("createdDate");
		SspUser requestor = securityService.currentUser();
		PagingWrapper<JournalEntry> journalEntries = journalEntryService.getAllForPerson(person, requestor, sAndP);
		
		final List<JournalEntryTO> journalEntryTOs = journalEntryTOFactory
				.asTOList(journalEntries.getRows());
		
		//Sort journal entries by modified date descending
        final List journalEntriesSorted = journalEntryTOs;
        Collections.sort(journalEntriesSorted, new Comparator<JournalEntryTO>() {
            @Override
            public int compare (final JournalEntryTO o1, final JournalEntryTO o2) {
                return PersonHistoryReportController.sortDateDescending(o1.getModifiedDate(), o2.getModifiedDate());
            }
        });
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final HashMap<String, String> sspLabels = PersonHistoryReportController.transferBlurbsToMap(blurbService.getAll( SortingAndPaging.allActiveSorted(null), PersonHistoryReportController.SSP_LABEL_NAMES_BLURB_QUERY));
		parameters.put("sspLabels", sspLabels);
		parameters.put("studentTO", personTO);
		Map<String, Collection<JournalEntryTO>> entries = new HashMap<String, Collection<JournalEntryTO>>();
		entries.put("journalEntries", journalEntriesSorted);
		SearchParameters.addReportDateToMap(parameters);
		ArrayList<Map<String, Collection<JournalEntryTO>>> entriesList = new ArrayList<Map<String, Collection<JournalEntryTO>>>();
		entriesList.add(entries);
		renderReport(response, parameters, entriesList, REPORT_URL, reportType,
				REPORT_FILE_TITLE + person.getLastName());
	
	}

}
