package org.jasig.ssp.dao;

import java.util.List;

import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.security.SspUser;
import org.springframework.stereotype.Repository;

/**
 * SelfHelpGuideResponse data access methods
 */
@Repository
public class SelfHelpGuideResponseDao
		extends AbstractPersonAssocAuditableCrudDao<SelfHelpGuideResponse>
		implements PersonAssocAuditableCrudDao<SelfHelpGuideResponse> {

	/**
	 * Construct an instance with the specific types for use by super class
	 * methods.
	 */
	public SelfHelpGuideResponseDao() {
		super(SelfHelpGuideResponse.class);
	}

	/**
	 * Get all SelfHelpGuideResponses, for all non-anonymous users, that exceed
	 * the defined threshold.
	 * 
	 * @return List of all applicable SelfHelpGuideResponses, for all
	 *         non-anonymous users.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuideResponse> forEarlyAlert() {
		return sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideResponse shgr " +
						"where shgr.selfHelpGuide.threshold > 0 " +
						"and shgr.selfHelpGuide.threshold < " +
						"(select count(*) " +
						"from SelfHelpGuideQuestionResponse " +
						"where response = true " +
						"and selfHelpGuideResponse.id = shgr.id) " +
						"and shgr.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
