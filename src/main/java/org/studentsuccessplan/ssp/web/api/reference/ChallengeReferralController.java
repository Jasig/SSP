package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeReferralTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challengeReferral")
public class ChallengeReferralController
		extends
		AbstractAuditableReferenceController<ChallengeReferral, ChallengeReferralTO> {

	@Autowired
	protected transient ChallengeReferralService service;

	@Override
	protected AuditableCrudService<ChallengeReferral> getService() {
		return service;
	}

	protected ChallengeReferralController() {
		super(ChallengeReferral.class, ChallengeReferralTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeReferralController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
