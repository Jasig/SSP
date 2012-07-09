package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.reports.StudentHistoryTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */

// TODO: Add PreAuthorize
@Controller
@RequestMapping("/1/report/{personId}/History")
public class PersonHistoryReportController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonHistoryReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient JournalEntryService journalEntryService;
	@Autowired
	private transient JournalEntryTOFactory journalEntryTOFactory;
	@Autowired
	private transient EarlyAlertService earlyAlertService;
	@Autowired
	private transient EarlyAlertTOFactory earlyAlertTOFactory;
	@Autowired
	private transient TaskService taskService;
	@Autowired
	private transient TaskTOFactory taskTOFactory;
	@Autowired
	protected transient SecurityService securityService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	void getAddressLabels(
			final HttpServletResponse response,
			final @PathVariable UUID personId,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		Person person = personService.get(personId);
		PersonTO personTO = personTOFactory.from(person);
		SspUser requestor = securityService.currentUser();

		// get all the journal entries for this person
		PagingWrapper<JournalEntry> journalEntrys = journalEntryService
				.getAllForPerson(person, requestor, null);
		List<JournalEntryTO> journalEntryTOs = journalEntryTOFactory
				.asTOList(journalEntrys.getRows());

		// get all the early alerts for this person
		PagingWrapper<EarlyAlert> earlyAlert = earlyAlertService
				.getAllForPerson(person, null);
		List<EarlyAlertTO> earlyAlertTOs = earlyAlertTOFactory
				.asTOList(earlyAlert.getRows());

		// get all the tasks for this person
		Map<String, List<Task>> taskMap = taskService.getAllGroupedByTaskGroup(
				person, requestor, null);
		Map<String, List<TaskTO>> taskTOMap = new HashMap<String, List<TaskTO>>();

		// change all tasks to TaskTOs
		for (Map.Entry<String, List<Task>> entry : taskMap.entrySet()) {
			String groupName = entry.getKey();
			List<Task> tasks = entry.getValue();
			taskTOMap.put(groupName, taskTOFactory.asTOList(tasks));
		}

		// separate the Students into bands by date
		List<StudentHistoryTO> studentHistoryTOs = SORT(earlyAlertTOs,
				taskTOMap, journalEntryTOs);

		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("reportDate", new Date());
		parameters.put("studentTO", personTO);

		final JRBeanArrayDataSource beanDs = new JRBeanArrayDataSource(
				studentHistoryTOs.toArray());

		final InputStream is = getClass().getResourceAsStream(
				"/reports/studentHistoryMaster.jasper");
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDs);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if (reportType.equals("pdf")) {
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if (reportType.equals("csv")) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment; filename=test.csv");

			JRCsvExporter exporter = new JRCsvExporter();
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

	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy/MM/dd");

	public static List<StudentHistoryTO> SORT(List<EarlyAlertTO> earlyAlerts,
			Map<String, List<TaskTO>> taskMap,
			List<JournalEntryTO> journalEntries) {

		final Map<String, StudentHistoryTO> studentHistoryMap = Maps
				.newHashMap();

		// first, iterate over each EarlyAlertTO, looking for matching dates int
		// eh PersonHistoryTO
		Iterator<EarlyAlertTO> alertIter = earlyAlerts.iterator();
		while (alertIter.hasNext()) {
			EarlyAlertTO thisEarlyAlertTO = alertIter.next();
			String snewDate = dateFormatter.format(thisEarlyAlertTO
					.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);
			} else {
				StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		Iterator<JournalEntryTO> journalEntryIter = journalEntries.iterator();
		while (journalEntryIter.hasNext()) {
			JournalEntryTO thisJournalEntryTO = journalEntryIter.next();
			String snewDate = dateFormatter.format(thisJournalEntryTO
					.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
			} else {
				StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		// Per the API, the tasks are already broken down into a map, sorted by
		// group.
		// we want to maintain this grouping, but sort these guys based date
		for (Map.Entry<String, List<TaskTO>> entry : taskMap.entrySet()) {
			String groupName = entry.getKey();
			List<TaskTO> tasks = entry.getValue();

			Iterator<TaskTO> taskIter = tasks.iterator();
			while (taskIter.hasNext()) {
				TaskTO thisTask = taskIter.next();
				String snewDate = dateFormatter.format(thisTask
						.getCreatedDate());
				if (studentHistoryMap.containsKey(snewDate)) {
					StudentHistoryTO studentHistoryTO = studentHistoryMap
							.get(snewDate);
					studentHistoryTO.addTask(groupName, thisTask);
				} else {
					StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
							snewDate);
					thisStudentHistoryTO.addTask(groupName, thisTask);
					studentHistoryMap.put(snewDate, thisStudentHistoryTO);
				}
			}
		}

		// at this point, we should have a StudentHistoryTO map with Dates
		Collection<StudentHistoryTO> studentHistoryTOs = studentHistoryMap
				.values();

		List<StudentHistoryTO> retVal = new ArrayList<StudentHistoryTO>();
		Iterator<StudentHistoryTO> studentHistoryTOIter = studentHistoryTOs
				.iterator();
		while (studentHistoryTOIter.hasNext()) {
			StudentHistoryTO currentStudentHistoryTO = studentHistoryTOIter
					.next();
			currentStudentHistoryTO.createTaskList();
			retVal.add(currentStudentHistoryTO);
		}

		return retVal;
	}

}