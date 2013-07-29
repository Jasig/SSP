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
package org.jasig.ssp.factory.impl;

import java.util.HashSet;

import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.JournalEntryDetailTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.util.SetOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalEntryTOFactoryImpl // NOPMD
		extends AbstractAuditableTOFactory<JournalEntryTO, JournalEntry>
		implements JournalEntryTOFactory {

	public JournalEntryTOFactoryImpl() {
		super(JournalEntryTO.class, JournalEntry.class);
	}

	@Autowired
	private transient JournalEntryDao dao;

	@Autowired
	private transient JournalSourceService journalSourceService;

	@Autowired
	private transient JournalTrackService journalTrackService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient JournalEntryDetailTOFactory journalEntryDetailTOFactory;

	@Override
	protected JournalEntryDao getDao() {
		return dao;
	}

	@Override
	public JournalEntry from(final JournalEntryTO tObject) // NOPMD
			throws ObjectNotFoundException {
		final JournalEntry model = super.from(tObject);

		model.setComment(tObject.getComment());
		model.setEntryDate(tObject.getEntryDate());

		if ((tObject.getJournalSource() != null)
				&& (tObject.getJournalSource().getId() != null)) {
			model.setJournalSource(journalSourceService.get(tObject
					.getJournalSource().getId()));
		}

		if ((tObject.getJournalTrack() != null)
				&& (tObject.getJournalTrack().getId() != null)) {
			model.setJournalTrack(journalTrackService.get(tObject
					.getJournalTrack().getId()));
		}
		else
		{
			model.setJournalTrack(null);
		}

		if ((tObject.getConfidentialityLevel() == null)
				|| (tObject.getConfidentialityLevel().getId() == null)) {
			model.setConfidentialityLevel(null);
		} else {
			model.setConfidentialityLevel(confidentialityLevelService
					.get(tObject.getConfidentialityLevel().getId()));
		}

		if ((tObject.getJournalEntryDetails() == null)
				|| tObject.getJournalEntryDetails().isEmpty()) {
			SetOps.softDeleteSetItems(model.getJournalEntryDetails());
		} else {
			SetOps.updateSet(
					model.getJournalEntryDetails(),
					journalEntryDetailTOFactory.asSet(
							tObject.getJournalEntryDetails() == null ? new HashSet<JournalEntryDetailTO>()
									: tObject.getJournalEntryDetails(), model));
		}

		// ensure JournalEntry.Id is set on all children as necessary
		if (model.getId() != null) {
			for (final JournalEntryDetail jed : model.getJournalEntryDetails()) {
				jed.setJournalEntry(model);
			}
		}

		return model;
	}
}