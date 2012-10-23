package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.CampusService;

/**
 * Data access class for the CampusService reference entity.
 */
@Repository
public class CampusServiceDao extends AbstractReferenceAuditableCrudDao<CampusService>
		implements AuditableCrudDao<CampusService> {

	public CampusServiceDao() {
		super(CampusService.class);
	}
}
