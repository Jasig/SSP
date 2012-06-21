package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.MessageTemplateTOFactory;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/messageTemplate")
public class MessageTemplateController
		extends
		AbstractAuditableReferenceController<MessageTemplate, MessageTemplateTO> {

	@Autowired
	protected transient MessageTemplateService service;

	@Override
	protected AuditableCrudService<MessageTemplate> getService() {
		return service;
	}

	@Autowired
	protected transient MessageTemplateTOFactory factory;

	@Override
	protected TOFactory<MessageTemplateTO, MessageTemplate> getFactory() {
		return factory;
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
