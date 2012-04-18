package org.studentsuccessplan.ssp.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
	public List<Person> getAll(ObjectStatus status) {
		return getAll(status, null, null, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {

		SortingAndPaging sAndP = new SortingAndPaging(status, firstResult,
				maxResults, sort,
				sortDirection, null);

		Criteria criteria = createCriteria();

		sAndP.addPagingToCriteria(criteria);
		sAndP.addStatusFilterToCriteria(criteria);

		if (StringUtils.isEmpty(sort)) {
			criteria.addOrder(Order.asc("lastName")).addOrder(
					Order.asc("firstName"));
		} else {
			sAndP.addSortingToCriteria(criteria);
		}

		return criteria.list();
	}

	public Person fromUsername(String username) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				Person.class);
		query.add(Restrictions.eq("username", username)).setFlushMode(
				FlushMode.COMMIT);
		return (Person) query.uniqueResult();
	}

	public Person fromUserId(String userId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				Person.class);
		query.add(Restrictions.eq("userId", userId));
		return (Person) query.uniqueResult();
	}
}
