package org.jasig.ssp.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.GoalTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.web.api.reference.AbstractAuditableReferenceController;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/goal")
public class GoalController
		extends
		AbstractAuditableReferenceController<Goal, GoalTO> {

	@Autowired
	protected transient GoalService service;

	@Override
	protected AuditableCrudService<Goal> getService() {
		return service;
	}

	@Autowired
	protected transient GoalTOFactory factory;

	@Override
	protected TOFactory<GoalTO, Goal> getFactory() {
		return factory;
	}

	protected GoalController() {
		super(Goal.class, GoalTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoalController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
