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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<JournalEntry>
		implements RestrictedPersonAssocAuditableDao<JournalEntry> {

	protected JournalEntryDao() {
		super(JournalEntry.class);
	}

	public Long getJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();

		setCriteria( query,  createDateFrom,  createDateTo, studentTypeIds, null);
		
		// restrict to coach
		query.add(Restrictions.eq("createdBy", coach));

		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}

	public Long getStudentJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
 
		setCriteria( query,  createDateFrom,  createDateTo, studentTypeIds, null);
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach))
        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}
	

	@SuppressWarnings("unchecked")
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentJournalCountForCoaches(List<Person> coaches, 
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
				add(Projections.countDistinct("person").as("journal_studentCount")).
				add(Projections.countDistinct("id").as("journal_entityCount")).
				add(Projections.groupProperty("createdBy").as("journal_coach"))).setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EntityStudentCountByCoachTO.class, "journal_"));
		
		return new PagingWrapper<EntityStudentCountByCoachTO>(totalRows, (List<EntityStudentCountByCoachTO>)query.list());
	}
	
	private Criteria setCriteria(Criteria query, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds, List<UUID> serviceReasonIds){
		// add possible studentTypeId Check
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
