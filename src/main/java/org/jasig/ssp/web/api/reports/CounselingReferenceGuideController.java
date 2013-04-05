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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.reports.StudentHistoryTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */
@Controller
@RequestMapping("/1/report/counselingreference")
public class CounselingReferenceGuideController extends AbstractBaseController {

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
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		final PagingWrapper<Challenge> challengeWrapper = challengeService.getAll(new SortingAndPaging(ObjectStatus.ACTIVE));
		final List<ChallengeTO> challengeTOs = challengeTOactory.asTOList(challengeWrapper.getRows());	
		
		
		
		//challengeTOs.get(0).getObjectStatus().ACTIVE
		
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("ReportTitle", "Counseling Reference Guide");
		
		JRDataSource beanDS;
		if (challengeTOs == null || challengeTOs.size() <= 0) {
			beanDS = new JREmptyDataSource();
		} else {
			beanDS = new JRBeanCollectionDataSource(challengeTOs);
		}

		final InputStream is = getClass().getResourceAsStream(
				"/reports/counselingReferenceGuide.jasper");
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperFillManager.fillReportToStream(is, os, parameters, beanDS);
		final InputStream decodedInput = new ByteArrayInputStream(
				os.toByteArray());

		if ("pdf".equals(reportType)) {
			response.setHeader("Content-disposition",
					"attachment; filename=Counseling_Reference_Guide.pdf");
			JasperExportManager.exportReportToPdfStream(decodedInput,
					response.getOutputStream());
		} else if ("csv".equals(reportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment; filename=Counseling_Reference_Guide.csv");

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
