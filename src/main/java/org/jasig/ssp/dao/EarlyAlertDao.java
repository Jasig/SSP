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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
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
		// validate
		if (personIds == null) {
			throw new IllegalArgumentException(
					"Must include a collection of personIds (students).");
		}

		// setup return value
		final Map<UUID, Number> countForPeopleId = Maps.newHashMap();

		// only run the query to fill the return Map if values were given
		if (!personIds.isEmpty()) {
			// setup query
			final ProjectionList projections = Projections.projectionList();
			projections.add(Projections.groupProperty("person.id").as(
					"personId"));
			projections.add(Projections.count("id"));

			final Criteria query = createCriteria();
			query.setProjection(projections);
			query.add(Restrictions.in("person.id", personIds));
			query.add(Restrictions.isNull("closedDate"));
			query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

			// run query
			@SuppressWarnings("unchecked")
			final List<Object[]> results = query.list();

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
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach))
		        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}
	
	
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
		
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
				totalRows = (Long) query.setProjection(Projections.rowCount())
							.uniqueResult();
		}
		
		query.setProjection( Projections.projectionList()
				.add(Projections.countDistinct("id").as("earlyalert_total"))
                .add(Projections.countDistinct("closedById").as("earlyalert_closed"))
                .add( Projections.groupProperty("person").as("earlyalert_person"))
                ).setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentReportTO.class, "earlyalert_"));
		
		return new PagingWrapper<EarlyAlertStudentReportTO>(totalRows, query.list());
	}

	public Long getCountOfAlertsForSchoolIds(
			Collection<String> schoolIds, Campus campus) {
		
		final Criteria query = createCriteria();
		
		query.createAlias("person",
				"person")
				.add(Restrictions
						.in("person.schoolId",schoolIds));
		
		if(campus != null){
			query.add(Restrictions
					.eq("campus", campus));
		}
		
		return (Long)query.setProjection(Projections.countDistinct("person")).list().get(0);
	}

	public Long getCountOfEarlyAlertsClosedByDate(Date closedDateFrom, Date closedDateTo, Campus campus) {
		final Criteria query = createCriteria();
		
		if (closedDateFrom != null) {
			query.add(Restrictions.ge("closedDate",
					closedDateFrom));
		}

		if (closedDateTo != null) {
			query.add(Restrictions.le("closedDate",
					closedDateTo));
		}
		
		if(campus != null){
			query.add(Restrictions
					.eq("campus", campus));
		}

		return  (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();
	}
	
	public Long getCountOfEarlyAlertsByCreatedDate(Date createdDateFrom, Date createdDateTo, Campus campus) {
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
	
	public Long getCountOfEarlyAlertStudentsByDate(Date createdDateFrom, Date createdDateTo, Campus campus) {
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
	
	private Criteria setPersonCriteria(Criteria criteria, AddressLabelSearchTO addressLabelSearchTO){
		if (addressLabelSearchTO.getCoach() != null
				&& addressLabelSearchTO.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("person.coach.id",
					addressLabelSearchTO.getCoach().getId()));
		}
		
		if (addressLabelSearchTO.getProgramStatus() != null) {

			criteria.createAlias("person.programStatuses",
					"personProgramStatuses")
					.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									addressLabelSearchTO
											.getProgramStatus()));

		}

		if (addressLabelSearchTO.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("person.specialServiceGroups",
					"personSpecialServiceGroups")
					.add(Restrictions
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