package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.ChallengeReferral;
import edu.sinclair.ssp.service.reference.ChallengeReferralService;
import edu.sinclair.ssp.transferobject.reference.ChallengeReferralTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challengeReferral")
public class ChallengeReferralController extends
		AbstractAuditableReferenceController<ChallengeReferral, ChallengeReferralTO> {

	@Autowired
	protected ChallengeReferralController(ChallengeReferralService service) {
		super(service, ChallengeReferral.class, ChallengeReferralTO.class);
	}
}
