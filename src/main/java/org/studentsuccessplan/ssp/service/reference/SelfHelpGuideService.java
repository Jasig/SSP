package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface SelfHelpGuideService extends
		AuditableCrudService<SelfHelpGuide> {

	@Override
	List<SelfHelpGuide> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	SelfHelpGuide get(UUID id) throws ObjectNotFoundException;

	@Override
	SelfHelpGuide create(SelfHelpGuide obj);

	@Override
	SelfHelpGuide save(SelfHelpGuide obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	List<SelfHelpGuide> getBySelfHelpGuideGroup(
			SelfHelpGuideGroup selfHelpGuideGroup);
}
