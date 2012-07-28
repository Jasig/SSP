package org.jasig.ssp.dao.tool;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractPersonAssocAuditableCrudDao;
import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * PersonTool DAO
 */
@Repository
public class PersonToolDao
		extends AbstractPersonAssocAuditableCrudDao<PersonTool>
		implements PersonAssocAuditableCrudDao<PersonTool> {

	protected PersonToolDao() {
		super(PersonTool.class);
	}

	public PagingWrapper<PersonTool> getAllForPersonAndTool(
			final UUID personId, final Tools tool, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("person.id", personId));
		query.add(Restrictions.eq("tool", tool));
		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}
}