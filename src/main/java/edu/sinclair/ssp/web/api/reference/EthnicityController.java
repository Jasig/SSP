package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.service.reference.EthnicityService;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/ethnicity")
public class EthnicityController extends
		AbstractAuditableReferenceController<Ethnicity, EthnicityTO> {

	@Autowired
	protected EthnicityController(EthnicityService service) {
		super(service, Ethnicity.class, EthnicityTO.class);
	}
}
