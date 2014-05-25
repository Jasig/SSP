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
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StringType;
import org.jasig.ssp.model.DirectoryPerson;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

/**
 * PersonSearch DAO
 */
@Repository
public class PersonSearchDao extends AbstractDao<Person> {

	public PersonSearchDao() {
		super(Person.class);
	}

	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient TermService termService;
	
	@Autowired
	private transient SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSearchDao.class);


	FileWriter fileWriter;

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
	public PagingWrapper<Person> searchBy(
			@NotNull final ProgramStatus programStatus,
			final Boolean requireProgramStatus,
			final Boolean outsideCaseload, @NotNull final String searchTerm,
			final Person advisor, final SortingAndPaging sAndP) {

		if (StringUtils.isBlank(searchTerm)) {
			throw new IllegalArgumentException("search term must be specified");
		}

		final Criteria query = createCriteria();


		boolean isRequiringProgramStatus = programStatus != null ||
				requireProgramStatus == null ||
				Boolean.TRUE.equals(requireProgramStatus);
		JoinType programStatusJoinType =
				isRequiringProgramStatus ? JoinType.INNER_JOIN : JoinType.LEFT_OUTER_JOIN;
		query.createAlias("programStatuses", "personProgramStatus", programStatusJoinType);

		if (programStatus != null) {
			query.add(Restrictions.eq("personProgramStatus.programStatus",
					programStatus));
		}

		if ((sAndP != null) && sAndP.isFilteredByStatus()) {
			query.add(Restrictions
					.isNull("personProgramStatus.expirationDate"));
		}

		if (Boolean.FALSE.equals(outsideCaseload)) {
			query.add(Restrictions.eq("coach", advisor));
		}

		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		final Disjunction terms = Restrictions.disjunction();

		final String searchTermLowercase = searchTerm.toLowerCase(Locale
				.getDefault());
		terms.add(Restrictions.ilike("firstName", searchTermLowercase,
				MatchMode.ANYWHERE));
		terms.add(Restrictions.ilike("lastName", searchTermLowercase,
				MatchMode.ANYWHERE));
		terms.add(Restrictions.ilike("schoolId", searchTermLowercase,
				MatchMode.ANYWHERE));

		terms.add(Restrictions
				.sqlRestriction(
						"lower({alias}.first_name) "
								+ configService.getDatabaseConcatOperator()
								+ " ' ' "
								+ configService.getDatabaseConcatOperator()
								+ " lower({alias}.last_name) like ? ",
						searchTermLowercase, new StringType()));

		query.add(terms);

		// eager load program status
		query.setFetchMode("personProgramStatus", FetchMode.JOIN);
		query.setFetchMode("personProgramStatus.programStatus", FetchMode.JOIN);

		final PagingWrapper<Object[]> results = processCriteriaWithSortingAndPaging(
				query, sAndP, true);

		final List<Person> people = Lists.newArrayList();
		for (Object[] personAndProgramStatus : results) {
			if ((personAndProgramStatus != null)
					&& (personAndProgramStatus.length > 0)) {
				if (personAndProgramStatus[0] instanceof Person) {
					people.add((Person) personAndProgramStatus[0]);
				} else if (personAndProgramStatus[1] instanceof Person) {
					people.add((Person) personAndProgramStatus[1]);
				}
			}
		}

		return new PagingWrapper<Person>(results.getResults(), people);
	}

	
	@SuppressWarnings("unchecked")
	public List<PersonSearchResult2> search(PersonSearchRequest personSearchRequest)
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
		
		Query query = createHqlQuery(stringBuilder.toString());
		
		addBindParams(personSearchRequest,query,currentTerm);
		query.setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				PersonSearchResult2.class, "person_"));
		return query.list();
	}

	private StringBuilder buildSelect(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" select distinct p.id as person_id, p.firstName as person_firstName, " +
				"p.middleName as person_middleName, " +
				"p.lastName as person_lastName, " +
				"p.schoolId as person_schoolId, " +
				"programStatus.name as person_currentProgramStatusName, " +
				"p.coach.firstName as person_coachFirstName, " +
				"p.coach.lastName as person_coachLastName, " +
				"p.coach.id as person_coachId, " +
				"p.studentIntakeCompleteDate as person_studentIntakeCompleteDate, " +
				"p.birthDate as person_birthDate, " +
				"p.studentType.name as person_studentTypeName, " +
				"p.photoUrl as person_photoUrl");
		return stringBuilder;
	}

	private void buildWhere(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		buildStudentIdOrName(personSearchRequest, filterTracker, stringBuilder);
		
		// currentlyRegistered 
		buildCurrentlyRegistered(personSearchRequest, filterTracker,stringBuilder);
		
		// coach && myCaseload
		buildCoach(personSearchRequest, filterTracker, stringBuilder);		
		
		// programStatus
		buildProgramStatus(personSearchRequest, filterTracker, stringBuilder);	
		
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
		
		appendAndOrWhere(stringBuilder, filterTracker);
		stringBuilder.append(" p.studentType is not null ");
		stringBuilder.append(" and p.objectStatus = :activeObjectStatus ");
		stringBuilder.append(" and programStatuses.expirationDate IS NULL");
	}


	private void buildBirthDate(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasBirthDate(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" p.birthDate = :birthDate ");
		}
		
	}


	private boolean hasBirthDate(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getBirthDate() != null;
	}


	private void buildMyPlans(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasMyPlans(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" plan.owner = :owner ");
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
			stringBuilder.append(" esfa.sapStatusCode = :sapStatusCode ");
			stringBuilder.append(" and esfa.schoolId = p.schoolId ");
		}
		
	}


	private boolean hasFinancialAidStatus(
			PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSapStatusCode() != null;
	}


	private void buildProgramStatus(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasProgramStatus(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" programStatus = :programStatus ");
		}
		
		
	}


	private boolean hasProgramStatus(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getProgramStatus() != null;
	}
	
	private void buildSpecialServiceGroup(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		if(hasSpecialServiceGroup(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" specialServiceGroup = :specialServiceGroup ");
		}
		
		
	}


	private boolean hasSpecialServiceGroup(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSpecialServiceGroup() != null;
	}


	private void addBindParams(PersonSearchRequest personSearchRequest,
			Query query, Term currentTerm) 
	{
		if(hasStudentId(personSearchRequest)) {
			final String wildcardedStudentIdOrNameTerm = new StringBuilder("%")
					.append(personSearchRequest.getSchoolId().toUpperCase())
					.append("%")
					.toString();
			query.setString("studentIdOrName", wildcardedStudentIdOrNameTerm);
		}

		if(hasPlanStatus(personSearchRequest))
		{
			query.setInteger("planObjectStatus", 
					PersonSearchRequest.PLAN_STATUS_ACTIVE.equals(personSearchRequest.getPlanStatus()) 
					? ObjectStatus.ACTIVE.ordinal() : ObjectStatus.INACTIVE.ordinal());
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
			query.setString("mapStatus",param.name());
		}
		
		if(hasGpaCriteria(personSearchRequest))
		{
			if(personSearchRequest.getGpaEarnedMin() != null)
			{
				query.setBigDecimal("gpaEarnedMin", personSearchRequest.getGpaEarnedMin());
			}
			if(personSearchRequest.getGpaEarnedMax() != null)
			{
				query.setBigDecimal("gpaEarnedMax", personSearchRequest.getGpaEarnedMax());
			}			
		}
		
		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			Person coach = personSearchRequest.getMyCaseload() != null && personSearchRequest.getMyCaseload() ? securityService.currentlyAuthenticatedUser().getPerson() : personSearchRequest.getCoach();
			query.setEntity("coach", coach);
		}
		
		if(hasDeclaredMajor(personSearchRequest))
		{
			query.setString("programCode", personSearchRequest.getDeclaredMajor());
		}
		
		if(hasHoursEarnedCriteria(personSearchRequest))
		{
			if(personSearchRequest.getHoursEarnedMin() != null)
			{
				query.setBigDecimal("hoursEarnedMin", personSearchRequest.getHoursEarnedMin());
			}
			if(personSearchRequest.getHoursEarnedMax() != null )
			{
				query.setBigDecimal("hoursEarnedMax", personSearchRequest.getHoursEarnedMax());
			}	
		}
		
		if(hasProgramStatus(personSearchRequest))
		{
			query.setEntity("programStatus", personSearchRequest.getProgramStatus());
		}
		
		if(hasSpecialServiceGroup(personSearchRequest))
		{
			query.setEntity("specialServiceGroup", personSearchRequest.getSpecialServiceGroup());
		}
		
		if(hasFinancialAidStatus(personSearchRequest))
		{
			query.setString("sapStatusCode", personSearchRequest.getSapStatusCode());
		}
		
		if(hasCurrentlyRegistered(personSearchRequest))
		{
			query.setString("currentTerm", currentTerm.getCode());
		}
		
		if(hasMyPlans(personSearchRequest))
		{
			query.setEntity("owner", securityService.currentlyAuthenticatedUser().getPerson());
		}
		
		if(hasBirthDate(personSearchRequest))
		{
			query.setDate("birthDate", personSearchRequest.getBirthDate());
		}		
		
		query.setInteger("activeObjectStatus", ObjectStatus.ACTIVE.ordinal());
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
			stringBuilder.append(" plan.objectStatus = :planObjectStatus ");
		}
	}


	private void buildMapStatus(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasMapStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" esps.status = :mapStatus ");
			stringBuilder.append(" and esps.schoolId = p.schoolId ");
		}
		
		if(hasMapStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" msr.plan in elements(p.plans) ");
			stringBuilder.append(" and msr.planStatus = :mapStatus ");
			
		}
	}


	private void buildCreditHours(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasHoursEarnedCriteria(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if(personSearchRequest.getHoursEarnedMax() != null && personSearchRequest.getHoursEarnedMin() != null)
			{
				stringBuilder.append(" est.creditHoursEarned >= :hoursEarnedMin ");
				stringBuilder.append(" and est.creditHoursEarned <= :hoursEarnedMax ");
			}
			if(personSearchRequest.getHoursEarnedMax() == null && personSearchRequest.getHoursEarnedMin() != null)
			{
				stringBuilder.append(" est.creditHoursEarned >= :hoursEarnedMin ");
			}
			if(personSearchRequest.getHoursEarnedMax() != null && personSearchRequest.getHoursEarnedMin() == null)
			{
				stringBuilder.append(" est.creditHoursEarned <= :hoursEarnedMax ");
			}	
			stringBuilder.append(" and est.schoolId = p.schoolId ");

		}
	}


	private void buildGpa(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasGpaCriteria(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if(personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() != null)
			{
				stringBuilder.append(" est.gradePointAverage >= :gpaEarnedMin ");
				stringBuilder.append(" and est.gradePointAverage <= :gpaEarnedMax ");
			}
			if(personSearchRequest.getGpaEarnedMax() == null && personSearchRequest.getGpaEarnedMin() != null)
			{
				stringBuilder.append(" est.gradePointAverage >= :gpaEarnedMin ");
			}
			if(personSearchRequest.getGpaEarnedMax() != null && personSearchRequest.getGpaEarnedMin() == null)
			{
				stringBuilder.append(" est.gradePointAverage <= :gpaEarnedMax ");
			}		
			stringBuilder.append(" and est.schoolId = p.schoolId ");

		}
	}


	private void buildDeclaredMajor(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasDeclaredMajor(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" esap.programCode = :programCode");
			stringBuilder.append(" and esap.schoolId = p.schoolId");
		}
	}


	private void buildCoach(PersonSearchRequest personSearchRequest,FilterTracker filterTracker, StringBuilder stringBuilder) 
	{
		if(hasCoach(personSearchRequest) || hasMyCaseload(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" p.coach = :coach ");
		}
	}


	private boolean hasMyCaseload(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getMyCaseload() != null && personSearchRequest.getMyCaseload();
	}


	private void buildCurrentlyRegistered(PersonSearchRequest personSearchRequest, FilterTracker filterTracker,StringBuilder stringBuilder) 
	{
		if(hasCurrentlyRegistered(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			if(personSearchRequest.getCurrentlyRegistered())
			{
				stringBuilder.append(" exists ( select 1 from RegistrationStatusByTerm rbt where rbt.termCode = :currentTerm and rbt.schoolId = p.schoolId and rbt.registeredCourseCount > 0 ) ");
			}
			else
			{
				stringBuilder.append(" not exists ( select 1 from RegistrationStatusByTerm rbt where rbt.termCode = :currentTerm and rbt.schoolId = p.schoolId )  or ");
				stringBuilder.append(" exists ( select 1 from RegistrationStatusByTerm rbt where rbt.termCode = :currentTerm and rbt.schoolId = p.schoolId and rbt.registeredCourseCount = 0)  ");
			}
		}
	}


	private void buildStudentIdOrName(PersonSearchRequest personSearchRequest,
			FilterTracker filterTracker, StringBuilder stringBuilder) {
		
		if(hasStudentId(personSearchRequest))
		{
			appendAndOrWhere(stringBuilder,filterTracker);
			stringBuilder.append(" ( ");
			stringBuilder.append(" upper(p.firstName) like :studentIdOrName ");
			stringBuilder.append(" or ");
			stringBuilder.append(" upper(p.lastName) like :studentIdOrName ");
			stringBuilder.append(" or ");
			stringBuilder.append(" upper(p.firstName ||' '|| p.lastName) like :studentIdOrName ");
			stringBuilder.append(" or ");
			stringBuilder.append(" upper(p.schoolId) like :studentIdOrName ");
			stringBuilder.append(" ) ");
		}
	}


	private boolean hasStudentId(PersonSearchRequest personSearchRequest) {
		return personSearchRequest.getSchoolId() != null;
	}


	private void buildJoins(PersonSearchRequest personSearchRequest,
			StringBuilder stringBuilder) 
	{
		stringBuilder.append(" left join p.programStatuses as programStatuses ");
		stringBuilder.append(" left join programStatuses.programStatus as programStatus ");
		
		if(hasMyPlans(personSearchRequest) || hasPlanStatus(personSearchRequest))
		{
			stringBuilder.append(" join p.plans as plan ");
		}	
		
		if(this.hasSpecialServiceGroup(personSearchRequest)){
			stringBuilder.append(" left join p.specialServiceGroups as specialServiceGroups ");
			stringBuilder.append(" left join specialServiceGroups.specialServiceGroup as specialServiceGroup ");
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


	private void buildFrom(PersonSearchRequest personSearchRequest, StringBuilder stringBuilder) 
	{
		stringBuilder.append(" from Person p ");
		
		if(hasDeclaredMajor(personSearchRequest))
		{
			stringBuilder.append(", ExternalStudentAcademicProgram esap ");
		}
		
		if(hasGpaCriteria(personSearchRequest) || hasHoursEarnedCriteria(personSearchRequest))
		{
			stringBuilder.append(", ExternalStudentTranscript est ");
		}
		
		boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasMapStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			stringBuilder.append(", ExternalPersonPlanStatus esps ");
		}
		if(hasMapStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			stringBuilder.append(", org.jasig.ssp.model.MapStatusReport msr ");
		}
		
		if(hasFinancialAidStatus(personSearchRequest))
		{
			stringBuilder.append(", ExternalStudentFinancialAid esfa ");
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
	
	@SuppressWarnings("unchecked")
	public List<PersonSearchResult2> directoryPersonSearch(PersonSearchRequest personSearchRequest)
	{
		Criteria query = sessionFactory.getCurrentSession().createCriteria(DirectoryPerson.class);
		if(personSearchRequest.getBirthDate() != null)
		{
			query.add(Restrictions.eq("birthDate", personSearchRequest.getBirthDate()));
		}
		
		if(personSearchRequest.getCurrentlyRegistered() != null && personSearchRequest.getCurrentlyRegistered())
		{
			query.add(Restrictions.gt("currentRegistrationStatus", 0));
		}
		
		if(StringUtils.isNotBlank(personSearchRequest.getDeclaredMajor()))
		{
			query.add(Restrictions.eq("declaredMajor", personSearchRequest.getDeclaredMajor()));
		}
		
		if(StringUtils.isNotBlank(personSearchRequest.getSchoolId()))
		{
			query.add(Restrictions.eq("schoolId", personSearchRequest.getSchoolId() + "%"));
		}
		
		if(StringUtils.isNotBlank(personSearchRequest.getLastName()))
		{
			query.add(Restrictions.like("lastName", personSearchRequest.getLastName() + "%"));
		}
		
		if(StringUtils.isNotBlank(personSearchRequest.getFirstName()))
		{
			query.add(Restrictions.like("firstName", personSearchRequest.getFirstName() + "%"));
		}
		
		if(personSearchRequest.getCoach() != null)
		{
			query.add(Restrictions.like("coach", personSearchRequest.getCoach()));
		}
		
		if(personSearchRequest.getProgramStatus() != null)
		{
			query.add(Restrictions.like("programStatus", personSearchRequest.getProgramStatus()));
		}
		
		if(StringUtils.isNotBlank(personSearchRequest.getMapStatus()))
		{
			query.add(Restrictions.like("mapStatus", personSearchRequest.getMapStatus()));
		}
		
		return null;
	}
	
	private void buildDirectoryPersonFrom(PersonSearchRequest personSearchRequest, StringBuilder stringBuilder) 
	{
		stringBuilder.append(" from PersonDirectory p ");
		
		if(personSearchRequest.getSpecialServiceGroup() != null){
			stringBuilder.append(" Person person ");
		}
		
		/*boolean calculateMapPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim());

		if(hasMapStatus(personSearchRequest) && !calculateMapPlanStatus)
		{
			stringBuilder.append(", ExternalPersonPlanStatus esps ");
		}
		if(hasMapStatus(personSearchRequest) && calculateMapPlanStatus)
		{
			stringBuilder.append(", org.jasig.ssp.model.MapStatusReport msr ");
		}
		*/
	}

}