package org.jasig.ssp.web.api;

import org.jasig.ssp.factory.GoalTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.transferobject.GoalTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Goal controller
 */
@Controller
@RequestMapping("/1/person/{personId}/goal")
public class GoalController
		extends AbstractRestrictedPersonAssocController<Goal, GoalTO> {

	/**
	 * Construct an instance with specific classes for use by the super class
	 * methods.
	 */
	protected GoalController() {
		super(Goal.class, GoalTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoalController.class);

	@Autowired
	protected transient GoalService service;

	@Autowired
	protected transient GoalTOFactory factory;

	@Override
	protected GoalService getService() {
		return service;
	}

	@Override
	protected TOFactory<GoalTO, Goal> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String permissionBaseName() {
		return "GOAL";
	}
}