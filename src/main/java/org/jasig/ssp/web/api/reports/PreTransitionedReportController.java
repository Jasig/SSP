package org.jasig.ssp.web.api.reports; // NOPMD

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.transferobject.reports.PersonReportTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>report/AddressLabels</code>
 */

// TODO: Add PreAuthorize
@Controller
@RequestMapping("/1/report/pretransitioned")
public class PreTransitionedReportController extends AbstractBaseController { // NOPMD

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PreTransitionedReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient SpecialServiceGroupService ssgService;
	@Autowired
	private transient ReferralSourceService referralSourcesService;
	@Autowired
	private transient ProgramStatusService programStatusService;
	@Autowired
	private transient StudentTypeService studentTypeService;

	// @Autowired
	// private transient PersonTOFactory factory;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void getPreTransitioned(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> referralSourcesIds,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) Date createDateFromCounselor,
			final @RequestParam(required = false) Date createDateToCounselor,
			final @RequestParam(required = false) Integer anticipatedStartYear,
			final @RequestParam(required = false) String anticipatedStartTerm,			
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		
		Person coach = null;
		PersonTO coachTO = null;
		if(coachId != null)
		{
			coach = personService.get(coachId);
			coachTO = personTOFactory.from(coach);
		}
		
		
		final AddressLabelSearchTO searchForm = new AddressLabelSearchTO(coachTO,
				programStatus, specialServiceGroupIds, referralSourcesIds,
				anticipatedStartTerm.length() == 0 ? null
						: anticipatedStartTerm, anticipatedStartYear,
				studentTypeIds, createDateFromCounselor, createDateToCounselor);

		final List<Person> people = personService.peopleFromCriteria(
				searchForm, SortingAndPaging.createForSingleSort(status, null,
						null, null, null, null));
		final List<PersonReportTO> peopleReportTOList = PersonReportTO.toPersonTOList(people);


		// Get the actual names of the UUIDs for the special groups
		final StringBuffer specialGroupsNamesStringBuffer = new StringBuffer();
		if ((specialServiceGroupIds != null)
				&& (specialServiceGroupIds.size() > 0)) {
			final Iterator<UUID> ssgIter = specialServiceGroupIds.iterator();
			while (ssgIter.hasNext()) {
				specialGroupsNamesStringBuffer.append("\u2022 " + ssgService.get(ssgIter.next()).getName());
				specialGroupsNamesStringBuffer.append("    ");																			
			}
			
			
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

		// Get the actual names of the UUIDs for the referralSources
		final StringBuffer referralSourcesNameStringBuffer = new StringBuffer();
		if ((referralSourcesIds != null) && (referralSourcesIds.size() > 0)) {
			final Iterator<UUID> referralSourceIter = referralSourcesIds
					.iterator();
			while (referralSourceIter.hasNext()) {
				referralSourcesNameStringBuffer.append("\u2022 " + referralSourcesService.get(
						referralSourceIter.next()).getName());
				
				referralSourcesNameStringBuffer.append("    ");																		
			}
			
		}
		
	

		// final String programStatusName = ((null!=programStatus &&
		// !programStatus.isEmpty())?programStatus.get(0)():"");
		// Get the actual name of the UUID for the programStatus
		final String programStatusName = (programStatus == null ? ""
				: programStatusService.get(programStatus).getName());

		final Map<String, Object> parameters = Maps.newHashMap();

		parameters.put("reportDate", new Date());
		parameters.put("homeDepartment", null);
		if (coachTO != null){
			parameters.put("coachName", coachTO.getFirstName() + " " + coachTO.getLastName());
		}
		parameters.put("studentType", studentTypeStringBuffer.toString());		
		parameters.put("programStatus", programStatusName);
		parameters.put("cohortTerm", StringUtils.defaultString(anticipatedStartTerm) + " " + (anticipatedStartYear == null?"" : anticipatedStartYear.toString()));
		parameters.put("studentCount", peopleReportTOList == null ? 0 : peopleReportTOList.size());
		parameters.put("specialServiceGroupNames", specialGroupsNamesStringBuffer.toString());
		parameters.put("referralSourceNames", referralSourcesNameStringBuffer.toString());

	

		JRDataSource beanDS;
		if (peopleReportTOList == null || peopleReportTOList.size() <= 0) {
			beanDS = new JREmptyDataSource();
		} else {
			beanDS = new JRBeanCollectionDataSource(peopleReportTOList);
		}

		final InputStream is = getClass().getResourceAsStream(
				"/reports/preTransitioned.jasper");
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDS);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if ("pdf".equals(reportType)) {
			response.setHeader("Content-disposition",
					"attachment; filename=Counselor_Case_Management_Report.pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment; filename=Counselor_Case_Management_Report.csv");

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