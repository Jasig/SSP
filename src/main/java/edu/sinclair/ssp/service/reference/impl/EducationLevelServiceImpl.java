package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.EducationLevelDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.EducationLevelService;

@Service
@Transactional
public class EducationLevelServiceImpl implements EducationLevelService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(EducationLevelServiceImpl.class);

	@Autowired
	private EducationLevelDao dao;

	@Override
	public List<EducationLevel> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public EducationLevel get(UUID id) throws ObjectNotFoundException {
		EducationLevel obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "EducationLevel");
		}
		return obj;
	}

	@Override
	public EducationLevel create(EducationLevel obj) {
		return dao.save(obj);
	}

	@Override
	public EducationLevel save(EducationLevel obj)
			throws ObjectNotFoundException {
		EducationLevel current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		EducationLevel current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(EducationLevelDao dao) {
		this.dao = dao;
	}

}
