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
import edu.sinclair.ssp.model.PersonFundingSource;

@Repository
public class PersonFundingSourceDao implements
		AuditableCrudDao<PersonFundingSource> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonFundingSourceDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonFundingSource> getAll(ObjectStatus status) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				PersonFundingSource.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonFundingSource get(UUID id) {
		return (PersonFundingSource) sessionFactory.getCurrentSession().get(
				PersonFundingSource.class, id);
	}

	public PersonFundingSource forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonFundingSource.class)
				.add(Restrictions.eq("person", person));
		return (PersonFundingSource) query.uniqueResult();
	}

	@Override
	public PersonFundingSource save(PersonFundingSource obj) {
		if (obj.getId() != null) {
			obj = (PersonFundingSource) sessionFactory.getCurrentSession()
					.merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonFundingSource obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

}
