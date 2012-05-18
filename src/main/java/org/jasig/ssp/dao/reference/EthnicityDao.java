package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Ethnicity;

/**
 * Data access class for the Ethnicity reference entity.
 */
@Repository
public class EthnicityDao extends AbstractReferenceAuditableCrudDao<Ethnicity>
		implements AuditableCrudDao<Ethnicity> {

	public EthnicityDao() {
		super(Ethnicity.class);
	}
}
