package edu.sinclair.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationLevel;

/**
 * CRUD methods for the PersonEducationLevel model.
 */
@Repository
public class PersonEducationLevelDao extends
		AbstractAuditableCrudDao<PersonEducationLevel> implements
		AuditableCrudDao<PersonEducationLevel> {

	/**
	 * Constructor
	 */
	public PersonEducationLevelDao() {
		super(PersonEducationLevel.class);
	}

	/**
	 * Return all PersonEducationLevels for the specified Person.
	 * 
	 * @param person
	 *            Lookup all PersonEducationLevels for this Person.
	 * 
	 * @return All PersonEducationLevels for the specified Person.
	 */
	@SuppressWarnings("unchecked")
	public List<PersonEducationLevel> forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationLevel.class)
				.add(Restrictions.eq("person", person));
		return query.list();
	}

}