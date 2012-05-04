package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalSource;

/**
 * Data access class for the JournalSource reference entity.
 */
@Repository
public class JournalSourceDao extends
		ReferenceAuditableCrudDao<JournalSource>
		implements AuditableCrudDao<JournalSource> {

	public JournalSourceDao() {
		super(JournalSource.class);
	}
}
