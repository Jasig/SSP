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

		if (specialServiceGroups != null && !specialServiceGroups.isEmpty()) {
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

		return getCoachPersonsLiteByUsernames(coachUsernames, sAndP, null);

	}
	
	@SuppressWarnings(UNCHECKED)
	public PagingWrapper<CoachPersonLiteTO> getCoachPersonsLiteByUsernames(
			final Collection<String> coachUsernames, final SortingAndPaging sAndP, final String homeDepartment) {
		
		Criteria criteria = createCriteria()
				.add(Restrictions.in("username", coachUsernames));
		
		if(homeDepartment != null && homeDepartment.length() > 0){
			criteria.createAlias("staffDetails", "personStaffDetails")
				.add(Restrictions.eq("personStaffDetails.departmentName", homeDepartment));
		}

		final long totalRows = (Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		// ignore department name and office location for now... would
		// require join we know we don't actually need for existing call sites
		criteria = createCriteria(sAndP);
		if(homeDepartment != null && homeDepartment.length() > 0){
			criteria.createAlias("staffDetails", "personStaffDetails")
				.add(Restrictions.eq("personStaffDetails.departmentName", homeDepartment));
		}else{
			criteria.createAlias("staffDetails", "personStaffDetails", JoinType.LEFT_OUTER_JOIN);
		}
		
		criteria.add(Restrictions.in("username", coachUsernames))
				.setProjection(Projections.projectionList()
						.add(Projections.property("id").as("person_id"))
						.add(Projections.property("firstName").as("person_firstName"))
						.add(Projections.property("lastName").as("person_lastName"))
						.add(Projections.property("primaryEmailAddress").as("person_primaryEmailAddress"))
						.add(Projections.property("workPhone").as("person_workPhone"))
						.add(Projections.property("personStaffDetails.departmentName").as("person_departmentName")))
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
		return  getAllAssignedCoachesLite(sAndP, null);
	}

	public PagingWrapper<CoachPersonLiteTO> getAllAssignedCoachesLite(SortingAndPaging sAndP, String homeDepartment) {

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
		
		if(homeDepartment != null && homeDepartment.length() >= 0){
			criteria.createAlias("staffDetails", "personStaffDetails");
			criteria.add(Restrictions.eq("personStaffDetails.departmentName", homeDepartment));
		}else{
			criteria.createAlias("staffDetails", "personStaffDetails", JoinType.LEFT_OUTER_JOIN);
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
					.add(Projections.property("workPhone").as("person_workPhone"))
					.add(Projections.property("personStaffDetails.departmentName").as("person_departmentName")))
				.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								CoachPersonLiteTO.class, "person_"));

		return new PagingWrapper<CoachPersonLiteTO>(totalRows, criteria.list());

	}
	
	private Boolean setCoachAlias(Criteria criteria, String alias, Boolean created){
		if(created.equals(true))
			return created;
		criteria.createAlias("coach", alias);
		return true;
	}
	
	protected Criteria setBasicSearchCriteria(Criteria criteria, final PersonSearchFormTO personSearchTO){
		Boolean coachCriteriaCreated = false;
		if (personSearchTO.getCoach() != null
				&& personSearchTO.getCoach().getId() != null) {
			coachCriteriaCreated = setCoachAlias( criteria,  "c", coachCriteriaCreated);
			criteria.add(Restrictions.eq("c.id",
					personSearchTO.getCoach().getId()));
		}else{
			
		}
		
		if (personSearchTO.getHomeDepartment() != null
				&& personSearchTO.getHomeDepartment().length() > 0) {
			coachCriteriaCreated = setCoachAlias( criteria,  "c", coachCriteriaCreated);
			
			criteria.createAlias("c.staffDetails", "coachStaffDetails");
			criteria.add(Restrictions.eq("coachStaffDetails.departmentName",
					personSearchTO.getHomeDepartment()));
		}
		
		if (personSearchTO.getProgramStatus() != null) {
			criteria.createAlias("programStatuses",
					"personProgramStatuses");
			criteria.add(Restrictions
							.eq("personProgramStatuses.programStatus.id",
									personSearchTO
											.getProgramStatus()));
			criteria.add(Restrictions.isNull("personProgramStatuses.expirationDate"));
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
			criteria.createAlias("disability", "personDisability");
			criteria.add(Restrictions.isNotNull("personDisability.id"));
		}
		
		if (personSearchTO.getDisabilityStatusId() != null) {
			if (personSearchTO.getDisabilityIsNotNull() == null || personSearchTO.getDisabilityIsNotNull() == false)
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
		
		if (personSearchTO.getServiceReasonsIds() != null && personSearchTO.getServiceReasonsIds().size() > 0) {
			criteria.createAlias("serviceReasons", "serviceReasons");
			criteria.createAlias("serviceReasons.serviceReason", "serviceReason");
			criteria.add(Restrictions.in("serviceReason.id",
					personSearchTO.getServiceReasonsIds()));
		}

		// don't bring back any non-students, there will likely be a better way
		// to do this later
		criteria.add(Restrictions.isNotNull("studentType"));
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<UUID> getStudentUUIDs(PersonSearchFormTO form){
		Criteria criteria = setBasicSearchCriteria(createCriteria(),  form);
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		return (List<UUID>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<BaseStudentReportTO> getStudentReportTOs(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		
		List<UUID> ids = getStudentUUIDs(form);
		if(ids.size() == 0)
			return null;
		
		final Criteria criteria = createCriteria(sAndP);

		criteria.add(Restrictions.in("id", ids));
			
		final ProjectionList projections = Projections.projectionList();
		
		criteria.setProjection(projections);
	
		addBasicStudentProperties(projections, criteria);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				BaseStudentReportTO.class));

		return new PagingWrapper<BaseStudentReportTO>(ids.size(), criteria.list());

	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<DisabilityServicesReportTO> getDisabilityReport(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		
		List<UUID> ids = getStudentUUIDs(form);
		if(ids.size() == 0)
			return null;
		
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.in("id", ids));
		// don't bring back any non-students, there will likely be a better way
		// to do this later
		
		final ProjectionList projections = Projections.projectionList();
		
		criteria.setProjection(projections);
				
		addBasicStudentProperties(projections, criteria);
		
		Criteria demographics = criteria.createAlias("demographics", "demographics", JoinType.LEFT_OUTER_JOIN);
		demographics.createAlias("demographics.ethnicity", "ethnicity", JoinType.LEFT_OUTER_JOIN);
		demographics.createAlias("demographics.veteranStatus", "veteranStatus", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("disabilityAgencies", "disabilityAgencies", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("disabilityAgencies.disabilityAgency", "disabilityAgency", JoinType.LEFT_OUTER_JOIN);
		if (form.getDisabilityTypeId() == null)
			criteria.createAlias("disabilityTypes", "personDisabilityTypes", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("personDisabilityTypes.disabilityType", "disabilityType", JoinType.LEFT_OUTER_JOIN);
		if (form.getDisabilityStatusId() == null) {
			criteria.createAlias("disability", "personDisability");
		}
		
		criteria.createAlias("personDisability.disabilityStatus", "disabilityStatus", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("educationGoal", "educationGoal", JoinType.LEFT_OUTER_JOIN);
		
		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {

			projections.add(Projections.groupProperty("ethnicity.name").as("ethnicity"));
			projections.add(Projections.groupProperty("veteranStatus.name").as("veteranStatus"));
			projections.add(Projections.groupProperty("disabilityAgency.name").as("disabilityAgencyName"));
			projections.add(Projections.groupProperty("disabilityType.name").as("disabilityType"));
			projections.add(Projections.groupProperty("disabilityAgency.createdDate").as("disabilityAgencyCreatedDate"));
			projections.add(Projections.groupProperty("educationGoal.plannedMajor").as("major"));
			projections.add(Projections.groupProperty("disabilityStatus.name").as("odsStatus"));
		} else {
			projections.add(Projections.groupProperty("ethnicity.name").as("ethnicity"));
			projections.add(Projections.groupProperty("veteranStatus.name").as("veteranStatus"));
			projections.add(Projections.groupProperty("disabilityType.name").as("disabilityType"));
			projections.add(Projections.groupProperty("disabilityAgency.name").as("disabilityAgencyName"));
			projections.add(Projections.groupProperty("disabilityAgency.createdDate").as("disabilityAgencyCreatedDate"));
			projections.add(Projections.groupProperty("educationGoal.plannedMajor").as("major"));
			projections.add(Projections.groupProperty("disabilityStatus.name").as("odsStatus"));
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				DisabilityServicesReportTO.class));

		return new PagingWrapper<DisabilityServicesReportTO>(ids.size(), criteria.list());

	}
	
	private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		projections.add(Projections.groupProperty("firstName").as("firstName"));
		projections.add(Projections.groupProperty("middleName").as("middleName"));
		projections.add(Projections.groupProperty("lastName").as("lastName"));
		projections.add(Projections.groupProperty("schoolId").as("schoolId"));
		projections.add(Projections.groupProperty("primaryEmailAddress").as("primaryEmailAddress"));
		projections.add(Projections.groupProperty("secondaryEmailAddress").as("secondaryEmailAddress"));
		projections.add(Projections.groupProperty("cellPhone").as("cellPhone"));
		projections.add(Projections.groupProperty("homePhone").as("homePhone"));
		projections.add(Projections.groupProperty("addressLine1").as("addressLine1"));
		projections.add(Projections.groupProperty("addressLine2").as("addressLine2"));
		projections.add(Projections.groupProperty("city").as("city"));
		projections.add(Projections.groupProperty("state").as("state"));
		projections.add(Projections.groupProperty("zipCode").as("zipCode"));
		projections.add(Projections.groupProperty("actualStartTerm").as("actualStartTerm"));
		projections.add(Projections.groupProperty("actualStartYear").as("actualStartYear"));
		projections.add(Projections.groupProperty("id").as("id"));
		
		criteria.createAlias("programStatuses", "personProgramStatuses", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("specialServiceGroups", "personSpecialServiceGroups", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("personSpecialServiceGroups.specialServiceGroup", "specialServiceGroup", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.groupProperty("specialServiceGroup.name").as("specialServiceGroup"));
		criteria.createAlias("personProgramStatuses.programStatus", "programStatus", JoinType.LEFT_OUTER_JOIN);
		
		projections.add(Projections.groupProperty("programStatus.name").as("programStatusName"));
		projections.add(Projections.groupProperty("personProgramStatuses.id").as("programStatusId"));
		projections.add(Projections.groupProperty("personProgramStatuses.expirationDate").as("programStatusExpirationDate"));
	
		// Join to Student Type
		criteria.createAlias("studentType", "studentType", JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as("studentType"));

		criteria.createAlias("coach", "c");
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
			projections.add(Projections.groupProperty("c.lastName").as("coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("coachUsername"));
		}
		return projections;
	}
}
