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

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.dao.external.ExternalSubstitutableCourseDao;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.ExternalSubstitutableCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.MapStatusReportCalcTask;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.reports.MapStatusReportCoachEmailInfo;
import org.jasig.ssp.transferobject.reports.MapStatusReportPerson;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummary;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummaryDetail;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapStatusReportCalcTaskImpl implements MapStatusReportCalcTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapStatusReportCalcTaskImpl.class);

	@Autowired 
	private transient PlanService planService;
	
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
	
	@Autowired 
	private MessageService messageService;
	
	@Autowired
	protected transient MessageTemplateService  messageTemplateService;

	public Class<Void> getBatchExecReturnType() {
		return Void.TYPE;
	}

		// intentionally not transactional... this is the main loop, each iteration
	// of which should be its own transaction.
	@Override
	public void exec(CallableExecutor<Void> batchExecutor) {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning map status report calculation because of thread interruption");
			return;
		}
		if(!Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim()))
		{
			LOGGER.info("Map Plan Status Report calculation will not execute because the property calculate_map_plan_status is set to false");
			return;			
		}
		LOGGER.info("BEGIN : MAPSTATUS REPORT ");
		
		MapStatusReportSummary summary = new MapStatusReportSummary();
		summary.setStartTime(Calendar.getInstance());
		
		//Hard delete all previous reports
		mapStatusReportService.deleteAllOldReports();
		
		
		final Collection<ExternalSubstitutableCourse> allSubstitutableCourses = mapStatusReportService.getAllSubstitutableCourses();
		
		//Load up our configs
		final Set<String> gradesSet = mapStatusReportService.getPassingGrades();
		final Set<String> additionalCriteriaSet = mapStatusReportService.getAdditionalCriteria();
		final boolean termBound = Boolean.parseBoolean(configService.getByNameEmpty("map_plan_status_term_bound_strict").trim());
		final boolean useSubstitutableCourses = Boolean.parseBoolean(configService.getByNameEmpty("map_plan_status_use_substitutable_courses").trim());
		
		//Lets figure out our cutoff term
		final Term cutoffTerm = mapStatusReportService.deriveCuttoffTerm();
		
		//Lightweight query to avoid the potential 'kitchen sink' we would pull out if we fetched the Plan object
		List<MapStatusReportPerson> allActivePlans = planService.getAllActivePlanIds();
		LOGGER.info("Starting report calculations for {} plans",allActivePlans.size());
		
		final List<Term> allTerms = termService.getAll();
		//Sort terms by startDate, we do this here so we have no dependency on the default sort order in termService.getAll()
		sortTerms(allTerms);
		
		//Iterate through the active plans.  A transaction is committed after each plan
		for (final MapStatusReportPerson planIdPersonIdPair : allActivePlans) {
			
			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning map status report calculation because of thread interruption");
				return;
			}
			
			LOGGER.info("MAP STATUS REPORT CALCULATION STARTING FOR: "+planIdPersonIdPair.getSchoolId());

			if (batchExecutor == null) {
				evaluatePlan(gradesSet, additionalCriteriaSet, cutoffTerm,
						allTerms, planIdPersonIdPair, allSubstitutableCourses,termBound,useSubstitutableCourses);
			} else {
				try {
					batchExecutor.exec(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							evaluatePlan(gradesSet, additionalCriteriaSet, cutoffTerm,
									allTerms, planIdPersonIdPair, allSubstitutableCourses,termBound,useSubstitutableCourses);
							return null;
						}
					});
				} catch ( RuntimeException e ) {
					throw e;
				} catch ( Exception e ) {
					throw new RuntimeException(e);
				}
			}
			
			LOGGER.info("FINISHED MAP STATUS REPORT CALCULATION FOR: " + planIdPersonIdPair.getSchoolId());
		}
		summary.setEndTime(Calendar.getInstance());
		summary.setStudentsInScope(allActivePlans.size());
		
		
		sendReportEmail(summary);
		sendOffPlanEmailsToCoaches();
		
		
		LOGGER.info("MAPSTATUS REPORT RUNTIME: "+(summary.getEndTime().getTimeInMillis() - summary.getStartTime().getTimeInMillis())+" ms.");
		LOGGER.info("END : MAPSTATUS REPORT ");

	}

	private void sendOffPlanEmailsToCoaches() {
		List<MapStatusReportCoachEmailInfo> coaches = mapStatusReportService.getCoachesWithOffPlanStudent();
		if(coaches.size() > 0 )
		{
			for (MapStatusReportCoachEmailInfo mapStatusReportCoachEmailInfo : coaches) {
				
				StringBuilder sb = new StringBuilder();
				sb.append("The following students have been determined to be Off Plan after comparing their transcript to their MAP</br>");
				int linebreak = 0;
				List<MapStatusReportPerson> offPlanPlansForCoach = mapStatusReportService.getOffPlanPlansForOwner(new Person(mapStatusReportCoachEmailInfo.getPersonId()));
				for (MapStatusReportPerson mapStatusReportPerson : offPlanPlansForCoach) 
				{
					sb.append(mapStatusReportPerson.getFirstName()+" "+mapStatusReportPerson.getLastName());
					if((linebreak % 3) == 2)
					{
						sb.append(",</br>");
					}else
					{
						sb.append(", ");
					}
					linebreak++;
					
				}
				if(sb.lastIndexOf(",") > 0)
				{
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				SubjectAndBody subjectAndBody = new SubjectAndBody("You have students that are off plan", sb.toString());
				if(!StringUtils.isEmpty(mapStatusReportCoachEmailInfo.getPrimaryEmail()))
				{
					String cc = null;
					try {
						if(!StringUtils.isEmpty(mapStatusReportCoachEmailInfo.getCoachEmail()) && !mapStatusReportCoachEmailInfo.getCoachEmail().equalsIgnoreCase(mapStatusReportCoachEmailInfo.getPrimaryEmail()))
						{
							cc = mapStatusReportCoachEmailInfo.getCoachEmail();
						}
						messageService.createMessage(mapStatusReportCoachEmailInfo.getPrimaryEmail(), cc, subjectAndBody);
					} catch (ObjectNotFoundException e) {
						LOGGER.error("There was an error sending a coach email", e);
					}
				}
			}
		}
		
	}

	private void sendReportEmail(MapStatusReportSummary summary) 
	{
		boolean sendEmail = Boolean.parseBoolean(configService.getByNameEmpty("map_plan_status_send_report_email").trim().toLowerCase());
		if(sendEmail)
		{
			List<MapStatusReportSummaryDetail> details = mapStatusReportService.getSummaryDetails();
			for (MapStatusReportSummaryDetail mapStatusReportSummaryDetail : details) {
				LOGGER.info("MAPSTATUSREPORT SUMMARY: "+ mapStatusReportSummaryDetail.getPlanStatus()+" COUNT: "+mapStatusReportSummaryDetail.getCount());
			}
			summary.setSummaryDetails(details);
			SubjectAndBody mapStatusEmail = messageTemplateService.createMapStatusReportEmail(summary);
			String mapEmail = configService.getByNameEmpty("map_plan_status_email").trim();
			if(!StringUtils.isEmpty(mapEmail))
			{
				try {
					messageService.createMessage(mapEmail, null, mapStatusEmail);
				} catch (ObjectNotFoundException e) {
					LOGGER.error("There was an error sending the map status report email", e);
				}
			}
 		}
	}

	private void evaluatePlan(Set<String> gradesSet, Set<String> criteriaSet,
			Term cutoffTerm,  List<Term> allTerms,
			MapStatusReportPerson planIdPersonIdPair, Collection<ExternalSubstitutableCourse> allSubstitutableCourses, boolean termBound, boolean useSubstitutableCourses) 
	{
		
		final MapStatusReport report = mapStatusReportService.evaluatePlan(gradesSet, criteriaSet, cutoffTerm, allTerms, planIdPersonIdPair, allSubstitutableCourses,termBound,useSubstitutableCourses);
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
}
