package org.jasig.ssp.dao.external;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalCourseRequisiteDao extends
		AbstractExternalDataDao<ExternalCourseRequisite> {

	public ExternalCourseRequisiteDao() {
		super(ExternalCourseRequisite.class);
	}

	@SuppressWarnings("unchecked")
	public List<ExternalCourseRequisite> getRequisitesForCourse(
			String requiringCourseCode) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("requiringCourseCode", requiringCourseCode), Restrictions.eq("requiredCourseCode", requiringCourseCode)));
		return (List<ExternalCourseRequisite>)criteria.list();
	}


	@SuppressWarnings("unchecked")
	public List<ExternalCourseRequisite> getRequisitesForCourses(
			List<String> requiringCourseCode) {
		Criteria criteria = this.createCriteria();
		
		criteria.add(Restrictions.in("requiringCourseCode", requiringCourseCode));
		return (List<ExternalCourseRequisite>)criteria.list();
	}
}
