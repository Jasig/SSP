package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.VeteranStatusTOFactory;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating VeteranStatus reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/veteranStatus</code>
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/veteranStatus")
public class VeteranStatusController
		extends
		AbstractAuditableReferenceController<VeteranStatus, VeteranStatusTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient VeteranStatusService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient VeteranStatusTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VeteranStatusController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected VeteranStatusController() {
		super(VeteranStatus.class, VeteranStatusTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<VeteranStatus> getService() {
		return service;
	}

	@Override
	protected TOFactory<VeteranStatusTO, VeteranStatus> getFactory() {
		return factory;
	}
}
