package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/challengeReferral")
public class ChallengeReferralController
		extends
		AbstractAuditableReferenceController<ChallengeReferral, ChallengeReferralTO> {

	@Autowired
	protected transient ChallengeReferralService service;

	@Override
	protected AuditableCrudService<ChallengeReferral> getService() {
		return service;
	}

	@Autowired
	protected transient ChallengeReferralTOFactory factory;

	@Override
	protected TOFactory<ChallengeReferralTO, ChallengeReferral> getFactory() {
		return factory;
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
