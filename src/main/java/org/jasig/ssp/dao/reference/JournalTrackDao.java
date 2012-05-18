package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalTrack;

/**
 * Data access class for the JournalTrack reference entity.
 */
@Repository
public class JournalTrackDao extends
		AbstractReferenceAuditableCrudDao<JournalTrack>
		implements AuditableCrudDao<JournalTrack> {

	public JournalTrackDao() {
		super(JournalTrack.class);
	}
}
