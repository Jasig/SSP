package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonDemographics;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonDemographics model.
 */
@Repository
public class PersonDemographicsDao extends
		AbstractAuditableCrudDao<PersonDemographics> implements
		AuditableCrudDao<PersonDemographics> {

	/**
	 * Constructor
	 */
	public PersonDemographicsDao() {
		super(PersonDemographics.class);
	}
}