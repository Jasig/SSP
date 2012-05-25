package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonChallenge;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonChallenge model.
 */
@Repository
public class PersonChallengeDao extends
		AbstractPersonAssocAuditableCrudDao<PersonChallenge> implements
		PersonAssocAuditableCrudDao<PersonChallenge> {

	/**
	 * Constructor
	 */
	public PersonChallengeDao() {
		super(PersonChallenge.class);
	}
}