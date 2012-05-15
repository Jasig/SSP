package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * SelfHelpGuideResponse data access methods
 */
@Repository
public class SelfHelpGuideResponseDao extends
		AbstractAuditableCrudDao<SelfHelpGuideResponse> implements
		AuditableCrudDao<SelfHelpGuideResponse> {

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
	@SuppressWarnings("unchecked")
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

	/**
	 * Get all SelfHelpGuideResponses for the specified person.
	 * 
	 * @param personId
	 *            Person identifier
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All SelfHelpGuideResponses for the specified person, filtered by
	 *         the specified sorting and paging option.
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<SelfHelpGuideResponse> getAllForPersonId(
			final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<SelfHelpGuideResponse>(totalRows,
				createCriteria(sAndP)
						.add(Restrictions.eq("person.id", personId))
						.list());
	}
}
