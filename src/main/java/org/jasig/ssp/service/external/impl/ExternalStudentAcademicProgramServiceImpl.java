package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentAcademicProgramDao;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentAcademicProgramServiceImpl extends
		AbstractExternalDataService<ExternalStudentAcademicProgram> implements
		ExternalStudentAcademicProgramService {

	@Autowired
	transient private ExternalStudentAcademicProgramDao dao;
	
	@Override
	protected ExternalDataDao<ExternalStudentAcademicProgram> getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ExternalStudentAcademicProgram> getAcademicProgramsBySchoolId(String schoolId){
		return dao.getAcademicProgramsBySchoolId(schoolId);
	}


}
