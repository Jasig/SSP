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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.hibernate.BatchProcessor;
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
		criteria.add(Restrictions.gt("dueDate", DateTimeUtils.midnight()));
		return criteria.list();
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
		
		EntityCountByCoachSearchForm form = new EntityCountByCoachSearchForm(null, 
				createDateFrom, 
				createDateTo, 
				studentTypeIds,
				null,
				null,
				null);
		setCriteria(query, form);
		
		// restrict to coach
		query.add(Restrictions.eq("createdBy", new AuditPerson(coach.getId())));

		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	} 
	

	public Long getStudentTaskCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
		EntityCountByCoachSearchForm form = new EntityCountByCoachSearchForm(null, 
				createDateFrom, 
				createDateTo, 
				studentTypeIds,
				null,
				null,
				null);
		setCriteria(query, form);
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach.getId()))
		        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}	
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentTaskCountForCoaches(EntityCountByCoachSearchForm form) {

		
 
		
		List<AuditPerson> auditCoaches = new ArrayList<AuditPerson>();
		for (Person person : form.getCoaches()) {
			auditCoaches.add(new AuditPerson(person.getId()));
		}
		BatchProcessor<AuditPerson,EntityStudentCountByCoachTO> processor = new BatchProcessor<AuditPerson,EntityStudentCountByCoachTO>(auditCoaches, form.getSAndP());
		do{
			final Criteria query = createCriteria();
			setCriteria( query,  form);
			
			query.setProjection(Projections.projectionList().
	        		add(Projections.countDistinct("person").as("task_studentCount")).
	        		add(Projections.countDistinct("id").as("task_entityCount")).
	        		add(Projections.groupProperty("createdBy").as("task_coach"))).setResultTransformer(
							new NamespacedAliasToBeanResultTransformer(
									EntityStudentCountByCoachTO.class, "task_"));
			processor.process(query,"createdBy");
		}while(processor.moreToProcess());
		
		return processor.getPagedResults();
	}
	
	
	
	private Criteria setCriteria(Criteria query, EntityCountByCoachSearchForm form){
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

	
}