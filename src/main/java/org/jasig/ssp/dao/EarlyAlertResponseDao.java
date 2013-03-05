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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.JoinType;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.EarlyAlertResponseCounts;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentResponseOutcomeReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * EarlyAlertResponse data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertResponseDao extends
		AbstractAuditableCrudDao<EarlyAlertResponse> implements
		AuditableCrudDao<EarlyAlertResponse> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertResponseDao() {
		super(EarlyAlertResponse.class);
	}

	/**
	 * Get all {@link EarlyAlertResponse} for the specified {@link EarlyAlert}.
	 * 
	 * @param earlyAlertId
	 *            Early Alert identifier
	 * @param sAndP
	 *            Sorting and paging filters
	 * @return All EarlyAlertResponses for the specified person.
	 */
	public PagingWrapper<EarlyAlertResponse> getAllForEarlyAlertId(
			final UUID earlyAlertId,
			final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(
				createCriteria().add(
						Restrictions.eq("earlyAlert.id", earlyAlertId)), sAndP,
				true);
	}

	public Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
		
		// add possible studentTypeId Check
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
			//.createAlias("person",
			//		"person")
			query.createAlias("earlyAlert",	"earlyAlert").
			createAlias("earlyAlert.person", "student").createAlias("student.studentType","studentType").add(
						Restrictions.in("studentType.id",studentTypeIds));
					
		}		

		// restrict to coach
		query.add(Restrictions.eq("createdBy", coach));

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
	
	public EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlerts(List<UUID> earlyAlertIds) {

		final Criteria query = createCriteria();
		final EarlyAlertResponseCounts counts = new EarlyAlertResponseCounts();
		
		query.createAlias("earlyAlert", "earlyAlert");
		query.add(Restrictions.in("earlyAlert.id", earlyAlertIds));
		query.setProjection(Projections.rowCount());
		if(!query.list().isEmpty())
			counts.setTotalResponses((Long)(query.list().get(0)));
		
		query.setProjection(Projections.countDistinct("earlyAlert.id"));
		if(!query.list().isEmpty())
			counts.setTotalEARespondedTo((Long)(query.list().get(0)));
		
		 query.add(Restrictions.isNull("earlyAlert.closedById"));
		 if(!query.list().isEmpty())
				counts.setTotalEARespondedToNotClosed((Long)(query.list().get(0)));
		 
		 return counts;
	}
	
	public EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlertsByOutcome(List<UUID> earlyAlertIds, UUID outcomeId) {

		final Criteria query = createCriteria();
		final EarlyAlertResponseCounts counts = new EarlyAlertResponseCounts();
		query.createAlias("earlyAlert", "earlyAlert");
		query.add(Restrictions.in("earlyAlert.id", earlyAlertIds));
		query.add(Restrictions.eq("earlyAlertOutcome.id", outcomeId));
		query.setProjection(Projections.rowCount());
		if(!query.list().isEmpty())
			counts.setTotalResponses((Long)(query.list().get(0)));
		
		query.setProjection(Projections.countDistinct("earlyAlert.id"));
		if(!query.list().isEmpty())
			counts.setTotalEARespondedTo((Long)(query.list().get(0)));
		
		 query.add(Restrictions.isNull("earlyAlert.closedById"));
		 if(!query.list().isEmpty())
				counts.setTotalEARespondedToNotClosed((Long)(query.list().get(0)));
		 return counts;
	}
	
	

	public Long getEarlyAlertRespondedToCount(Date createDateFrom,
			Date createDateTo, Campus campus, String rosterStatus) {
		final Criteria query = createCriteria();
		
		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		if(campus != null){
			query.createAlias("earlyAlert",	"earlyAlert").add(
					Restrictions.eq("earlyAlert.campus", campus));
		}
		
		// item count
		Long totalRows = (Long) query.setProjection(Projections.countDistinct("earlyAlert.id"))
				.uniqueResult();

		return totalRows;
	}
	
	@SuppressWarnings({ "unchecked" })
	public Collection<EarlyAlertStudentOutreachReportTO> getEarlyAlertOutreachCountByOutcome(Date createDateFrom,
			Date createDateTo, List<UUID> outcomes, String homeDepartment, Person coach) {
		final Criteria query = createCriteria();
		
		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		if (outcomes != null && outcomes.size() > 0) {
			query.add(Restrictions.in("earlyAlertOutcome.id", outcomes));
		}
		
		query.createAlias("earlyAlert", "earlyAlert");
		query.createAlias("earlyAlert.person", "student");
		Criteria coachCriteria = query.createAlias("student.coach","coach");
		if(coach != null){
			coachCriteria.add(
					Restrictions.eq("coach.id", coach.getId()));
		}
		
		if(homeDepartment != null && homeDepartment.length() > 0)
		{
			 query.createAlias("coach.staffDetails","personStaffDetails");
			 query.add(Restrictions.eq("personStaffDetails.departmentName", homeDepartment));
		}else{
			query.createAlias("coach.staffDetails","personStaffDetails", JoinType.LEFT_OUTER_JOIN);
		}

		
		ProjectionList projections = Projections.projectionList().
				add(Projections.groupProperty("earlyAlert.id").as("ea_outcome_earlyAlertId"));
		projections.add(Projections.groupProperty("coach.firstName").as("ea_outcome_coachFirstName"));
		projections.add(Projections.groupProperty("coach.middleName").as("ea_outcome_coachMiddleName"));
		projections.add(Projections.groupProperty("coach.lastName").as("ea_outcome_coachLastName"));
		projections.add(Projections.groupProperty("coach.id").as("ea_outcome_coachId"));
		projections.add(Projections.groupProperty("coach.schoolId").as("ea_outcome_coachSchoolId"));
		projections.add(Projections.groupProperty("personStaffDetails.departmentName").as("ea_outcome_coachDepartmentName"));
		query.createAlias("earlyAlertOutreachIds", "earlyAlertOutreachIds");
		projections.add(Projections.groupProperty("earlyAlertOutreachIds.name").as("ea_outcome_earlyAlertOutreachName"));
		

		query.setProjection(projections)
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentOutreachReportTO.class,"ea_outcome_"));

		
		// item count
		List<EarlyAlertStudentOutreachReportTO> values = query.list();
		if(values.size() == 0)
			return null;
		
		Iterator<EarlyAlertStudentOutreachReportTO> valueIterator = values.iterator();
		ArrayList<EarlyAlertStudentOutreachReportTO> responses = new ArrayList<EarlyAlertStudentOutreachReportTO>();
		while(valueIterator.hasNext()){
			EarlyAlertStudentOutreachReportTO value = valueIterator.next();
			Integer index = responses.indexOf(value);
			if(index != null && index >= 0){
				responses.get(index).processDuplicate(value);
			}else
				responses.add(value);
		}
		return responses;
	}
	
	@SuppressWarnings(UNCHECKED)
	public Long getEarlyAlertOutcomeTypeCountByCriteria(String outcomeType, UUID outcomeId,
			EarlyAlertStudentSearchTO searchForm)
			throws ObjectNotFoundException {

		final Criteria criteria = getCriteriaForOutcomeType(searchForm, null);
		criteria.createAlias(outcomeType, outcomeType);
		criteria.add(Restrictions.eq(outcomeType + ".id", outcomeId));
		criteria.setProjection(Projections.countDistinct("id"));
		return (Long)criteria.uniqueResult();
	}
	
	@SuppressWarnings(UNCHECKED)
	public Long getEarlyAlertCountByOutcomeCriteria(
			EarlyAlertStudentSearchTO searchForm)
			throws ObjectNotFoundException {

		final Criteria criteria = getCriteriaForOutcomeType(searchForm, null);
		
		if(searchForm.getOutcomeIds() != null && searchForm.getOutcomeIds().size() > 0){
			criteria.createAlias("earlyAlertOutcome", "earlyAlertOutcome");
			criteria.add(Restrictions.in("earlyAlertOutcome.id", searchForm.getOutcomeIds()));
		}
		criteria.setProjection(Projections.countDistinct("earlyAlert.id"));
		return (Long)criteria.uniqueResult();
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	public 	List<EarlyAlertStudentResponseOutcomeReportTO> getEarlyAlertResponseOutcomeTypeForStudentsByCriteria(String outcomeType,
			EarlyAlertStudentSearchTO searchForm,
			SortingAndPaging sAndP){
		
		final Criteria criteria = getCriteriaForOutcomeType(searchForm, sAndP);
		
		ProjectionList projections = getPersonProjection();
		criteria.createAlias(outcomeType, outcomeType);
		projections.add(Projections.groupProperty(outcomeType + ".name").as("ea_outcome_outcomeName"));
		
		criteria.setProjection(projections)
		.setResultTransformer(
				new NamespacedAliasToBeanResultTransformer(
						EarlyAlertStudentResponseOutcomeReportTO.class,"ea_outcome_"));
		
		return (List)criteria.list();
		
	}
	
	
	private ProjectionList getPersonProjection(){
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("person.firstName").as("ea_outcome_firstName"));
		projections.add(Projections.groupProperty("person.middleName").as("ea_outcome_middleName"));
		projections.add(Projections.groupProperty("person.lastName").as("ea_outcome_lastName"));
		projections.add(Projections.groupProperty("person.primaryEmailAddress").as("ea_outcome_primaryEmailAddress"));
		projections.add(Projections.groupProperty("person.schoolId").as("ea_outcome_schoolId"));
		
		projections.add(Projections.groupProperty("coach.firstName").as("ea_outcome_coachFirstName"));
		projections.add(Projections.groupProperty("coach.middleName").as("ea_outcome_coachMiddleName"));
		projections.add(Projections.groupProperty("coach.lastName").as("ea_outcome_coachLastName"));

		return projections;
	}
	
	
	private Criteria getCriteriaForOutcomeType(EarlyAlertStudentSearchTO searchForm,
			SortingAndPaging sAndP){
		final Criteria criteria = createCriteria();
		
		if(searchForm.getStartDate() != null)
			criteria.add(Restrictions.ge("createdDate", searchForm.getStartDate()));
		if(searchForm.getEndDate() != null)
			criteria.add(Restrictions.le("createdDate", searchForm.getEndDate() ));
		
		criteria.createAlias("earlyAlert", "earlyAlert");
		Criteria personCriteria = criteria.createAlias("earlyAlert.person", "person");

		setPersonCriteria(personCriteria, searchForm.getAddressLabelSearchTO());
		
		if(searchForm.getOutcomeIds() != null && searchForm.getOutcomeIds().size() > 0)
			criteria.add(Restrictions.in("earlyAlertOutcome.id", searchForm.getOutcomeIds()));
		if(sAndP != null)
			sAndP.addAll(criteria);
		
		return criteria;
	}
	
	@SuppressWarnings(UNCHECKED)
	public List<EarlyAlertStudentReportTO> getPeopleByEarlyAlertReferralIds(
			final List<UUID> earlyAlertReferralIds, 
			final Date createDateFrom, 
			final Date createDateTo,
			final PersonSearchFormTO personSearchForm,
			final SortingAndPaging sAndP)
			throws ObjectNotFoundException {

		final Criteria criteria = createCriteria();
		
		if(createDateFrom != null)
			criteria.add(Restrictions.ge("createdDate", createDateFrom));
		if(createDateTo != null)
			criteria.add(Restrictions.le("createdDate", createDateTo));
		
		criteria.createAlias("earlyAlertReferralIds", "earlyAlertReferral");
		if (earlyAlertReferralIds != null) {
			criteria 
				.add(Restrictions
				.in("earlyAlertReferral.id",
								earlyAlertReferralIds));
		}
		
		criteria.createAlias("earlyAlert", "earlyAlert");
		Criteria personCriteria = criteria.createAlias("earlyAlert.person", "person");

		setPersonCriteria(personCriteria, personSearchForm);
		
		List<UUID> ids = criteria.setProjection(Projections.distinct(Projections.property("id"))).list();
		
		final Criteria collectionCriteria = createCriteria();
		
		if(ids.size() == 0)
			return new ArrayList<EarlyAlertStudentReportTO>();
		
		collectionCriteria.add(Restrictions.in("id", ids));
		
		collectionCriteria.createAlias("earlyAlert", "earlyAlert");
		collectionCriteria.createAlias("earlyAlert.person", "person");
		collectionCriteria.createAlias("person.coach","coach");
		
		ProjectionList projections = Projections.projectionList().
				add(Projections.distinct(Projections.groupProperty("earlyAlert.id").as("early_alert_response_earlyAlertId")));
		
		addBasicStudentProperties(projections, collectionCriteria);
		collectionCriteria.addOrder(Order.asc("person.lastName"));
		collectionCriteria.addOrder(Order.asc("person.firstName"));
		collectionCriteria.addOrder(Order.asc("person.middleName"));
		collectionCriteria.setProjection(projections)
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentReportTO.class, "early_alert_response_"));
		
		return collectionCriteria.list();
	}
	
private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		criteria.createAlias("person.specialServiceGroups",
			"personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);	
		criteria.createAlias("person.programStatuses",
			"personProgramStatuses", JoinType.LEFT_OUTER_JOIN);
		
		
	
		projections.add(Projections.groupProperty("person.firstName").as("early_alert_response_firstName"));
		projections.add(Projections.groupProperty("person.middleName").as("early_alert_response_middleName"));
		projections.add(Projections.groupProperty("person.lastName").as("early_alert_response_lastName"));
		projections.add(Projections.groupProperty("person.schoolId").as("early_alert_response_schoolId"));
		projections.add(Projections.groupProperty("person.primaryEmailAddress").as("early_alert_response_primaryEmailAddress"));
		projections.add(Projections.groupProperty("person.secondaryEmailAddress").as("early_alert_response_secondaryEmailAddress"));
		projections.add(Projections.groupProperty("person.cellPhone").as("early_alert_response_cellPhone"));
		projections.add(Projections.groupProperty("person.homePhone").as("early_alert_response_homePhone"));
		projections.add(Projections.groupProperty("person.addressLine1").as("early_alert_response_addressLine1"));
		projections.add(Projections.groupProperty("person.addressLine2").as("early_alert_response_addressLine2"));
		projections.add(Projections.groupProperty("person.city").as("early_alert_response_city"));
		projections.add(Projections.groupProperty("person.state").as("early_alert_response_state"));
		projections.add(Projections.groupProperty("person.zipCode").as("early_alert_response_zipCode"));
		projections.add(Projections.groupProperty("person.id").as("early_alert_response_id"));
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup",JoinType.LEFT_OUTER_JOIN);
		projections.add(Projections.groupProperty("specialServiceGroup.name").as("early_alert_response_specialServiceGroup"));
		
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.groupProperty("programStatus.name").as("early_alert_response_programStatusName"));
		projections.add(Projections.groupProperty("personProgramStatuses.id").as("early_alert_response_programStatusId"));
		projections.add(Projections.groupProperty("personProgramStatuses.expirationDate").as("early_alert_response_programStatusExpirationDate"));
		
		// Join to Student Type
		criteria.createAlias("person.studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as("early_alert_response_studentType"));
		
		
		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			//projections.add(Projections.groupProperty("coach.id").as("coachId"));
			projections.add(Projections.groupProperty("coach.lastName").as("early_alert_response_coachLastName"))
					.add(Projections.groupProperty("coach.firstName").as("early_alert_response_coachFirstName"))
					.add(Projections.groupProperty("coach.middleName").as("early_alert_response_coachMiddleName"))
					.add(Projections.groupProperty("coach.schoolId").as("early_alert_response_coachSchoolId"))
					.add(Projections.groupProperty("coach.username").as("early_alert_response_coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			//projections.add(Projections.property("coach.id").as("coachId"));
			projections.add(Projections.groupProperty("coach.lastName").as("early_alert_response_coachLastName"))
					.add(Projections.groupProperty("coach.firstName").as("early_alert_response_coachFirstName"))
					.add(Projections.groupProperty("coach.middleName").as("early_alert_response_coachMiddleName"))
					.add(Projections.groupProperty("coach.schoolId").as("early_alert_response_coachSchoolId"))
					.add(Projections.groupProperty("coach.username").as("early_alert_response_coachUsername"));
		}
		return projections;
	}
	
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertResponseCountByCoaches(List<Person> coaches, 
			Date createDateFrom, 
			Date createDateTo, 
			List<UUID> studentTypeIds, 
			List<UUID> serviceReasonIds,
			SortingAndPaging sAndP) {

		final Criteria query = createCriteria();
 
		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		
		
		query.createAlias("earlyAlert", "earlyAlert");
		Criteria personCriteria = query.createAlias("earlyAlert.person", "person");
		personCriteria.add(Restrictions.in("earlyAlert.createdBy", coaches));
		
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
			personCriteria.add(Restrictions
					.in("person.studentType.id",studentTypeIds));
		}
		
		if (serviceReasonIds != null && !serviceReasonIds.isEmpty()) {
			query.createAlias("person.serviceReasons", "serviceReasons");
			query.createAlias("serviceReasons.serviceReason", "serviceReason");
			query.add(Restrictions
					.in("serviceReason.id",serviceReasonIds));
		}
		
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) query.setProjection(Projections.countDistinct("earlyAlert.createdBy"))
					.uniqueResult();
		}
		
		query.setProjection(Projections.projectionList().
        		add(Projections.countDistinct("earlyAlert.person").as("earlyalertresponse_studentCount")).
        		add(Projections.countDistinct("id").as("earlyalertresponse_entityCount")).
        		add(Projections.groupProperty("earlyAlert.createdBy").as("earlyalertresponse_coach")));
		
		query.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EntityStudentCountByCoachTO.class, "earlyalertresponse_"));
		
		return new PagingWrapper<EntityStudentCountByCoachTO>(totalRows,  (List<EntityStudentCountByCoachTO>)query.list());
	}
	
	

	private Criteria setPersonCriteria(Criteria criteria, PersonSearchFormTO personSearchFormTO){
		if (personSearchFormTO.getCoach() != null
				&& personSearchFormTO.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("person.coach.id",
					personSearchFormTO.getCoach().getId()));
		}
		
		criteria.createAlias("person.coach", "coach");
		
		if (personSearchFormTO.getHomeDepartment() != null
				&& personSearchFormTO.getHomeDepartment().length() > 0) {
			
			criteria.createAlias("coach.staffDetails", "staffDetails");
			criteria.add(Restrictions.eq("staffDetails.departmentName",
					personSearchFormTO.getHomeDepartment()));
		}
		
		if (personSearchFormTO.getProgramStatus() != null) {	
			criteria.createAlias("person.programStatuses",
					"personProgramStatuses");
			criteria.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									personSearchFormTO
											.getProgramStatus()));
			criteria.add(Restrictions.isNull("personProgramStatuses.expirationDate"));
		}

		if (personSearchFormTO.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("person.specialServiceGroups",
					"personSpecialServiceGroups");
			criteria.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									personSearchFormTO
											.getSpecialServiceGroupIds()));
		}

		if (personSearchFormTO.getReferralSourcesIds() != null) {
			criteria.createAlias("person.referralSources", "personReferralSources")
					.add(Restrictions.in(
							"personReferralSources.referralSource.id",
							personSearchFormTO.getReferralSourcesIds()));
		}

		if (personSearchFormTO.getAnticipatedStartTerm() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartTerm",
					personSearchFormTO.getAnticipatedStartTerm())
					.ignoreCase());
		}

		if (personSearchFormTO.getAnticipatedStartYear() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartYear",
					personSearchFormTO.getAnticipatedStartYear()));
		}

		if (personSearchFormTO.getStudentTypeIds() != null) {
			criteria.add(Restrictions.in("person.studentType.id",
					personSearchFormTO.getStudentTypeIds()));
		}

		if (personSearchFormTO.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("person.createdDate",
					personSearchFormTO.getCreateDateFrom()));
		}

		if (personSearchFormTO.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("person.createdDate",
					personSearchFormTO.getCreateDateTo()));
		}
		
		if (personSearchFormTO.getServiceReasonsIds() != null && personSearchFormTO.getServiceReasonsIds().size() > 0) {
			criteria.createAlias("person.serviceReasons", "serviceReasons");
			criteria.createAlias("serviceReasons.serviceReason", "serviceReason");
			criteria.add(Restrictions.in("serviceReason.id",
					personSearchFormTO.getServiceReasonsIds()));
		}


		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("person.studentType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	
}