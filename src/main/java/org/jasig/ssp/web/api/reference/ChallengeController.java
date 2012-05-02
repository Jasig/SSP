package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChallengeTOFactory;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/challenge")
public class ChallengeController
		extends
		AbstractAuditableReferenceController<Challenge, ChallengeTO> {

	@Autowired
	protected transient ChallengeService service;

	@Override
	protected AuditableCrudService<Challenge> getService() {
		return service;
	}

	@Autowired
	protected transient ChallengeTOFactory factory;

	@Override
	protected TOFactory<ChallengeTO, Challenge> getFactory() {
		return factory;
	}

	protected ChallengeController() {
		super(Challenge.class, ChallengeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
