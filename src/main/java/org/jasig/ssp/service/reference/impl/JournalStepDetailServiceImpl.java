package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDetailDao;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.reference.JournalStepDetailService;
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
}
