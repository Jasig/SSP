package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MessageTemplate;

/**
 * Data access class for the MessageTemplate reference entity.
 */
@Repository
public class MessageTemplateDao extends ReferenceAuditableCrudDao<MessageTemplate>
implements AuditableCrudDao<MessageTemplate> {

	public MessageTemplateDao() {
		super(MessageTemplate.class);
	}

	@SuppressWarnings("unchecked")
	public List<MessageTemplate> selectAffirmativeBySelfHelpGuideResponseId(
			UUID selfHelpGuideResponseId) {
		return this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+
								"from MessageTemplate c "
								+
								"inner join c.selfHelpGuideQuestions shgq "
								+
								"inner join shgq.selfHelpGuideQuestionResponses shgqr "
								+
								"where shgqr.response = 1 " +
								"and shgqr.selfHelpGuideResponse.id = ? " +
						"order by c.name")
						.setParameter(0, selfHelpGuideResponseId)
						.list();
	}

	@SuppressWarnings("unchecked")
	public List<MessageTemplate> searchByQuery(String query) {
		query = "%" + query.toUpperCase() + "%";

		return this.sessionFactory.getCurrentSession()
				.createQuery("select distinct c " +
						"from MessageTemplate c " +
						"inner join c.messageTemplateMessageTemplateReferrals ccr " +
						"where c.objectStatus.id = :objectStatusId " +
						"and c.showInSelfHelpSearch = true " +
						"and (upper(c.name) like :query " +
						"or upper(c.selfHelpGuideQuestion) like :query " +
						"or upper(c.selfHelpGuideDescription) like :query " +
						"or upper(c.tags) like :query) " +
						"and exists " +
						"(from MessageTemplateReferral " +
						"where id = ccr.messageTemplateReferral.id " +
						"and showInSelfHelpGuide = true " +
						"and objectStatus.id = :objectStatusId) " +
						"order by c.name")
						.setParameter("query", query)
						.setParameter("objectStatusId", ObjectStatus.ACTIVE)
						.list();

	}

	@SuppressWarnings("unchecked")
	public List<MessageTemplate> selectForStudentIntake() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from MessageTemplate " +
						"where showInStudentIntake = true " +
						"order by name")
						.list();
	}

}
