package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.MaritalStatusDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;

@Service
@Transactional
public class MaritalStatusServiceImpl implements MaritalStatusService {

	@Autowired
	private MaritalStatusDao dao;

	@Override
	public List<MaritalStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public MaritalStatus get(UUID id) throws ObjectNotFoundException {
		MaritalStatus obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "MaritalStatus");
		}

		return obj;
	}

	@Override
	public MaritalStatus create(MaritalStatus obj) {
		return dao.save(obj);
	}

	@Override
	public MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException {
		MaritalStatus current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		MaritalStatus current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(MaritalStatusDao dao) {
		this.dao = dao;
	}
}
