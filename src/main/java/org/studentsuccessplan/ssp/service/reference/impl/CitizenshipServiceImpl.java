package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.CitizenshipDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;

@Service
@Transactional
public class CitizenshipServiceImpl implements CitizenshipService {

	@Autowired
	private CitizenshipDao dao;

	@Override
	public List<Citizenship> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public Citizenship get(UUID id) throws ObjectNotFoundException {
		Citizenship obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Citizenship");
		}

		return obj;
	}

	@Override
	public Citizenship create(Citizenship obj) {
		return dao.save(obj);
	}

	@Override
	public Citizenship save(Citizenship obj) throws ObjectNotFoundException {
		Citizenship current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Citizenship current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(CitizenshipDao dao) {
		this.dao = dao;
	}
}
