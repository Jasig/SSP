package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface SelfHelpGuideGroupService extends
		AuditableCrudService<SelfHelpGuideGroup> {

	@Override
	public PagingWrapper<SelfHelpGuideGroup> getAll(SortingAndPaging sAndP);

	@Override
	public SelfHelpGuideGroup get(UUID id) throws ObjectNotFoundException;

	@Override
	public SelfHelpGuideGroup create(SelfHelpGuideGroup obj);

	@Override
	public SelfHelpGuideGroup save(SelfHelpGuideGroup obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
