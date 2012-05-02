package org.jasig.ssp.dao.reference;

import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Data access class for the VeteranStatus reference entity.
 */
@Repository
public class VeteranStatusDao extends ReferenceAuditableCrudDao<VeteranStatus>
		implements AuditableCrudDao<VeteranStatus> {

	public VeteranStatusDao() {
		super(VeteranStatus.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<VeteranStatus> getAll(final SortingAndPaging sAndP) {

		long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		return new PagingWrapper<VeteranStatus>(totalRows,
				createCriteria(sAndP).list());
	}
}
