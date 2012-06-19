package org.jasig.ssp.dao;

import java.util.Date;
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
	
	
	
	
	
	
	//TODO: Implement with a TO
	/**
	 * Retrieves a List of People, likely used by the Address Labels Report
	 * @param intakeDatefrom
	 * @param intakeDateTo
	 * @param homeDepartment
	 * @param coachId
	 * @param programStatus
	 * @param specialServiceGroupId
	 * @param referralSourcesId
	 * @param anticipatedStartTerm
	 * @param anticipatedStartYear
	 * @param studentTypeId
	 * @param registrationTerm
	 * @param registrationYear
	 * @param sAndP
	 * @return
	 * @throws ObjectNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List<Person> getPeopleByCriteria(
			Date intakeDatefrom, 
    		Date intakeDateTo,
			String homeDepartment,
			String coachId,
			String programStatus,
			String specialServiceGroupId,
			String referralSourcesId,
			String anticipatedStartTerm,
			Integer anticipatedStartYear,
			String studentTypeId,
			Date registrationTerm,
			Date registrationYear,						
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
				
		final Criteria criteria = createCriteria(sAndP);
		
		//TODO: Implement Search Critera
		if(intakeDatefrom != null){
		//	criteria.add(Restrictions.gt("studentIntakeRequestDate",intakeDatefrom));
		}
		if(intakeDatefrom != null){
		//	criteria.add(Restrictions.lt("studentIntakeRequestDate",intakeDateTo));
		}		
		if(homeDepartment != null){
		//	criteria.add(Restrictions.eq("homeDepartment",homeDepartment));
		}
		if(coachId != null){
			//criteria.add(Restrictions.eq("coachId",coachId));	
		}
		if(programStatus != null){
			//criteria.add(Restrictions.eq("programStatus",programStatus));	
		}
		if(specialServiceGroupId != null){
			//criteria.add(Restrictions.eq("specialServiceGroupId",specialServiceGroupId));
		}
		if(referralSourcesId != null){
			//criteria.add(Restrictions.eq("referralSourcesId",referralSourcesId));
		}
		if(anticipatedStartTerm != null){
			//criteria.add(Restrictions.eq("anticipatedStartTerm",anticipatedStartTerm));
		}
		if(anticipatedStartYear != null){
			//criteria.add(Restrictions.eq("anticipatedStartYear",anticipatedStartYear));
		}
		if(studentTypeId != null){
			//criteria.add(Restrictions.eq("studentTypeId",studentTypeId));
		}
		if(registrationTerm != null){
			//criteria.add(Restrictions.eq("registrationTerm",registrationTerm));
		}
		if(registrationYear != null){
			//criteria.add(Restrictions.eq("registrationYear",registrationYear));
		}
		

		return criteria.list();
		
	}
	
	
	
	
}
