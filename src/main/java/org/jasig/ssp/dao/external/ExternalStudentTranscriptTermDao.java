package org.jasig.ssp.dao.external;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentTranscriptTermDao extends
		AbstractExternalDataDao<ExternalStudentTranscriptTerm> {

	protected ExternalStudentTranscriptTermDao() {
		super(ExternalStudentTranscriptTerm.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptTerm> getExternalStudentTranscriptTermsBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));		
		return (List<ExternalStudentTranscriptTerm>)criteria.list();
	}
	
	public ExternalStudentTranscriptTerm getExternalStudentTranscriptTermBySchoolIdTermCode(String schoolId, String termCode){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.add(Restrictions.eq("termCode", termCode));
		return (ExternalStudentTranscriptTerm)criteria.list();
	}
}
