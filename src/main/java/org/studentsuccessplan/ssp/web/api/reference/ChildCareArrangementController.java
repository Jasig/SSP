package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/childCareArrangement")
public class ChildCareArrangementController extends
		AbstractAuditableReferenceController<ChildCareArrangement, ChildCareArrangementTO> {

	@Autowired
	protected ChildCareArrangementController(ChildCareArrangementService service) {
		super(service, ChildCareArrangement.class, ChildCareArrangementTO.class);
	}
}
