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
import edu.sinclair.ssp.model.reference.Challenge;

@Repository
public class ChallengeDao implements ReferenceDao<Challenge>{

	//private static final Logger logger = LoggerFactory.getLogger(ChallengeDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Challenge> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(Challenge.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));;
		}
		
		return query.list();
	}

	@Override
	public Challenge get(UUID id) {
		return (Challenge) this.sessionFactory.getCurrentSession().get(Challenge.class, id); 
	}

	@Override
	public Challenge save(Challenge obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(Challenge obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
