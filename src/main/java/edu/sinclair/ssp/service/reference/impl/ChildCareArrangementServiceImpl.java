package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ChildCareArrangementDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.ChildCareArrangementService;

@Service
@Transactional
public class ChildCareArrangementServiceImpl implements ChildCareArrangementService {

	@Autowired
	private ChildCareArrangementDao dao;

	@Override
	public List<ChildCareArrangement> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
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
