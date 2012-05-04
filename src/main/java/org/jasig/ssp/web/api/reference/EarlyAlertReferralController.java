package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReferralTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating EarlyAlertReferral reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/earlyAlertReferral</code>
 * 
 * @author jon.adams
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/earlyAlertReferral")
public class EarlyAlertReferralController
		extends
		AbstractAuditableReferenceController<EarlyAlertReferral, EarlyAlertReferralTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient EarlyAlertReferralService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient EarlyAlertReferralTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertReferralController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected EarlyAlertReferralController() {
		super(EarlyAlertReferral.class, EarlyAlertReferralTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<EarlyAlertReferral> getService() {
		return service;
	}

	@Override
	protected TOFactory<EarlyAlertReferralTO, EarlyAlertReferral> getFactory() {
		return factory;
	}
}
