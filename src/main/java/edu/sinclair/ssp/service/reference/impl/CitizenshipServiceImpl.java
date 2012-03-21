package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.CitizenshipDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.CitizenshipService;

@Service
@Transactional
public class CitizenshipServiceImpl implements CitizenshipService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(CitizenshipServiceImpl.class);

	@Autowired
	private CitizenshipDao dao;

	@Override
	public List<Citizenship> getAll(ObjectStatus status) {
		return dao.getAll(status);
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
