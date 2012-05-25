package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the EarlyAlertRouting entity.
 * 
 * @author jon.adams
 */
@Repository
public class EarlyAlertRoutingDao
		extends AbstractAuditableCrudDao<EarlyAlertRouting>
		implements AuditableCrudDao<EarlyAlertRouting> {

	/**
	 * Construct an instance with the specified specific class for use by base
	 * class methods.
	 */
	public EarlyAlertRoutingDao() {
		super(EarlyAlertRouting.class);
	}

	/**
	 * Gets all instances for the specified {@link Campus}, filtered by the
	 * specified filters.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param sAndP
	 *            Sorting and paging filters, if any
	 * @return All instance for the specified Person with any specified filters
	 *         applied.
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlertRouting> getAllForCampusId(
			final UUID campusId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("campus.id", campusId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<EarlyAlertRouting>(totalRows,
				createCriteria(sAndP)
						.add(Restrictions.eq("campus.id", campusId))
						.list());
	}
}