package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

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
