package org.studentsuccessplan.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

/**
 * Challenge controller responds to common REST requests with
 * {@link ChallengeTO} instances in JSON format.
 * <p>
 * Requires basic user authentication.
 * 
 * @author jon.adams
 * 
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challenge")
public class ChallengeController extends
		AbstractAuditableReferenceController<Challenge, ChallengeTO> {

	@Autowired
	protected transient ChallengeService citizenshipService;

	@Override
	protected AuditableCrudService<Challenge> getService() {
		return citizenshipService;
	}

	protected ChallengeController() {
		super(Challenge.class, ChallengeTO.class);
	}
}