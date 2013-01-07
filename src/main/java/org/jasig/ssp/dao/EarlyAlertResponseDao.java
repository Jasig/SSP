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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
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

	public Long getEarlyAlertResponseCountForDate(Date createDateFrom,
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
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
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
				if(outreach.getName() == "Phone Call"){
					update.setCountPhoneCalls(update.getCountPhoneCalls() + 1L);
				}
				if(outreach.getName() == "Email"){
					update.setCountEmail(update.getCountEmail() + 1L);
				}
				
				if(outreach.getName() == "In Person"){
					update.setCountInPerson(update.getCountInPerson() + 1L);
				}
				
				if(outreach.getName() == "Letter"){
					update.setCountLetter(update.getCountLetter() + 1L);
				}
				
				if(outreach.getName() == "Text"){
					update.setCountText(update.getCountText() + 1L);
				}
			}
		}
		return (Collection<EarlyAlertStudentOutreachReportTO>)responses.values();
	}
}