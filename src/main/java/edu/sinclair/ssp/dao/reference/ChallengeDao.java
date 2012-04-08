package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Challenge;

/**
 * Data access class for the Challenge reference entity.
 */
@Repository
public class ChallengeDao extends ReferenceAuditableCrudDao<Challenge>
		implements AuditableCrudDao<Challenge> {

	public ChallengeDao() {
		super(Challenge.class);
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<Challenge> selectAffirmativeBySelfHelpGuideResponseId(
			final UUID selfHelpGuideResponseId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+ "from Challenge c "
								+ "inner join c.selfHelpGuideQuestions shgq "
								+ "inner join shgq.selfHelpGuideQuestionResponses shgqr "
								+ "where shgqr.response = true "
								+ "and shgqr.selfHelpGuideResponse.id = ? "
								+ "order by c.name")
				.setParameter(0, selfHelpGuideResponseId)
				.list();
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<Challenge> searchByQuery(final String query) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+ "from Challenge c "
								+ "inner join c.challengeChallengeReferrals ccr "
								+ "where c.objectStatus = :objectStatus "
								+ "and c.showInSelfHelpSearch = true "
								+ "and (upper(c.name) like :query "
								+ "or upper(c.selfHelpGuideQuestion) like :query "
								+ "or upper(c.selfHelpGuideDescription) like :query "
								+ "or upper(c.tags) like :query) "
								+ "and exists " + "(from ChallengeReferral "
								+ "where id = ccr.challengeReferral.id "
								+ "and showInSelfHelpGuide = true "
								+ "and objectStatus = :objectStatus) "
								+ "order by c.name")
				.setParameter("query",
						"%" + query.toUpperCase(Locale.getDefault()) + "%")
				.setParameter("objectStatus", ObjectStatus.ACTIVE)
				.list();

	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<Challenge> getAllInStudentIntake() {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(Challenge.class)
				.add(Restrictions.eq("showInStudentIntake", true));
		return query.list();
	}

}
