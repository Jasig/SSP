package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideGroupDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideGroupService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class SelfHelpGuideGroupServiceImpl implements SelfHelpGuideGroupService {

	@Autowired
	private SelfHelpGuideGroupDao dao;

	@Override
	public List<SelfHelpGuideGroup> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
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
