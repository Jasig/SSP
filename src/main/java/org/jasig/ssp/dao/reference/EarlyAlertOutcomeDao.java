package org.jasig.ssp.dao.reference;

import org.hibernate.criterion.Projections;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the EarlyAlertOutcome reference entity.
 * 
 * @author jon.adams
 */
@Repository
public class EarlyAlertOutcomeDao extends
		ReferenceAuditableCrudDao<EarlyAlertOutcome>
		implements AuditableCrudDao<EarlyAlertOutcome> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public EarlyAlertOutcomeDao() {
		super(EarlyAlertOutcome.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlertOutcome> getAll(final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		return new PagingWrapper<EarlyAlertOutcome>(totalRows,
				createCriteria(sAndP).list());
	}
}
