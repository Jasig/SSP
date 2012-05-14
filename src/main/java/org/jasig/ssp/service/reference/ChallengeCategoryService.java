package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ChallengeCategoryService extends
		AuditableCrudService<ChallengeCategory> {

	@Override
	PagingWrapper<ChallengeCategory> getAll(SortingAndPaging sAndP);

	@Override
	ChallengeCategory get(UUID id) throws ObjectNotFoundException;

	@Override
	ChallengeCategory create(ChallengeCategory obj)
			throws ObjectNotFoundException;

	@Override
	ChallengeCategory save(ChallengeCategory obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
