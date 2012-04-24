package org.studentsuccessplan.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.util.sort.SortDirection;
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
		return getAll(new SortingAndPaging(status));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAll(final SortingAndPaging sAndP) {

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("lastName", SortDirection.ASC);
			sAndP.appendSortField("firstName", SortDirection.ASC);
		}

		Criteria criteria = createCriteria(sAndP);

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
