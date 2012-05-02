package org.jasig.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.EducationGoalDao;
import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.service.reference.EducationGoalService;

@Service
@Transactional
public class EducationGoalServiceImpl extends
		AbstractReferenceService<EducationGoal>
		implements EducationGoalService {

	public EducationGoalServiceImpl() {
		super(EducationGoal.class);
	}

	@Autowired
	transient private EducationGoalDao dao;

	protected void setDao(final EducationGoalDao dao) {
		this.dao = dao;
	}

	@Override
	protected EducationGoalDao getDao() {
		return dao;
	}
}
