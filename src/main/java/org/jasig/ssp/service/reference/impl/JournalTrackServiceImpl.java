package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.dao.reference.JournalTrackJournalStepDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
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
			final JournalStep journalStep, final JournalTrack journalTrack) {
		// get current steps for track
		final PagingWrapper<JournalTrackJournalStep> journalTrackJournalSteps = journalTrackJournalStepDao
				.getAllForJournalTrackAndJournalStep(journalTrack.getId(),
						journalStep.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		JournalTrackJournalStep journalTrackJournalStep = null;
		// if this step is already there and Active, ignore
		if (journalTrackJournalSteps.getResults() < 1) {
			journalTrackJournalStep = new JournalTrackJournalStep();
			journalTrackJournalStep.setJournalStep(journalStep);
			journalTrackJournalStep.setJournalTrack(journalTrack);
			journalTrackJournalStep.setObjectStatus(ObjectStatus.ACTIVE);

			journalTrackJournalStep = journalTrackJournalStepDao
					.save(journalTrackJournalStep);
		}

		return journalTrackJournalStep;
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
}
