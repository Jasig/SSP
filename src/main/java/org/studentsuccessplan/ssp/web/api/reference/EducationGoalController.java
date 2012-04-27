package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.EducationGoalTOFactory;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;
import org.studentsuccessplan.ssp.transferobject.reference.EducationGoalTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/educationGoal")
public class EducationGoalController
		extends
		AbstractAuditableReferenceController<EducationGoal, EducationGoalTO> {

	@Autowired
	protected transient EducationGoalService service;

	@Override
	protected AuditableCrudService<EducationGoal> getService() {
		return service;
	}

	@Autowired
	protected transient EducationGoalTOFactory factory;

	@Override
	protected TOFactory<EducationGoalTO, EducationGoal> getFactory() {
		return factory;
	}

	protected EducationGoalController() {
		super(EducationGoal.class, EducationGoalTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EducationGoalController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
