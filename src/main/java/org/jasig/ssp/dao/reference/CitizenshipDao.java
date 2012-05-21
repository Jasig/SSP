package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Citizenship;

/**
 * Data access class for the Citizenship reference entity.
 */
@Repository
public class CitizenshipDao extends AbstractReferenceAuditableCrudDao<Citizenship>
		implements AuditableCrudDao<Citizenship> {

	public CitizenshipDao() {
		super(Citizenship.class);
	}
}
