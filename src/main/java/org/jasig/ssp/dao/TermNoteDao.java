package org.jasig.ssp.dao;

import org.jasig.ssp.model.TermNote;
import org.springframework.stereotype.Repository;

@Repository
public class TermNoteDao extends AbstractAuditableCrudDao<TermNote> implements
		AuditableCrudDao<TermNote> {

	protected TermNoteDao(Class<TermNote> persistentClass) {
		super(persistentClass);
	}
	
	protected TermNoteDao() {
		super(TermNote.class);
	}
}
