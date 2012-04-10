package org.studentsuccessplan.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonDemographics;

/**
 * CRUD methods for the PersonDemographics model.
 */
@Repository
public class PersonDemographicsDao extends
		AbstractAuditableCrudDao<PersonDemographics> implements
		AuditableCrudDao<PersonDemographics> {

	/**
	 * Constructor
	 */
	public PersonDemographicsDao() {
		super(PersonDemographics.class);
	}

	/**
	 * Return the demographics for the specified Person.
	 * 
	 * @param person
	 *            Lookup the demographics for this Person.
	 * 
	 * @return The demographics for the specified Person.
	 */
	public PersonDemographics forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonDemographics.class)
				.add(Restrictions.eq("person", person));
		return (PersonDemographics) query.uniqueResult();
	}
}