package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityDisclosureAgreementTOFactory;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ConfidentialityDisclosureAgreementService;
import org.jasig.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
