package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.SpecialServicesReportingTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/report/SpecialServices</code>
 */

// TODO: Add PreAuthorize
@Controller
@RequestMapping("/1/report/SpecialServices")
public class SpecialServicesReportController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServicesReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient SpecialServiceGroupService ssgService;
 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	void getSpecialServices(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		// List<String> specialServiceGroupIDs;

		final List<Person> people = personService
				.peopleFromSpecialServiceGroups(specialServiceGroupIds,
						SortingAndPaging.createForSingleSort(status, null,
								null, null, null, null));

		final List<PersonTO> personTOs = personTOFactory.asTOList(people);

		LOGGER.debug("Number of personTOs: " + personTOs.size());

		// Get the actual names of the UUIDs for the special groups
		List<String> specialGroupsNames = new ArrayList<String>();
		if (specialServiceGroupIds != null && specialServiceGroupIds.size() > 0) {
			Iterator<UUID> ssgIter = specialServiceGroupIds.iterator();
			while (ssgIter.hasNext()) {
				specialGroupsNames
						.add(ssgService.get(ssgIter.next()).getName());
			}
		}

		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("ReportTitle", "Special Service Groups Report");
		parameters.put("DataFile",
				"SpecialServicesReportingTO.java - Bean Array");
		parameters.put("specialServiceGroupNames", specialGroupsNames);
		parameters.put("reportDate", new Date());

		final JRBeanCollectionDataSource beanDs = new JRBeanCollectionDataSource(
				getReportablePersons(people));

		final InputStream is = getClass().getResourceAsStream(
				"/reports/specialServiceGroups.jasper");
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

	List<SpecialServicesReportingTO> getReportablePersons(List<Person> persons) {
		List<SpecialServicesReportingTO> retVal = new ArrayList<SpecialServicesReportingTO>();
		Iterator<Person> personIter = persons.iterator();
		while (personIter.hasNext()) {
			retVal.add(new SpecialServicesReportingTO(personIter.next()));
		}

		return retVal;
	}

}