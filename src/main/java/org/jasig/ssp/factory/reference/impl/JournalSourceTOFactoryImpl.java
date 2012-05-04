package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.JournalSourceDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.JournalSourceTOFactory;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.transferobject.reference.JournalSourceTO;

@Service
@Transactional(readOnly = true)
public class JournalSourceTOFactoryImpl extends
		AbstractReferenceTOFactory<JournalSourceTO, JournalSource>
		implements JournalSourceTOFactory {

	public JournalSourceTOFactoryImpl() {
		super(JournalSourceTO.class, JournalSource.class);
	}

	@Autowired
	private transient JournalSourceDao dao;

	@Override
	protected JournalSourceDao getDao() {
		return dao;
	}

}
