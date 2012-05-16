package org.jasig.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
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

	public SelfHelpGuide getWithQuestions(final UUID id) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(SelfHelpGuide.class)
				.add(Restrictions.eq("id", id))
				.setFetchMode("selfHelpGuideQuestions", FetchMode.JOIN);

		return (SelfHelpGuide) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuide> findAllActiveForUnauthenticated() {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SelfHelpGuide " + "where objectStatus = ? "
								+ "and authenticationRequired = false "
								+ "order by name")
				.setParameter(0, ObjectStatus.ACTIVE).list();
	}

	@SuppressWarnings("unchecked")
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