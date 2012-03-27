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
import edu.sinclair.ssp.model.PersonEducationGoal;

@Repository
public class PersonEducationGoalDao implements
		AuditableCrudDao<PersonEducationGoal> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonEducationGoalDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonEducationGoal> getAll(ObjectStatus status) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				PersonEducationGoal.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonEducationGoal get(UUID id) {
		return (PersonEducationGoal) sessionFactory.getCurrentSession().get(
				PersonEducationGoal.class, id);
	}

	public PersonEducationGoal forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationGoal.class)
				.add(Restrictions.eq("person", person));
		return (PersonEducationGoal) query.uniqueResult();
	}

	@Override
	public PersonEducationGoal save(PersonEducationGoal obj) {
		if (obj.getId() != null) {
			obj = (PersonEducationGoal) sessionFactory.getCurrentSession()
					.merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonEducationGoal obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

}
