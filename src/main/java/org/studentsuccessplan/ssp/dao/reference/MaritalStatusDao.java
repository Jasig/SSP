package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;

/**
 * Data access class for the MaritalStatus reference entity.
 */
@Repository
public class MaritalStatusDao extends ReferenceAuditableCrudDao<MaritalStatus>
		implements AuditableCrudDao<MaritalStatus> {

	public MaritalStatusDao() {
		super(MaritalStatus.class);
	}
}
