package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.Referral;

/**
 * Data access class for the Referral reference entity.
 */
@Repository
public class ReferralDao extends ReferenceAuditableCrudDao<Referral> implements
		AuditableCrudDao<Referral> {

	public ReferralDao() {
		super(Referral.class);
	}

}
