package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.MessageTemplateService;
import org.studentsuccessplan.ssp.transferobject.reference.MessageTemplateTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/messageTemplate")
public class MessageTemplateController
		extends
		AbstractAuditableReferenceController<MessageTemplate, MessageTemplateTO> {

	@Autowired
	protected transient MessageTemplateService service;

	@Override
	protected AuditableCrudService<MessageTemplate> getService() {
		return service;
	}

	protected MessageTemplateController() {
		super(MessageTemplate.class, MessageTemplateTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageTemplateController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
