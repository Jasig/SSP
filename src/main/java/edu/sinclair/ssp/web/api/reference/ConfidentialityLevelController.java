package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.ConfidentialityLevel;
import edu.sinclair.ssp.service.reference.ConfidentialityLevelService;
import edu.sinclair.ssp.transferobject.reference.ConfidentialityLevelTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/confidentialityLevel")
public class ConfidentialityLevelController extends
		AbstractAuditableReferenceController<ConfidentialityLevel, ConfidentialityLevelTO> {

	@Autowired
	protected ConfidentialityLevelController(ConfidentialityLevelService service) {
		super(service, ConfidentialityLevel.class, ConfidentialityLevelTO.class);
	}
}
