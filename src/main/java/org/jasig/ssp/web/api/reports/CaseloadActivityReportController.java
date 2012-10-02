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
package org.jasig.ssp.web.api.reports; // NOPMD

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.CaseLoadActivityReportTO;
import org.jasig.ssp.transferobject.reports.CaseLoadReportTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Service methods for Reporting on Caseload Activity
 * <p>
 * Mapped to URI path <code>/1/report/caseloadactivity</code>
 */

@Controller
@RequestMapping("/1/report/caseloadactivity")
public class CaseloadActivityReportController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadActivityReportController.class);


	@Autowired
	private transient PersonService personService;
	@Autowired
	protected transient StudentTypeService studentTypeService;	
	@Autowired
	protected transient JournalEntryService journalEntryService;
	@Autowired
	protected transient TaskService taskService;
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;

	
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	void getCaseLoadActivity(
			final HttpServletResponse response,		
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) Date caDateFrom,
			final @RequestParam(required = false) Date caDateTo,			
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		
		//populate coaches to search for
		Collection<Person> coaches;		
		if (coachId != null){		
			Person coach = personService.get(coachId);
			coaches = new ArrayList<Person>();
			coaches.add(coach);
		}
		else{
			final PagingWrapper<Person>  coachesWrapper = personService.getAllCoaches(null);			
			coaches = coachesWrapper.getRows();
		}
		
						
		List<CaseLoadActivityReportTO> caseLoadActivityReportList = new ArrayList<CaseLoadActivityReportTO>();
				
		Iterator<Person> personIter = coaches.iterator();
		while(personIter.hasNext())
		{			
			Person currPerson = personIter.next(); 
			
			Long journalEntriesCount = journalEntryService.getCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);			
			Long studentJournalEntriesCount = journalEntryService.getStudentCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);			
			Long actionPlanTasksCount = taskService.getTaskCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);			
			Long studentTaskCountForCoach = taskService.getStudentTaskCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);
			Long earlyAlertsCount = earlyAlertService.getEarlyAlertCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);
			Long earlyAlertsResponded = earlyAlertResponseService.getEarlyAlertResponseCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);
			Long studentsEarlyAlertsCount = earlyAlertService.getStudentEarlyAlertCountForCoach(currPerson, caDateFrom, caDateTo, studentTypeIds);
			

			CaseLoadActivityReportTO caseLoadActivityReportTO = new CaseLoadActivityReportTO(
					currPerson.getFirstName(), 
					currPerson.getLastName(), 
					journalEntriesCount, 
					studentJournalEntriesCount,
					actionPlanTasksCount, 
					studentTaskCountForCoach, 
					earlyAlertsCount, 
					studentsEarlyAlertsCount, 
					earlyAlertsResponded);
			
			caseLoadActivityReportList.add(caseLoadActivityReportTO);
		}			
		
		
		// Get the actual names of the UUIDs for the special groups
		final StringBuffer studentTypeStringBuffer = new StringBuffer();
		if ((studentTypeIds != null)
				&& (studentTypeIds.size() > 0)) {
			final Iterator<UUID> stIter = studentTypeIds.iterator();
			while (stIter.hasNext()) {
				studentTypeStringBuffer.append("\u2022 " + studentTypeService.get(stIter.next()).getName());
				studentTypeStringBuffer.append("    ");																			
			}			
		}

		
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("statusDateFrom", caDateFrom);
		parameters.put("statusDateTo", caDateTo);
		parameters.put("homeDepartment", ""); //not available yet
		parameters.put("studentType", studentTypeStringBuffer.toString());
		
				
		final JRDataSource beanDs;
		if(caseLoadActivityReportList.isEmpty())
		{
			beanDs = new JREmptyDataSource();
		}
		else{
			beanDs = new JRBeanCollectionDataSource(caseLoadActivityReportList);
		}		
		
		final InputStream is = getClass().getResourceAsStream(
				"/reports/caseLoadActivity.jasper");
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDs);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if ("pdf".equals(reportType)) {
			response.setHeader(
					"Content-disposition",
					"attachment; filename=CaseLoadActivityReport.pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader(
					"Content-disposition",
					"attachment; filename=CaseLoadActivityReport.csv");

			final JRCsvExporter exporter = new JRCsvExporter();
			exporter.setParameter(JRExporterParameter.INPUT_STREAM,
					decodedInput);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);

			exporter.exportReport();
		}
		
		response.flushBuffer();
		is.close();
		os.close();		
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}


	
}