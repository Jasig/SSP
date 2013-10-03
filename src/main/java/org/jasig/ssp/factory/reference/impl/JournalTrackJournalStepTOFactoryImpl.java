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
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.JournalTrackJournalStepDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.JournalTrackJournalStepTOFactory;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.reference.JournalTrackJournalStepTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalStep transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class JournalTrackJournalStepTOFactoryImpl extends
		AbstractAuditableTOFactory<JournalTrackJournalStepTO, JournalTrackJournalStep>
		implements JournalTrackJournalStepTOFactory {

	public JournalTrackJournalStepTOFactoryImpl() {
		super(JournalTrackJournalStepTO.class, JournalTrackJournalStep.class);
	}

	@Autowired
	private transient JournalTrackJournalStepDao dao;

	@Autowired
	private transient JournalTrackService journalTrackService;

	@Autowired
	private transient JournalStepService journalStepService;


	@Override
	protected JournalTrackJournalStepDao getDao() {
		return dao;
	}

	@Override
	public JournalTrackJournalStep from(final JournalTrackJournalStepTO tObject)
			throws ObjectNotFoundException {

		final JournalTrackJournalStep model = super.from(tObject);

		// Can't use this mechanism to create an association and edit associated
		// objects at the same time.
		model.setJournalTrack(tObject.getJournalTrack() == null ?
				null : journalTrackService.get(tObject.getJournalTrack().getId()));
		model.setJournalStep(tObject.getJournalStep() == null ?
				null : journalStepService.get(tObject.getJournalStep().getId()));
		model.setSortOrder(tObject.getSortOrder());
		return model;
	}

}