package org.jasig.ssp.service;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TaskMessageEnqueue;

public interface TaskMessageEnqueueService extends
		AuditableCrudService<TaskMessageEnqueue> {

	List<TaskMessageEnqueue> getAllFromIds(List<UUID> ids);
	
	List<TaskMessageEnqueue> getAllForTask(Task task);
}
