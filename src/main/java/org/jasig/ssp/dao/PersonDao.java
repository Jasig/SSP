package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

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
	public PagingWrapper<Person> getAll(final ObjectStatus status) {
		return getAll(new SortingAndPaging(status));
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<Person> getAll(final SortingAndPaging sAndP) {

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("lastName", SortDirection.ASC);
			sAndP.appendSortField("firstName", SortDirection.ASC);
		}

		Criteria criteria = createCriteria();
		final long totalRows = (Long) criteria.setProjection(
				Projections.rowCount())
				.uniqueResult();

		criteria = createCriteria(sAndP);

		return new PagingWrapper<Person>(totalRows, criteria.list());
	}

	public Person fromUsername(final String username) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(
						Person.class);
		query.add(Restrictions.eq("username", username)).setFlushMode(
				FlushMode.COMMIT);
		return (Person) query.uniqueResult();
	}

	public Person fromUserId(final String userId) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(
						Person.class);
		query.add(Restrictions.eq("userId", userId));
		return (Person) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPeopleInList(final List<UUID> personIds,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.in("id", personIds));
		return criteria.list();
	}

	/**
	 * Retrieves the specified Person by their Student ID (school_id).
	 * 
	 * @param studentId
	 *            Required school identifier for the Person to retrieve. Can not
	 *            be null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	public Person getByStudentId(final String studentId)
			throws ObjectNotFoundException {
		return (Person) createCriteria()
				.add(Restrictions.eq("schoolId", studentId))
				.uniqueResult();
	}
}
