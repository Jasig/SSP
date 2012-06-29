package org.jasig.ssp.dao;

import java.util.List;

import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.jasig.ssp.security.SspUser;
import org.springframework.stereotype.Repository;

/**
 * SelfHelpGuideQuestionResponse data access methods
 */
@Repository
public class SelfHelpGuideQuestionResponseDao extends
		AbstractAuditableCrudDao<SelfHelpGuideQuestionResponse> implements
		AuditableCrudDao<SelfHelpGuideQuestionResponse> {

	/**
	 * Construct an instance with the specific types for use by super class
	 * methods.
	 */
	public SelfHelpGuideQuestionResponseDao() {
		super(SelfHelpGuideQuestionResponse.class);
	}

	/**
	 * Find all critical questions answered in the Self-Help Guide, by any
	 * non-anonymous users, that have not been sent.
	 * 
	 * @return List of SelfHelpGuideQuestionResponse responses that need Early
	 *         Alerts sent
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuideQuestionResponse> criticalResponsesForEarlyAlert() {
		return sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideQuestionResponse " +
						"where earlyAlertSent = false " +
						"and response = true " +
						"and selfHelpGuideQuestion.critical = true " +
						"and selfHelpGuideResponse.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
