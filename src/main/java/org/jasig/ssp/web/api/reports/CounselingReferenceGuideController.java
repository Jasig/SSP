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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/1/report/counselingreference")
public class CounselingReferenceGuideController extends ReportBaseController {

	private static final String REPORT_TITLE =  "Counseling Reference Guide";
	private static final String REPORT_FILE_TITLE = "Counseling_Reference_Guide";
	private static final String REPORT_URL = "/reports/counselingReferenceGuide.jasper";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CounselingReferenceGuideController.class);

	@Autowired
	private transient ChallengeService challengeService;
	@Autowired
	private transient ChallengeTOFactory challengeTOactory;
	
	@Autowired
	protected transient SecurityService securityService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getCounselingReferenceGuide(
			final HttpServletResponse response,		
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		final PagingWrapper<Challenge> challengeWrapper = challengeService.getAll(SortingAndPaging.
				createForSingleSortAll(ObjectStatus.ACTIVE, "name", "ASC"));
		
		final List<ChallengeTO> challengeTOs = challengeTOactory.asTOList(challengeWrapper.getRows());	
		
		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addReportTitleToMap(REPORT_TITLE, parameters);
		generateReport(response, parameters, challengeTOs, REPORT_URL, reportType, REPORT_FILE_TITLE);
		
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}