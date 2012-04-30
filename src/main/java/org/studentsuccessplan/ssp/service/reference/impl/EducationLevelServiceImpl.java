package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.EducationLevelDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.EducationLevelService;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class EducationLevelServiceImpl implements EducationLevelService {

	@Autowired
	private EducationLevelDao dao;

	@Override
	public PagingWrapper<EducationLevel> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
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
