package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.service.reference.ConfidentialityDisclosureAgreementService;
import edu.sinclair.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/confidentialityDisclosureAgreement")
public class ConfidentialityDisclosureAgreementController
		extends
		AbstractAuditableReferenceController<ConfidentialityDisclosureAgreement, ConfidentialityDisclosureAgreementTO> {

	@Autowired
	protected ConfidentialityDisclosureAgreementController(ConfidentialityDisclosureAgreementService service) {
		super(service, ConfidentialityDisclosureAgreement.class, ConfidentialityDisclosureAgreementTO.class);
	}
}
