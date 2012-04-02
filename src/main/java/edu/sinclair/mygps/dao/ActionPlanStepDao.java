package edu.sinclair.mygps.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ActionPlanStepDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("#{configProperties.sspSecurityDatabaseName}")
	private String sspSecurityDatabaseName;

	public void setSspSecurityDatabaseName(String sspSecurityDatabaseName) {
		this.sspSecurityDatabaseName = sspSecurityDatabaseName;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> selectAllByPersonId(UUID personId) {

		return sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT aps.actionPlanStepID AS id, " +
						"c.name AS challengeName, " +
						"cr.id AS challengeReferralId, " +
						"cr.name AS challengeReferralName, " +
						"aps.actionDesc AS description, " +
						"aps.targetDate AS dueDate, " +
						"aps.completedDate AS completedDate, " +
						"s.person_id AS personId, " +
						"CASE " +
						"WHEN ui.fullName IS NOT NULL " +
						"THEN ui.fullName " +
						"ELSE " +
						"op.createdBy " +
						"END AS createdBy " +
						"FROM ActionPlanStep aps " +
						"INNER JOIN ActionPlanChallenge apc ON aps.actionPlanChallengeID = apc.actionPlanChallengeID " +
						"INNER JOIN ActionPlan ap ON apc.actionPlanID = ap.actionPlanID " +
						"INNER JOIN ActionPlansByStudent apbs ON ap.actionPlanID = apbs.actionPlanID " +
						"INNER JOIN Student s ON apbs.studentID = s.studentID " +
						"INNER JOIN ChallengeReferral cr ON aps.challengeReferralLUID = CAST(cr.id AS VARCHAR(36)) " +
						"INNER JOIN Challenge c ON apc.challengeLUID = CAST(c.id AS VARCHAR(36)) " +
						"INNER JOIN ObjectProperties op ON ap.actionPlanID = op.objectID " +
						"LEFT JOIN " + sspSecurityDatabaseName + ".dbo.UserInfo ui ON op.createdBy = ui.networkUserID " +
						"WHERE s.person_id = ?")
						.addScalar("id")
						.addScalar("challengeName")
						.addScalar("challengeReferralId")
						.addScalar("challengeReferralName")
						.addScalar("description")
						.addScalar("dueDate")
						.addScalar("completedDate")
						.addScalar("personId")
						.addScalar("createdBy")
						.setParameter(0, personId)
						.list();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> selectAllIncompleteByPersonId(UUID personId) {

		return sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT aps.actionPlanStepID AS id, " +
						"c.name AS challengeName, " +
						"cr.id AS challengeReferralId, " +
						"cr.name AS challengeReferralName, " +
						"aps.actionDesc AS description, " +
						"aps.targetDate AS dueDate, " +
						"aps.completedDate AS completedDate, " +
						"s.person_id AS personId, " +
						"CASE " +
						"WHEN ui.fullName IS NOT NULL " +
						"THEN ui.fullName " +
						"ELSE " +
						"op.createdBy " +
						"END AS createdBy " +
						"FROM ActionPlanStep aps " +
						"INNER JOIN ActionPlanChallenge apc ON aps.actionPlanChallengeID = apc.actionPlanChallengeID " +
						"INNER JOIN ActionPlan ap ON apc.actionPlanID = ap.actionPlanID " +
						"INNER JOIN ActionPlansByStudent apbs ON ap.actionPlanID = apbs.actionPlanID " +
						"INNER JOIN Student s ON apbs.studentID = s.studentID " +
						"INNER JOIN ChallengeReferral cr ON aps.challengeReferralLUID = CAST(cr.id AS VARCHAR(36)) " +
						"INNER JOIN Challenge c ON apc.challengeLUID = CAST(c.id AS VARCHAR(36)) " +
						"INNER JOIN ObjectProperties op ON ap.actionPlanID = op.objectID " +
						"LEFT JOIN " + sspSecurityDatabaseName + ".dbo.UserInfo ui ON op.createdBy = ui.networkUserID " +
						"WHERE s.person_id = ? " +
						"AND aps.completedDate IS NULL")
						.addScalar("id")
						.addScalar("challengeName")
						.addScalar("challengeReferralId")
						.addScalar("challengeReferralName")
						.addScalar("description")
						.addScalar("dueDate")
						.addScalar("completedDate")
						.addScalar("personId")
						.addScalar("createdBy")
						.setParameter(0, personId)
						.list();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> selectAllIncompleteByPersonAndChallengeReferral(
			UUID personId, UUID challengeReferralId) {

		return sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT aps.actionPlanStepID AS id, " +
						"c.name AS challengeName, " +
						"cr.id AS challengeReferralId, " +
						"cr.name AS challengeReferralName, " +
						"aps.actionDesc AS description, " +
						"aps.targetDate AS dueDate, " +
						"aps.completedDate AS completedDate, " +
						"s.person_id AS personId, " +
						"CASE " +
						"WHEN ui.fullName IS NOT NULL " +
						"THEN ui.fullName " +
						"ELSE " +
						"op.createdBy " +
						"END AS createdBy " +
						"FROM ActionPlanStep aps " +
						"INNER JOIN ActionPlanChallenge apc ON aps.actionPlanChallengeID = apc.actionPlanChallengeID " +
						"INNER JOIN ActionPlan ap ON apc.actionPlanID = ap.actionPlanID " +
						"INNER JOIN ActionPlansByStudent apbs ON ap.actionPlanID = apbs.actionPlanID " +
						"INNER JOIN Student s ON apbs.studentID = s.studentID " +
						"INNER JOIN ChallengeReferral cr ON aps.challengeReferralLUID = CAST(cr.id AS VARCHAR(36)) " +
						"INNER JOIN Challenge c ON apc.challengeLUID = CAST(c.id AS VARCHAR(36)) " +
						"INNER JOIN ObjectProperties op ON ap.actionPlanID = op.objectID " +
						"LEFT JOIN " + sspSecurityDatabaseName + ".dbo.UserInfo ui ON op.createdBy = ui.networkUserID " +
						"WHERE s.person_id = ? " +
						"AND aps.challengeReferralLUID = ? " +
						"AND aps.completedDate IS NULL")
						.addScalar("id")
						.addScalar("challengeName")
						.addScalar("challengeReferralId")
						.addScalar("challengeReferralName")
						.addScalar("description")
						.addScalar("dueDate")
						.addScalar("completedDate")
						.addScalar("personId")
						.addScalar("createdBy")
						.setParameter(0, personId)
						.setParameter(1, challengeReferralId)
						.list();

	}


	@SuppressWarnings("unchecked")
	public List<Object[]> selectForReminderEmail() {

		return sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT aps.actionPlanStepID AS id, " +
						"c.name AS challengeName, " +
						"cr.id AS challengeReferralId, " +
						"cr.name AS challengeReferralName, " +
						"aps.actionDesc AS description, " +
						"aps.targetDate AS dueDate, " +
						"aps.completedDate AS completedDate, " +
						"s.person_id AS personId, " +
						"CASE " +
						"WHEN ui.fullName IS NOT NULL " +
						"THEN ui.fullName " +
						"ELSE " +
						"op.createdBy " +
						"END AS createdBy " +
						"FROM ActionPlanStep aps " +
						"INNER JOIN ActionPlanChallenge apc ON aps.actionPlanChallengeID = apc.actionPlanChallengeID " +
						"INNER JOIN ActionPlan ap ON apc.actionPlanID = ap.actionPlanID " +
						"INNER JOIN ActionPlansByStudent apbs ON ap.actionPlanID = apbs.actionPlanID " +
						"INNER JOIN Student s ON apbs.studentID = s.studentID " +
						"INNER JOIN ChallengeReferral cr ON aps.challengeReferralLUID = CAST(cr.id AS VARCHAR(36)) " +
						"INNER JOIN Challenge c ON apc.challengeLUID = CAST(c.id AS VARCHAR(36)) " +
						"INNER JOIN ObjectProperties op ON ap.actionPlanID = op.objectID " +
						"LEFT JOIN " + sspSecurityDatabaseName + ".dbo.UserInfo ui ON op.createdBy = ui.networkUserID " +
						"WHERE aps.targetDate IS NOT NULL " +
						"AND aps.targetDate > GETDATE() " +
						"AND aps.completedDate IS NULL " +
						"AND aps.reminderSentDate IS NULL")
						.addScalar("id")
						.addScalar("challengeName")
						.addScalar("challengeReferralId")
						.addScalar("challengeReferralName")
						.addScalar("description")
						.addScalar("dueDate")
						.addScalar("completedDate")
						.addScalar("personId")
						.addScalar("createdBy")
						.list();

	}

	@SuppressWarnings("unchecked")
	public Object[] selectById(UUID id) {

		List<Object[]> objects = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT aps.actionPlanStepID AS id, " +
						"c.name AS challengeName, " +
						"cr.id AS challengeReferralId, " +
						"cr.name AS challengeReferralName, " +
						"aps.actionDesc AS description, " +
						"aps.targetDate AS dueDate, " +
						"aps.completedDate AS completedDate, " +
						"s.person_id AS personId, " +
						"CASE " +
						"WHEN ui.fullName IS NOT NULL " +
						"THEN ui.fullName " +
						"ELSE " +
						"op.createdBy " +
						"END AS createdBy " +
						"FROM ActionPlanStep aps " +
						"INNER JOIN ActionPlanChallenge apc ON aps.actionPlanChallengeID = apc.actionPlanChallengeID " +
						"INNER JOIN ActionPlan ap ON apc.actionPlanID = ap.actionPlanID " +
						"INNER JOIN ActionPlansByStudent apbs ON ap.actionPlanID = apbs.actionPlanID " +
						"INNER JOIN Student s ON apbs.studentID = s.studentID " +
						"INNER JOIN ChallengeReferral cr ON aps.challengeReferralLUID = CAST(cr.id AS VARCHAR(36)) " +
						"INNER JOIN Challenge c ON apc.challengeLUID = CAST(c.id AS VARCHAR(36)) " +
						"INNER JOIN ObjectProperties op ON ap.actionPlanID = op.objectID " +
						"LEFT JOIN " + sspSecurityDatabaseName + ".dbo.UserInfo ui ON op.createdBy = ui.networkUserID " +
						"WHERE aps.actionPlanStepID = ?")
						.addScalar("id")
						.addScalar("challengeName")
						.addScalar("challengeReferralId")
						.addScalar("challengeReferralName")
						.addScalar("description")
						.addScalar("dueDate")
						.addScalar("completedDate")
						.addScalar("personId")
						.addScalar("createdBy")
						.setParameter(0, id)
						.list();

		if (objects.size() > 0) {
			return objects.get(0);
		} else {
			return null;
		}

	}

	public void saveAsComplete(UUID actionPlanStepId, String username) {

		this.sessionFactory.getCurrentSession()
		.createSQLQuery("UPDATE ActionPlanStep " +
				"SET outcome = 'C', " +
				"completedDate = GETDATE(), " +
				"completedBy = ? " +
				"WHERE actionPlanStepID = ?")
				.setParameter(0, username)
				.setParameter(1, actionPlanStepId)
				.executeUpdate();

	}

	public void saveAsIncomplete(UUID actionPlanStepId) {

		this.sessionFactory.getCurrentSession()
		.createSQLQuery("UPDATE ActionPlanStep " +
				"SET outcome = 'P', " +
				"completedDate = NULL, " +
				"completedBy = NULL " +
				"WHERE actionPlanStepID = ?")
				.setParameter(0, actionPlanStepId)
				.executeUpdate();

	}

	public void saveReminderSentDate(UUID actionPlanStepId) {

		this.sessionFactory.getCurrentSession()
		.createSQLQuery("UPDATE ActionPlanStep " +
				"SET reminderSentDate = GETDATE() " +
				"WHERE actionPlanStepID = ?")
				.setParameter(0, actionPlanStepId)
				.executeUpdate();

	}

}
