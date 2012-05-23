package org.jasig.ssp.dao.tool;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class PersonToolDao
		extends AbstractAuditableCrudDao<PersonTool>
		implements AuditableCrudDao<PersonTool> {

	protected PersonToolDao() {
		super(PersonTool.class);
	}

	public PagingWrapper<PersonTool> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("person.id", personId));
		return processCriteriaWithPaging(query, sAndP);
	}

	public PagingWrapper<PersonTool> getAllForPersonAndTool(
			final UUID personId,
			final Tools tool,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("person.id", personId));
		query.add(Restrictions.eq("tool", tool));
		return processCriteriaWithPaging(query, sAndP);
	}

}
