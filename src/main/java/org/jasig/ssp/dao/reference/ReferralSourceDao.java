package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ReferralSource;

/**
 * Data access class for the ReferralSource reference entity.
 */
@Repository
public class ReferralSourceDao extends AbstractReferenceAuditableCrudDao<ReferralSource>
		implements AuditableCrudDao<ReferralSource> {

	public ReferralSourceDao() {
		super(ReferralSource.class);
	}
}
