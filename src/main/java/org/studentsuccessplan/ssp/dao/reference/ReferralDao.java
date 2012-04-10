package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.Referral;

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
