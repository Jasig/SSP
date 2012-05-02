package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.ConfidentialityDisclosureAgreementTOFactory;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityDisclosureAgreementService;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/confidentialityDisclosureAgreement")
public class ConfidentialityDisclosureAgreementController
		extends
		AbstractAuditableReferenceController<ConfidentialityDisclosureAgreement, ConfidentialityDisclosureAgreementTO> {

	@Autowired
	protected transient ConfidentialityDisclosureAgreementService service;

	@Override
	protected AuditableCrudService<ConfidentialityDisclosureAgreement> getService() {
		return service;
	}

	@Autowired
	protected transient ConfidentialityDisclosureAgreementTOFactory factory;

	@Override
	protected TOFactory<ConfidentialityDisclosureAgreementTO, ConfidentialityDisclosureAgreement> getFactory() {
		return factory;
	}

	protected ConfidentialityDisclosureAgreementController() {
		super(ConfidentialityDisclosureAgreement.class,
				ConfidentialityDisclosureAgreementTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfidentialityDisclosureAgreementController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
