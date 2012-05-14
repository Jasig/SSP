package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
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
	public List<EarlyAlert> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}
}
