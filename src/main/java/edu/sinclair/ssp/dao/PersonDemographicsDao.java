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
import edu.sinclair.ssp.model.PersonDemographics;

@Repository
public class PersonDemographicsDao implements
		AuditableCrudDao<PersonDemographics> {

	//private static final Logger logger = LoggerFactory.getLogger(PersonDemographicsDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonDemographics> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonDemographics.class);

		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));
		}
		
		return query.list();
	}

	@Override
	public PersonDemographics get(UUID id) {
		return (PersonDemographics) 
				this.sessionFactory.getCurrentSession().get(
						PersonDemographics.class, id);
	}

	public PersonDemographics forPerson(Person person){
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(PersonDemographics.class)
				.add(Restrictions.eq("person", person));
		return (PersonDemographics) query.uniqueResult();
	}

	@Override
	public PersonDemographics save(PersonDemographics obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(PersonDemographics obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
