package org.jasig.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the ChallengeReferral reference entity.
 */
@Repository
public class ChallengeReferralDao extends
		AbstractReferenceAuditableCrudDao<ChallengeReferral> implements
		AuditableCrudDao<ChallengeReferral> {

	public ChallengeReferralDao() {
		super(ChallengeReferral.class);
	}

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeId(final UUID challengeId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr " // NOPMD
								+ "inner join cr.challengeChallengeReferrals ccr " // NOPMD
								+ "where cr.showInSelfHelpGuide = true " // NOPMD
								+ "and ccr.challenge.id = ? "
								+ "and cr.objectStatus = ? "
								+ "order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE).setMaxResults(100).list();
	}

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdAndQuery(
			final UUID challengeId,
			final String query) {
		final String wildcardQuery = "%" + query.toUpperCase() + "%"; // NOPMD

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

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			final UUID challengeId, final Person student, final String sessionId) {

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

	public PagingWrapper<ChallengeReferral> getAllForChallenge(
			final UUID challengeId, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		final Criteria subQuery = query
				.createCriteria("challengeChallengeReferrals");
		subQuery.add(Restrictions.eq("challenge.id", challengeId));
		sAndP.addStatusFilterToCriteria(subQuery);

		return processCriteriaWithPaging(query, sAndP);
	}
}
