package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalEntryServiceImpl extends
		AbstractAuditableCrudService<JournalEntry> implements
		JournalEntryService {

	@Autowired
	private transient JournalEntryDao dao;

	@Override
	public PagingWrapper<JournalEntry> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	protected JournalEntryDao getDao() {
		return dao;
	}

	@Override
	public JournalEntry save(final JournalEntry obj)
			throws ObjectNotFoundException {
		return getDao().save(obj);
	}

}
