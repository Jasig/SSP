package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ProgramStatusTOFactory;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.reference.ProgramStatusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating ProgramStatus reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/programStatus</code>
 */
@Controller
@RequestMapping("/1/reference/programStatus")
public class ProgramStatusController
		extends
		AbstractAuditableReferenceController<ProgramStatus, ProgramStatusTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient ProgramStatusService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient ProgramStatusTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProgramStatusController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected ProgramStatusController() {
		super(ProgramStatus.class, ProgramStatusTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<ProgramStatus> getService() {
		return service;
	}

	@Override
	protected TOFactory<ProgramStatusTO, ProgramStatus> getFactory() {
		return factory;
	}
}