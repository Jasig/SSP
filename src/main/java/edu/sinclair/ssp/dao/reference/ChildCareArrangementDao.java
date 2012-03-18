package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;

@Repository
public class ChildCareArrangementDao implements AuditableCrudDao<ChildCareArrangement>{

	//private static final Logger logger = LoggerFactory.getLogger(ChildCareArrangementDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<ChildCareArrangement> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(ChildCareArrangement.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));
		}
		
		return query.list();
	}

	@Override
	public ChildCareArrangement get(UUID id) {
		return (ChildCareArrangement) this.sessionFactory.getCurrentSession().get(ChildCareArrangement.class, id); 
	}

	@Override
	public ChildCareArrangement save(ChildCareArrangement obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(ChildCareArrangement obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
