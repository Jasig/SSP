package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;

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

		Criteria criteria = createCriteria();

		if (firstResult != null && firstResult.intValue() >= 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResults != null && maxResults.intValue() > 0) {
			criteria.setMaxResults(maxResults);
		}

		if (StringUtils.isEmpty(sort)) {
			criteria.addOrder(Order.asc("sortOrder")).addOrder(
					Order.asc("name"));
		} else {
			criteria = addOrderToCriteria(criteria, sort, sortDirection);
		}

		if (status != ObjectStatus.ALL) {
			criteria.add(Restrictions.eq("objectStatus", status));
		}

		return criteria.list();
	}
}
