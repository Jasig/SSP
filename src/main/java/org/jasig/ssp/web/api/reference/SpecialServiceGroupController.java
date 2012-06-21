package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SpecialServiceGroupTOFactory;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.transferobject.reference.SpecialServiceGroupTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/specialServiceGroup")
public class SpecialServiceGroupController
		extends
		AbstractAuditableReferenceController<SpecialServiceGroup, SpecialServiceGroupTO> {

	@Autowired
	protected transient SpecialServiceGroupService service;

	@Override
	protected AuditableCrudService<SpecialServiceGroup> getService() {
		return service;
	}

	@Autowired
	protected transient SpecialServiceGroupTOFactory factory;

	@Override
	protected TOFactory<SpecialServiceGroupTO, SpecialServiceGroup> getFactory() {
		return factory;
	}

	protected SpecialServiceGroupController() {
		super(SpecialServiceGroup.class, SpecialServiceGroupTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServiceGroupController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
