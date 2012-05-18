package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.MaritalStatus;

/**
 * Data access class for the MaritalStatus reference entity.
 */
@Repository
public class MaritalStatusDao extends AbstractReferenceAuditableCrudDao<MaritalStatus>
		implements AuditableCrudDao<MaritalStatus> {

	public MaritalStatusDao() {
		super(MaritalStatus.class);
	}
}
