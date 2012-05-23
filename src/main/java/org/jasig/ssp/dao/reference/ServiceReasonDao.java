package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ServiceReason;

/**
 * Data access class for the ServiceReason reference entity.
 */
@Repository
public class ServiceReasonDao extends AbstractReferenceAuditableCrudDao<ServiceReason>
		implements AuditableCrudDao<ServiceReason> {

	public ServiceReasonDao() {
		super(ServiceReason.class);
	}
}
