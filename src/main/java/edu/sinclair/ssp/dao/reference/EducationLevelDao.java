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
import edu.sinclair.ssp.model.reference.EducationLevel;

@Repository
public class EducationLevelDao implements AuditableCrudDao<EducationLevel>{

	//private static final Logger logger = LoggerFactory.getLogger(EducationLevelDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<EducationLevel> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(EducationLevel.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));;
		}
		
		return query.list();
	}

	@Override
	public EducationLevel get(UUID id) {
		return (EducationLevel) this.sessionFactory.getCurrentSession().get(EducationLevel.class, id); 
	}

	@Override
	public EducationLevel save(EducationLevel obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(EducationLevel obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
