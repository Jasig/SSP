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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.CaseLoadReportTO;
import org.jasig.ssp.transferobject.reports.CaseLoadSearchTO;
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
public class CaseloadReportController extends ReportBaseController {

	private static String REPORT_URL = "/reports/caseLoad.jasper";
	private static String REPORT_FILE_TITLE = "CaseLoad_Report";

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
			if (id == null) {
				return null;
			}
			for (ProgramStatusHelper helper : ProgramStatusHelper.values()) {
				if (id.equals(helper.id())) {
					return helper;
				}
			}
			return null;
		}

		static void maybeAddToCount(long add, CaseLoadReportTO target,
				UUID programStatusId) {
			ProgramStatusHelper helper = ProgramStatusHelper
					.byId(programStatusId);
			if (helper == null) {
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
	protected transient PersonSearchService caseLoadService;
	@Autowired
	protected transient StudentTypeService studentTypeService;
	
	@Autowired
	protected transient ServiceReasonService serviceReasonService;	
	
	@Autowired
	protected transient SpecialServiceGroupService ssgService;	

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
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
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		final List<UUID> cleanStudentTypeIds = SearchParameters.cleanUUIDListOfNulls(studentTypeIds);
		final List<UUID> cleanSpecialServiceGroupIds = SearchParameters.cleanUUIDListOfNulls(specialServiceGroupIds);
		final List<UUID> cleanServiceReasonsIds = SearchParameters.cleanUUIDListOfNulls(serviceReasonIds);
        CaseLoadSearchTO searchForm = new CaseLoadSearchTO(cleanStudentTypeIds, cleanServiceReasonsIds, cleanSpecialServiceGroupIds, homeDepartment);
		final List<CaseLoadReportTO> caseLoadReportList = collectCaseLoadReportTOs(searchForm);		
		final Map<String, Object> parameters = collectParamsForReport(searchForm);

		generateReport(response, parameters, caseLoadReportList, REPORT_URL,
				reportType, REPORT_FILE_TITLE);

	}

	private List<CaseLoadReportTO> collectCaseLoadReportTOs(
			CaseLoadSearchTO searchForm) {
		List<CaseLoadReportTO> caseLoadReportList = Lists.newArrayList();

		final Collection<CoachCaseloadRecordCountForProgramStatus> countsByCoachAndStatus = caseLoadService
				.currentCaseloadCountsByStatus(searchForm);
		UUID currentCoachId = null;
		CaseLoadReportTO caseLoadReportTO = null;
		for (CoachCaseloadRecordCountForProgramStatus countByCoachAndStatus : countsByCoachAndStatus) {
			if (currentCoachId == null
					|| !(currentCoachId.equals(countByCoachAndStatus
							.getCoachId()))) {
				if (caseLoadReportTO != null) {
					caseLoadReportList.add(caseLoadReportTO);
				}
				currentCoachId = countByCoachAndStatus.getCoachId();
				caseLoadReportTO = new CaseLoadReportTO(
						countByCoachAndStatus.getCoachFirstName(),
						countByCoachAndStatus.getCoachLastName(),
						departmentNameOrDefault(countByCoachAndStatus,
								DEPARTMENT_PLACEHOLDER));
			}
			ProgramStatusHelper.maybeAddToCount(
					countByCoachAndStatus.getCount(), caseLoadReportTO,
					countByCoachAndStatus.getProgramStatusId());
		}
		// make sure last one isn't forgotten
		if (caseLoadReportTO != null) {
			caseLoadReportList.add(caseLoadReportTO);
		}
		return caseLoadReportList;
	}

	private Map<String, Object> collectParamsForReport(CaseLoadSearchTO searchForm)
			throws ObjectNotFoundException {
		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addHomeDepartmentToMap(searchForm.getHomeDepartment(), parameters);
		SearchParameters.addStudentTypesToMap(searchForm.getStudentTypeIds(), parameters,
				studentTypeService);
		SearchParameters.addServiceReasonToMap(searchForm.getServiceReasonIds(), parameters, serviceReasonService);
		SearchParameters.addSpecialGroupsNamesToMap(searchForm.getSpecialServiceGroupIds(), parameters, ssgService);
		return parameters;
	}

	private String departmentNameOrDefault(
			CoachCaseloadRecordCountForProgramStatus countByCoachAndStatus,
			String defaultStr) {
		String deptName = countByCoachAndStatus.getCoachDepartmentName();
		return StringUtils.isBlank(deptName) ? defaultStr : deptName;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}