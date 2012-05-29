package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.StudentTypeTOFactory;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reference.StudentTypeTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StudentType controller
 * 
 * @author jon.adams
 * 
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/studentType")
public class StudentTypeController
		extends
		AbstractAuditableReferenceController<StudentType, StudentTypeTO> {

	@Autowired
	protected transient StudentTypeService service;

	@Override
	protected AuditableCrudService<StudentType> getService() {
		return service;
	}

	@Autowired
	protected transient StudentTypeTOFactory factory;

	@Override
	protected TOFactory<StudentTypeTO, StudentType> getFactory() {
		return factory;
	}

	protected StudentTypeController() {
		super(StudentType.class, StudentTypeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentTypeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}