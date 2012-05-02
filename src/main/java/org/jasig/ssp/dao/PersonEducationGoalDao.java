package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationGoal;

/**
 * CRUD methods for the PersonEducationGoal model.
 */
@Repository
public class PersonEducationGoalDao extends
		AbstractAuditableCrudDao<PersonEducationGoal> implements
		AuditableCrudDao<PersonEducationGoal> {

	/**
	 * Constructor
	 */
	public PersonEducationGoalDao() {
		super(PersonEducationGoal.class);
	}

	/**
	 * Return the education goal for the specified Person.
	 * 
	 * @param person
	 *            Lookup the education goal for this Person.
	 * 
	 * @return The education goal for the specified Person.
	 */
	public PersonEducationGoal forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationGoal.class)
				.add(Restrictions.eq("person", person));
		return (PersonEducationGoal) query.uniqueResult();
	}
}