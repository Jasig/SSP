package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptCourseTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentTranscriptCourseTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentTranscriptCourseTO, ExternalStudentTranscriptCourse> implements
		ExternalStudentTranscriptCourseTOFactory {

	public ExternalStudentTranscriptCourseTOFactoryImpl()
	{
		super(ExternalStudentTranscriptCourseTO.class,ExternalStudentTranscriptCourse.class);
	}
	public ExternalStudentTranscriptCourseTOFactoryImpl(
			Class<ExternalStudentTranscriptCourseTO> toClass,
			Class<ExternalStudentTranscriptCourse> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ExternalDataDao<ExternalStudentTranscriptCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
