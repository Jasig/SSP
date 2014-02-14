package org.jasig.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.TaskMessageEnqueueDao;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TaskMessageEnqueue;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TaskMessageEnqueueService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskMessageEnqueueServiceImpl extends
		AbstractAuditableCrudService<TaskMessageEnqueue> implements TaskMessageEnqueueService {

	@Autowired 
	private TaskMessageEnqueueDao dao;
	
	@Override
	protected AuditableCrudDao<TaskMessageEnqueue> getDao() {
		return dao;
	}

	@Override
	public TaskMessageEnqueue save(TaskMessageEnqueue obj)
			throws ObjectNotFoundException, ValidationException {
		// TODO Auto-generated method stub
		return dao.save(obj);
	}

	@Override
	public List<TaskMessageEnqueue> getAllFromIds(List<UUID> ids) {
		return dao.getAllForIds(ids);
	}

	@Override
	public List<TaskMessageEnqueue> getAllForTask(Task task) {
		// TODO Auto-generated method stub
		return dao.getAllForTask(task);
	}

}
