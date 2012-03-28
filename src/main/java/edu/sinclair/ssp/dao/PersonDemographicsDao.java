package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;

@Repository
public class PersonDemographicsDao implements
		AuditableCrudDao<PersonDemographics> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDemographicsDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonDemographics> getAll(ObjectStatus status) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(
				PersonDemographics.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @param status
	 *            Object status filter. Set to {@link ObjectStatus#ALL} to
	 *            return all results.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PersonDemographics> getAll(ObjectStatus status,
			int firstResult, int maxResults, String sortExpression) {
		if (firstResult < 0) {
			throw new IllegalArgumentException(
					"firstResult must be 0 or greater.");
		}

		if (maxResults < 1) {
			throw new IllegalArgumentException(
					"maxResults must be 1 or greater.");
		}

		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonDemographics.class)
				.setFirstResult(firstResult).setMaxResults(maxResults);

		if (StringUtils.isNotEmpty(sortExpression)) {
			if (StringUtils.endsWithIgnoreCase(sortExpression, "asc")) {
				query.addOrder(Order.asc(StringUtils.substringBefore(
						sortExpression, " ")));
			} else if (StringUtils.endsWithIgnoreCase(sortExpression, "desc")) {
				query.addOrder(Order.desc(StringUtils.substringBefore(
						sortExpression, " ")));
			} else {
				throw new IllegalArgumentException(
						"Invalid sort expression. Must be in the form of \"propertyName ASC\" or \"propertyName DESC\".");
			}
		}

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonDemographics get(UUID id) {
		return (PersonDemographics) sessionFactory.getCurrentSession().get(
				PersonDemographics.class, id);
	}

	public PersonDemographics forPerson(Person person) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonDemographics.class)
				.add(Restrictions.eq("person", person));
		return (PersonDemographics) query.uniqueResult();
	}

	@Override
	public PersonDemographics save(PersonDemographics obj) {
		if (obj.getId() != null) {
			obj = (PersonDemographics) sessionFactory.getCurrentSession()
					.merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonDemographics obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

}
