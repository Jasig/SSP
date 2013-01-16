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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.CaseLoadReportTO;
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

import com.google.common.collect.Lists;
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

	private static final String DEPARTMENT_PLACEHOLDER = "Not Available Yet";

	private static enum ProgramStatusHelper {

		ACTIVE(ProgramStatus.ACTIVE_ID) {
			@Override
			void addToCount(long add, CaseLoadReportTO target) {
				target.addToActiveCount(add);
			}
		},
		INACTIVE(ProgramStatus.INACTIVE_ID) {
			@Override
			void addToCount(long add, CaseLoadReportTO target) {
				target.addToInActiveCount(add);
			}
		},
		NON_PARTICIPATING(ProgramStatus.NON_PARTICIPATING_ID) {
			@Override
			void addToCount(long add, CaseLoadReportTO target) {
				target.addToNpCount(add);
			}
		},
		TRANSITIONED(ProgramStatus.TRANSITIONED_ID) {
			@Override
			void addToCount(long add, CaseLoadReportTO target) {
				target.addToTransitionedCount(add);
			}
		},
		NO_SHOW(ProgramStatus.NO_SHOW) {
			@Override
			void addToCount(long add, CaseLoadReportTO target) {
				target.addToNoShowCount(add);
			}
		};

		private UUID id;

		ProgramStatusHelper(UUID id) {
			this.id = id;
		}

		UUID id() {
			return id;
		}

		abstract void addToCount(long add, CaseLoadReportTO target);

		static ProgramStatusHelper byId(UUID id) {
			if ( id == null ) {
				return null;
			}
			for ( ProgramStatusHelper helper : ProgramStatusHelper.values() ) {
				if ( id.equals(helper.id()) ) {
					return helper;
				}
			}
			return null;
		}

		static void maybeAddToCount(long add, CaseLoadReportTO target, UUID programStatusId) {
			ProgramStatusHelper helper = ProgramStatusHelper.byId(programStatusId);
			if ( helper == null ) {
				return;
			}
			helper.addToCount(add, target);
		}
	}

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
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getCaseLoad(
			final HttpServletResponse response,		
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		final List<CaseLoadReportTO> caseLoadReportList =
				collectCaseLoadReportTOs(studentTypeIds);

		final Map<String, Object> parameters =
				collectParamsForReport(studentTypeIds);

		renderReport(caseLoadReportList, parameters, reportType, response);
	}

	private List<CaseLoadReportTO> collectCaseLoadReportTOs(
			List<UUID> studentTypeIds) {
		List<CaseLoadReportTO> caseLoadReportList = Lists.newArrayList();

		final Collection<CoachCaseloadRecordCountForProgramStatus> countsByCoachAndStatus =
				caseLoadService.currentCaseloadCountsByStatus(studentTypeIds);
		UUID currentCoachId = null;
		CaseLoadReportTO caseLoadReportTO = null;
		for ( CoachCaseloadRecordCountForProgramStatus countByCoachAndStatus : countsByCoachAndStatus ) {
			if ( currentCoachId == null || !(currentCoachId.equals(countByCoachAndStatus.getCoachId())) ) {
				if ( caseLoadReportTO != null ) {
					caseLoadReportList.add(caseLoadReportTO);
				}
				currentCoachId = countByCoachAndStatus.getCoachId();
				caseLoadReportTO = new CaseLoadReportTO(countByCoachAndStatus.getCoachFirstName(),
						countByCoachAndStatus.getCoachLastName(),
						departmentNameOrDefault(countByCoachAndStatus, DEPARTMENT_PLACEHOLDER));
			}
			ProgramStatusHelper.maybeAddToCount(countByCoachAndStatus.getCount(),
					caseLoadReportTO, countByCoachAndStatus.getProgramStatusId());
		}
		// make sure last one isn't forgotten
		if ( caseLoadReportTO != null ) {
			caseLoadReportList.add(caseLoadReportTO);
		}
		return caseLoadReportList;
	}

	private void renderReport(
			List<CaseLoadReportTO> caseLoadReportList,
			Map<String,Object> parameters,
			String reportType,
			HttpServletResponse response) throws IOException, JRException {

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

	private Map<String, Object> collectParamsForReport(
			List<UUID> studentTypeIds) throws ObjectNotFoundException {
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("homeDepartment", ""); //not available yet
		parameters.put("studentTypes", collectStudentTypeNamesAsString(studentTypeIds));
		return parameters;
	}

	private String collectStudentTypeNamesAsString(List<UUID> studentTypeIds)
			throws ObjectNotFoundException {
		final List<String> studentTypeNames = collectStudentTypeNames(studentTypeIds);
		return (studentTypeNames == null || studentTypeNames.isEmpty())
				? "" : studentTypeNames.toString();
	}

	/**
	 * Get the actual names of the UUIDs for the requested student types.
	 *
	 * @param studentTypeIds
	 * @return
	 * @throws ObjectNotFoundException
	 */
	private List<String> collectStudentTypeNames(List<UUID> studentTypeIds)
			throws ObjectNotFoundException {
		final List<String> studentTypeNames = Lists.newArrayList();
		if ((studentTypeIds != null) && (studentTypeIds.size() > 0)) {
			final Iterator<UUID> studentTypeIdsIter = studentTypeIds
					.iterator();
			while (studentTypeIdsIter.hasNext()) {
				// TODO shouldn't we just gracefully skip if you submit
				// a nonsense student type ID?
				studentTypeNames.add(studentTypeService.get(
						studentTypeIdsIter.next()).getName());
			}
		}
		return studentTypeNames;
	}

	private String departmentNameOrDefault(CoachCaseloadRecordCountForProgramStatus countByCoachAndStatus,
										   String defaultStr) {
		String deptName = countByCoachAndStatus.getCoachDepartmentName();
		return StringUtils.isBlank(deptName) ? defaultStr : deptName;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}