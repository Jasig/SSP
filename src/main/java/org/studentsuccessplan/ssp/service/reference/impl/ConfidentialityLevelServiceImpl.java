package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityLevelDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityLevelService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class ConfidentialityLevelServiceImpl implements
		ConfidentialityLevelService {

	@Autowired
	private ConfidentialityLevelDao dao;

	@Override
	public List<ConfidentialityLevel> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public ConfidentialityLevel get(UUID id) throws ObjectNotFoundException {
		ConfidentialityLevel obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ConfidentialityLevel");
		}

		return obj;
	}

	@Override
	public ConfidentialityLevel create(ConfidentialityLevel obj) {
		return dao.save(obj);
	}

	@Override
	public ConfidentialityLevel save(ConfidentialityLevel obj)
			throws ObjectNotFoundException {
		ConfidentialityLevel current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());
		current.setAcronym(obj.getAcronym());
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ConfidentialityLevel current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ConfidentialityLevelDao dao) {
		this.dao = dao;
	}
}
