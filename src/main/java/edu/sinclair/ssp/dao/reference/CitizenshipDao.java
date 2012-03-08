package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Citizenship;

@Repository
public class CitizenshipDao implements ReferenceDao<Citizenship>{

	//private static final Logger logger = LoggerFactory.getLogger(CitizenshipDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Citizenship> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(Citizenship.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));;
		}
		
		return query.list();
	}

	@Override
	public Citizenship get(UUID id) {
		return (Citizenship) this.sessionFactory.getCurrentSession().get(Citizenship.class, id); 
	}

	@Override
	public Citizenship save(Citizenship obj) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		return obj;
	}

	@Override
	public void delete(Citizenship obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
