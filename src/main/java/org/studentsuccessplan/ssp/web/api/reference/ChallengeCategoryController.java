package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChallengeCategoryService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeCategoryTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/challengeCategory")
public class ChallengeCategoryController
		extends
		AbstractAuditableReferenceController<ChallengeCategory, ChallengeCategoryTO> {

	@Autowired
	protected transient ChallengeCategoryService service;

	@Override
	protected AuditableCrudService<ChallengeCategory> getService() {
		return service;
	}

	protected ChallengeCategoryController() {
		super(ChallengeCategory.class, ChallengeCategoryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeCategoryController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
