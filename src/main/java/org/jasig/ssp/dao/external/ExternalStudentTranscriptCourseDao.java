package org.jasig.ssp.dao.external;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.springframework.stereotype.Repository;


@Repository
public class ExternalStudentTranscriptCourseDao extends
		AbstractExternalDataDao<ExternalStudentTranscriptCourse> {
	
	public ExternalStudentTranscriptCourseDao()
	{
		super(ExternalStudentTranscriptCourse.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		return (List<ExternalStudentTranscriptCourse>)criteria.list();
		
	}
}
