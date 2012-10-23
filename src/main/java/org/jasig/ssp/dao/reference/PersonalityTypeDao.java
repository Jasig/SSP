package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.PersonalityType;

/**
 * Data access class for the PersonalityType reference entity.
 */
@Repository
public class PersonalityTypeDao extends AbstractReferenceAuditableCrudDao<PersonalityType>
		implements AuditableCrudDao<PersonalityType> {

	public PersonalityTypeDao() {
		super(PersonalityType.class);
	}
}
