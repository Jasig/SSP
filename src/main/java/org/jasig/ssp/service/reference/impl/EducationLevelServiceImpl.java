package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.EducationLevelDao;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EducationLevelServiceImpl extends
		AbstractReferenceService<EducationLevel>
		implements EducationLevelService {

	public EducationLevelServiceImpl() {
		super();
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
