package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.FundingSourceTOFactory;
import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.FundingSourceService;
import org.jasig.ssp.transferobject.reference.FundingSourceTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/fundingSource")
public class FundingSourceController
		extends
		AbstractAuditableReferenceController<FundingSource, FundingSourceTO> {

	@Autowired
	protected transient FundingSourceService service;

	@Override
	protected AuditableCrudService<FundingSource> getService() {
		return service;
	}

	@Autowired
	protected transient FundingSourceTOFactory factory;

	@Override
	protected TOFactory<FundingSourceTO, FundingSource> getFactory() {
		return factory;
	}

	protected FundingSourceController() {
		super(FundingSource.class, FundingSourceTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FundingSourceController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
