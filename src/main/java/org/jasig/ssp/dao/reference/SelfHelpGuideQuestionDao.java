package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the SelfHelpGuideQuestion reference entity.
 */
@Repository
public class SelfHelpGuideQuestionDao extends
		AbstractReferenceAuditableCrudDao<SelfHelpGuideQuestion> implements
		AuditableCrudDao<SelfHelpGuideQuestion> {

	public SelfHelpGuideQuestionDao() {
		super(SelfHelpGuideQuestion.class);
	}
}
