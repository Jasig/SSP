package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDetailDao;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalStepDetailServiceImpl extends
		AbstractReferenceService<JournalStepDetail>
		implements JournalStepDetailService {

	@Autowired
	transient private JournalStepDetailDao dao;

	public JournalStepDetailServiceImpl() {
		super();
	}

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
			final SortingAndPaging sAndP) {
		return dao.getAllForJournalStep(journalStep.getId(), sAndP);
	}
}
