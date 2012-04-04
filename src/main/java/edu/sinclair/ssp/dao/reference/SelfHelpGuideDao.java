package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.SelfHelpGuide;

/**
 * Data access class for the SelfHelpGuide reference entity.
 */
@Repository
public class SelfHelpGuideDao extends
		ReferenceAuditableCrudDao<SelfHelpGuide> implements
AuditableCrudDao<SelfHelpGuide> {

	public SelfHelpGuideDao() {
		super(SelfHelpGuide.class);
	}

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuide> findAllActiveForUnauthenticated() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuide " +
						"where objectStatus.id = ? " +
						"and authenticationRequired = false " +
						"order by name")
				.setParameter(0, ObjectStatus.ACTIVE)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuide> selectActiveBySelfHelpGuideGroup(
			UUID selfHelpGuideGroupId) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select shg " +
						"from SelfHelpGuide shg " +
						"inner join shg.selfHelpGuideGroups shgg " +
						"where shgg.id = ? " +
						"and shg.objectStatus.id = ? " +
						"order by shg.name")
				.setParameter(0, selfHelpGuideGroupId)
				.setParameter(1, ObjectStatus.ACTIVE)
				.list();
	}

}
