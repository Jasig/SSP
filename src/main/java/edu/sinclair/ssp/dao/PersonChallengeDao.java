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
import edu.sinclair.ssp.model.PersonChallenge;

@Repository
public class PersonChallengeDao implements AuditableCrudDao<PersonChallenge> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonChallengeDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonChallenge> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonChallenge.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonChallenge get(UUID id) {
		return (PersonChallenge) this.sessionFactory.getCurrentSession().get(
				PersonChallenge.class, id);
	}

	public PersonChallenge forPerson(Person person) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonChallenge.class)
				.add(Restrictions.eq("person", person));
		return (PersonChallenge) query.uniqueResult();
	}

	@Override
	public PersonChallenge save(PersonChallenge obj) {
		if (obj.getId() != null) {
			this.sessionFactory.getCurrentSession().merge(obj);
		} else {
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonChallenge obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}