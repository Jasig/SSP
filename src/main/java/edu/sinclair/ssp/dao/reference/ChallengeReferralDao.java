package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import edu.sinclair.mygps.util.Constants;
import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.ChallengeReferral;

/**
 * Data access class for the ChallengeReferral reference entity.
 */
@Repository
public class ChallengeReferralDao extends
		ReferenceAuditableCrudDao<ChallengeReferral> implements
		AuditableCrudDao<ChallengeReferral> {

	public ChallengeReferralDao() {
		super(ChallengeReferral.class);
	}

	@SuppressWarnings("unchecked")
	public List<ChallengeReferral> selectBySelfHelpGuideResponseIdAndChallengeId(
			UUID selfHelpGuideResponseId,
			UUID challengeId) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"inner join ccr.challenge c " +
						"inner join c.selfHelpGuideChallengeResponses shgcr " +
						"where shgcr.selfHelpGuideResponse.id = ? " +
						"and c.id = ? " +
						"and cr.showInSelfHelpGuide = 1")
						.setParameter(0, selfHelpGuideResponseId)
						.setParameter(1, challengeId)
						.list();

	}

	@SuppressWarnings("unchecked")
	public List<ChallengeReferral> selectByChallenge(UUID challengeId) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = 1 " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus.id = ? " +
						"order by cr.name")
						.setParameter(0, challengeId)
						.setParameter(1, ObjectStatus.ACTIVE)
						.setMaxResults(Constants.RESULT_COUNT_LIMIT)
						.list();
	}

	public List<ChallengeReferral> searchByChallenge(UUID challengeId) {
		return searchByChallengeAndQuery(challengeId, "");
	}

	@SuppressWarnings("unchecked")
	public List<ChallengeReferral> searchByChallengeAndQuery(
			UUID challengeId, String query) {
		query = "%" + query.toUpperCase() + "%";

		return this.sessionFactory.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"inner join ccr.challenge c " +
						"where cr.showInSelfHelpGuide = true " +
						"and c.showInSelfHelpSearch = true " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus.id = ? " +
						"and (upper(cr.name) like ? " +
						"or upper(cr.publicDescription) like ?) " +
						"order by cr.name")
						.setParameter(0, challengeId)
						.setParameter(1, ObjectStatus.ACTIVE)
						.setParameter(2, query)
						.setParameter(3, query)
						.setMaxResults(Constants.SEARCH_RESULT_COUNT_LIMIT)
						.list();
	}

	public long selectCountByChallengeNotOnActiveTaskList(UUID challengeId,
			Person student, String sessionId) {

		return (Long) this.sessionFactory.getCurrentSession()
				.createQuery("select count(cr) " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = true " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus.id = 1 " +
						"and not exists " +
						"(from ActionPlanTask " +
						"where challengeReferral.id = cr.id " +
						"and objectStatus.id = 1 " +
						"and person.id = ? " +
						"and sessionId = " +
						"case " +
						"when person.id != ? " +
						"then sessionId " +
						"else " +
						"? " +
						"end)")
						.setParameter(0, challengeId)
				.setParameter(1, student.getId())
						.setParameter(2, Constants.ANONYMOUS_PERSON_ID)
				.setParameter(3, sessionId)
						.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ChallengeReferral> selectByChallengeNotOnActiveTaskList(
			UUID challengeId, Person student, String sessionId) {

		return this.sessionFactory.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = true " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus.id = 1 " +
						"and not exists " +
						"(from ActionPlanTask " +
						"where challengeReferral.id = cr.id " +
						"and objectStatus.id = 1 " +
						"and person.id = ? " +
						"and sessionId = " +
						"case " +
						"when person.id != ? " +
						"then sessionId " +
						"else " +
						"? " +
						"end)")
						.setParameter(0, challengeId)
						.setParameter(1, student.getId())
						.setParameter(2, Constants.ANONYMOUS_PERSON_ID)
						.setParameter(3, sessionId)
						.list();
	}


}
