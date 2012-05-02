package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.security.SspUser;

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
	public List<ChallengeReferral> byChallengeId(final UUID challengeId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "where cr.showInSelfHelpGuide = true "
								+ "and ccr.challenge.id = ? "
								+ "and cr.objectStatus = ? "
								+ "order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE).setMaxResults(100).list();
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdAndQuery(
			final UUID challengeId,
			final String query) {
		final String wildcardQuery = "%" + query.toUpperCase() + "%";

		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "inner join ccr.challenge c "
								+ "where cr.showInSelfHelpGuide = true "
								+ "and c.showInSelfHelpSearch = true "
								+ "and ccr.challenge.id = ? "
								+ "and cr.objectStatus = ? "
								+ "and (upper(cr.name) like ? "
								+ "or upper(cr.publicDescription) like ?) "
								+ "order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE)
				.setParameter(2, wildcardQuery)
				.setParameter(3, wildcardQuery).setMaxResults(100).list();
	}

	public long countByChallengeIdNotOnActiveTaskList(final UUID challengeId,
			final Person student, final String sessionId) {

		return (Long) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select count(cr) "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "where cr.showInSelfHelpGuide = true "
								+ "and ccr.challenge.id = :challengeId "
								+ "and cr.objectStatus = :objectStatus "
								+ "and not exists (from Task "
								+ "where challengeReferral.id = cr.id "
								+ "and objectStatus = :objectStatus "
								+ "and person.id = :studentId "
								+ "and sessionId = case "
								+ "when person.id != :anonPersonId "
								+ "then sessionId else :webSessionId end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", SspUser.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			UUID challengeId, Person student, String sessionId) {

		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "where cr.showInSelfHelpGuide = true "
								+ "and ccr.challenge.id = :challengeId "
								+ "and cr.objectStatus = :objectStatus "
								+ "and not exists " + "(from Task "
								+ "where challengeReferral.id = cr.id "
								+ "and objectStatus = :objectStatus "
								+ "and person.id = :studentId "
								+ "and sessionId = " + "case "
								+ "when person.id != :anonPersonId "
								+ "then sessionId " + "else "
								+ ":webSessionId " + "end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", SspUser.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
	}
}
