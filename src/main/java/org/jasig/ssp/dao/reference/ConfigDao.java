package org.jasig.ssp.dao.reference;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
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
	public PagingWrapper<Config> getAll(final SortingAndPaging sAndP) {
		SortingAndPaging sp = sAndP;
		if (sp == null) {
			sp = new SortingAndPaging(ObjectStatus.ACTIVE);
		}

		if (!sp.isSorted()) {
			sp.appendSortField("sortOrder", SortDirection.ASC);
			sp.appendSortField("name", SortDirection.ASC);
		}

		return super.getAll(sp);
	}

	@Override
	public Config getByName(final String name) {
		// TODO: (performance) Perfect example of data that should be cached
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("name", name));
		return (Config) query.uniqueResult();
	}
}