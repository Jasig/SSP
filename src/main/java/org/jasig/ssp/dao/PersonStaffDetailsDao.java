package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonStaffDetails;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonStaffDetails model.
 */
@Repository
public class PersonStaffDetailsDao extends
		AbstractAuditableCrudDao<PersonStaffDetails> implements
		AuditableCrudDao<PersonStaffDetails> {

	/**
	 * Constructor
	 */
	public PersonStaffDetailsDao() {
		super(PersonStaffDetails.class);
	}
}