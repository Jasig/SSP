package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.service.reference.ChallengeCategoryService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeCategoryTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challengeCategory")
public class ChallengeCategoryController extends
		AbstractAuditableReferenceController<ChallengeCategory, ChallengeCategoryTO> {

	@Autowired
	protected ChallengeCategoryController(ChallengeCategoryService service) {
		super(service, ChallengeCategory.class, ChallengeCategoryTO.class);
	}
}
