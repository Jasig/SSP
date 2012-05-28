package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonReferralSource;
import org.springframework.stereotype.Repository;

@Repository
public class PersonReferralSourceDao
		extends AbstractPersonAssocAuditableCrudDao<PersonReferralSource>
		implements PersonAssocAuditableCrudDao<PersonReferralSource> {

	/**
	 * Constructor
	 */
	public PersonReferralSourceDao() {
		super(PersonReferralSource.class);
	}
}
