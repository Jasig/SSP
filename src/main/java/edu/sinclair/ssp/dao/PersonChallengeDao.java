package edu.sinclair.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;

/**
 * CRUD methods for the PersonChallenge model.
 */
@Repository
public class PersonChallengeDao extends
		AbstractAuditableCrudDao<PersonChallenge> implements
		AuditableCrudDao<PersonChallenge> {

	/**
	 * Constructor
	 */
	public PersonChallengeDao() {
		super(PersonChallenge.class);
	}

	/**
	 * Return all PersonChallenges for the specified Person.
	 * 
	 * @param person
	 *            Lookup all PersonChallenges for this Person.
	 * 
	 * @return All PersonChallenges for the specified Person.
	 */
	@SuppressWarnings("unchecked")
	public List<PersonChallenge> forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonChallenge.class)
				.add(Restrictions.eq("person", person));
		return query.list();
	}
}