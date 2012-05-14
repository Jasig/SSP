package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * EarlyAlertResponse data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertResponseDao extends
		AbstractAuditableCrudDao<EarlyAlertResponse> implements
		AuditableCrudDao<EarlyAlertResponse> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertResponseDao() {
		super(EarlyAlertResponse.class);
	}

	/**
	 * Get all EarlyAlertResponses for the specified person.
	 * 
	 * @param personId
	 *            Person identifier
	 * @param sAndP
	 *            Sorting and paging filters
	 * @return All EarlyAlertResponses for the specified person.
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlertResponse> getAllForPersonId(
			final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.createAlias("earlyAlert", "ea")
				.add(Restrictions.eq("ea.person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<EarlyAlertResponse>(totalRows, createCriteria(
				sAndP)
				.createAlias("earlyAlert", "ea")
				.add(Restrictions.eq("ea.person.id", personId))
				.list());
	}
}
