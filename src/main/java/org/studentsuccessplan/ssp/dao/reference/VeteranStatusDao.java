package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
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
	public List<VeteranStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {

		SortingAndPaging sAndP = new SortingAndPaging(status, firstResult,
				maxResults, sort,
				sortDirection, null);

		Criteria criteria = createCriteria();

		sAndP.addPagingToCriteria(criteria);
		sAndP.addStatusFilterToCriteria(criteria);

		if (StringUtils.isEmpty(sort)) {
			criteria.addOrder(Order.asc("sortOrder")).addOrder(
					Order.asc("name"));
		} else {
			sAndP.addSortingToCriteria(criteria);
		}

		return criteria.list();
	}
}
