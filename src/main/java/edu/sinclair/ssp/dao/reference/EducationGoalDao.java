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
import edu.sinclair.ssp.model.reference.EducationGoal;

@Repository
public class EducationGoalDao implements AuditableCrudDao<EducationGoal> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(EducationGoalDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<EducationGoal> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(EducationGoal.class);
		query.addOrder(Order.asc("name"));

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public EducationGoal get(UUID id) {
		return (EducationGoal) this.sessionFactory.getCurrentSession().get(
				EducationGoal.class, id);
	}

	@Override
	public EducationGoal save(EducationGoal obj) {
		if (obj.getId() != null) {
			this.sessionFactory.getCurrentSession().merge(obj);
		} else {
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(EducationGoal obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
