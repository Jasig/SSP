package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

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

	public PagingWrapper<PersonTool> getAllForPersonId(
			final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("person.id", personId));
		return processCriteriaWithPaging(query, sAndP);
	}
}
