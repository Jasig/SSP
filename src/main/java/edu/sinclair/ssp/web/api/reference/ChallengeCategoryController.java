package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.ChallengeCategory;
import edu.sinclair.ssp.service.reference.ChallengeCategoryService;
import edu.sinclair.ssp.transferobject.reference.ChallengeCategoryTO;

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
