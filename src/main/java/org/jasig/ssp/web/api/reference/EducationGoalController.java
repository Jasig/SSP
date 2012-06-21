package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EducationGoalTOFactory;
import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EducationGoalService;
import org.jasig.ssp.transferobject.reference.EducationGoalTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
