package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityDisclosureAgreementService;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/confidentialityDisclosureAgreement")
public class ConfidentialityDisclosureAgreementController
		extends
		AbstractAuditableReferenceController<ConfidentialityDisclosureAgreement, ConfidentialityDisclosureAgreementTO> {

	@Autowired
	protected transient ConfidentialityDisclosureAgreementService citizenshipService;

	@Override
	protected AuditableCrudService<ConfidentialityDisclosureAgreement> getService() {
		return citizenshipService;
	}

	protected ConfidentialityDisclosureAgreementController() {
		super(ConfidentialityDisclosureAgreement.class,
				ConfidentialityDisclosureAgreementTO.class);
	}
}
