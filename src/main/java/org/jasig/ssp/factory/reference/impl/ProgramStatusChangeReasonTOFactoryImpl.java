package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.ProgramStatusChangeReasonDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ProgramStatusChangeReasonTOFactory;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.transferobject.reference.ProgramStatusChangeReasonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProgramStatusChangeReason transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class ProgramStatusChangeReasonTOFactoryImpl
		extends
		AbstractReferenceTOFactory<ProgramStatusChangeReasonTO, ProgramStatusChangeReason>
		implements ProgramStatusChangeReasonTOFactory {

	public ProgramStatusChangeReasonTOFactoryImpl() {
		super(ProgramStatusChangeReasonTO.class,
				ProgramStatusChangeReason.class);
	}

	@Autowired
	private transient ProgramStatusChangeReasonDao dao;

	@Override
	protected ProgramStatusChangeReasonDao getDao() {
		return dao;
	}
}