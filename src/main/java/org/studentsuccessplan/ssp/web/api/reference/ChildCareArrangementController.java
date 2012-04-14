package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/childCareArrangement")
public class ChildCareArrangementController
		extends
		AbstractAuditableReferenceController<ChildCareArrangement, ChildCareArrangementTO> {

	@Autowired
	protected transient ChildCareArrangementService service;

	@Override
	protected AuditableCrudService<ChildCareArrangement> getService() {
		return service;
	}

	protected ChildCareArrangementController() {
		super(ChildCareArrangement.class, ChildCareArrangementTO.class);
	}
}
