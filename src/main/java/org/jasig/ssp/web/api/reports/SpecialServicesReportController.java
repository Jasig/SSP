package org.jasig.ssp.web.api.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
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

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */

// TODO: Add PreAuthorize
@Controller
@RequestMapping("/1/report/SpecialServices")
public class SpecialServicesReportController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServicesReportController.class);

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient PersonTOFactory factory;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	void getSpecialServices(
			HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIDs,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException, JRException, IOException {

		// List<String> specialServiceGroupIDs;

		final List<Person> people = service.peopleFromSpecialServiceGroups(
				specialServiceGroupIDs,// addressLabelSearchTO,
				SortingAndPaging
						.createForSingleSort(status, start, limit, sort,
								sortDirection,
								null));

		// LOGGER.debug("Student TypeID: " + 
		// addressLabelSearchTO.getStudentTypeId());

		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Special Service Groups Report");
		parameters.put("DataFile", "Person.java - Bean Array");

		JRBeanArrayDataSource beanDs = new JRBeanArrayDataSource(
				people.toArray());
		InputStream is = getClass().getResourceAsStream(
				"/reports/specialServiceGroups.jasper");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDs);
		InputStream decodedInput = new ByteArrayInputStream(os.toByteArray());
		JasperExportManager.exportReportToPdfStream(decodedInput,
				response.getOutputStream());
		response.flushBuffer();
		is.close();
		os.close();
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

}