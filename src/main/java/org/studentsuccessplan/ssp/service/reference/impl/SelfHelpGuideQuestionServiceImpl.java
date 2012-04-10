package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideQuestionService;

@Service
@Transactional
public class SelfHelpGuideQuestionServiceImpl implements SelfHelpGuideQuestionService {

	@Autowired
	private SelfHelpGuideQuestionDao dao;

	@Override
	public List<SelfHelpGuideQuestion> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public SelfHelpGuideQuestion get(UUID id) throws ObjectNotFoundException {
		SelfHelpGuideQuestion obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "SelfHelpGuideQuestion");
		}

		return obj;
	}

	@Override
	public SelfHelpGuideQuestion create(SelfHelpGuideQuestion obj) {
		return dao.save(obj);
	}

	@Override
	public SelfHelpGuideQuestion save(SelfHelpGuideQuestion obj) throws ObjectNotFoundException {
		SelfHelpGuideQuestion current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		SelfHelpGuideQuestion current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(SelfHelpGuideQuestionDao dao) {
		this.dao = dao;
	}
}
