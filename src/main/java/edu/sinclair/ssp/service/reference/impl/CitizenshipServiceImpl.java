package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

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

	@Autowired
	private CitizenshipDao dao;

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @return All entities in the database filtered by the supplied status.
	 */
	@Override
	public List<Citizenship> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

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
	public List<Citizenship> getAll(ObjectStatus status, int firstResult,
			int maxResults, String sortExpression) {
		return dao.getAll(status, firstResult, maxResults, sortExpression);
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
