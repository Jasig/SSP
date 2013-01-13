/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
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

	@SuppressWarnings(UNCHECKED)
	public List<Person> getPeopleInList(@NotNull final List<UUID> personIds,
			final SortingAndPaging sAndP) throws ValidationException {
		if ((personIds == null) || personIds.isEmpty()) {
			throw new ValidationException(
					"Missing or empty list of Person identifiers.");
		}

		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.in("id", personIds));
		return criteria.list();
	}

	/**
	 * Retrieves the specified Person by their school_id.
	 * 
	 * @param schoolId
	 *            Required school identifier for the Person to retrieve. Can not
	 *            be null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	public Person getBySchoolId(final String schoolId)
			throws ObjectNotFoundException {

		if (!StringUtils.isNotBlank(schoolId)) {
			throw new IllegalArgumentException("schoolId can not be empty.");
		}

		final Person person = (Person) createCriteria().add(
				Restrictions.eq("schoolId", schoolId)).uniqueResult();

		if (person == null) {
			throw new ObjectNotFoundException(
					"Person not found with schoolId: " + schoolId,
					Person.class.getName());
		}

		return person;
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
		
		Criteria criteria = setBasicSearchCriteria(createCriteria(sAndP),  addressLabelSearchTO);

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
	
	

	@SuppressWarnings(UNCHECKED)
	public PagingWrapper<CoachPersonLiteTO> getCoachPersonsLiteByUsernames(
			final Collection<String> coachUsernames, final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria()
				.add(Restrictions.in("username", coachUsernames));

		final long totalRows = (Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		// ignore department name and office location for now... would
		// require join we know we don't actually need for existing call sites
		criteria = createCriteria(sAndP)
				.add(Restrictions.in("username", coachUsernames))
				.setProjection(Projections.projectionList()
						.add(Projections.property("id").as("person_id"))
						.add(Projections.property("firstName").as("person_firstName"))
						.add(Projections.property("lastName").as("person_lastName"))
						.add(Projections.property("primaryEmailAddress").as("person_primaryEmailAddress"))
						.add(Projections.property("workPhone").as("person_workPhone")))
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								CoachPersonLiteTO.class, "person_"));

		return new PagingWrapper<CoachPersonLiteTO>(totalRows, criteria.list());

	}

	public PagingWrapper<Person> getAllAssignedCoaches(SortingAndPaging sAndP) {

		DetachedCriteria coach_ids =
				DetachedCriteria.forClass(Person.class, "coach_ids");
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.distinct(Projections.property("coach.id")));
		coach_ids.setProjection(projections);
		coach_ids.add(Restrictions.isNotNull("coach"));

		Criteria criteria = createCriteria()
				.add(Subqueries.propertiesIn(new String[] {"id"}, coach_ids));

		if ( sAndP != null && sAndP.isFilteredByStatus() ) {
			sAndP.addStatusFilterToCriteria(criteria);
		}

		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		criteria.setProjection(null);

		if ( sAndP == null || !(sAndP.isSorted())) {
			criteria.addOrder(Order.asc("lastName")).addOrder(Order.asc("firstName"));
		} else {
			if ( sAndP.isSorted() ) {
				sAndP.addSortingToCriteria(criteria);
			}
			sAndP.addPagingToCriteria(criteria);
		}

		return new PagingWrapper<Person>(totalRows, criteria.list());

	}

	public PagingWrapper<CoachPersonLiteTO> getAllAssignedCoachesLite(SortingAndPaging sAndP) {

		DetachedCriteria coach_ids =
				DetachedCriteria.forClass(Person.class, "coach_ids");
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.distinct(Projections.property("coach.id")));
		coach_ids.setProjection(projections);
		coach_ids.add(Restrictions.isNotNull("coach"));

		Criteria criteria = createCriteria()
				.add(Subqueries.propertiesIn(new String[]{"id"}, coach_ids));

		if ( sAndP != null && sAndP.isFilteredByStatus() ) {
			sAndP.addStatusFilterToCriteria(criteria);
		}

		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		criteria.setProjection(null);
		criteria.setProjection(Projections.projectionList()
					.add(Projections.property("id").as("person_id"))
					.add(Projections.property("firstName").as("person_firstName"))
					.add(Projections.property("lastName").as("person_lastName"))
					.add(Projections.property("primaryEmailAddress").as("person_primaryEmailAddress"))
					.add(Projections.property("workPhone").as("person_workPhone")))
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								CoachPersonLiteTO.class, "person_"));

		return new PagingWrapper<CoachPersonLiteTO>(totalRows, criteria.list());

	}
	
	private Criteria setBasicSearchCriteria(Criteria criteria, final AddressLabelSearchTO addressLabelSearchTO){
		if (addressLabelSearchTO.getCoach() != null
				&& addressLabelSearchTO.getCoach().getId() != null) {
			// restrict to coach
			criteria.add(Restrictions.eq("coach.id",
					addressLabelSearchTO.getCoach().getId()));
		}
		
		
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
		return criteria;
	}
}
