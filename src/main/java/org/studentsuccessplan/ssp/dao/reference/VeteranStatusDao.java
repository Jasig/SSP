package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.util.sort.SortDirection;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
	public List<VeteranStatus> getAll(final SortingAndPaging sAndP) {

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("sortOrder", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}

		Criteria criteria = createCriteria(sAndP);

		return criteria.list();
	}
}
