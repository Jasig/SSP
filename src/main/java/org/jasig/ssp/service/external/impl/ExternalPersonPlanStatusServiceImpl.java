package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalCourseDao;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonPlanStatusDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalPersonPlanStatusServiceImpl extends
		AbstractExternalDataService<ExternalPersonPlanStatus> implements
		ExternalPersonPlanStatusService {


	@Autowired
	transient private ExternalPersonPlanStatusDao dao;

	@Override
	protected ExternalPersonPlanStatusDao getDao() {
		return dao;
	}
	
	@Override
	public ExternalPersonPlanStatus getBySchoolId(String schoolId)
			throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return getDao().getBySchoolId(schoolId);
	}

}
