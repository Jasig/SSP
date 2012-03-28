package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

@Repository
public class PersonDao implements AuditableCrudDao<Person> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @param status
	 *            Object status filter
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAll(ObjectStatus status) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Person.class).addOrder(Order.asc("lastName"))
				.addOrder(Order.asc("firstName"));

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @param status
	 *            Object status filter. Set to {@link ObjectStatus#ALL} to
	 *            return all results.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAll(ObjectStatus status, int firstResult,
			int maxResults, String sortExpression) {
		if (firstResult < 0) {
			throw new IllegalArgumentException(
					"firstResult must be 0 or greater.");
		}

		if (maxResults < 1) {
			throw new IllegalArgumentException(
					"maxResults must be 1 or greater.");
		}

		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Person.class).setFirstResult(firstResult)
				.setMaxResults(maxResults);

		if (StringUtils.isEmpty(sortExpression)) {
			query.addOrder(Order.asc("lastName"));
		} else if (StringUtils.endsWithIgnoreCase(sortExpression, "asc")) {
			query.addOrder(Order.asc(StringUtils.substringBefore(
					sortExpression, " ")));
		} else if (StringUtils.endsWithIgnoreCase(sortExpression, "desc")) {
			query.addOrder(Order.desc(StringUtils.substringBefore(
					sortExpression, " ")));
		} else {
			throw new IllegalArgumentException(
					"Invalid sort expression. Must be in the form of \"propertyName ASC\" or \"propertyName DESC\".");
		}

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public Person get(UUID id) {
		return (Person) sessionFactory.getCurrentSession()
				.get(Person.class, id);
	}

	public Person fromUsername(String username) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				Person.class);
		query.add(Restrictions.eq("username", username));
		return (Person) query.uniqueResult();
	}

	@Override
	public Person save(Person obj) {
		if (obj.getId() != null) {
			obj = (Person) sessionFactory.getCurrentSession().merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(Person obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}
}
