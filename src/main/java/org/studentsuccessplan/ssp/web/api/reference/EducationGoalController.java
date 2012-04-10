package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;
import org.studentsuccessplan.ssp.transferobject.reference.EducationGoalTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/educationGoal")
public class EducationGoalController extends
		AbstractAuditableReferenceController<EducationGoal, EducationGoalTO> {

	@Autowired
	protected EducationGoalController(EducationGoalService service) {
		super(service, EducationGoal.class, EducationGoalTO.class);
	}
}
