package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.Citizenship;

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
