package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.TaskGroupDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.TaskGroup;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.TaskGroupService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class TaskGroupServiceImpl implements TaskGroupService {

	@Autowired
	private TaskGroupDao dao;

	@Override
	public List<TaskGroup> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public TaskGroup get(UUID id) throws ObjectNotFoundException {
		TaskGroup obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "TaskGroup");
		}

		return obj;
	}

	@Override
	public TaskGroup create(TaskGroup obj) {
		return dao.save(obj);
	}

	@Override
	public TaskGroup save(TaskGroup obj) throws ObjectNotFoundException {
		TaskGroup current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		TaskGroup current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(TaskGroupDao dao) {
		this.dao = dao;
	}
}
