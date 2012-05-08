package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

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

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuideResponse> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

}
