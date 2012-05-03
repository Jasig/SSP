package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.JournalStepDetailDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.JournalStepDetailTOFactory;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalStepDetailTOFactoryImpl extends
		AbstractReferenceTOFactory<JournalStepDetailTO, JournalStepDetail>
		implements JournalStepDetailTOFactory {

	public JournalStepDetailTOFactoryImpl() {
		super(JournalStepDetailTO.class, JournalStepDetail.class);
	}

	@Autowired
	private transient JournalStepDetailDao dao;

	@Override
	protected JournalStepDetailDao getDao() {
		return dao;
	}

	@Override
	public JournalStepDetail from(final JournalStepDetailTO tObject)
			throws ObjectNotFoundException {
		final JournalStepDetail model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

}
