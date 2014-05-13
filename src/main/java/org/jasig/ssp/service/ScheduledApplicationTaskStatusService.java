package org.jasig.ssp.service;

import org.jasig.ssp.model.ScheduledApplicationTaskStatus;

public interface ScheduledApplicationTaskStatusService extends
		AuditableCrudService<ScheduledApplicationTaskStatus> {

	ScheduledApplicationTaskStatus beginTask(String taskName);
	ScheduledApplicationTaskStatus completeTask(String taskName);
	ScheduledApplicationTaskStatus failTask(String taskName);
	ScheduledApplicationTaskStatus resetTask(String taskName);
	ScheduledApplicationTaskStatus interruptTask(String taskName);
	
	ScheduledApplicationTaskStatus getByName(String taskName);
}
