package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.EducationLevelDao;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.service.reference.EducationLevelService;

@Service
@Transactional
public class EducationLevelServiceImpl extends
		AbstractReferenceService<EducationLevel>
		implements EducationLevelService {

	public EducationLevelServiceImpl() {
		super(EducationLevel.class);
	}

	@Autowired
	transient private EducationLevelDao dao;

	protected void setDao(final EducationLevelDao dao) {
		this.dao = dao;
	}

	@Override
	protected EducationLevelDao getDao() {
		return dao;
	}
}
