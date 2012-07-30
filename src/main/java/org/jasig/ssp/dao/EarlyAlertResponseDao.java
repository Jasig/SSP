package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
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
	 * Get all {@link EarlyAlertResponse} for the specified {@link EarlyAlert}.
	 * 
	 * @param earlyAlertId
	 *            Early Alert identifier
	 * @param sAndP
	 *            Sorting and paging filters
	 * @return All EarlyAlertResponses for the specified person.
	 */
	public PagingWrapper<EarlyAlertResponse> getAllForEarlyAlertId(
			final UUID earlyAlertId,
			final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(
				createCriteria().add(
						Restrictions.eq("earlyAlert.id", earlyAlertId)), sAndP,
				true);
	}
}