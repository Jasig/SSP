package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface SelfHelpGuideGroupService extends AuditableCrudService<SelfHelpGuideGroup> {

	@Override
	public List<SelfHelpGuideGroup> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public SelfHelpGuideGroup get(UUID id) throws ObjectNotFoundException;

	@Override
	public SelfHelpGuideGroup create(SelfHelpGuideGroup obj);

	@Override
	public SelfHelpGuideGroup save(SelfHelpGuideGroup obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
