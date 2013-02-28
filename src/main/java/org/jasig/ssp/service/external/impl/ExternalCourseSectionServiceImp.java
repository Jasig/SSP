package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalCourseSection;
import org.jasig.ssp.service.external.ExternalCourseSectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ExternalCourseSectionServiceImp extends
		AbstractExternalDataService<ExternalCourseSection> implements ExternalCourseSectionService {

	@Override
	protected ExternalDataDao<ExternalCourseSection> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
