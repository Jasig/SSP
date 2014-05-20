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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.JoinType;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeReferralSearchResult;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;
import org.jasig.ssp.transferobject.reports.JournalStepStudentReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class JournalEntryDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<JournalEntry>
		implements RestrictedPersonAssocAuditableDao<JournalEntry> {

	protected JournalEntryDao() {
		super(JournalEntry.class);
	}

	public Long getJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
		
		EntityCountByCoachSearchForm form = new EntityCountByCoachSearchForm(null, 
				createDateFrom, 
				createDateTo, 
				studentTypeIds,
				null,
				null,
				null);

		setCriteria( query,  form);
		
		// restrict to coach
		query.add(Restrictions.eq("createdBy", new AuditPerson(coach.getId())));

		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}

	public Long getStudentJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
		EntityCountByCoachSearchForm form = new EntityCountByCoachSearchForm(null, 
				createDateFrom, 
				createDateTo, 
				studentTypeIds,
				null,
				null,
				null);
		
		setCriteria( query,  form);
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach.getId()))
        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}
	

	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentJournalCountForCoaches(EntityCountByCoachSearchForm form) {

		
		List<Person> coaches = form.getCoaches();
		List<AuditPerson> auditCoaches = new ArrayList<AuditPerson>();
		for (Person person : coaches) {
			auditCoaches.add(new AuditPerson(person.getId()));
		}
		BatchProcessor<AuditPerson,EntityStudentCountByCoachTO> processor = new BatchProcessor<AuditPerson,EntityStudentCountByCoachTO>(auditCoaches, form.getSAndP());
		do{
			final Criteria query = createCriteria();
			setCriteria(query, form);
			
			query.setProjection(Projections.projectionList().
					add(Projections.countDistinct("person").as("journal_studentCount")).
					add(Projections.countDistinct("id").as("journal_entityCount")).
					add(Projections.groupProperty("createdBy").as("journal_coach"))).setResultTransformer(
							new NamespacedAliasToBeanResultTransformer(
									EntityStudentCountByCoachTO.class, "journal_"));
			
			processor.process(query, "createdBy");
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
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<JournalStepStudentReportTO> getJournalStepStudentReportTOsFromCriteria(JournalStepSearchFormTO personSearchForm,  
			SortingAndPaging sAndP){
		final Criteria criteria = createCriteria(sAndP);
		
		setPersonCriteria(criteria,personSearchForm);
		
		if (personSearchForm.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("createdDate",
					personSearchForm.getCreateDateFrom()));
		}

		if (personSearchForm.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("createdDate",
					personSearchForm.getCreateDateTo()));
		}
		
		if(personSearchForm.getGetStepDetails()){
			JoinType joinType = JoinType.INNER_JOIN;
			criteria.createAlias("journalEntryDetails", "journalEntryDetails", joinType);
			criteria.createAlias("journalEntryDetails.journalStepJournalStepDetail", "journalStepJournalStepDetail", joinType);
			criteria.createAlias("journalStepJournalStepDetail.journalStepDetail", "journalStepDetail", joinType);
			if(personSearchForm.getJournalStepDetailIds() != null && !personSearchForm.getJournalStepDetailIds().isEmpty())
				criteria.add(Restrictions.in("journalStepDetail.id", personSearchForm.getJournalStepDetailIds()));
		}else{
			criteria.createAlias("journalEntryDetails", "journalEntryDetails", JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("journalEntryDetails.journalStepJournalStepDetail", "journalStepJournalStepDetail", JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("journalStepJournalStepDetail.journalStepDetail", "journalStepDetail", JoinType.LEFT_OUTER_JOIN);
			if(personSearchForm.getJournalStepDetailIds() != null && !personSearchForm.getJournalStepDetailIds().isEmpty()){
				Criterion isNotIds = Restrictions.not(Restrictions.in("journalStepDetail.id", personSearchForm.getJournalStepDetailIds()));
				Criterion isNull = Restrictions.isNull("journalStepDetail.id");
				criteria.add(Restrictions.or(isNotIds, isNull));
			}else{
				criteria.add(Restrictions.isNull("journalStepDetail.id"));
			}
		}
		
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.distinct(Projections.groupProperty("journalEntryDetails.id").as("journalentry_journalEntryDetailId")));
		addBasicStudentProperties( projections, criteria);
		
		projections.add(Projections.groupProperty("journalStepDetail.name").as("journalentry_journalStepDetailName"));
		criteria.setProjection(projections);
		criteria.setResultTransformer(
				new NamespacedAliasToBeanResultTransformer(
						JournalStepStudentReportTO.class, "journalentry_"));
		if(criteria.list().size() > 1){
			List<JournalStepStudentReportTO> reports = criteria.list();
			Map<UUID, JournalStepStudentReportTO> cleanReports = new HashMap<UUID, JournalStepStudentReportTO>();
			for(JournalStepStudentReportTO report:reports){
				if(!cleanReports.containsKey(report.getJournalEntryDetailId())){
					cleanReports.put(report.getJournalEntryDetailId(), report);
				}
			}
			List<JournalStepStudentReportTO> sortReports = Lists.newArrayList(cleanReports.values());
			Collections.sort(sortReports, new Comparator<JournalStepStudentReportTO>() {
		        public int compare(JournalStepStudentReportTO o1, JournalStepStudentReportTO o2) {
		        	JournalStepStudentReportTO p1 = (JournalStepStudentReportTO) o1;
		        	JournalStepStudentReportTO p2 = (JournalStepStudentReportTO) o2;
		        	int value = p1.getLastName().compareToIgnoreCase(
		     	                    p2.getLastName());
		        	if(value != 0)
		        		return value;
		        	
		        	value = p1.getFirstName().compareToIgnoreCase(
     	                    p2.getFirstName());
			       if(value != 0)
	        		 return value;
			       if(p1.getMiddleName() == null && p2.getMiddleName() == null)
			    	   return 0;
			       if(p1.getMiddleName() == null)
			    	   return -1;
			       if(p2.getMiddleName() == null)
			    	   return 1;
			       return p1.getMiddleName().compareToIgnoreCase(
    	                    p2.getMiddleName());
		        }
		    });
			return  new PagingWrapper<JournalStepStudentReportTO>(sortReports.size(), sortReports);
		}
		return  new PagingWrapper<JournalStepStudentReportTO>(criteria.list().size(), (List<JournalStepStudentReportTO>)criteria.list());
	}
	
private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		criteria.createAlias("person.staffDetails", "personStaffDetails", JoinType.LEFT_OUTER_JOIN);	
		

		projections.add(Projections.groupProperty("person.firstName").as("journalentry_firstName"));
		projections.add(Projections.groupProperty("person.middleName").as("journalentry_middleName"));
		projections.add(Projections.groupProperty("person.lastName").as("journalentry_lastName"));
		projections.add(Projections.groupProperty("person.schoolId").as("journalentry_schoolId"));
		projections.add(Projections.groupProperty("person.primaryEmailAddress").as("journalentry_primaryEmailAddress"));
		projections.add(Projections.groupProperty("person.secondaryEmailAddress").as("journalentry_secondaryEmailAddress"));
		projections.add(Projections.groupProperty("person.cellPhone").as("journalentry_cellPhone"));
		projections.add(Projections.groupProperty("person.homePhone").as("journalentry_homePhone"));
		projections.add(Projections.groupProperty("person.addressLine1").as("journalentry_addressLine1"));
		projections.add(Projections.groupProperty("person.addressLine2").as("journalentry_addressLine2"));
		projections.add(Projections.groupProperty("person.city").as("journalentry_city"));
		projections.add(Projections.groupProperty("person.state").as("journalentry_state"));
		projections.add(Projections.groupProperty("person.zipCode").as("journalentry_zipCode"));
		projections.add(Projections.groupProperty("person.id").as("journalentry_id"));
		
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup", JoinType.LEFT_OUTER_JOIN );
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.groupProperty("specialServiceGroup.name").as("journalentry_specialServiceGroup"));
				
		projections.add(Projections.groupProperty("programStatus.name").as("journalentry_programStatusName"));
		projections.add(Projections.groupProperty("personProgramStatuses.id").as("journalentry_programStatusId"));
		projections.add(Projections.groupProperty("personProgramStatuses.expirationDate").as("journalentry_programStatusExpirationDate"));
		
		// Join to Student Type
		criteria.createAlias("person.studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as("journalentry_studentType"));
		
		

		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			//projections.add(Projections.groupProperty("coach.id").as("coachId"));
			projections.add(Projections.groupProperty("coach.lastName").as("journalentry_coachLastName"))
					.add(Projections.groupProperty("coach.firstName").as("journalentry_coachFirstName"))
					.add(Projections.groupProperty("coach.middleName").as("journalentry_coachMiddleName"))
					.add(Projections.groupProperty("coach.schoolId").as("journalentry_coachSchoolId"))
					.add(Projections.groupProperty("coach.username").as("journalentry_coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			//projections.add(Projections.property("coach.id").as("coachId"));
			projections.add(Projections.groupProperty("coach.lastName").as("journalentry_coachLastName"))
					.add(Projections.groupProperty("coach.firstName").as("journalentry_coachFirstName"))
					.add(Projections.groupProperty("coach.middleName").as("journalentry_coachMiddleName"))
					.add(Projections.groupProperty("coach.schoolId").as("journalentry_coachSchoolId"))
					.add(Projections.groupProperty("coach.username").as("journalentry_coachUsername"));
		}
		return projections;
	}
	
	private Criteria setPersonCriteria(Criteria criteria, PersonSearchFormTO personSearchForm){
		criteria.createAlias("person","person");
		criteria.createAlias("person.coach","coach");
		if (personSearchForm.getCoach() != null
				&& personSearchForm.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("coach.id",
					personSearchForm.getCoach().getId()));
		}
		
		if (personSearchForm.getHomeDepartment() != null
				&& personSearchForm.getHomeDepartment().length() > 0) {
			criteria.createAlias("coach.staffDetails","coachStaffDetails");
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

		}else{
			criteria.createAlias("person.programStatuses", "personProgramStatuses", JoinType.LEFT_OUTER_JOIN);	
			
		}
		if (personSearchForm.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("person.specialServiceGroups",
					"personSpecialServiceGroups");
				criteria.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									personSearchForm
											.getSpecialServiceGroupIds()));
		}else{
			criteria.createAlias("person.specialServiceGroups", "personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);
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

}
