package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuideGroup;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

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
