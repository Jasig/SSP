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
import edu.sinclair.ssp.model.reference.FundingSource;

@Repository
public class FundingSourceDao implements ReferenceDao<FundingSource>{

	//private static final Logger logger = LoggerFactory.getLogger(FundingSourceDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<FundingSource> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(FundingSource.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));;
		}
		
		return query.list();
	}

	@Override
	public FundingSource get(UUID id) {
		return (FundingSource) this.sessionFactory.getCurrentSession().get(FundingSource.class, id); 
	}

	@Override
	public FundingSource save(FundingSource obj) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		return obj;
	}

	@Override
	public void delete(FundingSource obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
