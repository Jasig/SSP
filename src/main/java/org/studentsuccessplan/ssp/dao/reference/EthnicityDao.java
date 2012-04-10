package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;

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
