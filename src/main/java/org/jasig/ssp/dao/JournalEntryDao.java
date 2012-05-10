package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryDao extends
		AbstractAuditableCrudDao<JournalEntry> implements
		AuditableCrudDao<JournalEntry> {

	protected JournalEntryDao() {
		super(JournalEntry.class);
	}

	@SuppressWarnings("unchecked")
	public List<JournalEntry> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

}
