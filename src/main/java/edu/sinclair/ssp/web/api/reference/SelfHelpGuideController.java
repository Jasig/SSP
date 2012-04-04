package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.SelfHelpGuide;
import edu.sinclair.ssp.service.reference.SelfHelpGuideService;
import edu.sinclair.ssp.transferobject.reference.SelfHelpGuideTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/selfHelpGuide")
public class SelfHelpGuideController
		extends
		AbstractAuditableReferenceController<SelfHelpGuide, SelfHelpGuideTO> {

	@Autowired
	protected SelfHelpGuideController(SelfHelpGuideService service) {
		super(service, SelfHelpGuide.class, SelfHelpGuideTO.class);
	}
}
