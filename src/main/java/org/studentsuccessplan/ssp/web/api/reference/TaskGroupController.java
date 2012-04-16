package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.TaskGroup;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.TaskGroupService;
import org.studentsuccessplan.ssp.transferobject.reference.TaskGroupTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/taskGroup")
public class TaskGroupController
		extends
		AbstractAuditableReferenceController<TaskGroup, TaskGroupTO> {

	@Autowired
	protected transient TaskGroupService service;

	@Override
	protected AuditableCrudService<TaskGroup> getService() {
		return service;
	}

	protected TaskGroupController() {
		super(TaskGroup.class, TaskGroupTO.class);
	}
}
