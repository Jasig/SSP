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
import edu.sinclair.ssp.model.reference.StudentStatus;

@Repository
public class StudentStatusDao implements AuditableCrudDao<StudentStatus>{

	//private static final Logger logger = LoggerFactory.getLogger(StudentStatusDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentStatus> getAll(ObjectStatus status) {
		Criteria query = this.sessionFactory.getCurrentSession()
				.createCriteria(StudentStatus.class);
		query.addOrder(Order.asc("name"));
		
		if(status!=ObjectStatus.ALL){
			query.add(Restrictions.eq("objectStatus", status));
		}
		
		return query.list();
	}

	@Override
	public StudentStatus get(UUID id) {
		return (StudentStatus) this.sessionFactory.getCurrentSession().get(StudentStatus.class, id); 
	}

	@Override
	public StudentStatus save(StudentStatus obj) {
		if(obj.getId()!=null){
			this.sessionFactory.getCurrentSession().merge(obj);
		}else{
			this.sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}
		
		return obj;
	}

	@Override
	public void delete(StudentStatus obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
