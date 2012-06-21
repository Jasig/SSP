package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Autowired
	protected transient SelfHelpGuideQuestionTOFactory factory;

	@Override
	protected TOFactory<SelfHelpGuideQuestionTO, SelfHelpGuideQuestion> getFactory() {
		return factory;
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
