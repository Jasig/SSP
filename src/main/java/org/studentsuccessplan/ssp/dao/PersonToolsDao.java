package edu.sinclair.ssp.dao;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.tool.PersonTool;

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
