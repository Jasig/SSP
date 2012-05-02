package org.jasig.ssp.dao;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.model.PersonFundingSource;

/**
 * CRUD methods for the PersonFundingSource model.
 */
@Repository
public class PersonFundingSourceDao extends
		AbstractAuditableCrudDao<PersonFundingSource> implements
		AuditableCrudDao<PersonFundingSource> {

	/**
	 * Constructor
	 */
	public PersonFundingSourceDao() {
		super(PersonFundingSource.class);
	}

}