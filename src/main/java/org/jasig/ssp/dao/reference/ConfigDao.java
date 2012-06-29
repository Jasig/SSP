package org.jasig.ssp.dao.reference;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Config reference entity.
 */
@Repository
public class ConfigDao extends AbstractReferenceAuditableCrudDao<Config>
		implements AuditableCrudDao<Config> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public ConfigDao() {
		super(Config.class);
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public PagingWrapper<Config> getAll(final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		return new PagingWrapper<Config>(totalRows,
				createCriteria(sAndP).list());
	}

	public Config getByName(final String name) {
		// TODO: (performance) Perfect example of data that should be cached
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("name", name));
		return (Config) query.uniqueResult();
	}
}
