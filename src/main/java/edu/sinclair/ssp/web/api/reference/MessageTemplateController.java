package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.MessageTemplate;
import edu.sinclair.ssp.service.reference.MessageTemplateService;
import edu.sinclair.ssp.transferobject.reference.MessageTemplateTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/messageTemplate")
public class MessageTemplateController
		extends
		AbstractAuditableReferenceController<MessageTemplate, MessageTemplateTO> {

	@Autowired
	protected MessageTemplateController(MessageTemplateService service) {
		super(service, MessageTemplate.class, MessageTemplateTO.class);
	}
}
