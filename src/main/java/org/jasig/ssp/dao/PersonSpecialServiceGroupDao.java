package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.springframework.stereotype.Repository;

@Repository
public class PersonSpecialServiceGroupDao
		extends AbstractPersonAssocAuditableCrudDao<PersonSpecialServiceGroup>
		implements PersonAssocAuditableCrudDao<PersonSpecialServiceGroup> {

	/**
	 * Constructor
	 */
	public PersonSpecialServiceGroupDao() {
		super(PersonSpecialServiceGroup.class);
	}
}