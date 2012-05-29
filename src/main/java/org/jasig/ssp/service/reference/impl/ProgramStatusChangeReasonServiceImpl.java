package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ProgramStatusChangeReasonDao;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.service.reference.ProgramStatusChangeReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProgramStatusChangeReason service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class ProgramStatusChangeReasonServiceImpl extends
		AbstractReferenceService<ProgramStatusChangeReason>
		implements ProgramStatusChangeReasonService {

	@Autowired
	transient private ProgramStatusChangeReasonDao dao;

	@Override
	protected ProgramStatusChangeReasonDao getDao() {
		return dao;
	}

	protected void setDao(final ProgramStatusChangeReasonDao dao) {
		this.dao = dao;
	}
}