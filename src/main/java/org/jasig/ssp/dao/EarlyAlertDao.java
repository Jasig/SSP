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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * EarlyAlert data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertDao extends
		AbstractPersonAssocAuditableCrudDao<EarlyAlert> implements
		PersonAssocAuditableCrudDao<EarlyAlert> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertDao() {
		super(EarlyAlert.class);
	}

	/**
	 * Count how many open early alerts exist for the specified people
	 * (students).
	 * 
	 * <p>
	 * An 'active' means it has not been closed (has a null closedDate) and has
	 * an ObjectStatus of {@link ObjectStatus#ACTIVE}.
	 * <p>
	 * If list is empty, no results will be returned.
	 * 
	 * @param personIds
	 *            personIds for all the students for which to count early
	 *            alerts; required, but can be empty (in that case, an empty Map
	 *            will be returned)
	 * @return Map of students (personId) with the count of open early alerts
	 *         for each.
	 */
	public Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			@NotNull final Collection<UUID> personIds) {
		List<List<UUID>> batches = prepareBatches(personIds);
		Map<UUID, Number> set = new HashMap<UUID,Number>();
		for(List<UUID> batch:batches){
			set.putAll(getCountOfActiveAlertsForPeopleIdsBatch(batch));
		}
		return set;
	}
	
	private Map<UUID, Number> getCountOfActiveAlertsForPeopleIdsBatch(
			@NotNull final Collection<UUID> personIds) {
		return getCountOfAlertsForPeopleId(personIds, new CriteriaCallback() {
			@Override
			public Criteria criteria(Criteria criteria) {
				criteria.add(Restrictions.isNull("closedDate"));
				return criteria;
			}
		});
	}

	public Map<UUID, Number> getCountOfClosedAlertsForPeopleIds(
			@NotNull final Collection<UUID> personIds) {
		return getCountOfAlertsForPeopleId(personIds, new CriteriaCallback() {
			@Override
			public Criteria criteria(Criteria criteria) {
				criteria.add(Restrictions.isNotNull("closedDate"));
				return criteria;
			}
		});
	}

	private interface CriteriaCallback {
		Criteria criteria(Criteria criteria);
	}

	private Map<UUID, Number> getCountOfAlertsForPeopleId(
			@NotNull final Collection<UUID> personIds,
			CriteriaCallback criteriaCallback) {
		// validate
		if (personIds == null) {
			throw new IllegalArgumentException(
					"Must include a collection of personIds (students).");
		}

		// setup return value
		final Map<UUID, Number> countForPeopleId = Maps.newHashMap();

		// only run the query to fill the return Map if values were given
		if (!personIds.isEmpty()) {

			BatchProcessor<UUID,Object[]> processor = new BatchProcessor<UUID,Object[]>(personIds);
			do{
				Criteria query = createCriteria();
				
				final ProjectionList projections = Projections.projectionList();
				projections.add(Projections.groupProperty("person.id").as(
						"personId"));
				projections.add(Projections.count("id"));
				query.setProjection(projections);
				
				query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

				if ( criteriaCallback != null ) {
					query = criteriaCallback.criteria(query);
				}
				
				processor.process(query, "person.id");
			}while(processor.moreToProcess());

			// run query
			@SuppressWarnings("unchecked")
			final List<Object[]> results = processor.getResults();

			// put query results into return value
			for (final Object[] result : results) {
				countForPeopleId.put((UUID) result[0], (Number) result[1]);
			}

			// ensure all people IDs that were request exist in return Map
			for (final UUID id : personIds) {
				if (!countForPeopleId.containsKey(id)) {
					countForPeopleId.put(id, 0);
				}
			}
		}

		return countForPeopleId;
	}

	public Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
 
		// add possible studentTypeId Check
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
		
			query.createAlias("person",
				"person")
				.add(Restrictions
						.in("person.studentType.id",studentTypeIds));
					
		}		
		
		// restrict to coach
		query.add(Restrictions.eq("createdBy", new AuditPerson(coach.getId())));

		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}

	public Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
 
		// add possible studentTypeId Check
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
		
			query.createAlias("person",
				"person")
				.add(Restrictions
						.in("person.studentType.id",studentTypeIds));
					
		}		
		
		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", new AuditPerson(coach.getId())))
		        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}
	
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlertStudentReportTO> getStudentsEarlyAlertCountSetForCritera(EarlyAlertStudentSearchTO criteriaTO, SortingAndPaging sAndP) {

		final Criteria query = createCriteria();
 
		setPersonCriteria(query.createAlias("person", "person"), criteriaTO.getAddressLabelSearchTO());
		
		if (criteriaTO.getStartDate() != null) {
			query.add(Restrictions.ge("createdDate",
					criteriaTO.getStartDate() ));
		}

		if (criteriaTO.getEndDate()  != null) {
			query.add(Restrictions.le("createdDate",
					criteriaTO.getEndDate()));
		}

		
		query.setProjection(null);
		
		List<UUID> ids = query.setProjection(Projections.distinct(Projections.property("id"))).list();

		if(ids.size() <= 0)
			return null;
		BatchProcessor<UUID,EarlyAlertStudentReportTO> processor = new BatchProcessor<UUID,EarlyAlertStudentReportTO>(ids, sAndP);
		do{
			final Criteria criteria = createCriteria();
			ProjectionList projections = Projections.projectionList()
					.add(Projections.countDistinct("id").as("earlyalert_total"))
					.add(Projections.countDistinct("closedBy").as("earlyalert_closed"));
				
				addBasicStudentProperties(projections, criteria); 
				
				projections.add(Projections.groupProperty("id").as("earlyalert_earlyAlertId"));
				criteria.setProjection(projections);
				criteria.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentReportTO.class, "earlyalert_"));
			
			processor.process(criteria, "id");
		}while(processor.moreToProcess());
		
		return processor.getPagedResults();
	}
	
	private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		criteria.createAlias("person","person");
		criteria.createAlias("person.programStatuses", "personProgramStatuses", JoinType.LEFT_OUTER_JOIN);		
		criteria.createAlias("person.coach","c");
		criteria.createAlias("person.staffDetails", "personStaffDetails", JoinType.LEFT_OUTER_JOIN);		
		criteria.createAlias("person.specialServiceGroups", "personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);

		projections.add(Projections.groupProperty("person.firstName").as("earlyalert_firstName"));
		projections.add(Projections.groupProperty("person.middleName").as("earlyalert_middleName"));
		projections.add(Projections.groupProperty("person.lastName").as("earlyalert_lastName"));
		projections.add(Projections.groupProperty("person.schoolId").as("earlyalert_schoolId"));
		projections.add(Projections.groupProperty("person.primaryEmailAddress").as("earlyalert_primaryEmailAddress"));
		projections.add(Projections.groupProperty("person.secondaryEmailAddress").as("earlyalert_secondaryEmailAddress"));
		projections.add(Projections.groupProperty("person.cellPhone").as("earlyalert_cellPhone"));
		projections.add(Projections.groupProperty("person.homePhone").as("earlyalert_homePhone"));
		projections.add(Projections.groupProperty("person.addressLine1").as("earlyalert_addressLine1"));
		projections.add(Projections.groupProperty("person.addressLine2").as("earlyalert_addressLine2"));
		projections.add(Projections.groupProperty("person.city").as("earlyalert_city"));
		projections.add(Projections.groupProperty("person.state").as("earlyalert_state"));
		projections.add(Projections.groupProperty("person.zipCode").as("earlyalert_zipCode"));
		projections.add(Projections.groupProperty("person.id").as("earlyalert_id"));
		
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup", JoinType.LEFT_OUTER_JOIN );
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.groupProperty("specialServiceGroup.name").as("earlyalert_specialServiceGroup"));
				
		projections.add(Projections.groupProperty("programStatus.name").as("earlyalert_programStatusName"));
		projections.add(Projections.groupProperty("personProgramStatuses.id").as("earlyalert_programStatusId"));
		projections.add(Projections.groupProperty("personProgramStatuses.expirationDate").as("earlyalert_programStatusExpirationDate"));
		
		// Join to Student Type
		criteria.createAlias("person.studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as("earlyalert_studentType"));
		
		

		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			//projections.add(Projections.groupProperty("c.id").as("coachId"));
			projections.add(Projections.groupProperty("c.lastName").as("earlyalert_coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("earlyalert_coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("earlyalert_coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("earlyalert_coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("earlyalert_coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			//projections.add(Projections.property("c.id").as("coachId"));
			projections.add(Projections.groupProperty("c.lastName").as("earlyalert_coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("earlyalert_coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("earlyalert_coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("earlyalert_coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("earlyalert_coachUsername"));
		}
		return projections;
	}

	public Long getCountOfAlertsForSchoolIds(
			Collection<String> schoolIds, Campus campus) {
		BatchProcessor<String,Long> processor = new BatchProcessor<String,Long>(schoolIds);
		do{
			final Criteria query = createCriteria();
			
			query.createAlias("person",
					"person");
			
			
			if(campus != null){
				query.add(Restrictions
						.eq("campus", campus));
			}
			query.setProjection(Projections.countDistinct("person"));
			
			processor.countDistinct(query, "person.schoolId");
		}while(processor.moreToProcess());
		
		return processor.getCount();
	}

	public Long getCountOfEarlyAlertsClosedByDate(Date closedDateFrom, Date closedDateTo, Campus campus, String rosterStatus) {
		final Criteria query = createCriteria();
		
		if (closedDateFrom != null) {
			query.add(Restrictions.ge("closedDate",
					closedDateFrom));
		}

		if (closedDateTo != null) {
			query.add(Restrictions.le("closedDate",
					closedDateTo));
		}
		
		query.add(Restrictions.isNotNull("closedDate"));
		
		if(campus != null){
			query.add(Restrictions
					.eq("campus", campus));
		}

		return  (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();
	}
	
	public Long getCountOfEarlyAlertsByCreatedDate(Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus) {
		final Criteria query = createCriteria();
		
		if ( createdDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createdDateFrom));
		}

		if (createdDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createdDateTo));
		}
		
		if(campus != null){
			query.add(Restrictions
					.eq("campus", campus));
		}
		
		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}
	
	public Long getCountOfEarlyAlertStudentsByDate(Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus) {
		final Criteria query = createCriteria();
		
		if ( createdDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createdDateFrom));
		}

		if (createdDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createdDateTo));
		}
		
		if(campus != null){
			query.add(Restrictions
					.eq("campus", campus));
		}
		
		query.createAlias("person",
				"person");
		
		// item count
		Long totalRows = (Long) query.setProjection(Projections.countDistinct("person"))
				.uniqueResult();

		return totalRows;
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertCountByCoaches(EntityCountByCoachSearchForm form) {

		
		List<Person> coaches = form.getCoaches();
		List<AuditPerson> auditCoaches = new ArrayList<AuditPerson>();
		for (Person person : coaches) {
			auditCoaches.add(new AuditPerson(person.getId()));
		}
		BatchProcessor<AuditPerson, EntityStudentCountByCoachTO> processor = new BatchProcessor<AuditPerson, EntityStudentCountByCoachTO>(auditCoaches, form.getSAndP());
		do{
			final Criteria query = createCriteria();
			setBasicCriteria( query,  form);
			query.setProjection(Projections.projectionList().
	        		add(Projections.countDistinct("person").as("earlyalert_studentCount")).
	        		add(Projections.countDistinct("id").as("earlyalert_entityCount")).
	        		add(Projections.groupProperty("createdBy").as("earlyalert_coach")));
			
			query.setResultTransformer(
							new NamespacedAliasToBeanResultTransformer(
									EntityStudentCountByCoachTO.class, "earlyalert_"));
			processor.process(query, "createdBy");
		}while(processor.moreToProcess());
		
		return processor.getPagedResults();
	}
	
	private Criteria setBasicCriteria(Criteria query, EntityCountByCoachSearchForm form){
		// add possible studentTypeId Check
		if (form.getStudentTypeIds() != null && !form.getStudentTypeIds().isEmpty() || 
				form.getServiceReasonIds() != null && !form.getServiceReasonIds().isEmpty() ||
				form.getSpecialServiceGroupIds()!= null && !form.getSpecialServiceGroupIds().isEmpty())
		{
			query.createAlias("person",
					"person");
		}
		if (form.getStudentTypeIds() != null && !form.getStudentTypeIds().isEmpty()) {
		
			
			query.add(Restrictions
						.in("person.studentType.id",form.getStudentTypeIds()));
					
		}		
		
		if (form.getCreateDateFrom() != null) {
			query.add(Restrictions.ge("createdDate",
					form.getCreateDateFrom()));
		}

		if (form.getCreateDateTo() != null) {
			query.add(Restrictions.le("createdDate",
					form.getCreateDateTo()));
		}
		
		if(form.getServiceReasonIds() != null && !form.getServiceReasonIds().isEmpty()){
			query.createAlias("person.serviceReasons", "serviceReasons");
			query.createAlias("serviceReasons.serviceReason", "serviceReason");
			query.add(Restrictions
					.in("serviceReason.id",form.getServiceReasonIds()));
			
		}
		
		if(form.getSpecialServiceGroupIds()!= null && !form.getSpecialServiceGroupIds().isEmpty()){
			query.createAlias("person.specialServiceGroups", "specialServiceGroups");
			query.createAlias("specialServiceGroups.specialServiceGroup", "specialServiceGroup");
			query.add(Restrictions
					.in("specialServiceGroup.id",form.getSpecialServiceGroupIds()));
		}
				
		return query;
	}

	public Long getEarlyAlertCountSetForCritera(EarlyAlertStudentSearchTO searchForm){
		final Criteria criteria = setPersonCriteria(createCriteria().createAlias("person", "person"), searchForm.getAddressLabelSearchTO());
		if (searchForm.getStartDate() != null) {
			criteria.add(Restrictions.ge("createdDate",
					searchForm.getStartDate() ));
		}

		if (searchForm.getEndDate()  != null) {
			criteria.add(Restrictions.le("createdDate",
					searchForm.getEndDate()));
		}
		
		Long total = (Long)criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
		
		return total;

	}
	
	public List<EarlyAlert> getResponseDueEarlyAlerts(Date lastResponseDate){
		String sql = "select distinct ea " + responseQuery();
		final Query query = createHqlQuery(sql);
		query.setParameter("lastResponseDate", lastResponseDate);
		query.setParameter("objectStatus", ObjectStatus.ACTIVE);
		return (List<EarlyAlert>)query.list();
	}
	
	public Map<UUID, Number> getResponsesDueCountEarlyAlerts(
			@NotNull final Collection<UUID> personIds, Date lastResponseDate) {
		
		Map<UUID,Number> responsesDuePerPerson = new HashMap<UUID,Number>();
		if(personIds.size() > 0){
			BatchProcessor<UUID,Object[]> processor = new BatchProcessor<UUID,Object[]>(personIds);
			String sql = "select distinct ea.person.id, count(ea) " +  responseQuery() 
					+ " and ea.person.id in :personIds group by ea.person.id";
			do{
				final Query query = createHqlQuery(sql);
				query.setParameter("objectStatus", ObjectStatus.ACTIVE);
				query.setParameter("lastResponseDate", lastResponseDate);
				processor.process(query, "personIds");
			}while(processor.moreToProcess());
			
			for (final Object[] result : processor.getResults()) {
				responsesDuePerPerson.put((UUID) result[0], (Number) result[1]);
			}
		}
		return responsesDuePerPerson;
	}
	
	
	private  String  responseQuery(){
		return "from EarlyAlert as ea where ((ea.closedDate is null and ea.objectStatus = :objectStatus "
				+ "and ea.lastResponseDate is null and ea.createdDate < :lastResponseDate) or "
				+ "(ea.closedDate is null and ea.objectStatus = :objectStatus and ea.lastResponseDate < :lastResponseDate)) ";
				/*+ "and  (((select max(ear.modifiedDate) from EarlyAlertResponse as ear "
			+ "where ear.earlyAlertId = ea.id) is empty)"
				+ "or (select max(ear.modifiedDate) from EarlyAlertResponse as ear2 "
			+ "where ear2.earlyAlertId = ea.id) <= :lastResponseDate)";*/
	}
	
	private Criteria setPersonCriteria(Criteria criteria, PersonSearchFormTO personSearchForm){
		if (personSearchForm.getCoach() != null
				&& personSearchForm.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("person.coach.id",
					personSearchForm.getCoach().getId()));
		}
		
		if (personSearchForm.getHomeDepartment() != null
				&& personSearchForm.getHomeDepartment().length() > 0) {
			criteria.createAlias("person.coach","c");
			criteria.createAlias("c.staffDetails","coachStaffDetails");
			criteria.add(Restrictions.eq("coachStaffDetails.departmentName",
					personSearchForm.getHomeDepartment()));
		}
		
		if (personSearchForm.getProgramStatus() != null) {
			criteria.createAlias("person.programStatuses",
					"personProgramStatuses");
			criteria.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									personSearchForm
											.getProgramStatus()));
			criteria.add(Restrictions.isNull("personProgramStatuses.expirationDate"));

		}
		if (personSearchForm.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("person.specialServiceGroups",
					"personSpecialServiceGroups");
				criteria.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									personSearchForm
											.getSpecialServiceGroupIds()));
		}

		if (personSearchForm.getReferralSourcesIds() != null) {
			criteria.createAlias("person.referralSources", "personReferralSources")
					.add(Restrictions.in(
							"personReferralSources.referralSource.id",
							personSearchForm.getReferralSourcesIds()));
		}

		if (personSearchForm.getAnticipatedStartTerm() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartTerm",
					personSearchForm.getAnticipatedStartTerm())
					.ignoreCase());
		}

		if (personSearchForm.getAnticipatedStartYear() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartYear",
					personSearchForm.getAnticipatedStartYear()));
		}

		if (personSearchForm.getStudentTypeIds() != null) {
			criteria.add(Restrictions.in("person.studentType.id",
					personSearchForm.getStudentTypeIds()));
		}

		if (personSearchForm.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("person.createdDate",
					personSearchForm.getCreateDateFrom()));
		}

		if (personSearchForm.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("person.createdDate",
					personSearchForm.getCreateDateTo()));
		}
		
		if (personSearchForm.getServiceReasonsIds() != null && personSearchForm.getServiceReasonsIds().size() > 0) {
			criteria.createAlias("person.serviceReasons", "serviceReasons");
			criteria.createAlias("serviceReasons.serviceReason", "serviceReason");
			criteria.add(Restrictions.in("serviceReason.id",
					personSearchForm.getServiceReasonsIds()));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("person.studentType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	private List<List<UUID>> prepareBatches(Collection<UUID> uuids){
		List<UUID> currentBatch = new ArrayList<UUID>(); 
		List<List<UUID>> batches = new ArrayList<List<UUID>>();
		int batchCounter = 0;
		for (UUID uuid : uuids) 
		{
			if(batchCounter == getBatchsize())
			{
				currentBatch.add(uuid);
				batches.add(currentBatch);
				currentBatch = new ArrayList<UUID>();
				batchCounter = 0;
			}
			else
			{
				currentBatch.add(uuid);
				batchCounter++;
			}
		}
		batches.add(currentBatch);
		return batches;
	}
}