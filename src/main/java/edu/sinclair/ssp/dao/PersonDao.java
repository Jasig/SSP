package edu.sinclair.ssp.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

/**
 * CRUD methods for the Person model.
 * <p>
 * Default sort order is <code>lastName</code> then <code>firstName</code>.
 */
@Repository
public class PersonDao extends AbstractAuditableCrudDao<Person> implements
		AuditableCrudDao<Person> {

	/**
	 * Constructor
	 */
	public PersonDao() {
		super(Person.class);
	}

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters. Sorted by <code>lastName</code> then <code>firstName</code>.
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
				.createCriteria(super.persistentClass)
				.addOrder(Order.asc("lastName"))
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

		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				super.persistentClass);

		if (firstResult >= 0) {
			query.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}

		if (StringUtils.isEmpty(sortExpression)) {
			query.addOrder(Order.asc("lastName")).addOrder(
					Order.asc("firstName"));
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

	public Person fromUsername(String username) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				Person.class);
		query.add(Restrictions.eq("username", username));
		return (Person) query.uniqueResult();
	}

	public Person fromUserId(String userId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				Person.class);
		query.add(Restrictions.eq("userId", userId));
		return (Person) query.uniqueResult();
	}
}
