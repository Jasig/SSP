package edu.sinclair.ssp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.SelfHelpGuideQuestionResponse;
import edu.sinclair.ssp.security.SspUser;

@Repository
public class SelfHelpGuideQuestionResponseDao extends
		AbstractAuditableCrudDao<SelfHelpGuideQuestionResponse> implements
		AuditableCrudDao<SelfHelpGuideQuestionResponse> {

	public SelfHelpGuideQuestionResponseDao() {
		super(SelfHelpGuideQuestionResponse.class);
	}

	@SuppressWarnings("unchecked")
	// :TODO should be limited? paging?
	public List<SelfHelpGuideQuestionResponse> criticalResponsesForEarlyAlert() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideQuestionResponse " +
						"where earlyAlertSent = false " +
						"and response = true " +
						"and selfHelpGuideQuestion.critical = true " +
						"and selfHelpGuideResponse.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
