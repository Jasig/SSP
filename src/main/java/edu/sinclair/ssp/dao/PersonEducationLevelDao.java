package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationLevel;

@Repository
public class PersonEducationLevelDao implements
AuditableCrudDao<PersonEducationLevel> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonEducationLevelDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonEducationLevel> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationLevel.class);

		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@Override
	public PersonEducationLevel get(UUID id) {
		return (PersonEducationLevel) this.sessionFactory.getCurrentSession()
				.get(PersonEducationLevel.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<PersonEducationLevel> forPerson(Person person) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationLevel.class)
				.add(Restrictions.eq("person", person));
		return query.list();
	}

	@Override
	public PersonEducationLevel save(PersonEducationLevel obj) {
		if (obj.getId() != null) {
			this.sessionFactory.getCurrentSession().merge(obj);
		} else {
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(PersonEducationLevel obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
