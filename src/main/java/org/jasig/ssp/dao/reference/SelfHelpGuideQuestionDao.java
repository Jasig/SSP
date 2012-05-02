package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;

/**
 * Data access class for the SelfHelpGuideQuestion reference entity.
 */
@Repository
public class SelfHelpGuideQuestionDao extends
		ReferenceAuditableCrudDao<SelfHelpGuideQuestion> implements
		AuditableCrudDao<SelfHelpGuideQuestion> {

	public SelfHelpGuideQuestionDao() {
		super(SelfHelpGuideQuestion.class);
	}

	@SuppressWarnings("unchecked")
	// :TODO paging
	public List<SelfHelpGuideQuestion> bySelfHelpGuide(UUID selfHelpGuideId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SelfHelpGuideQuestion "
								+ "where selfHelpGuide.id = ? "
								+ "and objectStatus = ? "
								+ "order by questionNumber")
				.setParameter(0, selfHelpGuideId)
				.setParameter(1, ObjectStatus.ACTIVE).list();
	}
}
