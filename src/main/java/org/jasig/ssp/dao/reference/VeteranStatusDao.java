package org.jasig.ssp.dao.reference;

import org.hibernate.criterion.Projections;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the VeteranStatus reference entity.
 */
@Repository
public class VeteranStatusDao extends AbstractReferenceAuditableCrudDao<VeteranStatus>
		implements AuditableCrudDao<VeteranStatus> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public VeteranStatusDao() {
		super(VeteranStatus.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<VeteranStatus> getAll(final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		return new PagingWrapper<VeteranStatus>(totalRows,
				createCriteria(sAndP).list());
	}
}
