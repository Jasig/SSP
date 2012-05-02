package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.StudentStatusTOFactory;
import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.transferobject.reference.StudentStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/studentStatus")
public class StudentStatusController
		extends
		AbstractAuditableReferenceController<StudentStatus, StudentStatusTO> {

	@Autowired
	protected transient StudentStatusService service;

	@Override
	protected AuditableCrudService<StudentStatus> getService() {
		return service;
	}

	@Autowired
	protected transient StudentStatusTOFactory factory;

	@Override
	protected TOFactory<StudentStatusTO, StudentStatus> getFactory() {
		return factory;
	}

	protected StudentStatusController() {
		super(StudentStatus.class, StudentStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentStatusController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
