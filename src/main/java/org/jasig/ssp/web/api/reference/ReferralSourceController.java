package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ReferralSourceTOFactory;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.transferobject.reference.ReferralSourceTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/referralSource")
public class ReferralSourceController
		extends
		AbstractAuditableReferenceController<ReferralSource, ReferralSourceTO> {

	@Autowired
	protected transient ReferralSourceService service;

	@Override
	protected AuditableCrudService<ReferralSource> getService() {
		return service;
	}

	@Autowired
	protected transient ReferralSourceTOFactory factory;

	@Override
	protected TOFactory<ReferralSourceTO, ReferralSource> getFactory() {
		return factory;
	}

	protected ReferralSourceController() {
		super(ReferralSource.class, ReferralSourceTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralSourceController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
