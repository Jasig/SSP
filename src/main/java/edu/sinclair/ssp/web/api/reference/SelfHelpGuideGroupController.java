package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.SelfHelpGuideGroup;
import edu.sinclair.ssp.service.reference.SelfHelpGuideGroupService;
import edu.sinclair.ssp.transferobject.reference.SelfHelpGuideGroupTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/selfHelpGuideGroup")
public class SelfHelpGuideGroupController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideGroup, SelfHelpGuideGroupTO> {

	@Autowired
	protected SelfHelpGuideGroupController(SelfHelpGuideGroupService service) {
		super(service, SelfHelpGuideGroup.class, SelfHelpGuideGroupTO.class);
	}
}
