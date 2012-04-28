package org.studentsuccessplan.ssp.dao;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.PersonDemographics;

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