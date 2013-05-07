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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.SspTimeZones;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Task DAO
 */
@Repository
public class TaskDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<Task>
		implements RestrictedPersonAssocAuditableDao<Task> {

	protected TaskDao() {
		super(Task.class);
	}

	private void addCompleteRestriction(final boolean complete,
			final Criteria criteria) {

		if (complete) {
			criteria.add(Restrictions.isNotNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNull("completedDate"));
		}
	}

	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllForPersonId(final UUID personId,
			final boolean complete, final SspUser requestor,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));

		addCompleteRestriction(complete, criteria);
		addConfidentialityLevelsRestriction(requestor, criteria);

		return criteria.list();
	}
   
	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllForSessionId(final String sessionId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		criteria.add(Restrictions.eq("person.id", SspUser.ANONYMOUS_PERSON_ID));
		return criteria.list();
	}

	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllForSessionId(final String sessionId,
			final boolean complete, final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		criteria.add(Restrictions.eq("person.id", SspUser.ANONYMOUS_PERSON_ID));
		addCompleteRestriction(complete, criteria);

		return criteria.list();
	}

	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllWhichNeedRemindersSent(final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.isNull("completedDate"));
		criteria.add(Restrictions.isNull("reminderSentDate"));
		criteria.add(Restrictions.isNotNull("dueDate"));

		// if you just pass a new java.util.Date here it will be coerced to
		// a java.sql.Date like you'd expect from the Hibernate mappings. But
		// that will involve interpreting the given date in the configured
		// "persistent timezone". E.g. if the persistent timezone is UTC, late
		// in the day on May 5 on the US west coast becomes midnight May 6 in
		// this query. You really want to preserve that May 5, though, b/c we
		// don't *actually* want date-only values interpreted in the persistent
		// timezone, at least not in this case.
		criteria.add(Restrictions.gt("dueDate", persistentMidnight()));
		return criteria.list();
	}

	private Date persistentMidnight() {
		Calendar localCal = Calendar.getInstance();
		Calendar persistentCal = Calendar.getInstance();
		persistentCal.setTimeZone(SspTimeZones.INSTANCE.getDbTimeZone());
		persistentCal.set(localCal.get(Calendar.YEAR),
				localCal.get(Calendar.MONTH),
				localCal.get(Calendar.DAY_OF_MONTH),
				0, 0, 0);
		return new Date(persistentCal.getTimeInMillis());
	}

	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllForPersonIdAndChallengeReferralId(
			final UUID personId, final boolean complete,
			final UUID challengeReferralId, final SspUser requestor,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("challengeReferral.id",
				challengeReferralId));

		addCompleteRestriction(complete, criteria);
		addConfidentialityLevelsRestriction(requestor, criteria);

		return criteria.list();
	}

	@SuppressWarnings(UNCHECKED)
	public List<Task> getAllForSessionIdAndChallengeReferralId(
			final String sessionId, final boolean complete,
			final UUID challengeReferralId, final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		criteria.add(Restrictions.eq("challengeReferral.id",
				challengeReferralId));
		criteria.add(Restrictions.eq("person.id", SspUser.ANONYMOUS_PERSON_ID));
		addCompleteRestriction(complete, criteria);

		return criteria.list();
	}


	public Long getTaskCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
		
		setCriteria(query, createDateFrom, createDateTo, studentTypeIds, null);
		
		// restrict to coach
		query.add(Restrictions.eq("createdBy", coach));

		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	} 
	

	public Long getStudentTaskCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
 
		setCriteria(query, createDateFrom, createDateTo, studentTypeIds, null);
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach))
		        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}	
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentTaskCountForCoaches(List<Person> coaches, 
			Date createDateFrom, 
			Date createDateTo, 
			List<UUID> studentTypeIds, 
			List<UUID> serviceReasonIds,
			SortingAndPaging sAndP) {

		final Criteria query = createCriteria();
 
		setCriteria( query,  createDateFrom,  createDateTo, studentTypeIds, serviceReasonIds);
		
		query.add(Restrictions.in("createdBy", coaches));
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
				totalRows = (Long) query.setProjection(Projections.rowCount())
							.uniqueResult();
		}
		
		
		query.setProjection(Projections.projectionList().
        		add(Projections.countDistinct("person").as("task_studentCount")).
        		add(Projections.countDistinct("id").as("task_entityCount")).
        		add(Projections.groupProperty("createdBy").as("task_coach"))).setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EntityStudentCountByCoachTO.class, "task_"));
		
		return new PagingWrapper<EntityStudentCountByCoachTO>(totalRows, (List<EntityStudentCountByCoachTO>)query.list());
	}
	
	
	
	private Criteria setCriteria(Criteria query, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds,  List<UUID> serviceReasonIds){
		if (studentTypeIds != null && !studentTypeIds.isEmpty() || serviceReasonIds != null && !serviceReasonIds.isEmpty())
		{
			query.createAlias("person",
					"person");
		}
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
		
			
			query.add(Restrictions
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
		
		if(serviceReasonIds != null && !serviceReasonIds.isEmpty()){
			query.createAlias("person.serviceReasons", "serviceReasons");
			query.createAlias("serviceReasons.serviceReason", "serviceReason");
			query.add(Restrictions
					.in("serviceReason.id",serviceReasonIds));
			
		}
				
		return query;
	}

	
}