package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDao;
import org.jasig.ssp.model.reference.JournalStep;
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
}
