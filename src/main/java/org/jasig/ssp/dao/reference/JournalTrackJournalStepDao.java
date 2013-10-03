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
package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class JournalTrackJournalStepDao
		extends AbstractReferenceAuditableCrudDao<JournalTrackJournalStep>
		implements AuditableCrudDao<JournalTrackJournalStep> {

	public JournalTrackJournalStepDao() {
		super(JournalTrackJournalStep.class);
	}

	public PagingWrapper<JournalTrackJournalStep> getAllForJournalTrack(
			final UUID journalTrackId, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalTrack.id", journalTrackId));
		sAndP.appendSortField("sortOrder", SortDirection.ASC);
		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}

	public PagingWrapper<JournalTrackJournalStep> getAllForJournalTrackAndJournalStep(
			final UUID journalTrackId, final UUID journalStepId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalTrack.id", journalTrackId));
		query.add(Restrictions.eq("journalStep.id", journalStepId));
		sAndP.appendSortField("sortOrder", SortDirection.ASC);
		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}

}
