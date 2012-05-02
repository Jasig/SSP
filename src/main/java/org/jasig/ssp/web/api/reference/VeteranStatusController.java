package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.VeteranStatusTOFactory;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.VeteranStatusService;
import org.studentsuccessplan.ssp.transferobject.reference.VeteranStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/veteranStatus")
public class VeteranStatusController
		extends
		AbstractAuditableReferenceController<VeteranStatus, VeteranStatusTO> {

	@Autowired
	protected transient VeteranStatusService service;

	@Override
	protected AuditableCrudService<VeteranStatus> getService() {
		return service;
	}

	@Autowired
	protected transient VeteranStatusTOFactory factory;

	@Override
	protected TOFactory<VeteranStatusTO, VeteranStatus> getFactory() {
		return factory;
	}

	protected VeteranStatusController() {
		super(VeteranStatus.class, VeteranStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VeteranStatusController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
