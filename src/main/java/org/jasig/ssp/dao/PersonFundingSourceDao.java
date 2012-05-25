package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonFundingSource;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonFundingSource model.
 */
@Repository
public class PersonFundingSourceDao
		extends AbstractPersonAssocAuditableCrudDao<PersonFundingSource>
		implements PersonAssocAuditableCrudDao<PersonFundingSource> {

	/**
	 * Constructor
	 */
	public PersonFundingSourceDao() {
		super(PersonFundingSource.class);
	}

}