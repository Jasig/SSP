package org.studentsuccessplan.ssp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.model.SelfHelpGuideResponse;
import org.studentsuccessplan.ssp.security.SspUser;

@Repository
public class SelfHelpGuideResponseDao extends
		AbstractAuditableCrudDao<SelfHelpGuideResponse> implements
		AuditableCrudDao<SelfHelpGuideResponse> {

	public SelfHelpGuideResponseDao() {
		super(SelfHelpGuideResponse.class);
	}

	@SuppressWarnings("unchecked")
	// :TODO should be limited? paging?
	public List<SelfHelpGuideResponse> forEarlyAlert() {
		return this.sessionFactory.getCurrentSession()
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
