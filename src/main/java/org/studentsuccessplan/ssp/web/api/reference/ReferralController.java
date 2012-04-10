package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.Referral;
import edu.sinclair.ssp.service.reference.ReferralService;
import edu.sinclair.ssp.transferobject.reference.ReferralTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/referral")
public class ReferralController extends
		AbstractAuditableReferenceController<Referral, ReferralTO> {

	@Autowired
	protected ReferralController(final ReferralService service) {
		super(service, Referral.class, ReferralTO.class);
	}
}
