package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

@Repository
public class PersonDao implements AuditableCrudDao<Person>{

	//private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(Person.class)
				.addOrder(Order.asc("lastName"))
				.addOrder(Order.asc("firstName"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));
		}
		
		return query.list();
	}

	@Override
	public Person get(UUID id) {
		return (Person) this.sessionFactory.getCurrentSession().get(Person.class, id); 
	}

	public Person fromUsername(String username){
		Criteria query = this.sessionFactory.getCurrentSession().createCriteria(Person.class);
		query.add(Restrictions.eq("username", username));
		return (Person) query.uniqueResult();
	}

	@Override
	public Person save(Person obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(Person obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}
}
