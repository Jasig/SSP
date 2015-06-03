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
package org.jasig.ssp.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.internal.SessionFactoryImpl;
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.impl.ScheduledTaskWrapperServiceImpl;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.csvwriter.CaseloadCsvWriterHelper;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON started");
				Query query = sessionFactory.getCurrentSession().createSQLQuery("select REFRESH_MV_DIRECTORY_PERSON();");
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON ended successfully");
				query.list();
			}catch(Exception exp){
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON started after catch.");
				Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON;");
				query.list();
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON ended succesfully after catch.");
			}
		}else{
			LOGGER.info("REFRESH_MV_DIRECTORY_PERSON started");
			Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON;");
			query.list();
			LOGGER.info("REFRESH_MV_DIRECTORY_PERSON ended successfully");
		}
	}
	
	public void refreshDirectoryPersonBlue(){
		
		if(isPostgresSession()){
			try{
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE started");
				Query query = sessionFactory.getCurrentSession().createSQLQuery("select REFRESH_MV_DIRECTORY_PERSON_BLUE();");
				query.list();
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE ended");
			}catch(Exception exp){
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE started after catch");
				Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON_BLUE;");
				query.list();
				LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE ended successfully after catch.");
			}
		}else{
			LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE started");
			Query query = sessionFactory.getCurrentSession().createSQLQuery("exec REFRESH_MV_DIRECTORY_PERSON_BLUE;");
			query.list();
			LOGGER.info("REFRESH_MV_DIRECTORY_PERSON_BLUE ended successfully");
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
	 * @return List of people that match the specified filters
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<PersonSearchResult2> search(PersonSearchRequest personSearchRequest) {
		Pair<Long, Query> querySet = prepSearchQuery(sessionFactory.getCurrentSession(), personSearchRequest, false);
		querySet.getSecond().setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				PersonSearchResult2.class, "person_"));
		return new PagingWrapper<PersonSearchResult2>(querySet.getFirst(), querySet.getSecond().list());
	}

	private Pair<Long, Query> prepSearchQuery(Object session,
			PersonSearchRequest personSearchRequest, boolean fullResultSearch) {
		Term currentTerm;
		Map<String,Object> params = null;
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

		final String hqlSelect;

		if (fullResultSearch) {
			hqlSelect = buildFullSelect().toString();
		} else {
			hqlSelect = buildSelect().toString();
		}

		final StringBuilder hqlWithoutSelect = new StringBuilder();
		
		buildFrom(personSearchRequest,hqlWithoutSelect);
		
		buildJoins(personSearchRequest,hqlWithoutSelect);
		
		buildWhere(personSearchRequest, filterTracker, hqlWithoutSelect);
		
		params = getBindParams(personSearchRequest, currentTerm);
		
		Pair<Long,Query> querySet =  personSearchRequest
				.getSortAndPage()
				.applySortingAndPagingToPagedQuery(session, "dp.schoolId", hqlSelect,
						hqlWithoutSelect, false, null, false, params);
		
		
		querySet.getSecond().setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				PersonSearchResult2.class, "person_"));

		return querySet;
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
				"dp.primaryEmailAddress as person_primaryEmailAddress, " +
				"dp.personId as person_id, " +
				"dp.studentIntakeCompleteDate as person_studentIntakeCompleteDate, " +
				"dp.birthDate as person_birthDate, " +
				"dp.actualStartTerm as person_actualStartTerm, " +
				"dp.studentTypeName as person_studentTypeName, " +
				"dp.programStatusName as person_currentProgramStatusName, " +
				"dp.activeAlertsCount as person_activeAlerts, " +
				"dp.closedAlertsCount as person_closedAlerts, " +
				"dp.earlyAlertResponseDueCount as person_numberEarlyAlertResponsesRequired, " +
				"dp.coachFirstName as person_coachFirstName, " +
				"dp.coachLastName as person_coachLastName, " +
				"dp.coachId as person_coachId, " +
				"dp.photoUrl as person_photoUrl ");
		return stringBuilder;
	}

	private StringBuilder buildFullSelect(){
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(" select distinct " +
				"dp.schoolId as person_schoolId," +
				"dp.username as person_username, " +
				"dp.firstName as person_firstName, " +
				"dp.middleName as person_middleName, " +
				"dp.lastName as person_lastName, " +
				"dp.primaryEmailAddress as person_primaryEmailAddress, " +
				"dp.secondaryEmailAddress as person_secondaryEmailAddress, " +
				"dp.personId as person_id, " +
				"dp.addressLine1 as person_addressLine1, " +
				"dp.addressLine2 as person_addressLine2, " +
				"dp.city as person_city, " +
				"dp.state as person_state, " +
				"dp.zipCode as person_zipCode, " +
				"dp.residencyCounty as person_residencyCounty, " +
				"dp.homePhone as person_homePhone, " +
				"dp.workPhone as person_workPhone, " +
				"dp.cellPhone as person_cellPhone, " +
				"dp.actualStartTerm as person_actualStartTerm, " +
				"dp.actualStartYear as person_actualStartYear, " +
				"dp.f1Status as person_f1Status, " +
				"dp.gradePointAverage as person_gradePointAverage, " +
				"dp.localGpa as person_localGpa, " +
				"dp.programGpa as person_programGpa, " +
				"dp.currentRegistrationStatus as person_currentRegistrationStatus, " +
				"dp.creditHoursEarned as person_creditHoursEarned, " +
				"dp.studentIntakeCompleteDate as person_studentIntakeCompleteDate, " +
				"dp.birthDate as person_birthDate, " +
				"dp.actualStartTerm as person_actualStartTerm, " +
				"dp.studentTypeName as person_studentTypeName, " +
				"dp.programStatusName as person_currentProgramStatusName, " +
				"dp.activeAlertsCount as person_activeAlerts, " +
				"dp.closedAlertsCount as person_closedAlerts, " +
				"dp.earlyAlertResponseDueCount as person_numberEarlyAlertResponsesRequired, " +
				"dp.coachFirstName as person_coachFirstName, " +
				"dp.coachLastName as person_coachLastName, " +
				"dp.coachId as person_coachId, " +
				"dp.coachSchoolId as person_coachSchoolId, " +
				"dp.photoUrl as person_photoUrl ");

		return stringBuilder;
	}

	private void buildWhere(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		buildPersonObjectStatus(personSearchRequest, filterTracker, stringBuilder);
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
		
		//planStatus
		buildPlanStatus(personSearchRequest, filterTracker,stringBuilder);
		
		//planExists
		buildPlanExists(personSearchRequest,filterTracker, stringBuilder);
		  
		//financialAidStatus
		buildFinancialAidStatus(personSearchRequest,filterTracker, stringBuilder);
		
		//myPlans
		buildMyPlans(personSearchRequest,filterTracker, stringBuilder);
		
		//birthDate
		buildBirthDate(personSearchRequest,filterTracker, stringBuilder);

        //actualStartTerm
        buildActualStartTerm(personSearchRequest, filterTracker, stringBuilder);
		
		buildEarlyAlertCriteria(personSearchRequest,filterTracker, stringBuilder);
		
		//watchList
		buildWatchList(personSearchRequest,filterTracker, stringBuilder);
		
		addProgramStatusRequired(personSearchRequest, filterTracker, stringBuilder);
	}
	
	private void buildPersonObjectStatus(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(requiresObjectStatus(personSearchRequest))
		{
			if(sspPersonOnly(personSearchRequest)){
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.objectStatus = :personObjectStatus ");
			}else{
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" (dp.objectStatus = :personObjectStatus or dp.objectStatus is NULL) ");
			}
		}
	}
	
	
	private Boolean sspPersonOnly(PersonSearchRequest personSearchRequest){
		return personSearchRequest.getPersonTableType() != null && personSearchRequest.getPersonTableType().equals(personSearchRequest.PERSON_TABLE_TYPE_SSP_ONLY);
	}
	private Boolean requiresObjectStatus(PersonSearchRequest personSearchRequest){
		if(personSearchRequest.getPersonTableType() != null && personSearchRequest.getPersonTableType().equals(personSearchRequest.PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY))
			return false;
		return true;
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

    private void buildActualStartTerm(PersonSearchRequest personSearchRequest, FilterTracker filterTracker,
                                      StringBuilder stringBuilder) {
        if( hasActualStartTerm(personSearchRequest) ) {
            appendAndOrWhere(stringBuilder, filterTracker);
            stringBuilder.append(" dp.actualStartTerm in (:actualStartTerm) ");
        }
    }

    private boolean hasActualStartTerm(PersonSearchRequest personSearchRequest) {
    	List<String> startTermList = personSearchRequest.getActualStartTerm();
    	return (startTermList != null) && (startTermList.size() > 0);
        //return (StringUtils.isNotBlank(personSearchRequest.getActualStartTerm()));
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
				appendAndOrWhere(stringBuilder,filterTracker);
				stringBuilder.append(" dp.earlyAlertResponseDueCount <= 0 ");
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
			stringBuilder.append(" dp.sapStatusCode in (:sapStatusCode)");
		}
		
	}


	private boolean hasFinancialAidStatus(
			PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSapStatusCode() != null && !personSearchRequest.getSapStatusCode().isEmpty();
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
			stringBuilder.append(" dp.programStatusName in (:programStatusName) ");
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
			stringBuilder.append(" specialServiceGroups.objectStatus = 1 and specialServiceGroup in (:specialServiceGroup) and specialServiceGroup is not null ");
		}
	}


	private boolean hasSpecialServiceGroup(PersonSearchRequest personSearchRequest) {
		return (personSearchRequest.getSpecialServiceGroup() != null && !(personSearchRequest.getSpecialServiceGroup().size() <= 0) );
	}
	
	private void buildWatchList(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasAnyWatchCriteria(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" ws.person.id = :watcherId ");
			stringBuilder.append(" and ws.student.id = dp.personId ");			
		}
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

		if(hasPlanExists(personSearchRequest))
		{
			// otherwise the conditional is handled structurally (exists test)
			if (PersonSearchRequest.PLAN_EXISTS_ACTIVE.equals(personSearchRequest.getPlanExists())) {
				params.put("planObjectStatus",ObjectStatus.ACTIVE);
			} else if (PersonSearchRequest.PLAN_EXISTS_INACTIVE.equals(personSearchRequest.getPlanExists())) {
				params.put("planObjectStatus", ObjectStatus.INACTIVE);
			} else if (PersonSearchRequest.PLAN_EXISTS_NONE.equals(personSearchRequest.getPlanExists())) {
				// this is handled structurally (exists test)
			} else {
				params.put("planObjectStatus",null);
			}
		}
		if(hasPlanStatus(personSearchRequest))
		{ 
			PlanStatus param = null;
			if(PersonSearchRequest.PLAN_STATUS_ON_PLAN.equals(personSearchRequest.getPlanStatus()))
			{
				param = PlanStatus.ON;
			}
			if(PersonSearchRequest.PLAN_STATUS_OFF_PLAN.equals(personSearchRequest.getPlanStatus()))
			{
				param = PlanStatus.OFF;
			}
			if(PersonSearchRequest.PLAN_STATUS_ON_TRACK_SEQUENCE.equals(personSearchRequest.getPlanStatus()))
			{
				param = PlanStatus.ON_TRACK_SEQUENCE;
			}
			if(PersonSearchRequest.PLAN_STATUS_ON_TRACK_SUBSTITUTION.equals(personSearchRequest.getPlanStatus()))
			{
				param = PlanStatus.ON_TRACK_SUBSTITUTION;
			}			
			params.put("planStatus",param);
		}
		
		if (hasAnyGpaCriteria(personSearchRequest)) {
			if (personSearchRequest.getGpaEarnedMin() != null) {
				params.put("gpaEarnedMin", personSearchRequest.getGpaEarnedMin());
			}
			if (personSearchRequest.getGpaEarnedMax() != null) {
				params.put("gpaEarnedMax", personSearchRequest.getGpaEarnedMax());
			}

            if (personSearchRequest.getLocalGpaMin() != null) {
                params.put("localGpaMin", personSearchRequest.getLocalGpaMin());
            }
            if (personSearchRequest.getLocalGpaMax() != null) {
                params.put("localGpaMax", personSearchRequest.getLocalGpaMax());
            }

            if (personSearchRequest.getProgramGpaMin() != null) {
                params.put("programGpaMin", personSearchRequest.getProgramGpaMin());
            }
            if (personSearchRequest.getProgramGpaMax() != null) {
                params.put("programGpaMax", personSearchRequest.getProgramGpaMax());
            }
		}

		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			Person me = null;
			List<Person> coaches = null;
			if ( hasMyCaseload(personSearchRequest) ) {
				me = securityService.currentlyAuthenticatedUser().getPerson();
			}
			if ( hasCoach(personSearchRequest) ) {
				coaches = personSearchRequest.getCoach();
			}

			UUID queryPersonId = null;
			List<Person> compareTo = null;
			if ( me != null ) {
				queryPersonId = me.getId();
				compareTo = coaches;
			} else if ( coaches != null ) {
				//queryPersonId = coaches.getId();
				//compareTo = me;
			}
			// If me and coach aren't the same, the query is non-sensical, so set the 'queryPersonId' to null which
			// will effectively force the query to return no results.
			//if ( queryPersonId != null && compareTo != null ) {
			//	for(Person p: compareTo) {
					
			//	}
			//	queryPersonId = queryPersonId.equals(compareTo.getId()) ? queryPersonId : null;
			//	queryPersonId = queryPersonId.equals(compareTo.getId()) ? queryPersonId : null;
			//}
			List<UUID> coachIds = new ArrayList<UUID>();
			//List<Person> coaches = personSearchRequest.getCoach();
			for(Person coach: coaches) {
				coachIds.add(coach.getId());
			}
			params.put("coachId", coachIds);
		}

		if(hasAnyWatchCriteria(personSearchRequest))
		{
			Person me = null;
			Person watcher = null;
			if ( hasMyWatchList(personSearchRequest) ) {
				me = securityService.currentlyAuthenticatedUser().getPerson();
			}
			if ( hasWatcher(personSearchRequest) ) {
				watcher = personSearchRequest.getWatcher();
			}

			UUID queryPersonId = null;
			Person compareTo = null;
			if ( me != null ) {
				queryPersonId = me.getId();
				compareTo = watcher;
			} else if ( watcher != null ) {
				queryPersonId = watcher.getId();
				compareTo = me;
			}
			// If me and watcher aren't the same, the query is non-sensical, so set the 'queryPersonId' to null which
			// will effectively force the query to return no results.
			if ( queryPersonId != null && compareTo != null ) {
				queryPersonId = queryPersonId.equals(compareTo.getId()) ? queryPersonId : null;
			}
			params.put("watcherId", queryPersonId);
		}

		if(hasDeclaredMajor(personSearchRequest))
		{
			params.put("programCode", personSearchRequest.getDeclaredMajor());
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
		
		
		//String hql = "from A where name in (:names)";
		//Query q = s.createQuery(hql);
		//q.setParameterList("names", l);
		
		if(hasProgramStatus(personSearchRequest))
		{
			params.put("programStatusName", personSearchRequest.getProgramStatusNames());
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
		
		if(requiresObjectStatus(personSearchRequest)){
			params.put("personObjectStatus", ObjectStatus.ACTIVE);
		}

        if(hasActualStartTerm(personSearchRequest)) {
            params.put("actualStartTerm", personSearchRequest.getActualStartTerm());
        }
		
		return params;
	}


	private boolean hasCoach(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getCoach() != null && personSearchRequest.getCoach().size()>0;
	}


	private void buildPlanExists(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder)
	{
		if(hasPlanExists(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if ( PersonSearchRequest.PLAN_EXISTS_NONE.equals(personSearchRequest.getPlanExists()) ) {
				stringBuilder.append(" not exists elements(p.plans) ");
			} else {
				stringBuilder.append(" plan.objectStatus = :planObjectStatus and plan.person.id = dp.personId ");
			}
			if ( PersonSearchRequest.PLAN_EXISTS_INACTIVE.equals(personSearchRequest.getPlanExists()) ) {
				stringBuilder.append(" and not exists ( from Plan as myPlans where myPlans.person.id = dp.personId and myPlans.objectStatus = "
						+ ObjectStatus.ACTIVE.ordinal() + " ) ");
			}
		}
	}


	private void buildPlanStatus(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder)
	{
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasPlanStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" esps.status = :planStatus and esps.schoolId = dp.schoolId ");
		}
		
		if(hasPlanStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" msr.plan in elements(p.plans) ");
			stringBuilder.append(" and msr.planStatus = :planStatus and msr.person.id = dp.personId ");
			
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


	private void buildGpa(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) {
		if (hasAnyGpaCriteria(personSearchRequest)) {
			appendAndOrWhere(stringBuilder,filterTracker);
            boolean appendAnd = false;

            if (personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() != null) {
				stringBuilder.append(" dp.gradePointAverage >= :gpaEarnedMin ");
				stringBuilder.append(" and dp.gradePointAverage <= :gpaEarnedMax ");
                appendAnd = true;
			}
			if (personSearchRequest.getGpaEarnedMax() == null && personSearchRequest.getGpaEarnedMin() != null) {
				stringBuilder.append(" dp.gradePointAverage >= :gpaEarnedMin ");
                appendAnd = true;
			}
			if (personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() == null) {
				stringBuilder.append(" dp.gradePointAverage <= :gpaEarnedMax ");
                appendAnd = true;
			}


            if ((personSearchRequest.getLocalGpaMax() != null || personSearchRequest.getLocalGpaMin() != null) && appendAnd) {
                stringBuilder.append(" and ");
            }
            if (personSearchRequest.getLocalGpaMax() != null && personSearchRequest.getLocalGpaMin() != null ) {
                stringBuilder.append(" dp.localGpa >= :localGpaMin ");
                stringBuilder.append(" and dp.localGpa <= :localGpaMax ");
                appendAnd = true;
            }
            if (personSearchRequest.getLocalGpaMax() == null && personSearchRequest.getLocalGpaMin() != null) {
                stringBuilder.append(" dp.localGpa >= :localGpaMin ");
                appendAnd = true;
            }
            if (personSearchRequest.getLocalGpaMax() != null && personSearchRequest.getLocalGpaMin() == null) {
                stringBuilder.append(" dp.localGpa <= :localGpaMax ");
                appendAnd = true;
            }


            if ((personSearchRequest.getProgramGpaMax() != null || personSearchRequest.getProgramGpaMin() != null) && appendAnd) {
                stringBuilder.append(" and ");
            }
            if (personSearchRequest.getProgramGpaMax() != null && personSearchRequest.getProgramGpaMin() != null) {
                stringBuilder.append(" dp.programGpa >= :programGpaMin ");
                stringBuilder.append(" and dp.programGpa <= :programGpaMax ");
            }
            if (personSearchRequest.getProgramGpaMax() == null && personSearchRequest.getProgramGpaMin() != null) {
                stringBuilder.append(" dp.programGpa >= :programGpaMin ");
            }
            if (personSearchRequest.getProgramGpaMax() != null && personSearchRequest.getProgramGpaMin() == null) {
                stringBuilder.append(" dp.programGpa <= :programGpaMax ");
            }
		}
	}
	
	private void buildDeclaredMajor(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasDeclaredMajor(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" esap.programCode in (:programCode) ");
			stringBuilder.append(" and esap.schoolId = dp.id ");
		}
	}


	private void buildCoach(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		
		LOGGER.debug("HAS COACH");
		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" dp.coachId in (:coachId) ");
		}
		LOGGER.debug("END HAS COACH");
	}


	private boolean hasMyCaseload(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getMyCaseload() != null && personSearchRequest.getMyCaseload();
	}


	private void buildCurrentlyRegistered(PersonSearchRequest personSearchRequest, FilterTracker filterTracker,StringBuilder stringBuilder) 
	{
		if(hasCurrentlyRegistered(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder, filterTracker);
			if(personSearchRequest.getCurrentlyRegistered())
			{
				stringBuilder.append("dp.currentRegistrationStatus > 0");
			} else {
				stringBuilder.append(" (dp.currentRegistrationStatus is null or dp.currentRegistrationStatus <= 0) ");
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
		
		if(hasMyPlans(personSearchRequest) || hasPlanExists(personSearchRequest))
		{
			if ( hasPlanExists(personSearchRequest) && PersonSearchRequest.PLAN_EXISTS_NONE.equals(personSearchRequest.getPlanExists()) ) {
				stringBuilder.append(" left join p.plans as plan ");
			} else {
				stringBuilder.append(" join p.plans as plan ");
			}
		}
		
		if(this.hasSpecialServiceGroup(personSearchRequest)){
			stringBuilder.append(" inner join p.specialServiceGroups as specialServiceGroups ");
			stringBuilder.append(" inner join specialServiceGroups.specialServiceGroup as specialServiceGroup ");
		}
	}

	private boolean hasPlanExists(PersonSearchRequest personSearchRequest)
	{
		//return personSearchRequest.getPlanExists() != null && !personSearchRequest.getPlanExists().isEmpty();
		return !StringUtils.isEmpty(personSearchRequest.getPlanExists());
	}


	private boolean hasPlanStatus(PersonSearchRequest personSearchRequest) {
		return !StringUtils.isEmpty(personSearchRequest.getPlanStatus());
	}


	private boolean hasHoursEarnedCriteria(PersonSearchRequest personSearchRequest) 
	{
		return personSearchRequest.getHoursEarnedMax() != null || personSearchRequest.getHoursEarnedMin() != null;
	}
	
	private boolean personRequired(PersonSearchRequest personSearchRequest){
		return hasSpecialServiceGroup(personSearchRequest) || hasPlanExists(personSearchRequest)
				|| hasMyPlans(personSearchRequest) || hasPlanStatus(personSearchRequest);
	}

	private Boolean buildFrom(PersonSearchRequest personSearchRequest, StringBuilder stringBuilder) 
	{
		ScheduledApplicationTaskStatus status = scheduledApplicationTaskService.getByName(ScheduledTaskWrapperServiceImpl.REFRESH_DIRECTORY_PERSON_TASK_NAME);
		ScheduledApplicationTaskStatus status_blue = scheduledApplicationTaskService.getByName(ScheduledTaskWrapperServiceImpl.REFRESH_DIRECTORY_PERSON_BLUE_TASK_NAME);
		
		if(status != null && status.getStatus() != null && status.getStatus().equals(ScheduledTaskStatus.COMPLETED)){
			stringBuilder.append(" from MaterializedDirectoryPerson dp ");
		}else if(status_blue != null && status_blue.getStatus() != null && status_blue.getStatus().equals(ScheduledTaskStatus.COMPLETED)){
			stringBuilder.append(" from MaterializedDirectoryPersonBlue dp ");
		}else
			return false;
		
		if(personRequired(personSearchRequest)){
			stringBuilder.append(", Person p");
		}
		
		if(hasDeclaredMajor(personSearchRequest))
		{
			stringBuilder.append(", ExternalStudentAcademicProgram esap ");
		}
		
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasPlanStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			stringBuilder.append(", ExternalPersonPlanStatus esps ");
		}
		if(hasPlanStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			stringBuilder.append(", MapStatusReport msr ");
		}
		if(hasAnyWatchCriteria(personSearchRequest))
		{
			stringBuilder.append(", WatchStudent ws ");
		}		
		
		return true;
	}

	private boolean hasMyWatchList(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getMyWatchList() != null && personSearchRequest.getMyWatchList();
	}

	private boolean hasWatcher(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getWatcher() != null;
	}

	private boolean hasAnyWatchCriteria(PersonSearchRequest personSearchRequest) {
		return hasMyWatchList(personSearchRequest) || hasWatcher(personSearchRequest);
	}

	private boolean hasAnyGpaCriteria(PersonSearchRequest personSearchRequest) {
		return (personSearchRequest.getGpaEarnedMin() != null || personSearchRequest.getGpaEarnedMax() != null ||
                personSearchRequest.getLocalGpaMin() != null || personSearchRequest.getLocalGpaMax() != null ||
                personSearchRequest.getProgramGpaMin() != null || personSearchRequest.getProgramGpaMax() != null);
	}

	private boolean hasDeclaredMajor(PersonSearchRequest personSearchRequest) 
	{
		List<String> declaredMajors = personSearchRequest.getDeclaredMajor();
		return (declaredMajors != null) && (declaredMajors.size() > 0);
		//return !StringUtils.isEmpty(personSearchRequest.getDeclaredMajor());
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

	public List<PersonSearchResultFull> exportableCustomizableSearch(PersonSearchRequest personSearchRequest) throws IOException {

		StatelessSession openStatelessSession = null;

		try {
			openStatelessSession = sessionFactory.openStatelessSession();
			Pair<Long, Query> querySet = prepSearchQuery(openStatelessSession, personSearchRequest, true);

			querySet.getSecond().setResultTransformer(new NamespacedAliasToBeanResultTransformer(
					PersonSearchResultFull.class, "person_"));

			querySet.getSecond().getQueryString();

			Query query = querySet.getSecond().setFetchSize(10).setReadOnly(true);

			return query.list();

		} finally {
			if ( openStatelessSession != null ) {
				try {
					openStatelessSession.close();
				} catch (Exception e) {
					// nothing to do and likely harmless
					LOGGER.info("Failed to close Hibernate StatelessSession, Customizable Exporter", e);
				}
			}
		}
	 }

	public Long getCaseloadCountFor(PersonSearchRequest personSearchRequest, SortingAndPaging buildSortAndPage) {
		
		Pair<Long, Query> querySet = prepSearchQuery(sessionFactory.getCurrentSession(),personSearchRequest, false);

		return querySet.getFirst();
	}

	public void exportableSearch(
			CaseloadCsvWriterHelper csvWriterHelper, PersonSearchRequest personSearchRequest) throws IOException {

		StatelessSession openStatelessSession = null;

		try {
			openStatelessSession = sessionFactory.openStatelessSession();
			Pair<Long, Query> querySet = prepSearchQuery(openStatelessSession, personSearchRequest, false);

			querySet.getSecond().setResultTransformer(new NamespacedAliasToBeanResultTransformer(
					PersonSearchResult2.class, "person_"));

			querySet.getSecond().getQueryString();

			Query query = querySet.getSecond().setFetchSize(10).setReadOnly(true);

			ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);

			csvWriterHelper.write(results, -1L);
		} finally {
			if ( openStatelessSession != null ) {
				try {
					openStatelessSession.close();
				} catch (Exception e) {
					// nothing to do and likely harmless
					LOGGER.info("Failed to close Hibernate StatelessSession", e);
				}
			}
		}
	}
}
