package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
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
		AbstractReferenceAuditableCrudDao<EarlyAlertSuggestion>
		implements AuditableCrudDao<EarlyAlertSuggestion> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public EarlyAlertSuggestionDao() {
		super(EarlyAlertSuggestion.class);
	}

	@Override
	public PagingWrapper<EarlyAlertSuggestion> getAll(
			final SortingAndPaging sAndP) {
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
}