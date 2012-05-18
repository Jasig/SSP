package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Campus;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Campus reference entity.
 * 
 * @author jon.adams
 */
@Repository
public class CampusDao extends AbstractReferenceAuditableCrudDao<Campus>
		implements AuditableCrudDao<Campus> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public CampusDao() {
		super(Campus.class);
	}
}
