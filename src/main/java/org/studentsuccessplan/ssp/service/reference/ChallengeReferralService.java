package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface ChallengeReferralService extends AuditableCrudService<ChallengeReferral> {

	@Override
	public List<ChallengeReferral> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public ChallengeReferral get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChallengeReferral create(ChallengeReferral obj);

	@Override
	public ChallengeReferral save(ChallengeReferral obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
