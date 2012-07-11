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
import java.util.Locale;
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

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	void getAddressLabels(
			final HttpServletResponse response,
			final @PathVariable UUID personId,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		final Person person = personService.get(personId);
		final PersonTO personTO = personTOFactory.from(person);
		final SspUser requestor = securityService.currentUser();

		LOGGER.debug("Requester id: " + requestor.getPerson().getId());
		// get all the journal entries for this person
		final PagingWrapper<JournalEntry> journalEntrys = journalEntryService
				.getAllForPerson(person, requestor, null);
		final List<JournalEntryTO> journalEntryTOs = journalEntryTOFactory
				.asTOList(journalEntrys.getRows());
		LOGGER.debug("JournalEntryTOs.size(): " + journalEntryTOs.size());

		// get all the early alerts for this person
		final PagingWrapper<EarlyAlert> earlyAlert = earlyAlertService
				.getAllForPerson(person, null);
		final List<EarlyAlertTO> earlyAlertTOs = earlyAlertTOFactory
				.asTOList(earlyAlert.getRows());
		LOGGER.debug("EarlyAlertTOs.size(): " + earlyAlertTOs.size());

		// get all the tasks for this person
		final Map<String, List<Task>> taskMap = taskService
				.getAllGroupedByTaskGroup(
						person, requestor, null);
		final Map<String, List<TaskTO>> taskTOMap = new HashMap<String, List<TaskTO>>();
		LOGGER.debug("taskTOMap.size(): " + taskMap.size());

		// change all tasks to TaskTOs
		for (final Map.Entry<String, List<Task>> entry : taskMap.entrySet()) {
			final String groupName = entry.getKey();
			final List<Task> tasks = entry.getValue();
			taskTOMap.put(groupName, taskTOFactory.asTOList(tasks));
		}

		// separate the Students into bands by date
		final List<StudentHistoryTO> studentHistoryTOs = sort(earlyAlertTOs,
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

	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"yyyy/MM/dd", Locale.US);

	public static List<StudentHistoryTO> sort(
			final List<EarlyAlertTO> earlyAlerts,
			final Map<String, List<TaskTO>> taskMap,
			final List<JournalEntryTO> journalEntries) {

		final Map<String, StudentHistoryTO> studentHistoryMap = Maps
				.newHashMap();

		// first, iterate over each EarlyAlertTO, looking for matching dates int
		// eh PersonHistoryTO
		final Iterator<EarlyAlertTO> alertIter = earlyAlerts.iterator();
		while (alertIter.hasNext()) {
			final EarlyAlertTO thisEarlyAlertTO = alertIter.next();
			final String snewDate = DATE_FORMATTER.format(thisEarlyAlertTO
					.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				final StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);
			} else {
				final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		final Iterator<JournalEntryTO> journalEntryIter = journalEntries
				.iterator();
		while (journalEntryIter.hasNext()) {
			final JournalEntryTO thisJournalEntryTO = journalEntryIter.next();
			final String snewDate = DATE_FORMATTER.format(thisJournalEntryTO
					.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				final StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
			} else {
				final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		// Per the API, the tasks are already broken down into a map, sorted by
		// group.
		// we want to maintain this grouping, but sort these guys based date
		for (final Map.Entry<String, List<TaskTO>> entry : taskMap.entrySet()) {
			final String groupName = entry.getKey();
			final List<TaskTO> tasks = entry.getValue();

			final Iterator<TaskTO> taskIter = tasks.iterator();
			while (taskIter.hasNext()) {
				final TaskTO thisTask = taskIter.next();
				final String snewDate = DATE_FORMATTER.format(thisTask
						.getCreatedDate());
				if (studentHistoryMap.containsKey(snewDate)) {
					final StudentHistoryTO studentHistoryTO = studentHistoryMap
							.get(snewDate);
					studentHistoryTO.addTask(groupName, thisTask);
				} else {
					final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
							snewDate);
					thisStudentHistoryTO.addTask(groupName, thisTask);
					studentHistoryMap.put(snewDate, thisStudentHistoryTO);
				}
			}
		}

		// at this point, we should have a StudentHistoryTO map with Dates
		final Collection<StudentHistoryTO> studentHistoryTOs = studentHistoryMap
				.values();

		final List<StudentHistoryTO> retVal = new ArrayList<StudentHistoryTO>();
		final Iterator<StudentHistoryTO> studentHistoryTOIter = studentHistoryTOs
				.iterator();
		while (studentHistoryTOIter.hasNext()) {
			final StudentHistoryTO currentStudentHistoryTO = studentHistoryTOIter
					.next();
			currentStudentHistoryTO.createTaskList();
			retVal.add(currentStudentHistoryTO);
		}

		return retVal;
	}
}