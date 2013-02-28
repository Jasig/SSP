package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentsByCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentsByCourse;
import org.jasig.ssp.transferobject.external.ExternalStudentsByCourseTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentsByCourseTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentsByCourseTO, ExternalStudentsByCourse> implements
		ExternalStudentsByCourseTOFactory {

	public ExternalStudentsByCourseTOFactoryImpl(
			Class<ExternalStudentsByCourseTO> toClass,
			Class<ExternalStudentsByCourse> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}
	
	public ExternalStudentsByCourseTOFactoryImpl(){
		super(ExternalStudentsByCourseTO.class, ExternalStudentsByCourse.class);
	}

	@Override
	protected ExternalDataDao<ExternalStudentsByCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
