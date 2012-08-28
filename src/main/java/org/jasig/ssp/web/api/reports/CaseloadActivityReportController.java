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
 * Service methods for Reporting on CaseLoad
 * <p>
 * Mapped to URI path <code>/1/report/Caseload</code>
 */

@Controller
@RequestMapping("/1/report/caseloadactivity")
public class CaseloadActivityReportController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadActivityReportController.class);


	@Autowired
	private transient PersonService personService;
	@Autowired
	protected transient SecurityService securityService;
	@Autowired
	protected transient ProgramStatusService programStatusService;
	@Autowired
	protected transient CaseloadService caseLoadService;
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
		
		final PagingWrapper<Person>  coachesWrapper = personService.getAllCoaches(null);
		
		Collection<Person> coaches = coachesWrapper.getRows();
		

		List<CaseLoadActivityReportTO> caseLoadActivityReportList = new ArrayList<CaseLoadActivityReportTO>();
				
	//	final ProgramStatus ps_a = programStatusService.get(UUID.fromString(activeUid));
	//	final ProgramStatus ps_ia = programStatusService.get(UUID.fromString(inactiveUid));
	//	final ProgramStatus ps_np = programStatusService.get(UUID.fromString(nonParUid));
	//	final ProgramStatus ps_t = programStatusService.get(UUID.fromString(transitionedUid));
	//	final ProgramStatus ps_ns = programStatusService.get(UUID.fromString(noShowUid));

		Iterator<Person> personIter = coaches.iterator();
		while(personIter.hasNext())
		{			
			Person currPerson = personIter.next(); 
			Long journalEntriesCount = journalEntryService.getCountForCoach(currPerson);
			
			//TODO: This is not right
			Long studentJournalEntriesCount = journalEntryService.getStudentCountForCoach(currPerson);
			
			Long actionPlanTasksCount = taskService.getTaskCountForCoach(currPerson);
			
			//TODO: do the actionPlanStudent Report
			Long studentTaskCountForCoach = taskService.getStudentTaskCountForCoach(currPerson);// = taskService.getStudentCountForCoach(currPerson);
			Long earlyAlertsCount = earlyAlertService.getEarlyAlertCountForCoach(currPerson);
			Long earlyAlertsResponded = earlyAlertResponseService.getEarlyAlertResponseCountForCoach(currPerson);
			Long studentsEarlyAlertsCount = earlyAlertService.getStudentEarlyAlertCountForCoach(currPerson);
			

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
		
		
		// Get the actual names of the UUIDs for the referralSources
		final List<String> studentTypeNames = new ArrayList<String>();
		if ((studentTypeIds != null) && (studentTypeIds.size() > 0)) {
			final Iterator<UUID> studentTypeIdsIter = studentTypeIds
					.iterator();
			while (studentTypeIdsIter.hasNext()) {
				studentTypeNames.add(studentTypeService.get(
						studentTypeIdsIter.next()).getName());
			}
		}

		
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("statusDateFrom", caDateFrom);
		parameters.put("statusDateTo", caDateTo);
		parameters.put("homeDepartment", ""); //not available yet
		parameters.put("studentTypes", ((studentTypeNames == null || studentTypeNames.isEmpty()) ? "" :  studentTypeNames.toString()));
		
				
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
					"attachment; filename=CaseLoadReport.pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader(
					"Content-disposition",
					"attachment; filename=CaseLoadReport.csv");

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