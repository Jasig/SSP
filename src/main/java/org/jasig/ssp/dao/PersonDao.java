package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
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

	public Person create(final Person obj) {
		final Person person = super.save(obj);
		sessionFactory.getCurrentSession().flush();
		return person;
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
	@SuppressWarnings(UNCHECKED)
	public PagingWrapper<Person> getAll(final SortingAndPaging sAndP) {

		if (!sAndP.isSorted()) {
			sAndP.appendSortField("lastName", SortDirection.ASC);
			sAndP.appendSortField("firstName", SortDirection.ASC);
		}

		Criteria criteria = createCriteria();
		final long totalRows = (Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		criteria = createCriteria(sAndP);

		return new PagingWrapper<Person>(totalRows, criteria.list());
	}

	public Person fromUsername(@NotNull final String username) {
		if (!StringUtils.isNotBlank(username)) {
			throw new IllegalArgumentException("username can not be empty.");
		}

		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Person.class);
		query.add(Restrictions.eq("username", username)).setFlushMode(
				FlushMode.COMMIT);
		return (Person) query.uniqueResult();
	}

	public Person fromUserId(@NotNull final String userId) {
		if (!StringUtils.isNotBlank(userId)) {
			throw new IllegalArgumentException("userId can not be empty.");
		}

		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Person.class);
		query.add(Restrictions.eq("userId", userId));
		return (Person) query.uniqueResult();
	}

	@SuppressWarnings(UNCHECKED)
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
		return (Person) createCriteria().add(
				Restrictions.eq("schoolId", studentId)).uniqueResult();
	}

	/**
	 * Retrieves a List of People, likely used by the Address Labels Report
	 * 
	 * @param addressLabelSearchTO
	 *            Search criteria
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of People, filtered appropriately
	 * 
	 * @throws ObjectNotFoundException
	 *             If any referenced data is not found.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<Person> getPeopleByCriteria( // NOPMD
			final AddressLabelSearchTO addressLabelSearchTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		final Criteria criteria = createCriteria(sAndP);

		if (addressLabelSearchTO.getProgramStatus() != null) {

			criteria.createAlias("programStatuses",
					"personProgramStatuses")
					.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									addressLabelSearchTO
											.getProgramStatus()));

		}

		if (addressLabelSearchTO.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("specialServiceGroups",
					"personSpecialServiceGroups")
					.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									addressLabelSearchTO
											.getSpecialServiceGroupIds()));
		}

		if (addressLabelSearchTO.getReferralSourcesIds() != null) {
			criteria.createAlias("referralSources", "personReferralSources")
					.add(Restrictions.in(
							"personReferralSources.referralSource.id",
							addressLabelSearchTO.getReferralSourcesIds()));
		}

		if (addressLabelSearchTO.getAnticipatedStartTerm() != null) {
			criteria.add(Restrictions.eq("anticipatedStartTerm",
					addressLabelSearchTO.getAnticipatedStartTerm())
					.ignoreCase());
		}

		if (addressLabelSearchTO.getAnticipatedStartYear() != null) {
			criteria.add(Restrictions.eq("anticipatedStartYear",
					addressLabelSearchTO.getAnticipatedStartYear()));
		}

		if (addressLabelSearchTO.getStudentTypeIds() != null) {
			criteria.add(Restrictions.in("studentType.id",
					addressLabelSearchTO.getStudentTypeIds()));
		}

		if (addressLabelSearchTO.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("createdDate",
					addressLabelSearchTO.getCreateDateFrom()));
		}

		if (addressLabelSearchTO.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("createdDate",
					addressLabelSearchTO.getCreateDateTo()));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("studentType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);


		return criteria.list();
	}

	/**
	 * Retrieves a List of People, likely used by the Address Labels Report
	 * 
	 * @param specialServiceGroups
	 *            Search criteria
	 * @param sAndP
	 *            Sorting and paging parameters.
	 * @return List of People, filtered appropriately
	 * @throws ObjectNotFoundException
	 *             If any referenced data is not found.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<Person> getPeopleBySpecialServices(
			final List<UUID> specialServiceGroups, final SortingAndPaging sAndP)
			throws ObjectNotFoundException {

		final Criteria criteria = createCriteria(sAndP);

		if (specialServiceGroups != null) {
			criteria.createAlias("specialServiceGroups",
					"personSpecialServiceGroups")
					.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									specialServiceGroups));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("studentType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

}
