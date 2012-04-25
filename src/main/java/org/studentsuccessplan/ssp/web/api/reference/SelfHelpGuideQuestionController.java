package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideQuestionService;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/selfHelpGuideQuestion")
public class SelfHelpGuideQuestionController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideQuestion, SelfHelpGuideQuestionTO> {

	@Autowired
	protected transient SelfHelpGuideQuestionService service;

	@Override
	protected AuditableCrudService<SelfHelpGuideQuestion> getService() {
		return service;
	}

	protected SelfHelpGuideQuestionController() {
		super(SelfHelpGuideQuestion.class, SelfHelpGuideQuestionTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideQuestionController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
