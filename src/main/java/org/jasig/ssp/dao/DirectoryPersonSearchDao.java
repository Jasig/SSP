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
package org.jasig.ssp.dao;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.model.ScheduledTaskStatus;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.impl.ScheduledTaskWrapperServiceImpl;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * PersonSearch DAO
 */
@Repository
public class DirectoryPersonSearchDao  {

	public DirectoryPersonSearchDao() {
		super();
 	}

	@Autowired
	private transient ConfigService configService;
	
	private static final String ACTIVATE_MATERIALIZED_DIRECTORY_PERSON_VIEW = "activate_materialized_directory_person_view";
	
	@Autowired
	private transient TermService termService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient ScheduledApplicationTaskStatusService scheduledApplicationTaskService;
	
	@Autowired
	protected transient SessionFactory sessionFactory;

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DirectoryPersonSearchDao.class);


	FileWriter fileWriter;

	public void refreshDirectoryPerson(){
		
		if(isPostgresSession()){
			try{
				Query query = sessionFactory.getCurrentSession().createSQLQuery("select REFRESH_MV_DIRECTORY_PERSON();");
				query.list();
			}catch(Exception exp){
				Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON;");
				query.list();
			}
		}else{
			Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON;");
			query.list();
		}
	}
	
	private Boolean isPostgresSession(){
		try{
			Properties properties = System.getProperties();
			String dialect = properties.getProperty("db_dialect");
			if(StringUtils.isBlank(dialect)){
				SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory; 
				Properties props = sfi.getProperties();
				dialect = props.get("hibernate.dialect").toString();
			}
			return dialect.toUpperCase().contains("POSTGRES") ? true:false;
		}catch(Exception exp){
			
		}

		return true;
	}
	/**
	 * Search people by the specified terms.
	 * 
	 * @param programStatus
	 *            program status filter
	 * @param requireProgramStatus
	 *            implicitly <code>true</code> if <code>programStatus</code> is
	 *            non-null. Else <code>false</code> allows searching without
	 *            requiring a program status; defaults to <code>true</code>
	 * @param outsideCaseload
	 *            false allows searches without checking the Coach (advisor)
	 *            property; defaults to true
	 * @param searchTerm
	 *            Search term that search first and last name and school ID;
	 *            required
	 * @param advisor
	 *            required if outsideCaseload is not {@link Boolean#FALSE}.
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of people that match the specified filters
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<PersonSearchResult2> search(PersonSearchRequest personSearchRequest)
	{
		Term currentTerm;
		FilterTracker filterTracker = new FilterTracker();
		try
		{
			currentTerm = termService.getCurrentTerm();
		}
		//If there is no current term, lets degrade silently
		catch(ObjectNotFoundException e)
		{
			LOGGER.error("CURRENT TERM NOT SET, org.jasig.ssp.dao.PersonSearchDao.search(PersonSearchRequest) is being called but will not function properly");
			currentTerm = new Term();
			currentTerm.setName("CURRENT TERM NOT SET");
			currentTerm.setStartDate(Calendar.getInstance().getTime());
			currentTerm.setEndDate(Calendar.getInstance().getTime());
			
		}
		StringBuilder stringBuilder = buildSelect();
		
		buildFrom(personSearchRequest,stringBuilder);
		
		buildJoins(personSearchRequest,stringBuilder);
		
		buildWhere(personSearchRequest, filterTracker, stringBuilder);
		
		Map<String,Object> params = getBindParams(personSearchRequest, currentTerm);
		
		Pair<Long,Query> querySet =  personSearchRequest
				.getSortAndPage()
				.applySortingAndPagingToPagedQuery(sessionFactory.getCurrentSession(), stringBuilder, false, null, true, params);
		
		
		querySet.getSecond().setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				PersonSearchResult2.class, "person_"));
		 return new PagingWrapper<PersonSearchResult2>(querySet.getFirst(), querySet.getSecond().list());
	}

	private StringBuilder buildSelect(){
		StringBuilder stringBuilder = new StringBuilder();
		/*stringBuilder.append(" select distinct dp.id as person_id, dp.personId as person_personId, dp.firstName as person_firstName, " +
				"dp.middleName as person_middleName, " +
				"dp.lastName as person_lastName, " +
				"dp.schoolId as person_schoolId, " +
				
			;*/
		stringBuilder.append(" select distinct " +
				 "dp.schoolId as person_schoolId," +
				 "dp.firstName as person_firstName, " +
				 "dp.middleName as person_middleName, " +
				 "dp.lastName as person_lastName, " +
				 "dp.personId as person_id, " +
				 "dp.studentIntakeCompleteDate as person_studentIntakeCompleteDate, " +
				 "dp.birthDate as person_birthDate, " +
				 "dp.studentTypeName as person_studentTypeName, " +
				 "dp.programStatusName as person_currentProgramStatusName, " +
				 "dp.activeAlertsCount as person_activeAlerts, " +
				 "dp.closedAlertsCount as person_closedAlerts, " +
				 "dp.coachFirstName as person_coachFirstName, " +
				 "dp.coachLastName as person_coachLastName, " +
				 "dp.coachId as person_coachId, " +
				 "dp.photoUrl as person_photoUrl ");
		return stringBuilder;
	}
	private void buildWhere(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		buildSchoolId(personSearchRequest, filterTracker, stringBuilder);
		buildFirstName(personSearchRequest, filterTracker, stringBuilder);
		buildLastName(personSearchRequest, filterTracker, stringBuilder);
		// currentlyRegistered 
		buildCurrentlyRegistered(personSearchRequest, filterTracker,stringBuilder);
		
		// coach && myCaseload
		buildCoach(personSearchRequest, filterTracker, stringBuilder);		
		
		// programStatus
		buildProgramStatus(personSearchRequest, filterTracker, stringBuilder);	
		
		// personTableType
		buildPersonTableType(personSearchRequest, filterTracker,  stringBuilder);
		
		// specialServiceGroup
		buildSpecialServiceGroup( personSearchRequest, filterTracker, stringBuilder);
		
		//declaredMajor
		buildDeclaredMajor(personSearchRequest, filterTracker, stringBuilder);
		
		//gpa
		buildGpa(personSearchRequest, filterTracker, stringBuilder);
		
		//credit hours earned
		buildCreditHours(personSearchRequest, filterTracker, stringBuilder);
		
		//mapStatus
		buildMapStatus(personSearchRequest, filterTracker,stringBuilder);
		
		//planStatus
		buildPlanStatus(personSearchRequest,filterTracker, stringBuilder);
		  
		//financialAidStatus
		buildFinancialAidStatus(personSearchRequest,filterTracker, stringBuilder);
		
		//myPlans
		buildMyPlans(personSearchRequest,filterTracker, stringBuilder);
		
		//birthDate
		buildBirthDate(personSearchRequest,filterTracker, stringBuilder);
		
		buildEarlyAlertCriteria(personSearchRequest,filterTracker, stringBuilder);
		
		addProgramStatusRequired(personSearchRequest, filterTracker, stringBuilder);
		
		//appendAndOrWhere(stringBuilder, filterTracker);
		//stringBuilder.append(" dp.studentType is not null ");
		//stringBuilder.append(" and dp.objectStatus = :activeObjectStatus ");
		//stringBuilder.append(" and dp.programStatus IS NOT NULL");
	}


	private void buildBirthDate(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasBirthDate(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.birthDate = :birthDate ");
		}
		
	}

	private boolean hasBirthDate(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getBirthDate() != null;
	}
	
	private void buildEarlyAlertCriteria(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(requiresActiveEarlyAlerts(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.activeAlertsCount > 0 ");
			if(personSearchRequest.getEarlyAlertResponseLate().equals(PersonSearchRequest.EARLY_ALERT_RESPONSE_RESPONSE_OVERDUE) )
			{
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.earlyAlertResponseDueCount > 0 ");
			}
			if(personSearchRequest.getEarlyAlertResponseLate().equals(PersonSearchRequest.EARLY_ALERT_RESPONSE_RESPONSE_CURRENT) )
			{
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.earlyAlertResponseCurrentCount > 0 ");
			}
		}
	}
	
	private Boolean requiresActiveEarlyAlerts(PersonSearchRequest personSearchRequest){
		return personSearchRequest.getEarlyAlertResponseLate() != null;
	}


	private void buildMyPlans(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasMyPlans(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" plan.owner = :owner ");
			
		}
	}
	
	private void addProgramStatusRequired(PersonSearchRequest personSearchRequest, FilterTracker filterTracker, StringBuilder stringBuilder){
		if(personRequired(personSearchRequest)){
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.programStatusName is not null and dp.programStatusName <> '' and dp.personId = p.id ");
		}
	}


	private boolean hasMyPlans(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getMyPlans() != null && personSearchRequest.getMyPlans();
	}


	private void buildFinancialAidStatus(
			PersonSearchRequest personSearchRequest, FilterTracker filterTracker,
			StringBuilder stringBuilder) {
		
		if(hasFinancialAidStatus(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.sapStatusCode = :sapStatusCode ");
		}
		
	}


	private boolean hasFinancialAidStatus(
			PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSapStatusCode() != null;
	}
	
	private void buildPersonTableType(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder){
		if(this.hasPersonTableType(personSearchRequest)){
			if(personSearchRequest.getPersonTableType().equals(personSearchRequest.PERSON_TABLE_TYPE_SSP_ONLY)){
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.personId IS NOT NULL ");
			}else if(personSearchRequest.getPersonTableType().equals(personSearchRequest.PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY)){
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.personId IS NULL ");
			}
		}
	}


	private void buildProgramStatus(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasProgramStatus(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.programStatusName = :programStatusName ");
		}
		
		
	}


	private boolean hasProgramStatus(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getProgramStatus() != null;
	}
	
	private boolean hasPersonTableType(PersonSearchRequest personSearchRequest){
		return StringUtils.isNotBlank(personSearchRequest.getPersonTableType());
	}
	
	private void buildSpecialServiceGroup(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasSpecialServiceGroup(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" specialServiceGroup = :specialServiceGroup and specialServiceGroup is not null ");
		}
	}


	private boolean hasSpecialServiceGroup(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSpecialServiceGroup() != null;
	}

	
	private Map<String, Object> getBindParams(PersonSearchRequest personSearchRequest, Term currentTerm) 
	{
		HashMap<String,Object> params= new HashMap<String,Object>();
		if(hasSchoolId(personSearchRequest)) {
			params.put("schoolId",personSearchRequest.getSchoolId().trim().toUpperCase());
		}
		
		if(hasFirstName(personSearchRequest)) {
			params.put("firstName",personSearchRequest.getFirstName().trim().toUpperCase() + "%");
		}
		
		if(hasLastName(personSearchRequest)) {
			params.put("lastName",personSearchRequest.getLastName().trim().toUpperCase() + "%");
		}

		if(hasPlanStatus(personSearchRequest))
		{
			params.put("planObjectStatus", 
					PersonSearchRequest.PLAN_STATUS_ACTIVE.equals(personSearchRequest.getPlanStatus()) 
					? ObjectStatus.ACTIVE : ObjectStatus.INACTIVE);
		}
		if(hasMapStatus(personSearchRequest))
		{ 
			PlanStatus param = null;
			if(PersonSearchRequest.MAP_STATUS_ON_PLAN.equals(personSearchRequest.getMapStatus()))
			{
				param = PlanStatus.ON;
			}
			if(PersonSearchRequest.MAP_STATUS_OFF_PLAN.equals(personSearchRequest.getMapStatus()))
			{
				param = PlanStatus.OFF;
			}
			if(PersonSearchRequest.MAP_STATUS_ON_TRACK_SEQUENCE.equals(personSearchRequest.getMapStatus()))
			{
				param = PlanStatus.ON_TRACK_SEQUENCE;
			}
			if(PersonSearchRequest.MAP_STATUS_ON_TRACK_SUBSTITUTION.equals(personSearchRequest.getMapStatus()))
			{
				param = PlanStatus.ON_TRACK_SUBSTITUTIO;
			}			
			params.put("mapStatus",param);
		}
		
		if(hasGpaCriteria(personSearchRequest))
		{
			if(personSearchRequest.getGpaEarnedMin() != null)
			{
				params.put("gpaEarnedMin", personSearchRequest.getGpaEarnedMin());
			}
			if(personSearchRequest.getGpaEarnedMax() != null)
			{
				params.put("gpaEarnedMax", personSearchRequest.getGpaEarnedMax());
			}			
		}
		
		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			Person coach = personSearchRequest.getMyCaseload() != null && personSearchRequest.getMyCaseload() ? securityService.currentlyAuthenticatedUser().getPerson() : personSearchRequest.getCoach();
			params.put("coachId", coach.getId());
		}
		
		if(hasDeclaredMajor(personSearchRequest))
		{
			params.put("declaredMajor", personSearchRequest.getDeclaredMajor());
		}
		
		if(hasHoursEarnedCriteria(personSearchRequest))
		{
			if(personSearchRequest.getHoursEarnedMin() != null)
			{
				params.put("hoursEarnedMin", personSearchRequest.getHoursEarnedMin());
			}
			if(personSearchRequest.getHoursEarnedMax() != null )
			{
				params.put("hoursEarnedMax", personSearchRequest.getHoursEarnedMax());
			}	
		}
		
		if(hasProgramStatus(personSearchRequest))
		{
			params.put("programStatusName", personSearchRequest.getProgramStatus().getName());
		}
		
		if(hasSpecialServiceGroup(personSearchRequest))
		{
			params.put("specialServiceGroup", personSearchRequest.getSpecialServiceGroup());
		}
		
		if(hasFinancialAidStatus(personSearchRequest))
		{
			params.put("sapStatusCode", personSearchRequest.getSapStatusCode());
		}
		
		if(hasMyPlans(personSearchRequest))
		{
			params.put("owner", securityService.currentlyAuthenticatedUser().getPerson());
		}
		
		if(hasBirthDate(personSearchRequest))
		{
			params.put("birthDate", personSearchRequest.getBirthDate());
		}		
		
		return params;
	}


	private boolean hasCoach(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getCoach() != null;
	}


	private void buildPlanStatus(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasPlanStatus(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" plan.objectStatus = :planObjectStatus and plan.person.id = dp.personId ");
		}
	}


	private void buildMapStatus(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasMapStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" esps.status = :mapStatus and esps.schoolId = dp.id ");
		}
		
		if(hasMapStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" msr.plan in elements(p.plans) ");
			stringBuilder.append(" and msr.planStatus = :mapStatus and msr.person.id = dp.personId ");
			
		}
	}


	private void buildCreditHours(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasHoursEarnedCriteria(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if(personSearchRequest.getHoursEarnedMax() != null && personSearchRequest.getHoursEarnedMin() != null)
			{
				stringBuilder.append(" dp.creditHoursEarned >= :hoursEarnedMin ");
				stringBuilder.append(" and dp.creditHoursEarned <= :hoursEarnedMax ");
			}
			if(personSearchRequest.getHoursEarnedMax() == null && personSearchRequest.getHoursEarnedMin() != null)
			{
				stringBuilder.append(" dp.creditHoursEarned >= :hoursEarnedMin ");
			}
			if(personSearchRequest.getHoursEarnedMax() != null && personSearchRequest.getHoursEarnedMin() == null)
			{
				stringBuilder.append(" dp.creditHoursEarned <= :hoursEarnedMax ");
			}	
		}
	}


	private void buildGpa(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasGpaCriteria(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if(personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() != null)
			{
				stringBuilder.append(" dp.gradePointAverage >= :gpaEarnedMin ");
				stringBuilder.append(" and dp.gradePointAverage <= :gpaEarnedMax ");
			}
			if(personSearchRequest.getGpaEarnedMax() == null && personSearchRequest.getGpaEarnedMin() != null)
			{
				stringBuilder.append(" dp.gradePointAverage >= :gpaEarnedMin ");
			}
			if(personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() == null)
			{
				stringBuilder.append(" dp.gradePointAverage <= :gpaEarnedMax ");
			}		

		}
	}


	private void buildDeclaredMajor(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasDeclaredMajor(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.declaredMajor = :declaredMajor");
		}
	}


	private void buildCoach(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.coachId = :coachId ");
		}
	}


	private boolean hasMyCaseload(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getMyCaseload() != null && personSearchRequest.getMyCaseload();
	}


	private void buildCurrentlyRegistered(PersonSearchRequest personSearchRequest, FilterTracker filterTracker,StringBuilder stringBuilder) 
	{
		if(hasCurrentlyRegistered(personSearchRequest))
		{
			if(personSearchRequest.getCurrentlyRegistered())
			{
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append("dp.currentRegistrationStatus > 0");
			}
		}
	}

	private void buildSchoolId(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		
		if(hasSchoolId(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" upper(dp.schoolId) = :schoolId ");
		}
	}
	private void buildLastName(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		
		if(hasLastName(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" upper(dp.lastName) like :lastName ");
		}
	}
	
	private void buildFirstName(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		
		if(hasFirstName(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" upper(dp.firstName) like :firstName ");
		}
	}


	private boolean hasSchoolId(PersonSearchRequest personSearchRequest) {
		return StringUtils.isNotBlank(personSearchRequest.getSchoolId());
	}
	
	private boolean hasFirstName(PersonSearchRequest personSearchRequest) {
		return StringUtils.isNotBlank(personSearchRequest.getFirstName());
	}
	
	private boolean hasLastName(PersonSearchRequest personSearchRequest) {
		return StringUtils.isNotBlank(personSearchRequest.getLastName());
	}


	private void buildJoins(PersonSearchRequest personSearchRequest,
			StringBuilder stringBuilder) 
	{
		
		if(hasMyPlans(personSearchRequest) || hasPlanStatus(personSearchRequest))
		{
			stringBuilder.append(" join p.plans as plan ");
		}	
		
		if(this.hasSpecialServiceGroup(personSearchRequest)){
			stringBuilder.append(" inner join p.specialServiceGroups as specialServiceGroups ");
			stringBuilder.append(" inner join specialServiceGroups.specialServiceGroup as specialServiceGroup ");
		}
	}

	private boolean hasPlanStatus(PersonSearchRequest personSearchRequest) 
	{
		return !StringUtils.isEmpty(personSearchRequest.getPlanStatus());
	}


	private boolean hasMapStatus(PersonSearchRequest personSearchRequest) {
		return !StringUtils.isEmpty(personSearchRequest.getMapStatus());
	}


	private boolean hasHoursEarnedCriteria(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getHoursEarnedMax() != null || personSearchRequest.getHoursEarnedMin() != null;
	}
	
	private boolean personRequired(PersonSearchRequest personSearchRequest){
		return hasSpecialServiceGroup(personSearchRequest) || hasPlanStatus(personSearchRequest) 
				|| hasMyPlans(personSearchRequest) || hasMapStatus(personSearchRequest);
	}

	private void buildFrom(PersonSearchRequest personSearchRequest, StringBuilder stringBuilder) 
	{
		ScheduledApplicationTaskStatus status = scheduledApplicationTaskService.getByName(ScheduledTaskWrapperServiceImpl.REFRESH_DIRECTORY_PERSON_TASK_NAME);
		
		boolean mv_activated = Boolean.parseBoolean(configService.getByNameEmpty(ACTIVATE_MATERIALIZED_DIRECTORY_PERSON_VIEW).trim());
		if(!mv_activated || status == null || status.getStatus() == null || status.getStatus().equals(ScheduledTaskStatus.RUNNING)){
			stringBuilder.append(" from ViewDirectoryPerson dp ");
		}else{
			stringBuilder.append(" from MaterializedDirectoryPerson dp ");
		}
		
		if(personRequired(personSearchRequest)){
			stringBuilder.append(", Person p");
		}
		
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasMapStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			stringBuilder.append(", ExternalPersonPlanStatus esps ");
		}
		if(hasMapStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			stringBuilder.append(", MapStatusReport msr ");
		}
	}

	private boolean hasGpaCriteria(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getGpaEarnedMax() != null || personSearchRequest.getGpaEarnedMax() != null;
	}


	private boolean hasDeclaredMajor(PersonSearchRequest personSearchRequest) 
	{
		return !StringUtils.isEmpty(personSearchRequest.getDeclaredMajor());
	}


	private boolean hasCurrentlyRegistered(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getCurrentlyRegistered() != null;
	}


	private void appendAndOrWhere(StringBuilder stringBuilder, FilterTracker filterTracker) 
	{
		if(!filterTracker.isFirstFilter())
		{
			stringBuilder.append(" and ");
		}
		else
		{
			stringBuilder.append(" where ");
		}
		filterTracker.setFirstFilter(false);
	}
	
	//Necessary due to the pass by value nature of booleans
	private class FilterTracker
	{
		private boolean isFirstFilter = true;

		public boolean isFirstFilter() {
			return isFirstFilter;
		}

		public void setFirstFilter(boolean isFirstFilter) {
			this.isFirstFilter = isFirstFilter;
		}
	}

}