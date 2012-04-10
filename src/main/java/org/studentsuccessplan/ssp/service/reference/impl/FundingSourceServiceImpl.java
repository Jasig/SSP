package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.FundingSourceDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.FundingSourceService;

@Service
@Transactional
public class FundingSourceServiceImpl implements FundingSourceService {

	@Autowired
	private FundingSourceDao dao;

	@Override
	public List<FundingSource> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public FundingSource get(UUID id) throws ObjectNotFoundException {
		FundingSource obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "FundingSource");
		}

		return obj;
	}

	@Override
	public FundingSource create(FundingSource obj) {
		return dao.save(obj);
	}

	@Override
	public FundingSource save(FundingSource obj) throws ObjectNotFoundException {
		FundingSource current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		FundingSource current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(FundingSourceDao dao) {
		this.dao = dao;
	}
}
