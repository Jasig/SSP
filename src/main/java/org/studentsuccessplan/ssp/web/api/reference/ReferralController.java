package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.Referral;
import org.studentsuccessplan.ssp.service.reference.ReferralService;
import org.studentsuccessplan.ssp.transferobject.reference.ReferralTO;

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
