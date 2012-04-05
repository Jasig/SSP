package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.SelfHelpGuideQuestionDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.SelfHelpGuideQuestionService;

@Service
@Transactional
public class SelfHelpGuideQuestionServiceImpl implements
		SelfHelpGuideQuestionService {

	@Autowired
	private SelfHelpGuideQuestionDao dao;

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database filtered by the supplied status.
	 */
	@Override
	public List<SelfHelpGuideQuestion> getAll(ObjectStatus status,
			int firstResult, int maxResults, String sortExpression) {
		return dao.getAll(status, firstResult, maxResults, sortExpression);
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
	public SelfHelpGuideQuestion save(SelfHelpGuideQuestion obj)
			throws ObjectNotFoundException {
		SelfHelpGuideQuestion current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	/**
	 * Mark the specific instance as {@link ObjectStatus#DELETED}.
	 * 
	 * @param id
	 *            Instance identifier
	 * @exception ObjectNotFoundException
	 *                if the specified ID does not exist.
	 */
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
