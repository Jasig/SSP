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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.MapStatusReportDao;
import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.dao.external.ExternalSubstitutableCourseDao;
import org.jasig.ssp.model.AnomalyCode;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.MapStatusReportCourseDetails;
import org.jasig.ssp.model.MapStatusReportSubstitutionDetails;
import org.jasig.ssp.model.MapStatusReportTermDetails;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubstitutionCode;
import org.jasig.ssp.model.TermStatus;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.external.ExternalSubstitutableCourse;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.reports.MapPlanStatusReportCourse;
import org.jasig.ssp.transferobject.reports.MapStatusReportCoachEmailInfo;
import org.jasig.ssp.transferobject.reports.MapStatusReportPerson;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummaryDetail;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class MapStatusReportServiceImpl extends AbstractPersonAssocAuditableService<MapStatusReport>
		implements MapStatusReportService {

	@Autowired 
	private MapStatusReportDao dao;
 
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapStatusReportServiceImpl.class);
	
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
	private transient ExternalSubstitutableCourseDao externalSubstitutableCourseDao;
	
	private static String CONFIGURABLE_MATCH_CRITERIA_COURSE_TITLE = "COURSE_TITLE";
	
	private static String CONFIGURABLE_MATCH_CRITERIA_CREDIT_HOURS = "CREDIT_HOURS";
	
	private static String CONFIGURABLE_MATCH_CRITERIA_COURSE_CODE = "COURSE_CODE";

	
	@Override
	public MapStatusReport save(MapStatusReport obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.save(obj);
	}

	@Override
	public void deleteAllOldReports() {
		dao.deleteAllOldReports();
	}

	@Override
	protected PersonAssocAuditableCrudDao<MapStatusReport> getDao() {
		return dao;
	}
	
	@Override
	public MapStatusReport evaluatePlan(Set<String> gradesSet, 
			Set<String> criteriaSet,
			Term cutoffTerm,  
			List<Term> allTerms,
			MapStatusReportPerson planAndPersonInfo,
			Collection<ExternalSubstitutableCourse> allSubstitutableCourses,
			boolean termBound, 
			boolean useSubstitutableCourses) 
	{
		LOGGER.info("Loading plan with id {}",planAndPersonInfo.getPlanId());
		String schoolId = planAndPersonInfo.getSchoolId();
		
		
		List<MapPlanStatusReportCourse> planCourses = planService.getAllPlanCoursesForStatusReport(planAndPersonInfo.getPlanId()); 
		LOGGER.info("Loading plan "+planAndPersonInfo.getPlanId()+" courses with with count {}",planCourses.size());
		final MapStatusReport report = initReport(planAndPersonInfo);
		
		//We only add something to course details in the case where there is an anomaly 
		List<MapStatusReportCourseDetails> reportCourseDetails = new ArrayList<MapStatusReportCourseDetails>();
		
		//We will create an entry for all plan terms prior to the cutoff term, regardless if anomaly is present
		List<MapStatusReportTermDetails> reportTermDetails = new ArrayList<MapStatusReportTermDetails>();
		
		//Create an entry whenever there is a term or course substitution
		List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails = new ArrayList<MapStatusReportSubstitutionDetails>();
		
		
		List<ExternalStudentTranscriptCourse> transcript = externalStudentTranscriptCourseService.getTranscriptsBySchoolId(schoolId);
		
		//Organize Plan Courses by term.. Preprocessing this data by term helps with term based matching
		Map<String,List<MapPlanStatusReportCourse>> planCoursesByTerm = organizePlanCoursesByTerm(planCourses);
		
		//Organize Transcript Courses by Term  Preprocessing this data by term helps with term based matching
		Map<String,List<ExternalStudentTranscriptCourse>> transcriptCoursesByTerm = organizeTranscriptCourseByTerm(transcript);
		
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
								term, planCoursesForTerm,courseReportsByTerm,transcript,allSubstitutableCourses,
								reportSubstitutionDetails);
						 
					}
				}
		}
		
		//Build the term part of the report
		buildTermDetails(report, reportTermDetails, courseReportsByTerm,planCoursesByTerm);
		
		report.setPlanStatus(calculatePlanStatus(reportCourseDetails,reportSubstitutionDetails,termBound,useSubstitutableCourses));
		report.setPlanRatio(calculatePlanRatio(report,planCourses,reportCourseDetails,reportSubstitutionDetails,termBound,useSubstitutableCourses,planCoursesByTerm,reportTermDetails));
		report.setCourseDetails(reportCourseDetails);
		report.setTermDetails(reportTermDetails);
		report.setSubstitutionDetails(reportSubstitutionDetails);
		report.setPlanNote(generatePlanNote(report));
		return report;
	}

	private PlanStatus calculatePlanStatus(
			List<MapStatusReportCourseDetails> reportCourseDetails, 
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails, 
			boolean termBound, 
			boolean useSubstitutableCourses) {

		
		if(reportCourseDetails.size() > 0 )
			return PlanStatus.OFF;
		
		if(termBound == true && useSubstitutableCourses == true)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				return PlanStatus.OFF;
			if(containsCourseSubstitution(reportSubstitutionDetails))
				return PlanStatus.ON_TRACK_SUBSTITUTIO;
		}
		if(termBound == false && useSubstitutableCourses == false)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				return PlanStatus.ON_TRACK_SEQUENCE;
			if(containsCourseSubstitution(reportSubstitutionDetails))
				return PlanStatus.OFF;
		}
		if(termBound == true && useSubstitutableCourses == false)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				return PlanStatus.OFF;
			if(containsCourseSubstitution(reportSubstitutionDetails))
				return PlanStatus.OFF;
		}
		if(termBound == false && useSubstitutableCourses == true)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				return PlanStatus.ON_TRACK_SEQUENCE;
			if(containsCourseSubstitution(reportSubstitutionDetails))
				return PlanStatus.ON_TRACK_SUBSTITUTIO;
		}	
		return PlanStatus.ON;
	}	
	
	private boolean containsTermSubstitution(
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails) {
		for (MapStatusReportSubstitutionDetails mapStatusReportSubstitutionDetails : reportSubstitutionDetails) {
			if(SubstitutionCode.TERM.equals(mapStatusReportSubstitutionDetails.getSubstitutionCode()))
				return true;
		}
		return false;
	}
	private int numTermSubstitution(
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails) {
		int result = 0;
		for (MapStatusReportSubstitutionDetails mapStatusReportSubstitutionDetails : reportSubstitutionDetails) {
			if(SubstitutionCode.TERM.equals(mapStatusReportSubstitutionDetails.getSubstitutionCode()))
				result++;
		}
		return result;
	}
	
	private int numCourseSubstitution(
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails) {
		int result = 0;
		for (MapStatusReportSubstitutionDetails mapStatusReportSubstitutionDetails : reportSubstitutionDetails) {
			if(SubstitutionCode.SUBSTITUTABLE_COURSE.equals(mapStatusReportSubstitutionDetails.getSubstitutionCode()))
				result++;
		}
		return result;
	}
	private boolean containsCourseSubstitution(
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails) {
		for (MapStatusReportSubstitutionDetails mapStatusReportSubstitutionDetails : reportSubstitutionDetails) {
			if(SubstitutionCode.SUBSTITUTABLE_COURSE.equals(mapStatusReportSubstitutionDetails.getSubstitutionCode()))
				return true;
		}
		return false;
	}
	private BigDecimal calculatePlanRatio(
			MapStatusReport report, List<MapPlanStatusReportCourse> planCourses,
			List<MapStatusReportCourseDetails> reportCourseDetails, 
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails, 
			boolean termBound, 
			boolean useSubstitutableCourses, 
			Map<String, List<MapPlanStatusReportCourse>> planCoursesByTerm, 
			List<MapStatusReportTermDetails> reportTermDetails) 
	{
		int totalPlanCoursesInScope = 0;
		//Calculate total number of plans in scope
		for(MapStatusReportTermDetails reportTermDetail : reportTermDetails)
		{
			List<MapPlanStatusReportCourse> planCoursesForTerm = planCoursesByTerm.get(reportTermDetail.getTermCode());
			if(planCoursesForTerm != null && !planCoursesForTerm.isEmpty())
			{
				totalPlanCoursesInScope = totalPlanCoursesInScope + planCoursesForTerm.size();
			}
			
		}
		
		if(planCourses.isEmpty())
			return new BigDecimal(1);
		int anomalies = reportCourseDetails.size();
		if(termBound == true && useSubstitutableCourses == true)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				anomalies =anomalies + numTermSubstitution(reportSubstitutionDetails);
		}
		if(termBound == false && useSubstitutableCourses == false)
		{
			if(containsCourseSubstitution(reportSubstitutionDetails))
				anomalies =anomalies + numCourseSubstitution(reportSubstitutionDetails);
		}
		if(termBound == true && useSubstitutableCourses == false)
		{
			if(containsTermSubstitution(reportSubstitutionDetails))
				anomalies =anomalies + numTermSubstitution(reportSubstitutionDetails);
			if(containsCourseSubstitution(reportSubstitutionDetails))
				anomalies =anomalies + numCourseSubstitution(reportSubstitutionDetails);
		}
		report.setPlanRatioDemerits(anomalies);
		report.setTotalPlanCourses(totalPlanCoursesInScope);
		return new BigDecimal(new Float(totalPlanCoursesInScope - anomalies) / new Float(totalPlanCoursesInScope));
	}

	private MapStatusReport initReport(MapStatusReportPerson planIdPersonIdPair) 
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
			Map<String, List<MapStatusReportCourseDetails>> courseReportsByTerm, 
			Map<String, List<MapPlanStatusReportCourse>> planCoursesByTerm) 
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
				termDetail.setTermRatio(new BigDecimal(1));
			} else
			{ 
				if(coursesDetailsForTerm.size() == 1)
				{
					MapStatusReportCourseDetails mapStatusReportCourseDetails = coursesDetailsForTerm.get(0);
					termDetail.setReport(report);
					termDetail.setTermCode(termCode);
					termDetail.setAnomalyNote("Term has one anomaly: "+mapStatusReportCourseDetails.getAnomalyCode());
					termDetail.setAnomalyCode(mapStatusReportCourseDetails.getAnomalyCode());
					termDetail.setTermRatio(calculateTermRatio(termCode,planCoursesByTerm,coursesDetailsForTerm));
				}
				if(coursesDetailsForTerm.size() > 1)
				{
					termDetail.setReport(report);
					termDetail.setTermCode(termCode);
					termDetail.setAnomalyNote("There are "+coursesDetailsForTerm.size()+" anomalies for this term");
					termDetail.setAnomalyCode(AnomalyCode.MULTIPLE_ANOMALIES_IN_TERM);
					termDetail.setTermRatio(calculateTermRatio(termCode,planCoursesByTerm,coursesDetailsForTerm));
				}
			}
			reportTermDetails.add(termDetail);
		}
	}

	private BigDecimal calculateTermRatio(
			String termCode, Map<String, List<MapPlanStatusReportCourse>> planCoursesByTerm,
			List<MapStatusReportCourseDetails> coursesDetailsForTerm) 
	{
		if(planCoursesByTerm.get(termCode).isEmpty())
			return new BigDecimal(1);
		return new BigDecimal(new Float(planCoursesByTerm.get(termCode).size() - coursesDetailsForTerm.size())/new Float(planCoursesByTerm.get(termCode).size()));
	}

	private void evaluateTerm(
			Set<String> gradesSet,
			Set<String> criteriaSet,
			MapStatusReport report,
			List<MapStatusReportCourseDetails> reportCourseDetails,
			Map<String, List<ExternalStudentTranscriptCourse>> transcriptCoursesByTerm,
			Term term, List<MapPlanStatusReportCourse> planCoursesForTerm, 
			Map<String, List<MapStatusReportCourseDetails>> courseReportsByTerm, 
			List<ExternalStudentTranscriptCourse> transcript, 
			Collection<ExternalSubstitutableCourse> allSubstitutableCourses, 
			List<MapStatusReportSubstitutionDetails> reportSubstitutionDetails) 
	{
		
		List<ExternalStudentTranscriptCourse> transcriptCoursesForTerm = transcriptCoursesByTerm.get(term.getCode());
		//Iterate through the courses for the term and try to find a match
		
		for (MapPlanStatusReportCourse mapPlanStatusReportCourse : planCoursesForTerm) 
		{
			//First try to find term bound match
			ExternalStudentTranscriptCourse matchedTranscriptCourse = findTranscriptCourseMatch(mapPlanStatusReportCourse,transcriptCoursesForTerm,criteriaSet);
			if(matchedTranscriptCourse == null)
			{
				//Second try to find term unbounded match
				matchedTranscriptCourse = findTranscriptCourseMatch(mapPlanStatusReportCourse,transcript,criteriaSet);
				if(matchedTranscriptCourse != null)
				{
					//If we find a term unbounded match, create an substitution entry
					reportSubstitutionDetails.add(createSubstitutionEntry(matchedTranscriptCourse,mapPlanStatusReportCourse,SubstitutionCode.TERM,report));
				}
				
			}
			if(matchedTranscriptCourse == null)
			{
				//Third try to find a substitutable course
				matchedTranscriptCourse = findTranscriptCourseMatchSubstitutableCourse(mapPlanStatusReportCourse,transcript,criteriaSet,allSubstitutableCourses);
				if(matchedTranscriptCourse != null)
				{
					//If we find a substitution match, create an substitution entry
					reportSubstitutionDetails.add(createSubstitutionEntry(matchedTranscriptCourse,mapPlanStatusReportCourse,SubstitutionCode.SUBSTITUTABLE_COURSE,report));
				}

			}
			MapStatusReportCourseDetails courseAnomaly = evaluateCourse(gradesSet, report,reportCourseDetails, term,mapPlanStatusReportCourse,matchedTranscriptCourse);
			if(courseAnomaly != null)
			{
				courseReportsByTerm.get(term.getCode()).add(courseAnomaly);
			}
		}
	}

	private MapStatusReportSubstitutionDetails createSubstitutionEntry(
			ExternalStudentTranscriptCourse matchedTranscriptCourse,
			MapPlanStatusReportCourse mapPlanStatusReportCourse,
			SubstitutionCode substitutionCode,
			MapStatusReport report) 
	{
		MapStatusReportSubstitutionDetails detail = new MapStatusReportSubstitutionDetails();
		detail.setFormattedCourse(mapPlanStatusReportCourse.getFormattedCourse());
		detail.setCourseCode(mapPlanStatusReportCourse.getCourseCode() == null ? " " : mapPlanStatusReportCourse.getCourseCode());
		detail.setTermCode(mapPlanStatusReportCourse.getTermCode());
		detail.setSubstitutedFormattedCourse(matchedTranscriptCourse.getFormattedCourse());
		detail.setSubstitutedTermCode(matchedTranscriptCourse.getTermCode());
		detail.setSubstitutedCourseCode(" ");
		detail.setSubstitutionNote(" ");
		detail.setSubstitutionCode(substitutionCode);
		detail.setReport(report);
		return detail;
	}

	private ExternalStudentTranscriptCourse findTranscriptCourseMatchSubstitutableCourse(
			MapPlanStatusReportCourse mapPlanStatusReportCourse,
			List<ExternalStudentTranscriptCourse> transcript,
			Set<String> criteriaSet, Collection<ExternalSubstitutableCourse> allSubstitutableCourses) 
	{
		if(allSubstitutableCourses == null || allSubstitutableCourses.isEmpty())
			return null;
		for(ExternalSubstitutableCourse substitutableCourse : allSubstitutableCourses)
		{
			//If term or program code are defined as null as part of the substitution then it's considered term or program unbounded
			//In otherwords, if term is null it applies to all terms, if program is null is applies to all programs
			if(mapPlanStatusReportCourse.getFormattedCourse().trim().equalsIgnoreCase(substitutableCourse.getSourceFormattedCourse().trim())
					&& (substitutableCourse.getTermCode() == null || mapPlanStatusReportCourse.getTermCode().trim().equalsIgnoreCase(substitutableCourse.getTermCode().trim()))
					&& (substitutableCourse.getProgramCode() == null || mapPlanStatusReportCourse.getTermCode().trim().equalsIgnoreCase(substitutableCourse.getProgramCode().trim()))					)
			{
				//if a substitution is found, check to see if the student has taken the target course
				for(ExternalStudentTranscriptCourse transcriptCourse : transcript)
				{
					if(transcriptCourse.getFormattedCourse().trim().equalsIgnoreCase(substitutableCourse.getTargetFormattedCourse().trim())
							&& (substitutableCourse.getTermCode() == null || transcriptCourse.getTermCode().trim().equalsIgnoreCase(substitutableCourse.getTermCode().trim()))
							&& (substitutableCourse.getProgramCode() == null || transcriptCourse.getTermCode().trim().equalsIgnoreCase(substitutableCourse.getProgramCode().trim()))							)
					return transcriptCourse;
						
				}
			}			
		}
		return null;
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
			//The default matching criteria is term_code + formatted_course.  There may be schools where this isn't good enough
			//so these additional search criteria are configurable in case schools need something more detailed.
			if(criteriaSet.contains(MapStatusReportServiceImpl.CONFIGURABLE_MATCH_CRITERIA_COURSE_TITLE))
			{
				if(!mapPlanStatusReportCourse.getCourseTitle().trim().equalsIgnoreCase(externalStudentTranscriptCourse.getTitle().trim()))
				{
					match = false;
				}
			}	
			if(criteriaSet.contains(MapStatusReportServiceImpl.CONFIGURABLE_MATCH_CRITERIA_CREDIT_HOURS))
			{
				if(!mapPlanStatusReportCourse.getCreditHours().equals(externalStudentTranscriptCourse.getCreditEarned()))
				{
					match = false;
				}			
			}
			if(criteriaSet.contains(MapStatusReportServiceImpl.CONFIGURABLE_MATCH_CRITERIA_COURSE_CODE))
			{
				//its quite possible that course code may not be on the transcript.  If this is true, we shouldn't consider course_code at all
				if(mapPlanStatusReportCourse.getCourseCode() != null && externalStudentTranscriptCourse.getCourseCode() != null)
				{
					if(!mapPlanStatusReportCourse.getCourseCode().trim().equalsIgnoreCase(externalStudentTranscriptCourse.getCourseCode().trim()))
					{
						match = false;
					}			
				}
			}			
			if(match)
			{
				return externalStudentTranscriptCourse;
			}
		}
		return null;
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
  

	@Override
	public List<ExternalSubstitutableCourse> getAllSubstitutableCourses()
	{
		return externalSubstitutableCourseDao.getAllSubstitutableCourses();
	}
	
	@Override
	public Set<String> getAdditionalCriteria() {
		Set<String> additionalCriteriaSet = new HashSet<String>();
		String additionalMatchingCriteriaString = configService.getByNameEmpty("map_plan_status_addition_course_matching_criteria");
		if(!StringUtils.isEmpty(additionalMatchingCriteriaString.trim()))
		{
			String[] criteria = additionalMatchingCriteriaString.split(",");
			for (String string : criteria) {
				//Normalize additional criteria to upper/trimmed 
				additionalCriteriaSet.add(string.toUpperCase().trim());
			}
		}
		return additionalCriteriaSet;
	}

	@Override
	public Set<String> getPassingGrades() {
		Set<String> gradesSet = new HashSet<String>();
		String[] grades = configService.getByNameEmpty("map_plan_status_passing_grades").split(",");
		for (String string : grades) {
			//Normalize grades to upper/trimmed 
			gradesSet.add(string.toUpperCase().trim());
		}
		return gradesSet;
	}

	@Override
	public Term deriveCuttoffTerm() {
		String cutoffTermCode = configService.getByNameEmpty("map_plan_status_cutoff_term_code");
		if(cutoffTermCode.trim().isEmpty() )
		{
			//If a registration window is open for a defined term.  Use that 
			List<Term> termsWithRegistrationWindowOpen = termService.getTermsWithRegistrationWindowOpenIfAny();
			Term latestTerm = null;
			if(!termsWithRegistrationWindowOpen.isEmpty())
			{
				for (Term term : termsWithRegistrationWindowOpen) {
					if(latestTerm == null)
					{
						latestTerm = term;
					}
					else
					{
						if(term.getStartDate().after(latestTerm.getStartDate()))
						{
							latestTerm = term;
						}
					}
				}
			}
			try {
				//If there is no registration window open, go with the current term.
				return latestTerm == null ? termService.getCurrentTerm() : latestTerm;
			} catch (ObjectNotFoundException e) {
				//if we can't resolve the current term, we have bigger problems
				LOGGER.error("Map Status Calculation will stop because the current term cannot be resolved.  This is likely a data issue in the EXTERNAL_TERM table");
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
					LOGGER.error("Map Status Calculation will stop because the current term cannot be resolved.  This is likely a data issue in the EXTERNAL_TERM table");
					throw new IllegalArgumentException("Current term could not be resolved.  Map Report Calcuation aborted.");
				}
			}
		}
	}

	@Override
	public List<MapStatusReportSummaryDetail> getSummaryDetails() {
		return dao.getSummaryDetails();
	}

	@Override
	public List<MapStatusReportCoachEmailInfo> getCoachesWithOffPlanStudent() {
		return dao.getCoachesWithOffPlanStudent();
	}

	@Override
	public List<MapStatusReportPerson> getOffPlanPlansForOwner(Person owner) {
		return dao.getOffPlanPlansForOwner(owner);
	}

	@Override
	public List<MapStatusReportCourseDetails> getAllCourseDetailsForPerson(
			Person person) {
		return dao.getAllCourseDetailsForPerson(person);
	}

	@Override
	public List<MapStatusReportTermDetails> getAllTermDetailsForPerson(
			Person person) {
		return dao.getAllTermDetailsForPerson(person);

	}

	@Override
	public List<MapStatusReportSubstitutionDetails> getAllSubstitutionDetailsForPerson(
			Person person) {
		return dao.getAllSubstitutionDetailsForPerson(person);

	}

	@Override
	public void oldReportForStudent(UUID personId) {
		 dao.oldReportForStudent(personId);
	}

}