package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationPlan;

@Repository
public class PersonEducationPlanDao implements
		AuditableCrudDao<PersonEducationPlan> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonEducationPlanDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonEducationPlan> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationPlan.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonEducationPlan get(UUID id) {
		return (PersonEducationPlan) this.sessionFactory.getCurrentSession()
				.get(PersonEducationPlan.class, id);
	}

	public PersonEducationPlan forPerson(Person person) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationPlan.class)
				.add(Restrictions.eq("person", person));
		return (PersonEducationPlan) query.uniqueResult();
	}

	@Override
	public PersonEducationPlan save(PersonEducationPlan obj) {
		if (obj.getId() != null) {
			this.sessionFactory.getCurrentSession().merge(obj);
		} else {
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonEducationPlan obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
