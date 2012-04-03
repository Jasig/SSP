package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.reference.MaritalStatusService;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/maritalStatus")
public class MaritalStatusController extends
		AbstractAuditableReferenceController<MaritalStatus, MaritalStatusTO> {

	@Autowired
	protected MaritalStatusController(MaritalStatusService service) {
		super(service, MaritalStatus.class, MaritalStatusTO.class);
	}
}
