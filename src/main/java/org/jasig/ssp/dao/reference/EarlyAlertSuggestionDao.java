package org.jasig.ssp.dao.reference;

import org.hibernate.criterion.Projections;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the EarlyAlertSuggestion reference entity.
 * 
 * @author jon.adams
 */
@Repository
public class EarlyAlertSuggestionDao extends
		ReferenceAuditableCrudDao<EarlyAlertSuggestion>
		implements AuditableCrudDao<EarlyAlertSuggestion> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public EarlyAlertSuggestionDao() {
		super(EarlyAlertSuggestion.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<EarlyAlertSuggestion> getAll(
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		return new PagingWrapper<EarlyAlertSuggestion>(totalRows,
				createCriteria(sAndP).list());
	}
}
