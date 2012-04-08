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
	// :TODO paging?
	public List<ChallengeReferral> byChallengeId(UUID challengeId) {
		return this.sessionFactory
				.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = true " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus = ? " +
						"order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE)
				.setMaxResults(Constants.RESULT_COUNT_LIMIT)
				.list();
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdAndQuery(
			UUID challengeId, String query) {
		query = "%" + query.toUpperCase() + "%";

		return this.sessionFactory
				.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"inner join ccr.challenge c " +
						"where cr.showInSelfHelpGuide = true " +
						"and c.showInSelfHelpSearch = true " +
						"and ccr.challenge.id = ? " +
						"and cr.objectStatus = ? " +
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

	public long countByChallengeIdNotOnActiveTaskList(UUID challengeId,
			Person student, String sessionId) {

		return (Long) this.sessionFactory
				.getCurrentSession()
				.createQuery("select count(cr) " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = true " +
						"and ccr.challenge.id = :challengeId " +
						"and cr.objectStatus = :objectStatus " +
						"and not exists " +
						"(from Task " +
						"where challengeReferral.id = cr.id " +
						"and objectStatus = :objectStatus " +
						"and person.id = :studentId " +
						"and sessionId = " +
						"case " +
						"when person.id != :anonPersonId " +
						"then sessionId " +
						"else " +
						":webSessionId " +
						"end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", Constants.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			UUID challengeId, Person student, String sessionId) {

		return this.sessionFactory
				.getCurrentSession()
				.createQuery("select cr " +
						"from ChallengeReferral cr " +
						"inner join cr.challengeChallengeReferrals ccr " +
						"where cr.showInSelfHelpGuide = true " +
						"and ccr.challenge.id = :challengeId " +
						"and cr.objectStatus = :objectStatus " +
						"and not exists " +
						"(from Task " +
						"where challengeReferral.id = cr.id " +
						"and objectStatus = :objectStatus " +
						"and person.id = :studentId " +
						"and sessionId = " +
						"case " +
						"when person.id != :anonPersonId " +
						"then sessionId " +
						"else " +
						":webSessionId " +
						"end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", Constants.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE)
				.list();
	}

}
