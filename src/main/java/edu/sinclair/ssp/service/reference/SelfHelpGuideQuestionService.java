package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface SelfHelpGuideQuestionService extends AuditableCrudService<SelfHelpGuideQuestion> {

	@Override
	public List<SelfHelpGuideQuestion> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public SelfHelpGuideQuestion get(UUID id) throws ObjectNotFoundException;

	@Override
	public SelfHelpGuideQuestion create(SelfHelpGuideQuestion obj);

	@Override
	public SelfHelpGuideQuestion save(SelfHelpGuideQuestion obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
