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
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.StudentTypeService;
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
@RequestMapping("/1/report/Caseload")
public class CaseloadReportController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadReportController.class);

	final String activeUid = "b2d12527-5056-a51a-8054-113116baab88";
	final String inactiveUid = "b2d125a4-5056-a51a-8042-d50b8eff0df1";
	final String nonParUid = "b2d125c3-5056-a51a-8004-f1dbabde80c2";
	final String transitionedUid = "b2d125e3-5056-a51a-800f-6891bc7d1ddc";
	final String noShowUid = "b2d12640-5056-a51a-80cc-91264965731a";	
	
	

	
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
	void getCaseLoad(
			final HttpServletResponse response,		
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) Date programStatusDateFrom,
			final @RequestParam(required = false) Date programStatusDateTo,			
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		final PagingWrapper<Person>  coachesWrapper = personService.getAllCoaches(null);
		
		
		Collection<Person> coaches = coachesWrapper.getRows();
		
		LOGGER.debug("There are this many coaches: " + coaches.size());
		
		List<CaseLoadReportTO> caseLoadReportList = new ArrayList<CaseLoadReportTO>();
				
		final ProgramStatus ps_a = programStatusService.get(UUID.fromString(activeUid));
		final ProgramStatus ps_ia = programStatusService.get(UUID.fromString(inactiveUid));
		final ProgramStatus ps_np = programStatusService.get(UUID.fromString(nonParUid));
		final ProgramStatus ps_t = programStatusService.get(UUID.fromString(transitionedUid));
		final ProgramStatus ps_ns = programStatusService.get(UUID.fromString(noShowUid));

		Iterator<Person> personIter = coaches.iterator();
		while(personIter.hasNext())
		{			
			Person temp = personIter.next();
			Long activeCount = caseLoadService.caseLoadCountFor(ps_a,temp, studentTypeIds, programStatusDateFrom, programStatusDateTo);
			Long inActiveCount = caseLoadService.caseLoadCountFor(ps_ia,temp, studentTypeIds, programStatusDateFrom, programStatusDateTo);
			Long npCount = caseLoadService.caseLoadCountFor(ps_np,temp, studentTypeIds, programStatusDateFrom, programStatusDateTo);
			Long transitionedCount = caseLoadService.caseLoadCountFor(ps_t,temp, studentTypeIds, programStatusDateFrom, programStatusDateTo);
			Long noShowCount = caseLoadService.caseLoadCountFor(ps_ns,temp, studentTypeIds, programStatusDateFrom, programStatusDateTo);	
			CaseLoadReportTO caseLoadReportTO = new CaseLoadReportTO(temp.getFirstName(), temp.getLastName(), temp.getStaffDetails().getDepartmentName(),
					activeCount,
					inActiveCount,
					npCount,
					transitionedCount,
					noShowCount);
			
			caseLoadReportList.add(caseLoadReportTO);
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
		parameters.put("statusDateFrom", programStatusDateFrom);
		parameters.put("statusDateTo", programStatusDateTo);
		parameters.put("homeDepartment", ""); //not available yet
		parameters.put("studentTypes", ((studentTypeNames == null || studentTypeNames.isEmpty()) ? "" :  studentTypeNames.toString()));
		
				
		final JRDataSource beanDs;
		if(caseLoadReportList.isEmpty())
		{
			beanDs = new JREmptyDataSource();
		}
		else{
			beanDs = new JRBeanCollectionDataSource(caseLoadReportList);
		}		
		
		final InputStream is = getClass().getResourceAsStream(
				"/reports/caseLoad.jasper");
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