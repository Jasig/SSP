package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChallengeCategory;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ChallengeCategoryService extends AuditableCrudService<ChallengeCategory> {

	@Override
	public List<ChallengeCategory> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public ChallengeCategory get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChallengeCategory create(ChallengeCategory obj);

	@Override
	public ChallengeCategory save(ChallengeCategory obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
