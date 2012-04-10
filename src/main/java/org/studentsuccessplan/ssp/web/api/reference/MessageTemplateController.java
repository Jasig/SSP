package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.reference.MessageTemplateService;
import org.studentsuccessplan.ssp.transferobject.reference.MessageTemplateTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/messageTemplate")
public class MessageTemplateController extends
		AbstractAuditableReferenceController<MessageTemplate, MessageTemplateTO> {

	@Autowired
	protected MessageTemplateController(MessageTemplateService service) {
		super(service, MessageTemplate.class, MessageTemplateTO.class);
	}
}
