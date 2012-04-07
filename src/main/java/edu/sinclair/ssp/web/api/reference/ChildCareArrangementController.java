package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.reference.ChildCareArrangementService;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;

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
