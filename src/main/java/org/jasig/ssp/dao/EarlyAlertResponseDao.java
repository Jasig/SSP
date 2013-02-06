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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.ObjectNotFoundException;
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
				
		// item count
		List<EarlyAlertResponse> values = query.list();
		if(values.size() == 0)
			return null;
		
		Iterator<EarlyAlertResponse> valueIterator = values.iterator();
		Map<UUID, EarlyAlertStudentOutreachReportTO> responses = new HashMap<UUID, EarlyAlertStudentOutreachReportTO>();
		while(valueIterator.hasNext()){
			EarlyAlertResponse value = valueIterator.next();
			EarlyAlertStudentOutreachReportTO update = null;
			UUID coachId = value.getEarlyAlert().getPerson().getCoach().getId();
			if(responses.containsKey(coachId)){
				update = responses.get(coachId);
			}else{
				update = new EarlyAlertStudentOutreachReportTO(value.getEarlyAlert().getPerson().getCoach(),0L,0L,0L,0L,0L,0L);
				responses.put(coachId, update);
			}
			Iterator<EarlyAlertOutreach> outreachIterator = value.getEarlyAlertOutreachIds().iterator();
			if(outreachIterator.hasNext()){
				EarlyAlertOutreach outreach = outreachIterator.next();
				if(outreach.getName().equals("Phone Call")){
					update.setCountPhoneCalls(update.getCountPhoneCalls() + 1L);
				}
				if(outreach.getName().equals("Email")){
					update.setCountEmail(update.getCountEmail() + 1L);
				}
				
				if(outreach.getName().equals("In Person")){
					update.setCountInPerson(update.getCountInPerson() + 1L);
				}
				
				if(outreach.getName().equals("Letter")){
					update.setCountLetter(update.getCountLetter() + 1L);
				}
				
				if(outreach.getName().equals("Text")){
					update.setCountText(update.getCountText() + 1L);
				}
				update.setTotalEarlyAlerts(update.getTotalEarlyAlerts() + 1L);
			}
			
		}
		return (Collection<EarlyAlertStudentOutreachReportTO>)responses.values();
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

		criteria.setProjection(Projections.
				distinct(Projections.property("earlyAlert.person").as("early_alert_response_person")))
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EarlyAlertStudentReportTO.class, "early_alert_response_"));
		
		return criteria.list();
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