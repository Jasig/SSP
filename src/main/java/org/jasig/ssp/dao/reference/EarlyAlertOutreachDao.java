package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the EarlyAlertOutreach reference entity.
 * 
 * @author jon.adams
 */
@Repository
public class EarlyAlertOutreachDao extends
		AbstractReferenceAuditableCrudDao<EarlyAlertOutreach>
		implements AuditableCrudDao<EarlyAlertOutreach> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public EarlyAlertOutreachDao() {
		super(EarlyAlertOutreach.class);
	}

	@Override
	public PagingWrapper<EarlyAlertOutreach> getAll(final SortingAndPaging sAndP) {
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