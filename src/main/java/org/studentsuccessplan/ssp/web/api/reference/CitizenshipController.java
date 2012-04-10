package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.reference.CitizenshipService;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/citizenship")
public class CitizenshipController extends
		AbstractAuditableReferenceController<Citizenship, CitizenshipTO> {

	@Autowired
	protected CitizenshipController(CitizenshipService service) {
		super(service, Citizenship.class, CitizenshipTO.class);
	}
}
