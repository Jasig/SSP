package edu.sinclair.ssp.dao;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.PersonFundingSource;

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