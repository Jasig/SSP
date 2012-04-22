package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface SelfHelpGuideQuestionService extends
		AuditableCrudService<SelfHelpGuideQuestion> {

	@Override
	public PagingWrapper<SelfHelpGuideQuestion> getAll(SortingAndPaging sAndP);

	@Override
	public SelfHelpGuideQuestion get(UUID id) throws ObjectNotFoundException;

	@Override
	public SelfHelpGuideQuestion create(SelfHelpGuideQuestion obj);

	@Override
	public SelfHelpGuideQuestion save(SelfHelpGuideQuestion obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
