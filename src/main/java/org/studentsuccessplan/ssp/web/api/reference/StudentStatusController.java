package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.service.reference.StudentStatusService;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/studentStatus")
public class StudentStatusController extends
		AbstractAuditableReferenceController<StudentStatus, StudentStatusTO> {

	@Autowired
	protected StudentStatusController(StudentStatusService service) {
		super(service, StudentStatus.class, StudentStatusTO.class);
	}
}
