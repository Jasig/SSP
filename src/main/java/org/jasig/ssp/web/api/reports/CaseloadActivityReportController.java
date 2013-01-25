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
import java.util.Arrays;
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
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.CaseLoadActivityReportTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
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
	protected transient TermService termService;
	
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
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getCaseLoadActivity(
			final HttpServletResponse response,
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		// populate coaches to search for
		
		final DateTerm dateTerm =  new DateTerm(createDateFrom,  createDateTo, termCode, termService);

		List<Person> coaches;
		if (coachId != null) {
			Person coach = personService.get(coachId);
			coaches = new ArrayList<Person>();
			coaches.add(coach);
		} else {
			coaches = new ArrayList<Person>(personService
					.getAllCurrentCoaches(Person.PERSON_NAME_AND_ID_COMPARATOR));
		}

		List<CaseLoadActivityReportTO> caseLoadActivityReportList = new ArrayList<CaseLoadActivityReportTO>();
		
		final PagingWrapper<EntityStudentCountByCoachTO> journalCounts = journalEntryService.getStudentJournalCountForCoaches(
				coaches, 
				dateTerm.getStartDate(), 
				dateTerm.getEndDate(), 
				studentTypeIds, 
				null);
		
		final PagingWrapper<EntityStudentCountByCoachTO> taskCounts = taskService.getStudentTaskCountForCoaches(
				coaches, 
				dateTerm.getStartDate(), 
				dateTerm.getEndDate(), 
				studentTypeIds, 
				null);
		
		final PagingWrapper<EntityStudentCountByCoachTO> earlyAlertCounts = earlyAlertService.getStudentEarlyAlertCountByCoaches(
				coaches, 
				dateTerm.getStartDate(), 
				dateTerm.getEndDate(), 
				studentTypeIds, 
				null);
		
		final PagingWrapper<EntityStudentCountByCoachTO> earlyAlertResponseCounts = earlyAlertResponseService.getStudentEarlyAlertResponseCountByCoaches(
				coaches, 
				dateTerm.getStartDate(), 
				dateTerm.getEndDate(), 
				studentTypeIds, 
				null);
		
		
		 Map<UUID, EntityStudentCountByCoachTO> indexedJournals = getIndexedByCoaches(journalCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedTasks = getIndexedByCoaches(taskCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedEarlyAlerts = getIndexedByCoaches(earlyAlertCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedEarlyAlertResponses = getIndexedByCoaches(earlyAlertResponseCounts.getRows());
		 

		for (Person coach:coaches) {	
			UUID sspUserId = coach.getId();
			EntityStudentCountByCoachTO studentsJournal = getEntityStudentCountByCoachTO(indexedJournals,  sspUserId);
			EntityStudentCountByCoachTO studentsTask = getEntityStudentCountByCoachTO(indexedTasks,  sspUserId);
			EntityStudentCountByCoachTO studentsEarlyAlerts = getEntityStudentCountByCoachTO(indexedEarlyAlerts,  sspUserId);
			EntityStudentCountByCoachTO studentsEarlyAlertResponses = getEntityStudentCountByCoachTO(indexedEarlyAlertResponses,  sspUserId);
	
			CaseLoadActivityReportTO caseLoadActivityReportTO = new CaseLoadActivityReportTO(
					coach.getFirstName(), coach.getLastName(),
					getEntityCount(studentsJournal), getStudentCount(studentsJournal),
					getEntityCount(studentsTask), getStudentCount(studentsTask),
					getEntityCount(studentsEarlyAlerts), getStudentCount(studentsEarlyAlerts),
					getEntityCount(studentsEarlyAlertResponses));

			caseLoadActivityReportList.add(caseLoadActivityReportTO);
		}

		// Get the actual names of the UUIDs for the special groups
		final StringBuffer studentTypeStringBuffer = new StringBuffer();
		if ((studentTypeIds != null) && (studentTypeIds.size() > 0)) {
			final Iterator<UUID> stIter = studentTypeIds.iterator();
			while (stIter.hasNext()) {
				studentTypeStringBuffer.append("\u2022 "
						+ studentTypeService.get(stIter.next()).getName());
				studentTypeStringBuffer.append("    ");
			}
		}

		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("statusDateFrom", dateTerm.getStartDate());
		parameters.put("statusDateTo", dateTerm.getEndDate());
		parameters.put("termCode", dateTerm.getTermCode());
		parameters.put("termName", dateTerm.getTermName());
		parameters.put("homeDepartment", ""); // not available yet
		parameters.put("studentType", studentTypeStringBuffer.toString());

		final JRDataSource beanDs;
		if (caseLoadActivityReportList.isEmpty()) {
			beanDs = new JREmptyDataSource();
		} else {
			beanDs = new JRBeanCollectionDataSource(caseLoadActivityReportList);
		}

		final InputStream is = getClass().getResourceAsStream(
				"/reports/caseLoadActivity.jasper");
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDs);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if ("pdf".equals(reportType)) {
			response.setHeader("Content-disposition",
					"attachment; filename=CaseLoadActivityReport.pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
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
	
	private  Map<UUID, EntityStudentCountByCoachTO>  getIndexedByCoaches(Collection<EntityStudentCountByCoachTO> entities){
			return EntityStudentCountByCoachTO.getIndexedListByCoach(new ArrayList<EntityStudentCountByCoachTO>(entities));
	}
	
	private EntityStudentCountByCoachTO getEntityStudentCountByCoachTO(Map<UUID, EntityStudentCountByCoachTO> entities, UUID coachId){
		if(coachId != null && entities.containsKey(coachId))
			return entities.get(coachId);
		return null;
	}
	
	private Long getStudentCount(EntityStudentCountByCoachTO countObject){
		return countObject == null ?  0L:countObject.getStudentCount();
	}
	
	private Long getEntityCount(EntityStudentCountByCoachTO countObject){
		return countObject == null ?  0L:countObject.getEntityCount();
	}

}