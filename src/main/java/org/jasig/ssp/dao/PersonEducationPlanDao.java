package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonEducationPlan model.
 */
@Repository
public class PersonEducationPlanDao extends
		AbstractAuditableCrudDao<PersonEducationPlan> implements
		AuditableCrudDao<PersonEducationPlan> {

	/**
	 * Constructor
	 */
	public PersonEducationPlanDao() {
		super(PersonEducationPlan.class);
	}

	/**
	 * Return the education plan for the specified Person.
	 * 
	 * @param person
	 *            Lookup the education plan for this Person.
	 * 
	 * @return The education plan for the specified Person.
	 */
	public PersonEducationPlan forPerson(final Person person) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationPlan.class)
				.add(Restrictions.eq("person", person));
		return (PersonEducationPlan) query.uniqueResult();
	}
}