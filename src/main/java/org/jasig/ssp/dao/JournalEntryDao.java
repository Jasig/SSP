package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.util.sort.PagingWrapper;
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
	public PagingWrapper<JournalEntry> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<JournalEntry>(totalRows,
				createCriteria(sAndP)
						.add(Restrictions.eq("person.id", personId))
						.list());
	}
}
