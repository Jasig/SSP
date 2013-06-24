package org.jasig.ssp.dao.external;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalPersonPlanStatusDao extends
		AbstractExternalDataDao<ExternalPersonPlanStatus> {

	public ExternalPersonPlanStatusDao() {
		super(ExternalPersonPlanStatus.class);
	}
	
	
	public ExternalPersonPlanStatus getBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		return (ExternalPersonPlanStatus) criteria.uniqueResult();
	}
}
