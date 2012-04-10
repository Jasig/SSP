package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.Citizenship;

/**
 * Data access class for the Citizenship reference entity.
 */
@Repository
public class CitizenshipDao extends ReferenceAuditableCrudDao<Citizenship>
		implements AuditableCrudDao<Citizenship> {

	public CitizenshipDao() {
		super(Citizenship.class);
	}
}
