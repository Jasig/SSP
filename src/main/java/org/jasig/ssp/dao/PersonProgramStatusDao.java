package org.jasig.ssp.dao;

import javax.validation.ValidationException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * PersonProgramStatus DAO
 * 
 * @author jon.adams
 * 
 */
@Repository
public class PersonProgramStatusDao
		extends AbstractPersonAssocAuditableCrudDao<PersonProgramStatus>
		implements PersonAssocAuditableCrudDao<PersonProgramStatus> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusDao.class);

	/**
	 * Constructor
	 */
	public PersonProgramStatusDao() {
		super(PersonProgramStatus.class);
	}

	/**
	 * Gets the active PersonProgramStatus
	 * 
	 * @param person
	 *            the Person
	 * @return the active PersonProgramStatus or null if empty
	 */
	public PersonProgramStatus getActive(final Person person) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person", person));
		criteria.add(Restrictions.isNull("expirationDate"));

		try {
			return (PersonProgramStatus) criteria.uniqueResult();
		} catch (final HibernateException exc) {
			LOGGER.error(
					"Multiple PersonProgramStatus instances for a single person were found, which should not be allowed.",
					exc);
			throw new ValidationException(
					"Multiple PersonProgramStatus instances for a single person were found, which is not allowed.",
					exc);
		}
	}
}