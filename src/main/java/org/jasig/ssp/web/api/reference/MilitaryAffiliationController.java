package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.MilitaryAffiliationTOFactory;
import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.MilitaryAffiliationService;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/militaryAffiliation")
public class MilitaryAffiliationController
		extends
		AbstractAuditableReferenceController<MilitaryAffiliation, MilitaryAffiliationTO> {

	@Autowired
	protected transient MilitaryAffiliationService service;

	@Override
	protected AuditableCrudService<MilitaryAffiliation> getService() {
		return service;
	}

	@Autowired
	protected transient MilitaryAffiliationTOFactory factory;

	@Override
	protected TOFactory<MilitaryAffiliationTO, MilitaryAffiliation> getFactory() {
		return factory;
	}

	protected MilitaryAffiliationController() {
		super(MilitaryAffiliation.class, MilitaryAffiliationTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MilitaryAffiliationController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
