package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduledApplicationTaskStatusDao extends
		AbstractAuditableCrudDao<ScheduledApplicationTaskStatus> {

	protected ScheduledApplicationTaskStatusDao() {
		super(ScheduledApplicationTaskStatus.class);
	}
	
	public ScheduledApplicationTaskStatus getByTaskName(String taskName) throws HibernateException{
		Criteria criteria = createCriteria().add(Restrictions.eq("taskName", taskName));
		return (ScheduledApplicationTaskStatus) criteria.uniqueResult();
	}
	
}
