package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.EducationGoalDao;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;

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
