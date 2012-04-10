package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.SelfHelpGuideDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuide;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.SelfHelpGuideService;

@Service
@Transactional
public class SelfHelpGuideServiceImpl implements SelfHelpGuideService {

	@Autowired
	private SelfHelpGuideDao dao;

	@Override
	public List<SelfHelpGuide> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public SelfHelpGuide get(UUID id) throws ObjectNotFoundException {
		SelfHelpGuide obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "SelfHelpGuide");
		}

		return obj;
	}

	@Override
	public SelfHelpGuide create(SelfHelpGuide obj) {
		return dao.save(obj);
	}

	@Override
	public SelfHelpGuide save(SelfHelpGuide obj) throws ObjectNotFoundException {
		SelfHelpGuide current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		SelfHelpGuide current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(SelfHelpGuideDao dao) {
		this.dao = dao;
	}
}
