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
package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.dao.reference.JournalTrackJournalStepDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalTrack implementation service
 * 
 * @author daniel.bower
 */
@Service
@Transactional
public class JournalTrackServiceImpl
		extends AbstractReferenceService<JournalTrack>
		implements JournalTrackService {

	@Autowired
	transient private JournalTrackDao dao;

	@Autowired
	transient private JournalTrackJournalStepDao journalTrackJournalStepDao;

	@Override
	protected JournalTrackDao getDao() {
		return dao;
	}

	protected void setDao(final JournalTrackDao dao) {
		this.dao = dao;
	}

	@Override
	public JournalTrackJournalStep addJournalStepToJournalTrack(
			final JournalStep journalStep, final JournalTrack journalTrack, int sortOrder) {
		// get current steps for track
		final PagingWrapper<JournalTrackJournalStep> journalTrackJournalSteps = journalTrackJournalStepDao
				.getAllForJournalTrackAndJournalStep(journalTrack.getId(),
						journalStep.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
			
			if(journalTrackJournalSteps.getResults() > 0)
			{
				journalTrackJournalStepDao.delete(journalTrackJournalSteps.getRows().iterator().next());
			}

			PagingWrapper<JournalTrackJournalStep> allForJournalTrack = journalTrackJournalStepDao.getAllForJournalTrack(journalTrack.getId(), new SortingAndPaging(ObjectStatus.ACTIVE));
			int order = 0;
			for (JournalTrackJournalStep journalTrackJournalStep : allForJournalTrack) 
			{
				if(order >= sortOrder )
				{
					journalTrackJournalStep.setSortOrder(order + 1);
					journalTrackJournalStepDao.save(journalTrackJournalStep);
				}
				order++;
			}
			
		
		JournalTrackJournalStep newJournalTrackJournalStep = null;
			newJournalTrackJournalStep = new JournalTrackJournalStep();
			newJournalTrackJournalStep.setJournalStep(journalStep);
			newJournalTrackJournalStep.setJournalTrack(journalTrack);
			newJournalTrackJournalStep.setObjectStatus(ObjectStatus.ACTIVE);
			newJournalTrackJournalStep.setSortOrder(sortOrder);
			newJournalTrackJournalStep = journalTrackJournalStepDao
					.save(newJournalTrackJournalStep);
		return newJournalTrackJournalStep;
	}

	@Override
	public JournalTrackJournalStep removeJournalStepFromJournalTrack(
			final JournalStep journalStep, final JournalTrack journalTrack) {
		// get current steps for track
		final PagingWrapper<JournalTrackJournalStep> journalTrackJournalSteps = journalTrackJournalStepDao
				.getAllForJournalTrackAndJournalStep(journalTrack.getId(),
						journalStep.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		JournalTrackJournalStep journalTrackJournalStep = null;
		// if this step is already there and Active, delete
		if (journalTrackJournalSteps.getResults() > 0) {
			for (final JournalTrackJournalStep item : journalTrackJournalSteps
					.getRows()) {
				item.setObjectStatus(ObjectStatus.INACTIVE);

				// we'll just return the last one
				journalTrackJournalStep = journalTrackJournalStepDao.save(item);
			}
		}

		return journalTrackJournalStep;
	}

	@Override
	public PagingWrapper<JournalTrackJournalStep> getJournalStepAssociationsForJournalTrack(
			final UUID journalTrackId, final SortingAndPaging sAndP) {
		return journalTrackJournalStepDao.getAllForJournalTrack(journalTrackId, sAndP);
	}
}
