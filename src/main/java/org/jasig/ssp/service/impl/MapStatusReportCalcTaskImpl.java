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
package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.model.AnomalyCode;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.MapStatusReportCourseDetails;
import org.jasig.ssp.model.MapStatusReportTermDetails;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.TermStatus;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.MapStatusReportCalcTask;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.reports.MapPlanStatusReportCourse;
import org.jasig.ssp.transferobject.reports.PlanIdPersonIdPair;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapStatusReportCalcTaskImpl implements MapStatusReportCalcTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapStatusReportCalcTaskImpl.class);
	
	@Autowired
	private transient ExternalPersonService externalPersonService;

	@Autowired
	private transient PersonService personService;
	
	@Autowired 
	private transient PlanService planService;
	
	@Autowired 
	private transient ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;
	
	@Autowired 
	private transient TermService termService;

	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient MapStatusReportService mapStatusReportService;

	@Autowired
	private transient ExternalPersonDao dao;

	@Autowired
	private WithTransaction withTransaction;
	
	private static String CONFIGURABLE_MATCH_CRITERIA_COURSE_TITLE = "COURSE_TITLE";
	
	private static String CONFIGURABLE_MATCH_CRITERIA_CREDIT_HOURS = "CREDIT_HOURS";


	// intentionally not transactional... this is the main loop, each iteration
	// of which should be its own transaction.
	@Override
	public void exec() {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning external person sync because of thread interruption");
			return;
		}
		if(!Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim()))
		{
			LOGGER.info("Map Plan Status Report calculation will not execute because the property calculate_map_plan_status is set to false");
			return;			
		}
		
		//Hard delete all previous reports
		mapStatusReportService.deleteAllOldReports();
		
		
		//Load up and normalize our configs
		Set<String> gradesSet = new HashSet<String>();
		initPassingGrades(gradesSet);
		
		Set<String> additionalCriteriaSet = new HashSet<String>();
		initAdditionalCriteria(additionalCriteriaSet);
		
		//Lets get the cutoff term and passing grades from the config table
		Term cutoffTerm = deriveCuttoffTerm();
		LOGGER.info("BEGIN : MAPSTATUS REPORT ");
		
		//Lightweight query to avoid the potential 'kitchen sink' we would pull out if we fetched the Plan object
		List<PlanIdPersonIdPair> allActivePlans = planService.getAllActivePlanIds();
		LOGGER.info("Starting report calculations for {} plans",allActivePlans.size());
		
		List<Term> allTerms = termService.getAll();
		//Sort terms by startDate, we do this here so we have no dependency to the default sort order in termService.getAll()
		sortTerms(allTerms);
		
		//Iterate through the active plans.  A transaction is committed after each plan
		for (PlanIdPersonIdPair planIdPersonIdPair : allActivePlans) {
			evaluatePlan(gradesSet, additionalCriteriaSet, cutoffTerm, 
					allTerms, planIdPersonIdPair);
		}
	}

	private void initAdditionalCriteria(Set<String> additionalCriteriaSet) {
		String additionalMatchingCriteriaString = configService.getByNameEmpty("map_plan_status_addition_course_matching_criteria");
		if(!StringUtils.isEmpty(additionalMatchingCriteriaString.trim()))
		{
			String[] criteria = additionalMatchingCriteriaString.split(",");
			for (String string : criteria) {
				//Normalize additional criteria to upper/trimmed 
				additionalCriteriaSet.add(string.toUpperCase().trim());
			}
		}
	}

	private void initPassingGrades(Set<String> gradesSet) {
		String[] grades = configService.getByNameEmpty("map_plan_status_passing_grades").split(",");
		for (String string : grades) {
			//Normalize grades to upper/trimmed 
			gradesSet.add(string.toUpperCase().trim());
		}
	}

	private void evaluatePlan(Set<String> gradesSet, Set<String> criteriaSet,
			Term cutoffTerm,  List<Term> allTerms,
			PlanIdPersonIdPair planIdPersonIdPair) 
	{
		LOGGER.info("Loading plan with id {}",planIdPersonIdPair.getPlanId());
		String schoolId = null;
		List<MapPlanStatusReportCourse> planCourses = planService.getAllPlanCoursesForStatusReport(planIdPersonIdPair.getPlanId()); 
		LOGGER.info("Loading plan "+planIdPersonIdPair.getPlanId()+" courses with with count {}",planCourses.size());
		try {
			  schoolId = personService.getSchoolIdForPersonId(planIdPersonIdPair.getPersonId());
		} catch (ObjectNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		final MapStatusReport report = initReport(planIdPersonIdPair);
		
		//We only add something to course details in the case where there is an anomaly 
		List<MapStatusReportCourseDetails> reportCourseDetails = new ArrayList<MapStatusReportCourseDetails>();
		
		//We will create an entry for all plan terms prior to the cutoff term, regardless if anomaly is present
		List<MapStatusReportTermDetails> reportTermDetails = new ArrayList<MapStatusReportTermDetails>();
		List<ExternalStudentTranscriptCourse> transcriptsBySchoolId = externalStudentTranscriptCourseService.getTranscriptsBySchoolId(schoolId);
		
		//Organize Plan Courses by term.. Preprocessing this data by term helps with term based matching
		Map<String,List<MapPlanStatusReportCourse>> planCoursesByTerm = organizePlanCoursesByTerm(planCourses);
		
		//Organize Transcript Courses by Term  Preprocessing this data by term helps with term based matching
		Map<String,List<ExternalStudentTranscriptCourse>> transcriptCoursesByTerm = organizeTranscriptCourseByTerm(transcriptsBySchoolId);
		
		
		Map<String,List<MapStatusReportCourseDetails>> courseReportsByTerm = new HashMap<String,List<MapStatusReportCourseDetails>>();
		
		//Iterate through terms, if there are plan courses for a particular term, start status calculation
		for(Term term : allTerms)
		{
				if(term.getStartDate().before(cutoffTerm.getStartDate()) || term.getStartDate().equals(cutoffTerm.getStartDate()))
				{
					List<MapPlanStatusReportCourse> planCoursesForTerm = planCoursesByTerm.get(term.getCode());
					if(planCoursesForTerm != null && !planCoursesForTerm.isEmpty())
					{
						courseReportsByTerm.put(term.getCode(),new ArrayList<MapStatusReportCourseDetails>());
						evaluateTerm(gradesSet, criteriaSet, report,
								reportCourseDetails, transcriptCoursesByTerm,
								term, planCoursesForTerm,courseReportsByTerm);
						 
					}
				}
		}
		
		//Build the term part of the report
		buildTermDetails(report, reportTermDetails, courseReportsByTerm);
		
		//Stich the report together for the hibernate save
		report.setPlanStatus(reportCourseDetails.size() > 0 ? PlanStatus.OFF : PlanStatus.ON);
		report.setCourseDetails(reportCourseDetails);
		report.setTermDetails(reportTermDetails);
		report.setPlanNote(generatePlanNote(report));
		
		
		try {
			//Any new writes to this task should be included here
			withTransaction.withNewTransaction(new Callable<MapStatusReport>() {

				@Override
				public MapStatusReport call() throws Exception {
					return mapStatusReportService.save(report);
				}
			});
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	private MapStatusReport initReport(PlanIdPersonIdPair planIdPersonIdPair) 
	{
		final MapStatusReport report = new MapStatusReport();
		report.setPerson(new Person(planIdPersonIdPair.getPersonId()));
		report.setPlan(new Plan(planIdPersonIdPair.getPlanId()));
		return report;
	}
	

	private String generatePlanNote(
			MapStatusReport report) 
	{
		return report.getCourseDetails().size() > 0 ? report.getCourseDetails().size()+" plan course(s) have issues" : " ";
	}

	private void buildTermDetails(final MapStatusReport report,
			List<MapStatusReportTermDetails> reportTermDetails,
			Map<String, List<MapStatusReportCourseDetails>> courseReportsByTerm) 
	{
		Set<String> evaluatedTerms = courseReportsByTerm.keySet();
		for (String termCode : evaluatedTerms) {
			List<MapStatusReportCourseDetails> coursesDetailsForTerm = courseReportsByTerm.get(termCode);
			//if no course details exist for a given term it means it was onplan
			MapStatusReportTermDetails termDetail = new MapStatusReportTermDetails();
			termDetail.setTermStatus(TermStatus.TEST);
			if(coursesDetailsForTerm.isEmpty())
			{
				termDetail.setReport(report);
				termDetail.setTermCode(termCode);
				termDetail.setAnomalyNote("Term has no anomalies");
				termDetail.setAnomalyCode(AnomalyCode.NO_ANOMALY);
			} else
			{ 
				if(coursesDetailsForTerm.size() == 1)
				{
					MapStatusReportCourseDetails mapStatusReportCourseDetails = coursesDetailsForTerm.get(0);
					termDetail.setReport(report);
					termDetail.setTermCode(termCode);
					termDetail.setAnomalyNote("Term has one anomaly: "+mapStatusReportCourseDetails.getAnomalyCode());
					termDetail.setAnomalyCode(mapStatusReportCourseDetails.getAnomalyCode());
				}
				if(coursesDetailsForTerm.size() > 1)
				{
					termDetail.setReport(report);
					termDetail.setTermCode(termCode);
					termDetail.setAnomalyNote("There are "+coursesDetailsForTerm.size()+" anomalies for this term");
					termDetail.setAnomalyCode(AnomalyCode.MULTIPLE_ANOMALIES_IN_TERM);
				}
			}
			reportTermDetails.add(termDetail);
		}
	}

	private void evaluateTerm(
			Set<String> gradesSet,
			Set<String> criteriaSet,
			MapStatusReport report,
			List<MapStatusReportCourseDetails> reportCourseDetails,
			Map<String, List<ExternalStudentTranscriptCourse>> transcriptCoursesByTerm,
			Term term, List<MapPlanStatusReportCourse> planCoursesForTerm, Map<String, List<MapStatusReportCourseDetails>> courseReportsByTerm) 
	{
		
		List<ExternalStudentTranscriptCourse> transcriptCoursesForTerm = transcriptCoursesByTerm.get(term.getCode());
		//Iterate through the courses for the term and try to find a match
		
		for (MapPlanStatusReportCourse mapPlanStatusReportCourse : planCoursesForTerm) 
		{
			ExternalStudentTranscriptCourse matchedTranscriptCourse = findTranscriptCourseMatch(mapPlanStatusReportCourse,transcriptCoursesForTerm,criteriaSet);
			MapStatusReportCourseDetails courseAnomaly = evaluateCourse(gradesSet, report,reportCourseDetails, term,	mapPlanStatusReportCourse,matchedTranscriptCourse);
			if(courseAnomaly != null)
			{
				courseReportsByTerm.get(term.getCode()).add(courseAnomaly);
			}
		}
	}

	private MapStatusReportCourseDetails evaluateCourse(Set<String> passingGradesSet,
			MapStatusReport report,
			List<MapStatusReportCourseDetails> reportCourseDetails, Term term,
			MapPlanStatusReportCourse mapPlanStatusReportCourse,
			ExternalStudentTranscriptCourse matchedTranscriptCourse) {
		
		MapStatusReportCourseDetails courseDetail = null;
		//If there is no matched course, that's an anomaly
		if(matchedTranscriptCourse == null)
		{
			if(term.getEndDate().after(new Date()))
			{
				courseDetail = new MapStatusReportCourseDetails(report, mapPlanStatusReportCourse.getTermCode(), mapPlanStatusReportCourse.getFormattedCourse(), 
						mapPlanStatusReportCourse.getCourseCode(), "", AnomalyCode.COURSE_NOT_REGISTERED);
				reportCourseDetails.add(courseDetail);
			}
			else
			{
				courseDetail = new MapStatusReportCourseDetails(report, mapPlanStatusReportCourse.getTermCode(), mapPlanStatusReportCourse.getFormattedCourse(), 
						mapPlanStatusReportCourse.getCourseCode(), "", AnomalyCode.COURSE_NOT_TAKEN);
				reportCourseDetails.add(courseDetail);

			}
		}
		else
		//If the grade in the transcript is not a configured 'passing grade', that's an anomaly
		if(!passingGradesSet.contains(matchedTranscriptCourse.getGrade().trim().toUpperCase()))
		{
			if(term.getEndDate().after(new Date()))
			{
				courseDetail = new MapStatusReportCourseDetails(report, mapPlanStatusReportCourse.getTermCode(), mapPlanStatusReportCourse.getFormattedCourse(), 
						mapPlanStatusReportCourse.getCourseCode(), "", AnomalyCode.CURR_OR_FUT_COURSE_NO_GRADE);
				reportCourseDetails.add(courseDetail);

			}
			else
			{
				courseDetail = new MapStatusReportCourseDetails(report, mapPlanStatusReportCourse.getTermCode(), mapPlanStatusReportCourse.getFormattedCourse(), 
						mapPlanStatusReportCourse.getCourseCode(), "", AnomalyCode.COURSE_NOT_PASSED);
				reportCourseDetails.add(courseDetail);

			}
		}
		
		//will be null if no anomaly is found
		return courseDetail;
	}

	private ExternalStudentTranscriptCourse findTranscriptCourseMatch(MapPlanStatusReportCourse mapPlanStatusReportCourse,List<ExternalStudentTranscriptCourse> transcriptCoursesForTerm, Set<String> criteriaSet) 
	{
		if(transcriptCoursesForTerm == null || transcriptCoursesForTerm.isEmpty())
			return null;
		
		for (ExternalStudentTranscriptCourse externalStudentTranscriptCourse : transcriptCoursesForTerm)
		{
			boolean match = true;
			if(!mapPlanStatusReportCourse.getFormattedCourse().trim().equalsIgnoreCase(externalStudentTranscriptCourse.getFormattedCourse().trim()))
			{
				match = false;
			}
			//The default matching criteria is term_code + formatted_course.  There maybe schools where this isn't good enough
			//so these additional search criteria are configurable incase schools need something more detailed.
			if(criteriaSet.contains(MapStatusReportCalcTaskImpl.CONFIGURABLE_MATCH_CRITERIA_COURSE_TITLE))
			{
				if(!mapPlanStatusReportCourse.getCourseTitle().trim().equalsIgnoreCase(externalStudentTranscriptCourse.getTitle().trim()))
				{
					match = false;
				}
			}	
			//For phase 1 this is term check redundant because the only caller of this method (at the time this comment was written) will be
			//called with the term already matched but included so this method could be reused in the future without assuming
			//term match pre-processing
			if(!mapPlanStatusReportCourse.getTermCode().trim().equalsIgnoreCase(externalStudentTranscriptCourse.getTermCode().trim()))
			{
				match = false;
			}			
			if(criteriaSet.contains(MapStatusReportCalcTaskImpl.CONFIGURABLE_MATCH_CRITERIA_CREDIT_HOURS))
			{
				if(!mapPlanStatusReportCourse.getCreditHours().equals(externalStudentTranscriptCourse.getCreditEarned()))
				{
					match = false;
				}			
			}
			if(match)
			{
				return externalStudentTranscriptCourse;
			}
		}
		return null;
	}

	private Term deriveCuttoffTerm() {
		String cutoffTermCode = configService.getByNameEmpty("map_plan_status_cutoff_term_code");
		if(cutoffTermCode.trim().isEmpty() )
		{
			try {
				return termService.getCurrentTerm();
			} catch (ObjectNotFoundException e) {
				//if we can't resolve the current term, we have bigger problems
				LOGGER.error("Map Status Calculation will stop because the current term cannot be resolved.  This is likely an data issue in the EXTERNAL_TERM table");
				throw new IllegalArgumentException("Current term could not be resolved.  Map Report Calcuation aborted.");
			}
		}
		else
		{
			try {
				return termService.getByCode(cutoffTermCode);
			} catch (ObjectNotFoundException e) {
				try {
					return termService.getCurrentTerm();
				} catch (ObjectNotFoundException e1) {
					//if we can't resolve the current term, we have bigger problems
					LOGGER.error("Map Status Calculation will stop because the current term cannot be resolved.  This is likely an data issue in the EXTERNAL_TERM table");
					throw new IllegalArgumentException("Current term could not be resolved.  Map Report Calcuation aborted.");
				}
			}
		}
	}

	private void sortTerms(List<Term> allTerms) {
		Collections.sort(allTerms, new Comparator<Term>() {

			@Override
			public int compare(Term o1, Term o2) {
				if(o1.getStartDate().before(o2.getStartDate()))
					return -1;
				if(o1.getStartDate().after(o2.getStartDate()))
					return 1;	
				//Hopefully this isnt ever the case
				if(o1.getStartDate().equals(o2.getStartDate()))
					return 0;	
				return 0;
			}
		});
	}

	private Map<String, List<ExternalStudentTranscriptCourse>> organizeTranscriptCourseByTerm(List<ExternalStudentTranscriptCourse> transcriptsBySchoolId) {
		Map<String, List<ExternalStudentTranscriptCourse>> transcriptCoursesByTerm = new HashMap<String,List<ExternalStudentTranscriptCourse>>();
		for(ExternalStudentTranscriptCourse transcriptCourse:transcriptsBySchoolId)
		{
			if(transcriptCoursesByTerm.get(transcriptCourse.getTermCode()) == null)
			{
				List<ExternalStudentTranscriptCourse> list = new ArrayList<ExternalStudentTranscriptCourse>();
				list.add(transcriptCourse);
				transcriptCoursesByTerm.put(transcriptCourse.getTermCode(), list);
			} else
			{
				List<ExternalStudentTranscriptCourse> list = transcriptCoursesByTerm.get(transcriptCourse.getTermCode());
				list.add(transcriptCourse);
			}
		}
		return transcriptCoursesByTerm;
	}

	private Map<String, List<MapPlanStatusReportCourse>> organizePlanCoursesByTerm(List<MapPlanStatusReportCourse> planCourses) {
		Map<String, List<MapPlanStatusReportCourse>> planCoursesByTerm = new HashMap<String,List<MapPlanStatusReportCourse>>();
		for(MapPlanStatusReportCourse planCourse:planCourses)
		{
			if(planCoursesByTerm.get(planCourse.getTermCode()) == null)
			{
				List<MapPlanStatusReportCourse> list = new ArrayList<MapPlanStatusReportCourse>();
				list.add(planCourse);
				planCoursesByTerm.put(planCourse.getTermCode(), list);
			} else
			{
				List<MapPlanStatusReportCourse> list = planCoursesByTerm.get(planCourse.getTermCode());
				list.add(planCourse);
			}
		}
		return planCoursesByTerm;
	}


}
