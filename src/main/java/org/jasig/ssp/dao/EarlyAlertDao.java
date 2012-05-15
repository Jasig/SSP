package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * EarlyAlert data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertDao extends
		AbstractAuditableCrudDao<EarlyAlert> implements
		AuditableCrudDao<EarlyAlert> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertDao() {
		super(EarlyAlert.class);
	}

	/**
	 * Get all EarlyAlerts for the specified person.
	 * 
	 * @param personId
	 *            Person identifier
	 * @param sAndP
	 *            Sorting and paging filters
	 * @return All EarlyAlerts for the specified person, filtered by the
	 *         specified sorting and paging filters.
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlert> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		final List<EarlyAlert> rows = createCriteria(sAndP)
				.add(Restrictions.eq("person.id", personId))
				.list();

		return new PagingWrapper<EarlyAlert>(totalRows, rows);
	}
}
