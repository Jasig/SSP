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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projection;
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
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.EarlyAlertResponseCounts;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
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
			Date createDateTo, Campus campus) {
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
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Collection<EarlyAlertStudentOutreachReportTO> getEarlyAlertOutreachCountByOutcome(Date createDateFrom,
			Date createDateTo, List<UUID> outcomes, Person coach) {
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
		
		ProjectionList projections = Projections.projectionList().
				add(Projections.groupProperty("earlyAlert.id").as("early_response_earlyAlertId"));
		projections.add(Projections.groupProperty("coach.firstName").as("early_response_coachFirstName"));
		projections.add(Projections.groupProperty("coach.middleName").as("early_response_coachMiddleName"));
		projections.add(Projections.groupProperty("coach.lastName").as("early_response_coachLastName"));
		projections.add(Projections.groupProperty("coach.id").as("early_response_coachId"));
		query.createAlias("earlyAlertOutreachIds", "earlyAlertOutreachIds");
		projections.add(Projections.groupProperty("earlyAlertOutreachIds.name").as("early_response_earlyAlertOutreachName"));
		

		query.setProjection(projections)
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentOutreachReportTO.class,"early_response_"));

		
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
	public List<EarlyAlertStudentReportTO> getPeopleByEarlyAlertReferralIds(
			final List<UUID> earlyAlertReferralIds, 
			final Date createDateFrom, 
			final Date createDateTo,
			final PersonSearchFormTO addressLabelSearchTO,
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

		setPersonCriteria(personCriteria, addressLabelSearchTO);
		ProjectionList projections = Projections.projectionList().
				add(Projections.distinct(Projections.groupProperty("earlyAlert.id").as("early_alert_response_earlyAlertId")));
		
		addBasicStudentProperties(projections, criteria);
		criteria.setProjection(projections)
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentReportTO.class, "early_alert_response_"));
		
		return criteria.list();
	}
	
private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
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
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup");
		projections.add(Projections.groupProperty("specialServiceGroup.name").as("early_alert_response_specialServiceGroup"));
		
		criteria.add(Restrictions
				.isNull("personProgramStatuses.expirationDate"));
		
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus");
		
		projections.add(Projections.groupProperty("programStatus.name").as("early_alert_response_currentProgramStatusName"));

		// Join to Student Type
		criteria.createAlias("person.studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as("early_alert_response_studentType"));
		
		criteria.createAlias("person.coach","c");

		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			//projections.add(Projections.groupProperty("c.id").as("coachId"));
			projections.add(Projections.groupProperty("c.lastName").as("early_alert_response_coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("early_alert_response_coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("early_alert_response_coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("early_alert_response_coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("early_alert_response_coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			//projections.add(Projections.property("c.id").as("coachId"));
			projections.add(Projections.groupProperty("c.lastName").as("early_alert_response_coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("early_alert_response_coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("early_alert_response_coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("early_alert_response_coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("early_alert_response_coachUsername"));
		}
		return projections;
	}
	
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertResponseCountByCoaches(List<Person> coaches, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds, SortingAndPaging sAndP) {

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
	
	

	private Criteria setPersonCriteria(Criteria criteria, PersonSearchFormTO addressLabelSearchTO){
		if (addressLabelSearchTO.getCoach() != null
				&& addressLabelSearchTO.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("person.coach.id",
					addressLabelSearchTO.getCoach().getId()));
		}
		criteria.createAlias("person.programStatuses",
				"personProgramStatuses");
		criteria.createAlias("person.specialServiceGroups",
				"personSpecialServiceGroups");
		if (addressLabelSearchTO.getProgramStatus() != null) {			
			criteria.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									addressLabelSearchTO
											.getProgramStatus()));

		}

		if (addressLabelSearchTO.getSpecialServiceGroupIds() != null) {
			
			criteria.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									addressLabelSearchTO
											.getSpecialServiceGroupIds()));
		}

		if (addressLabelSearchTO.getReferralSourcesIds() != null) {
			criteria.createAlias("person.referralSources", "personReferralSources")
					.add(Restrictions.in(
							"personReferralSources.referralSource.id",
							addressLabelSearchTO.getReferralSourcesIds()));
		}

		if (addressLabelSearchTO.getAnticipatedStartTerm() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartTerm",
					addressLabelSearchTO.getAnticipatedStartTerm())
					.ignoreCase());
		}

		if (addressLabelSearchTO.getAnticipatedStartYear() != null) {
			criteria.add(Restrictions.eq("person.anticipatedStartYear",
					addressLabelSearchTO.getAnticipatedStartYear()));
		}

		if (addressLabelSearchTO.getStudentTypeIds() != null) {
			criteria.add(Restrictions.in("person.studentType.id",
					addressLabelSearchTO.getStudentTypeIds()));
		}

		if (addressLabelSearchTO.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("person.createdDate",
					addressLabelSearchTO.getCreateDateFrom()));
		}

		if (addressLabelSearchTO.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("person.createdDate",
					addressLabelSearchTO.getCreateDateTo()));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("person.studentType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	
}