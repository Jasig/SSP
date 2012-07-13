package org.jasig.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the SelfHelpGuide reference entity.
 */
@Repository
public class SelfHelpGuideDao extends
		AbstractReferenceAuditableCrudDao<SelfHelpGuide>
		implements AuditableCrudDao<SelfHelpGuide> {

	public SelfHelpGuideDao() {
		super(SelfHelpGuide.class);
	}

	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuide> findAllActiveForUnauthenticated() {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SelfHelpGuide " + "where objectStatus = ? "
								+ "and authenticationRequired = false "
								+ "order by name")
				.setParameter(0, ObjectStatus.ACTIVE).list();
	}

	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuide> findAllActiveBySelfHelpGuideGroup(
			final UUID selfHelpGuideGroupId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select shg " + "from SelfHelpGuide shg "
								+ "inner join shg.selfHelpGuideGroups shgg "
								+ "where shgg.id = ? "
								+ "and shg.objectStatus = ? "
								+ "order by shg.name")
				.setParameter(0, selfHelpGuideGroupId)
				.setParameter(1, ObjectStatus.ACTIVE).list();
	}
}