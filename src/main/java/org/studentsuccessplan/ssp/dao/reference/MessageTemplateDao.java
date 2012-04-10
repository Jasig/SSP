package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;

/**
 * Data access class for the MessageTemplate reference entity.
 */
@Repository
public class MessageTemplateDao extends
		ReferenceAuditableCrudDao<MessageTemplate>
		implements AuditableCrudDao<MessageTemplate> {

	public MessageTemplateDao() {
		super(MessageTemplate.class);
	}
}
