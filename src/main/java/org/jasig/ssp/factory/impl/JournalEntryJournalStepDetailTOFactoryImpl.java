package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.JournalEntryJournalStepDetailDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.JournalEntryJournalStepDetailTOFactory;
import org.jasig.ssp.factory.reference.JournalStepDetailTOFactory;
import org.jasig.ssp.model.JournalEntryJournalStepDetail;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.JournalEntryJournalStepDetailTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalEntryJournalStepDetailTOFactoryImpl
		extends
		AbstractAuditableTOFactory<JournalEntryJournalStepDetailTO, JournalEntryJournalStepDetail>
		implements JournalEntryJournalStepDetailTOFactory {

	public JournalEntryJournalStepDetailTOFactoryImpl() {
		super(JournalEntryJournalStepDetailTO.class,
				JournalEntryJournalStepDetail.class);
	}

	@Autowired
	private transient JournalEntryJournalStepDetailDao dao;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient JournalStepDetailTOFactory journalStepDetailTOFactory;

	@Override
	protected JournalEntryJournalStepDetailDao getDao() {
		return dao;
	}

	@Override
	public JournalEntryJournalStepDetail from(
			final JournalEntryJournalStepDetailTO tObject)
			throws ObjectNotFoundException {
		final JournalEntryJournalStepDetail model = super.from(tObject);

		if (tObject.getJournalEntryId() != null) {
			model.setJournalEntry(journalEntryService.get(tObject
					.getJournalEntryId()));
		}

		if (tObject.getJournalStepDetail() != null) {
			model.setJournalStepDetail(journalStepDetailTOFactory.from(tObject
					.getJournalStepDetail()));
		}

		return model;
	}
}
