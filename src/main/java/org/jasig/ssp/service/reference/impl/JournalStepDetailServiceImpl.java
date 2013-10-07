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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.dao.reference.JournalStepDetailDao;
import org.jasig.ssp.dao.reference.JournalStepJournalStepDetailDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalStepDetail implementation service
 * 
 * @author daniel.bower
 * 
 */
@Service
@Transactional
public class JournalStepDetailServiceImpl extends
		AbstractReferenceService<JournalStepDetail> implements
		JournalStepDetailService {

	@Autowired
	transient private JournalStepDetailDao dao;
	
	@Autowired
	transient private JournalStepJournalStepDetailDao journalStepJournalStepDetailDao;

	protected void setDao(final JournalStepDetailDao dao) {
		this.dao = dao;
	}

	@Override
	protected JournalStepDetailDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<JournalStepDetail> getAllForJournalStep(
			final JournalStep journalStep,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		List<JournalStepDetail> details = new ArrayList<JournalStepDetail>();
		List<JournalStepJournalStepDetail> journalStepJournalStepDetails = journalStep.getJournalStepJournalStepDetails();
		for (JournalStepJournalStepDetail journalStepJournalStepDetail : journalStepJournalStepDetails) {
			if(sAndP.getStatus() == null || journalStepJournalStepDetail.getObjectStatus().equals(sAndP.getStatus()))
			{
				details.add(journalStepJournalStepDetail.getJournalStepDetail());
			}
		}
		return new PagingWrapper<JournalStepDetail>(details);
	} 
	
	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		super.delete(id);
		dao.softDeleteReferencingAssociations(id);
	}
}