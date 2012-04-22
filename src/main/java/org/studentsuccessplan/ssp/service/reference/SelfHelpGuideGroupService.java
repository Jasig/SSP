package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
