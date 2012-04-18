package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.ChildCareArrangementDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class ChildCareArrangementServiceImpl implements ChildCareArrangementService {

	@Autowired
	private ChildCareArrangementDao dao;

	@Override
	public List<ChildCareArrangement> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public ChildCareArrangement get(UUID id) throws ObjectNotFoundException {
		ChildCareArrangement obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ChildCareArrangement");
		}

		return obj;
	}

	@Override
	public ChildCareArrangement create(ChildCareArrangement obj) {
		return dao.save(obj);
	}

	@Override
	public ChildCareArrangement save(ChildCareArrangement obj) throws ObjectNotFoundException {
		ChildCareArrangement current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ChildCareArrangement current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ChildCareArrangementDao dao) {
		this.dao = dao;
	}
}
