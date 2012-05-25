package org.jasig.ssp.dao;

import org.jasig.ssp.model.PersonEducationLevel;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonEducationLevel model.
 */
@Repository
public class PersonEducationLevelDao
		extends AbstractPersonAssocAuditableCrudDao<PersonEducationLevel>
		implements PersonAssocAuditableCrudDao<PersonEducationLevel> {

	/**
	 * Constructor
	 */
	public PersonEducationLevelDao() {
		super(PersonEducationLevel.class);
	}

}