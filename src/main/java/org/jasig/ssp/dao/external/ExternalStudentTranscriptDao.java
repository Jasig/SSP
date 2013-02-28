package org.jasig.ssp.dao.external;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentTranscriptDao extends
		AbstractExternalDataDao<ExternalStudentTranscript> {

		public ExternalStudentTranscriptDao()
		{
			super(ExternalStudentTranscript.class);
		}
		
		public ExternalStudentTranscript getRecordsBySchoolId(String schoolId){
			Criteria criteria = this.createCriteria();
			
			criteria.add(Restrictions.eq("schoolId", schoolId));
			return (ExternalStudentTranscript) criteria.uniqueResult();
		}
}
