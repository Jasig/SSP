package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ChildCareArrangementTOFactory;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.jasig.ssp.transferobject.reference.ChildCareArrangementTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/childCareArrangement")
public class ChildCareArrangementController
		extends
		AbstractAuditableReferenceController<ChildCareArrangement, ChildCareArrangementTO> {

	@Autowired
	protected transient ChildCareArrangementService service;

	@Override
	protected AuditableCrudService<ChildCareArrangement> getService() {
		return service;
	}

	@Autowired
	protected transient ChildCareArrangementTOFactory factory;

	@Override
	protected TOFactory<ChildCareArrangementTO, ChildCareArrangement> getFactory() {
		return factory;
	}

	protected ChildCareArrangementController() {
		super(ChildCareArrangement.class, ChildCareArrangementTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChildCareArrangementController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
