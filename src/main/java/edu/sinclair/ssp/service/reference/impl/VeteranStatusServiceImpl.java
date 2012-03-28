package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(VeteranStatusServiceImpl.class);

	@Autowired
	private VeteranStatusDao dao;

	@Override
	public List<VeteranStatus> getAll(ObjectStatus status) {
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
	public List<VeteranStatus> getAll(ObjectStatus status, int firstResult,
			int maxResults, String sortExpression) {
		return dao.getAll(status, firstResult, maxResults, sortExpression);
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
