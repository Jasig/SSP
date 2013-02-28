package org.jasig.ssp.dao.external;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentAcademicProgramDao extends
		AbstractExternalDataDao<ExternalStudentAcademicProgram> {

	public ExternalStudentAcademicProgramDao()
	{
		super(ExternalStudentAcademicProgram.class);
	}
	
	public List<ExternalStudentAcademicProgram> getAcademicProgramsBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.addOrder(Order.asc("degreeName"));
		
		return (List<ExternalStudentAcademicProgram>)criteria.list();
	}
}
