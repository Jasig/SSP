package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;
import edu.sinclair.ssp.service.reference.SelfHelpGuideQuestionService;
import edu.sinclair.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/selfHelpGuideQuestion")
public class SelfHelpGuideQuestionController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideQuestion, SelfHelpGuideQuestionTO> {

	@Autowired
	protected SelfHelpGuideQuestionController(SelfHelpGuideQuestionService service) {
		super(service, SelfHelpGuideQuestion.class, SelfHelpGuideQuestionTO.class);
	}
}
