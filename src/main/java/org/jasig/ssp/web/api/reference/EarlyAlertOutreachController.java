package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertOutreachTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutreachTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating EarlyAlertOutreach reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/earlyAlertOutreach</code>
 * 
 * @author jon.adams
 */
@Controller
@RequestMapping("/1/reference/earlyAlertOutreach")
public class EarlyAlertOutreachController
		extends
		AbstractAuditableReferenceController<EarlyAlertOutreach, EarlyAlertOutreachTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient EarlyAlertOutreachService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient EarlyAlertOutreachTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertOutreachController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected EarlyAlertOutreachController() {
		super(EarlyAlertOutreach.class, EarlyAlertOutreachTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<EarlyAlertOutreach> getService() {
		return service;
	}

	@Override
	protected TOFactory<EarlyAlertOutreachTO, EarlyAlertOutreach> getFactory() {
		return factory;
	}
}
