package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Goal entity.
 */
@Repository
public class GoalDao
		extends AbstractAuditableCrudDao<Goal>
		implements AuditableCrudDao<Goal> {

	public GoalDao() {
		super(Goal.class);
	}

	@SuppressWarnings("unchecked")
	public PagingWrapper<Goal> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<Goal>(totalRows,
				createCriteria(sAndP)
						.add(Restrictions.eq("person.id", personId))
						.list());
	}
}
