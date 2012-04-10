package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideQuestionService;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/selfHelpGuideQuestion")
public class SelfHelpGuideQuestionController extends
		AbstractAuditableReferenceController<SelfHelpGuideQuestion, SelfHelpGuideQuestionTO> {

	@Autowired
	protected SelfHelpGuideQuestionController(SelfHelpGuideQuestionService service) {
		super(service, SelfHelpGuideQuestion.class, SelfHelpGuideQuestionTO.class);
	}
}
