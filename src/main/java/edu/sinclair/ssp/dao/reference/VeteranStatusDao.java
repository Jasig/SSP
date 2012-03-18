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
import edu.sinclair.ssp.model.reference.VeteranStatus;

@Repository
public class VeteranStatusDao implements AuditableCrudDao<VeteranStatus>{

	//private static final Logger logger = LoggerFactory.getLogger(VeteranStatusDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<VeteranStatus> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(VeteranStatus.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));
		}
		
		return query.list();
	}

	@Override
	public VeteranStatus get(UUID id) {
		return (VeteranStatus) this.sessionFactory.getCurrentSession().get(VeteranStatus.class, id); 
	}

	@Override
	public VeteranStatus save(VeteranStatus obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(VeteranStatus obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
