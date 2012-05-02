package org.jasig.ssp.dao;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.model.tool.PersonTool;

/**
 * CRUD methods for the PersonDemographics model.
 */
@Repository
public class PersonToolsDao extends
		AbstractAuditableCrudDao<PersonTool> implements
		AuditableCrudDao<PersonTool> {

	protected PersonToolsDao() {
		super(PersonTool.class);
	}

}
