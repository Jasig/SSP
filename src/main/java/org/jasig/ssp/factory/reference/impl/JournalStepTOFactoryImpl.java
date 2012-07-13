package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalStep transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class JournalStepTOFactoryImpl extends
		AbstractReferenceTOFactory<JournalStepTO, JournalStep>
		implements JournalStepTOFactory {

	public JournalStepTOFactoryImpl() {
		super(JournalStepTO.class, JournalStep.class);
	}

	@Autowired
	private transient JournalStepDao dao;

	@Override
	protected JournalStepDao getDao() {
		return dao;
	}

	@Override
	public JournalStep from(final JournalStepTO tObject)
			throws ObjectNotFoundException {
		final JournalStep model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());
		model.setUsedForTransition(tObject.isUsedForTransition());

		return model;
	}
}