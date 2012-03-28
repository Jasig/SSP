package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.Ethnicity;

/**
 * Data access class for the Ethnicity reference entity.
 */
@Repository
public class EthnicityDao extends ReferenceAuditableCrudDao<Ethnicity>
		implements AuditableCrudDao<Ethnicity> {

	public EthnicityDao() {
		super(Ethnicity.class);
	}
}
