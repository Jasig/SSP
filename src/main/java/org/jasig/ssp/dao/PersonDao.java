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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
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
			final PersonSearchFormTO personSearchFormTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		
		Criteria criteria = setBasicSearchCriteria(createCriteria(sAndP),  personSearchFormTO);


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
	
	protected Criteria setBasicSearchCriteria(Criteria criteria, final PersonSearchFormTO personSearchTO){
		criteria.createAlias("coach", "c");
		if (personSearchTO.getCoach() != null
				&& personSearchTO.getCoach().getId() != null) {
			// restrict to coach
			
			criteria.add(Restrictions.eq("c.id",
					personSearchTO.getCoach().getId()));
		}
		
		if (personSearchTO.getProgramStatus() != null) {
			criteria.createAlias("programStatuses",
					"personProgramStatuses");
			criteria.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									personSearchTO
											.getProgramStatus()));
		}
		
		if (personSearchTO.getSpecialServiceGroupIds() != null) {
			criteria.createAlias("specialServiceGroups",
					"personSpecialServiceGroups");
			criteria.add(Restrictions
							.in("personSpecialServiceGroups.specialServiceGroup.id",
									personSearchTO
											.getSpecialServiceGroupIds()));
		}

		if (personSearchTO.getReferralSourcesIds() != null) {
			criteria.createAlias("referralSources", "personReferralSources")
					.add(Restrictions.in(
							"personReferralSources.referralSource.id",
							personSearchTO.getReferralSourcesIds()));
		}

		if (personSearchTO.getAnticipatedStartTerm() != null && personSearchTO.getAnticipatedStartTerm().length() > 0) {
			criteria.add(Restrictions.eq("anticipatedStartTerm",
					personSearchTO.getAnticipatedStartTerm())
					.ignoreCase());
		}

		if (personSearchTO.getAnticipatedStartYear() != null) {
			criteria.add(Restrictions.eq("anticipatedStartYear",
					personSearchTO.getAnticipatedStartYear()));
		}
		
		if (personSearchTO.getActualStartTerm() != null  && personSearchTO.getActualStartTerm().length() > 0) {
			criteria.add(Restrictions.eq("actualStartTerm",
					personSearchTO.getActualStartTerm())
					.ignoreCase());
		}

		if (personSearchTO.getActualStartYear() != null) {
			criteria.add(Restrictions.eq("actualStartYear",
					personSearchTO.getActualStartYear()));
		}

		if (personSearchTO.getStudentTypeIds() != null) {
			criteria.add(Restrictions.in("studentType.id",
					personSearchTO.getStudentTypeIds()));
		}

		if (personSearchTO.getCreateDateFrom() != null) {
			criteria.add(Restrictions.ge("createdDate",
					personSearchTO.getCreateDateFrom()));
		}

		if (personSearchTO.getCreateDateTo() != null) {
			criteria.add(Restrictions.le("createdDate",
					personSearchTO.getCreateDateTo()));
		}
		
		if (personSearchTO.getDisabilityIsNotNull() != null && personSearchTO.getDisabilityIsNotNull() == true) {
			criteria.add(Restrictions.isNotNull("disability"));
		}
		
		if (personSearchTO.getDisabilityStatusId() != null) {
			criteria.createAlias("disability", "personDisability");
			criteria.add(Restrictions.eq(
					"personDisability.disabilityStatus.id",
					personSearchTO.getDisabilityStatusId()));
		}
		
		if (personSearchTO.getDisabilityTypeId() != null) {
			criteria.createAlias("disabilityTypes", "personDisabilityTypes")
			.add(Restrictions.eq(
					"personDisabilityTypes.disabilityType.id",
					personSearchTO.getDisabilityTypeId()));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("studentType"));
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<BaseStudentReportTO> getStudentReportTOs(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		Criteria criteria = setBasicSearchCriteria(createCriteria(sAndP),  form);
		
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		// clear the row count projection
		criteria.setProjection(null);

				
		// don't bring back any non-students, there will likely be a better way
		// to do this later
		
		final ProjectionList projections = Projections.projectionList();
		
		criteria.setProjection(projections);
		if(form.getProgramStatus() == null){
			criteria.createAlias("programStatuses", "personProgramStatuses", JoinType.LEFT_OUTER_JOIN);
		}
		
		criteria.add(Restrictions
				.isNull("personProgramStatuses.expirationDate"));
		
		if(form.getSpecialServiceGroupIds() == null){
			criteria.createAlias("specialServiceGroups", "personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);
		}
	
		addBasicStudentProperties(projections, criteria);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				BaseStudentReportTO.class));

		return new PagingWrapper<BaseStudentReportTO>(totalRows, criteria.list());

	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<DisabilityServicesReportTO> getDisabilityReport(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		Criteria criteria = setBasicSearchCriteria(createCriteria(sAndP),  form);
		
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		// clear the row count projection
		criteria.setProjection(null);

				
		// don't bring back any non-students, there will likely be a better way
		// to do this later
		
		final ProjectionList projections = Projections.projectionList();
		
		criteria.setProjection(projections);
		
		if(form.getProgramStatus() == null){
			criteria.createAlias("programStatuses", "personProgramStatuses", JoinType.LEFT_OUTER_JOIN);
		}
		
		if(form.getSpecialServiceGroupIds() == null){
			criteria.createAlias("specialServiceGroups", "personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);
		}
		
		addBasicStudentProperties(projections, criteria);
		Criteria demographics = criteria.createAlias("demographics", "demographics");
		demographics.createAlias("demographics.ethnicity", "ethnicity");
		demographics.createAlias("demographics.veteranStatus", "veteranStatus");
		
		criteria.createAlias("disabilityAgencies", "disabilityAgencies");
		
		criteria.createAlias("disabilityAgencies.disabilityAgency", "disabilityAgency");
		if (form.getDisabilityTypeId() == null)
			criteria.createAlias("disabilityTypes", "personDisabilityTypes");
		
		criteria.createAlias("personDisabilityTypes.disabilityType", "disabilityType");
		if (form.getDisabilityStatusId() == null) {
			criteria.createAlias("disability", "personDisability");
		}
		criteria.createAlias("personDisability.disabilityStatus", "disabilityStatus");
		
		criteria.createAlias("educationGoal", "educationGoal");
		
		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {

			projections.add(Projections.groupProperty("ethnicity.name").as("ethnicity"));
			projections.add(Projections.groupProperty("veteranStatus.name").as("veteranStatus"));
			projections.add(Projections.groupProperty("disabilityAgency.name").as("disabilityAgencyName"));
			projections.add(Projections.property("disabilityType.name").as("disabilityType"));
			projections.add(Projections.groupProperty("disabilityAgency.createdDate").as("disabilityAgencyCreatedDate"));
			projections.add(Projections.property("educationGoal.plannedMajor").as("major"));
			projections.add(Projections.property("disabilityStatus.name").as("odsStatus"));
		} else {
			projections.add(Projections.property("ethnicity.name").as("ethnicity"));
			projections.add(Projections.property("veteranStatus.name").as("veteranStatus"));
			projections.add(Projections.property("disabilityType.name").as("disabilityType"));
			projections.add(Projections.property("disabilityAgency.name").as("disabilityAgencyName"));
			projections.add(Projections.property("disabilityAgency.createdDate").as("disabilityAgencyCreatedDate"));
			projections.add(Projections.property("educationGoal.plannedMajor").as("major"));
			projections.add(Projections.property("disabilityStatus.name").as("odsStatus"));
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				DisabilityServicesReportTO.class));

		// Add Paging
		if (sAndP != null) {
			sAndP.addAll(criteria);
		}

		return new PagingWrapper<DisabilityServicesReportTO>(totalRows, criteria.list());

	}
	
	private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		projections.add(Projections.property("firstName").as("firstName"));
		projections.add(Projections.property("middleName").as("middleName"));
		projections.add(Projections.property("lastName").as("lastName"));
		projections.add(Projections.property("schoolId").as("schoolId"));
		projections.add(Projections.property("primaryEmailAddress").as("primaryEmailAddress"));
		projections.add(Projections.property("secondaryEmailAddress").as("secondaryEmailAddress"));
		projections.add(Projections.property("cellPhone").as("cellPhone"));
		projections.add(Projections.property("homePhone").as("homePhone"));
		projections.add(Projections.property("addressLine1").as("addressLine1"));
		projections.add(Projections.property("addressLine2").as("addressLine2"));
		projections.add(Projections.property("city").as("city"));
		projections.add(Projections.property("state").as("state"));
		projections.add(Projections.property("zipCode").as("zipCode"));
		projections.add(Projections.property("id").as("id"));
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup", JoinType.LEFT_OUTER_JOIN);
		projections.add(Projections.property("specialServiceGroup.name").as("specialServiceGroup"));
		
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.property("programStatus.name").as("programCurrentStatusName"));

		// Join to Student Type
		criteria.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.property("studentType.name").as("studentType"));

		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			//projections.add(Projections.groupProperty("c.id").as("coachId"));
			projections.add(Projections.groupProperty("c.lastName").as("coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			//projections.add(Projections.property("c.id").as("coachId"));
			projections.add(Projections.property("c.lastName").as("coachLastName"))
					.add(Projections.property("c.firstName").as("coachFirstName"))
					.add(Projections.property("c.middleName").as("coachMiddleName"))
					.add(Projections.property("c.schoolId").as("coachSchoolId"))
					.add(Projections.property("c.username").as("coachUsername"));
		}
		return projections;
	}
}
