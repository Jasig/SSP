package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.MilitaryAffiliation;

/**
 * Data access class for the MilitaryAffiliation reference entity.
 */
@Repository
public class MilitaryAffiliationDao extends AbstractReferenceAuditableCrudDao<MilitaryAffiliation>
		implements AuditableCrudDao<MilitaryAffiliation> {

	public MilitaryAffiliationDao() {
		super(MilitaryAffiliation.class);
	}
}
