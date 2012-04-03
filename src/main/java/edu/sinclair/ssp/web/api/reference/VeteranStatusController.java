package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.reference.VeteranStatusService;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/veteranStatus")
public class VeteranStatusController extends
		AbstractAuditableReferenceController<VeteranStatus, VeteranStatusTO> {

	@Autowired
	protected VeteranStatusController(VeteranStatusService service) {
		super(service, VeteranStatus.class, VeteranStatusTO.class);
	}
}
