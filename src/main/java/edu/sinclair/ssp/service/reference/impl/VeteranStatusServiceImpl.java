package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.VeteranStatusDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

@Service
@Transactional
public class VeteranStatusServiceImpl implements VeteranStatusService {

	@Autowired
	private VeteranStatusDao dao;

	@Override
	public List<VeteranStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public VeteranStatus get(UUID id) throws ObjectNotFoundException {
		VeteranStatus obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "VeteranStatus");
		}

		return obj;
	}

	@Override
	public VeteranStatus create(VeteranStatus obj) {
		return dao.save(obj);
	}

	@Override
	public VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException {
		VeteranStatus current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		VeteranStatus current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(VeteranStatusDao dao) {
		this.dao = dao;
	}
}
