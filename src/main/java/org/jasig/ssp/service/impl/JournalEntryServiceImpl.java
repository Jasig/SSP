package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalEntryServiceImpl
		extends AbstractPersonAssocAuditableService<JournalEntry>
		implements JournalEntryService {

	@Autowired
	private transient JournalEntryDao dao;

	@Override
	protected JournalEntryDao getDao() {
		return dao;
	}

}
