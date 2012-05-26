package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonServiceReason;
import org.springframework.stereotype.Repository;

@Repository
public class PersonServiceReasonDao
		extends AbstractPersonAssocAuditableCrudDao<PersonServiceReason>
		implements PersonAssocAuditableCrudDao<PersonServiceReason> {

	/**
	 * Constructor
	 */
	public PersonServiceReasonDao() {
		super(PersonServiceReason.class);
	}
}
