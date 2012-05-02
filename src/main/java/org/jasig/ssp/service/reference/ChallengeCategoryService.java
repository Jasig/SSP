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
	public PagingWrapper<ChallengeCategory> getAll(SortingAndPaging sAndP);

	@Override
	public ChallengeCategory get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChallengeCategory create(ChallengeCategory obj);

	@Override
	public ChallengeCategory save(ChallengeCategory obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
