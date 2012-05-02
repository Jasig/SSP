package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

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
