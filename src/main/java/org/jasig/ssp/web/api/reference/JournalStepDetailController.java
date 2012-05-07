package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepDetailTOFactory;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/journalStepDetail")
public class JournalStepDetailController
		extends
		AbstractAuditableReferenceController<JournalStepDetail, JournalStepDetailTO> {

	@Autowired
	protected transient JournalStepDetailService service;

	@Override
	protected AuditableCrudService<JournalStepDetail> getService() {
		return service;
	}

	@Autowired
	protected transient JournalStepDetailTOFactory factory;

	@Override
	protected TOFactory<JournalStepDetailTO, JournalStepDetail> getFactory() {
		return factory;
	}

	protected JournalStepDetailController() {
		super(JournalStepDetail.class, JournalStepDetailTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalStepDetailController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
