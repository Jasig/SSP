package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChallengeReferral;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

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
