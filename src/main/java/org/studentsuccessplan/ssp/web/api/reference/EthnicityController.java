package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;
import org.studentsuccessplan.ssp.transferobject.reference.EthnicityTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/ethnicity")
public class EthnicityController extends
		AbstractAuditableReferenceController<Ethnicity, EthnicityTO> {

	@Autowired
	protected transient EthnicityService citizenshipService;

	@Override
	protected AuditableCrudService<Ethnicity> getService() {
		return citizenshipService;
	}

	protected EthnicityController() {
		super(Ethnicity.class, EthnicityTO.class);
	}
}
