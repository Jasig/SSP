package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.JournalEntryJournalStepDetailTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.util.SetOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalEntryTOFactoryImpl extends
		AbstractAuditableTOFactory<JournalEntryTO, JournalEntry>
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
	private transient JournalEntryJournalStepDetailTOFactory journalEntryJournalStepDetailTOFactory;

	@Override
	protected JournalEntryDao getDao() {
		return dao;
	}

	@Override
	public JournalEntry from(final JournalEntryTO tObject)
			throws ObjectNotFoundException {
		final JournalEntry model = super.from(tObject);

		model.setComment(tObject.getComment());
		model.setEntryDate(tObject.getEntryDate());

		if (tObject.getJournalSourceId() != null) {
			model.setJournalSource(journalSourceService.get(tObject
					.getJournalSourceId()));
		}

		if (tObject.getJournalTrackId() != null) {
			model.setJournalTrack(journalTrackService.get(tObject
					.getJournalTrackId()));
		}

		if (tObject.getConfidentialityLevelId() == null) {
			model.setConfidentialityLevel(null);
		} else {
			model.setConfidentialityLevel(confidentialityLevelService
					.get(tObject.getConfidentialityLevelId()));
		}

		if ((tObject.getJournalEntryJournalStepDetails() == null)
				|| tObject.getJournalEntryJournalStepDetails().isEmpty()) {
			SetOps.softDeleteSetItems(model.getJournalEntryJournalStepDetails());
		} else {
			SetOps.updateSet(model.getJournalEntryJournalStepDetails(),
					journalEntryJournalStepDetailTOFactory.asSet(tObject
							.getJournalEntryJournalStepDetails()));
		}

		return model;
	}

}
