package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.EducationGoalDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.EducationGoalService;

@Service
@Transactional
public class EducationGoalServiceImpl implements EducationGoalService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(EducationGoalServiceImpl.class);

	@Autowired
	private EducationGoalDao dao;

	@Override
	public List<EducationGoal> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public EducationGoal get(UUID id) throws ObjectNotFoundException {
		EducationGoal obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "EducationGoal");
		}
		return obj;
	}

	@Override
	public EducationGoal create(EducationGoal obj) {
		return dao.save(obj);
	}

	@Override
	public EducationGoal save(EducationGoal obj) throws ObjectNotFoundException {
		EducationGoal current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		EducationGoal current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(EducationGoalDao dao) {
		this.dao = dao;
	}

}
