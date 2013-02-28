package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentAcademicProgramTOFactory;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.transferobject.external.ExternalStudentAcademicProgramTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentAcademicProgramTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentAcademicProgramTO, ExternalStudentAcademicProgram> implements
		ExternalStudentAcademicProgramTOFactory {

	public ExternalStudentAcademicProgramTOFactoryImpl(
			Class<ExternalStudentAcademicProgramTO> toClass,
			Class<ExternalStudentAcademicProgram> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}
	
	public ExternalStudentAcademicProgramTOFactoryImpl(){
		super(ExternalStudentAcademicProgramTO.class, ExternalStudentAcademicProgram.class);
	}

	@Override
	protected ExternalDataDao<ExternalStudentAcademicProgram> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
