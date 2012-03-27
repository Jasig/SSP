package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MaritalStatus;

@Repository
public class MaritalStatusDao implements AuditableCrudDao<MaritalStatus> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(MaritalStatusDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<MaritalStatus> getAll(ObjectStatus status) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				MaritalStatus.class);
		query.addOrder(Order.asc("name"));

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public MaritalStatus get(UUID id) {
		return (MaritalStatus) sessionFactory.getCurrentSession().get(
				MaritalStatus.class, id);
	}

	@Override
	public MaritalStatus save(MaritalStatus obj) {
		if (obj.getId() != null) {
			obj = (MaritalStatus) sessionFactory.getCurrentSession().merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(MaritalStatus obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

}
