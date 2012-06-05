package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.JournalEntryDetailDao;
import org.jasig.ssp.dao.reference.JournalStepJournalStepDetailDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.JournalEntryDetailTOFactory;
import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalEntryDetailTOFactoryImpl
		extends
		AbstractAuditableTOFactory<JournalEntryDetailTO, JournalEntryDetail>
		implements JournalEntryDetailTOFactory {

	public JournalEntryDetailTOFactoryImpl() {
		super(JournalEntryDetailTO.class,
				JournalEntryDetail.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalEntryDetailTOFactoryImpl.class);

	@Autowired
	private transient JournalEntryDetailDao dao;

	@Autowired
	private transient JournalStepJournalStepDetailDao journalStepJournalStepDetailDao;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Override
	protected JournalEntryDetailDao getDao() {
		return dao;
	}

	@Override
	public JournalEntryDetail from(
			final JournalEntryDetailTO tObject)
			throws ObjectNotFoundException {
		final JournalEntryDetail model = super.from(tObject);

		if (tObject.getJournalEntryId() != null) {
			model.setJournalEntry(journalEntryService.get(tObject
					.getJournalEntryId()));
		}

		if ((tObject.getJournalStep() == null)
				&& (tObject.getJournalStepDetail() != null)
				&& (tObject.getJournalStep().getId() != null)
				&& (tObject.getJournalStepDetail().getId() != null)) {
			final PagingWrapper<JournalStepJournalStepDetail> jsJsds = journalStepJournalStepDetailDao
					.getAllForJournalStepDetailAndJournalStep(tObject
							.getJournalStepDetail().getId(), tObject
							.getJournalStep().getId(), new SortingAndPaging(
							ObjectStatus.ACTIVE));
			if (jsJsds.getResults() > 1) {
				model.setJournalStepJournalStepDetail(jsJsds.getRows()
						.iterator().next());
				LOGGER.error("Multiple Active JournalStepJournalStepDetail objects exists for a Journal Step and Journal Step Detail");
			} else if (jsJsds.getResults() == 1) {
				model.setJournalStepJournalStepDetail(jsJsds.getRows()
						.iterator().next());
			} else {
				model.setJournalStepJournalStepDetail(null);
			}
		} else {
			model.setJournalStepJournalStepDetail(null);
		}

		return model;
	}
}
