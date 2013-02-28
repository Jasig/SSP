package org.jasig.ssp.dao.external;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentTestDao extends
		AbstractExternalDataDao<ExternalStudentTest> {
	
	public ExternalStudentTestDao() {
		super(ExternalStudentTest.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTest> getStudentTestResults(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.addOrder(Order.desc("testDate"));
		return (List<ExternalStudentTest>)criteria.list();
	}

}
