package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertOutcomeTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutcomeTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating EarlyAlertOutcome reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/earlyAlertOutcome</code>
 * 
 * @author jon.adams
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/earlyAlertOutcome")
public class EarlyAlertOutcomeController
		extends
		AbstractAuditableReferenceController<EarlyAlertOutcome, EarlyAlertOutcomeTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient EarlyAlertOutcomeService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient EarlyAlertOutcomeTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertOutcomeController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected EarlyAlertOutcomeController() {
		super(EarlyAlertOutcome.class, EarlyAlertOutcomeTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<EarlyAlertOutcome> getService() {
		return service;
	}

	@Override
	protected TOFactory<EarlyAlertOutcomeTO, EarlyAlertOutcome> getFactory() {
		return factory;
	}
}
