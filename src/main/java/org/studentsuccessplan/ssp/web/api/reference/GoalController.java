package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.GoalService;
import org.studentsuccessplan.ssp.transferobject.reference.GoalTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/goal")
public class GoalController
		extends
		AbstractAuditableReferenceController<Goal, GoalTO> {

	@Autowired
	protected transient GoalService service;

	@Override
	protected AuditableCrudService<Goal> getService() {
		return service;
	}

	protected GoalController() {
		super(Goal.class, GoalTO.class);
	}
}
