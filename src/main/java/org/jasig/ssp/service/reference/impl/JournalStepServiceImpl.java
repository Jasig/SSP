package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDao;
import org.jasig.ssp.dao.reference.JournalStepJournalStepDetailDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalStepServiceImpl extends
		AbstractReferenceService<JournalStep>
		implements JournalStepService {

	@Autowired
	transient private JournalStepDao dao;

	@Autowired
	transient private JournalStepJournalStepDetailDao journalStepJournalStepDetailDao;

	protected void setDao(final JournalStepDao dao) {
		this.dao = dao;
	}

	@Override
	protected JournalStepDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<JournalStep> getAllForJournalTrack(
			final JournalTrack journalTrack,
			final SortingAndPaging sAndP) {
		return getDao().getAllForJournalTrack(journalTrack.getId(), sAndP);
	}

	@Override
	public JournalStepJournalStepDetail addJournalStepDetailToJournalStep(
			final JournalStepDetail journalStepDetail,
			final JournalStep journalStep) {
		final PagingWrapper<JournalStepJournalStepDetail> journalStepDetailCategories = journalStepJournalStepDetailDao
				.getAllForJournalStepDetailAndJournalStep(
						journalStepDetail.getId(),
						journalStep.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		JournalStepJournalStepDetail journalStepJournalStepDetail = null;
		// if this journalStepDetail is already there and ACTIVE, ignore
		if (journalStepDetailCategories.getResults() < 1) {
			journalStepJournalStepDetail = new JournalStepJournalStepDetail();
			journalStepJournalStepDetail.setJournalStep(journalStep);
			journalStepJournalStepDetail
					.setJournalStepDetail(journalStepDetail);
			journalStepJournalStepDetail.setObjectStatus(ObjectStatus.ACTIVE);

			journalStepJournalStepDetail = journalStepJournalStepDetailDao
					.save(journalStepJournalStepDetail);
		}

		return journalStepJournalStepDetail;
	}

	@Override
	public JournalStepJournalStepDetail removeJournalStepDetailFromJournalStep(
			final JournalStepDetail journalStepDetail,
			final JournalStep journalStep) {
		// get current journalStepDetails for journalStep
		final PagingWrapper<JournalStepJournalStepDetail> journalStepDetailCategories = journalStepJournalStepDetailDao
				.getAllForJournalStepDetailAndJournalStep(
						journalStepDetail.getId(),
						journalStep.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		JournalStepJournalStepDetail journalStepJournalStepDetail = null;
		// if this journalStepDetail is already there and ACTIVE, delete
		if (journalStepDetailCategories.getResults() > 0) {
			for (JournalStepJournalStepDetail item : journalStepDetailCategories
					.getRows()) {
				item.setObjectStatus(ObjectStatus.DELETED);

				// we'll just return the last one
				journalStepJournalStepDetail = journalStepJournalStepDetailDao
						.save(item);
			}
		}

		return journalStepJournalStepDetail;
	}
}
