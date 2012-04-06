package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.SelfHelpGuideGroupDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuideGroup;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.SelfHelpGuideGroupService;

@Service
@Transactional
public class SelfHelpGuideGroupServiceImpl implements SelfHelpGuideGroupService {

	@Autowired
	private SelfHelpGuideGroupDao dao;

	@Override
	public List<SelfHelpGuideGroup> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public SelfHelpGuideGroup get(UUID id) throws ObjectNotFoundException {
		SelfHelpGuideGroup obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "SelfHelpGuideGroup");
		}

		return obj;
	}

	@Override
	public SelfHelpGuideGroup create(SelfHelpGuideGroup obj) {
		return dao.save(obj);
	}

	@Override
	public SelfHelpGuideGroup save(SelfHelpGuideGroup obj) throws ObjectNotFoundException {
		SelfHelpGuideGroup current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		SelfHelpGuideGroup current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(SelfHelpGuideGroupDao dao) {
		this.dao = dao;
	}
}
