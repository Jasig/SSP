/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.factory.PersonSearchResult2TOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.WatchStudentService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyFormatting;
import org.jasig.ssp.util.csvwriter.CaseloadCsvWriterHelper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/1/exportableCaseload")
public class ExportableCaseloadController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExportableCaseloadController.class);
	private static final String DEFAULT_APP_TITLE = "export"; //SSP is branding, so go very generic

	private static final String[] CUSTOM_OPTIONS_MASTER_LIST = {"address", "alternatecontact", "sspstatus",
			"demographics", "academicprogram", "academicgpa", "academicstanding", "department", "servicereasons",
			"referralsources", "specialservicegroups", "sapstatus", "starttermyear", "facompletionrate", "mapinfo",
			"extra"};

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	

    @Autowired
    private transient ConfigService configService;
    
	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient ProgramStatusService programStatusService;
	
	@Autowired
	private transient PersonSearchService service;
	
	@Autowired
	private transient PersonSearchResult2TOFactory factory;	
	
	@Autowired
	protected transient WatchStudentService watchStudentService;
	
	@Autowired
	private transient PersonSearchRequestTOFactory personSearchRequestFactory;
	
	@Autowired
	private transient SecurityService securityService;
	
	@PreAuthorize("hasRole('ROLE_PERSON_CASELOAD_READ') and hasRole('ROLE_BULK_SEARCH_EXPORT')")
	@RequestMapping(value = "/caseload", method = RequestMethod.GET)
	public @ResponseBody
	void myCaseload(
			HttpServletResponse response,
			final @RequestParam(required = false) UUID programStatusId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException, ValidationException, IOException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}
		
		response.setHeader("Content-Disposition", "attachment; filename="+buildFileName("caseload_")); 	
		response.setContentType("text/csv");
		
		CaseloadCsvWriterHelper csvWriterHelper = new CaseloadCsvWriterHelper(response.getWriter());
	    service.exportableCaseLoadFor(csvWriterHelper,
				programStatus, securityService.currentUser().getPerson(),
				buildSortAndPage( limit,  start,  sort,  sortDirection));

	}
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_READ') and hasRole('ROLE_BULK_SEARCH_EXPORT')")
	@RequestMapping(value = "/watchlist", method = RequestMethod.GET)
	public @ResponseBody
	void myWatchlist(
			HttpServletResponse response,
			final @RequestParam(required = false) UUID programStatusId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException, ValidationException, IOException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}
		

		response.setHeader("Content-Disposition", "attachment; filename="+buildFileName("watchlist_")); 
		response.setContentType("text/csv");
		watchStudentService.exportWatchListFor(response.getWriter(),programStatus, securityService.currentUser().getPerson(), buildSortAndPage( limit,  start,  sort,  sortDirection));
	}
	
	private String buildFileName(String string) 
	{
		StringBuilder fileName = new StringBuilder();
		Calendar now = Calendar.getInstance();
		String appTitle = configService.getByNameNullOrDefaultValue("app_title");
		appTitle = normalizeAppTitle(appTitle);
		fileName.append(appTitle+"_");
		fileName.append(string);
		fileName.append(now.get(Calendar.MONTH));
		fileName.append(now.get(Calendar.DAY_OF_MONTH));
		fileName.append(now.get(Calendar.YEAR)+"_");
		fileName.append(now.get(Calendar.HOUR));
		fileName.append(now.get(Calendar.MINUTE)+".csv");

		return fileName.toString();
	}

	protected String normalizeAppTitle(String appTitle) {
		if (StringUtils.isBlank(appTitle)) {
			return DEFAULT_APP_TITLE;
		}
		return appTitle.replaceAll("\\s", "_");
	}

	@PreAuthorize("hasRole('ROLE_PERSON_SEARCH_READ') and hasRole('ROLE_BULK_SEARCH_EXPORT')")
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public @ResponseBody void  search(
	 HttpServletResponse response,
	 final @RequestParam(required = false) String schoolId,
	 final @RequestParam(required = false) String firstName,
	 final @RequestParam(required = false) String lastName,
	 final @RequestParam(required = false) String programStatus,
	 final @RequestParam(required = false) String coachId,
	 final @RequestParam(required = false) String declaredMajor,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMin,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMax,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMin,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMax,
     final @RequestParam(required = false) BigDecimal localGpaMin,
     final @RequestParam(required = false) BigDecimal localGpaMax,
     final @RequestParam(required = false) BigDecimal programGpaMin,
     final @RequestParam(required = false) BigDecimal programGpaMax,
	 final @RequestParam(required = false) Boolean currentlyRegistered,
	 final @RequestParam(required = false) String earlyAlertResponseLate,
	 final @RequestParam(required = false) String sapStatusCode,
	 final @RequestParam(required = false) String specialServiceGroup,
	 final @RequestParam(required = false) String planStatus,
	 final @RequestParam(required = false) String planExists,
	 final @RequestParam(required = false) Boolean myCaseload,
	 final @RequestParam(required = false) Boolean myPlans,
	 final @RequestParam(required = false) Boolean myWatchList,
	 final @RequestParam(required = false) @DateTimeFormat(pattern=DateOnlyFormatting.DEFAULT_DATE_PATTERN) Date birthDate,
     final @RequestParam(required = false) String actualStartTerm,
	 final @RequestParam(required = false) String personTableType,
	 final @RequestParam(required = false) Integer start,
	 final @RequestParam(required = false) Integer limit,
	 final @RequestParam(required = false) String sort,
	 final @RequestParam(required = false) String sortDirection,
	 final HttpServletRequest request) throws ObjectNotFoundException, IOException
	 {

		
		response.setHeader("Content-Disposition", "attachment; filename="+buildFileName("search_")); 
		response.setContentType("text/csv");
		
		SortingAndPaging sortAndPage = buildSortAndPage( limit,  start,  sort,  sortDirection);
		PersonSearchRequest form = personSearchRequestFactory.from(schoolId,
				firstName, lastName, 
				programStatus,specialServiceGroup, 
				coachId,declaredMajor,
				hoursEarnedMin,hoursEarnedMax,
				gpaEarnedMin,gpaEarnedMax,
                localGpaMin, localGpaMax,
                programGpaMin, programGpaMax,
				currentlyRegistered,earlyAlertResponseLate,
				sapStatusCode,
				planStatus,planExists,
				myCaseload,myPlans,myWatchList, birthDate, actualStartTerm, personTableType, sortAndPage);

		service.exportDirectoryPersonSearch(response.getWriter(), form);
		
	}

	@PreAuthorize("hasRole('ROLE_PERSON_SEARCH_READ') and hasRole('ROLE_BULK_SEARCH_EXPORT')")
	@RequestMapping(value="/customizableSearch", method = RequestMethod.GET)
	public @ResponseBody void  customizableSearch(
			HttpServletResponse response,
			final @RequestParam(required = false) String schoolId,
			final @RequestParam(required = false) String firstName,
			final @RequestParam(required = false) String lastName,
			final @RequestParam(required = false) String programStatus,
			final @RequestParam(required = false) String coachId,
			final @RequestParam(required = false) String declaredMajor,
			final @RequestParam(required = false) BigDecimal hoursEarnedMin,
			final @RequestParam(required = false) BigDecimal hoursEarnedMax,
			final @RequestParam(required = false) BigDecimal gpaEarnedMin,
			final @RequestParam(required = false) BigDecimal gpaEarnedMax,
			final @RequestParam(required = false) BigDecimal localGpaMin,
			final @RequestParam(required = false) BigDecimal localGpaMax,
			final @RequestParam(required = false) BigDecimal programGpaMin,
			final @RequestParam(required = false) BigDecimal programGpaMax,
			final @RequestParam(required = false) Boolean currentlyRegistered,
			final @RequestParam(required = false) String earlyAlertResponseLate,
			final @RequestParam(required = false) String sapStatusCode,
			final @RequestParam(required = false) String specialServiceGroup,
			final @RequestParam(required = false) String planStatus,
			final @RequestParam(required = false) String planExists,
			final @RequestParam(required = false) Boolean myCaseload,
			final @RequestParam(required = false) Boolean myPlans,
			final @RequestParam(required = false) Boolean myWatchList,
			final @RequestParam(required = false) @DateTimeFormat(pattern=DateOnlyFormatting.DEFAULT_DATE_PATTERN) Date birthDate,
			final @RequestParam(required = false) String actualStartTerm,
			final @RequestParam(required = false) String personTableType,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection,
			final @RequestParam(required = false) List customOptions,
			final HttpServletRequest request) throws ObjectNotFoundException, IOException
	{

		response.setHeader("Content-Disposition", "attachment; filename="+buildFileName("customsearch_"));
		response.setContentType("text/csv");

		SortingAndPaging sortAndPage = buildSortAndPage(limit,  start,  sort,  sortDirection);
		PersonSearchRequest form = personSearchRequestFactory.from(schoolId,
				firstName, lastName,
				programStatus,specialServiceGroup,
				coachId,declaredMajor,
				hoursEarnedMin,hoursEarnedMax,
				gpaEarnedMin,gpaEarnedMax,
				localGpaMin, localGpaMax,
				programGpaMin, programGpaMax,
				currentlyRegistered,earlyAlertResponseLate,
				sapStatusCode,
				planStatus,planExists,
				myCaseload,myPlans,myWatchList, birthDate, actualStartTerm, personTableType, sortAndPage);

		service.exportDirectoryPersonSearchCustomizable(response.getWriter(), form, cleanCustomOptionsAndMapToMaster(customOptions));
	}

	private Map<Integer, Boolean> cleanCustomOptionsAndMapToMaster (List<String> customOptions) {
		final Map<Integer, Boolean> customOptionsCleaned = Maps.newHashMap();

		for (int index=0; index < CUSTOM_OPTIONS_MASTER_LIST.length; index++) {
			customOptionsCleaned.put(index, false); //load default of false for all options
		}

		if ( !CollectionUtils.isEmpty(customOptions)) {
			for (String option : customOptions) {
				                                                               //if option exists set true
				if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[0])) {
					customOptionsCleaned.put(0, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[1])) {
					customOptionsCleaned.put(1, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[2])) {
					customOptionsCleaned.put(2, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[3])) {
					customOptionsCleaned.put(3, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[4])) {
					customOptionsCleaned.put(4, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[5])) {
					customOptionsCleaned.put(5, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[6])) {
					customOptionsCleaned.put(6, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[7])) {
					customOptionsCleaned.put(7, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[8])) {
					customOptionsCleaned.put(8, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[9])) {
					customOptionsCleaned.put(9, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[10])) {
					customOptionsCleaned.put(10, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[11])) {
					customOptionsCleaned.put(11, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[12])) {
					customOptionsCleaned.put(12, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[13])) {
					customOptionsCleaned.put(13, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[14])) {
					customOptionsCleaned.put(14, true);

				} else if (option.toLowerCase().contains(CUSTOM_OPTIONS_MASTER_LIST[15])) {
					customOptionsCleaned.put(15, true);

				} else {
					//Do nothing reserved if default needed
				}
			}
		}
		return customOptionsCleaned;
	}
		
	private SortingAndPaging buildSortAndPage(Integer limit, Integer start, String sort, String sortDirection){
		String sortConfigured = sort == null ? "dp.lastName":"dp."+sort;
		if(sortConfigured.equals("dp.coach")){
			sortConfigured = "dp.coachLastName";
		}else if(sortConfigured.equals("dp.currentProgramStatusName")){
			sortConfigured = "dp.programStatusName";
		}else if(sortConfigured.equals("dp.numberOfEarlyAlerts")){
			sortConfigured = "dp.activeAlertsCount";
		}else if(sortConfigured.equals("dp.studentType")){
			sortConfigured = "dp.studentTypeName";
		}
		SortingAndPaging sortAndPage = SortingAndPaging
		.createForSingleSortWithPaging(ObjectStatus.ALL, start, limit, sortConfigured,
				sortDirection, "dp.lastName");
		if(sortConfigured.equals("dp.coachLastName")){
			sortAndPage.prependSortField("dp.coachFirstName", SortDirection.getSortDirection(sortDirection));
		}
		return sortAndPage;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
}
