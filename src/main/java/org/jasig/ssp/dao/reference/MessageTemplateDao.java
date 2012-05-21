package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.MessageTemplate;

/**
 * Data access class for the MessageTemplate reference entity.
 */
@Repository
public class MessageTemplateDao extends
		AbstractReferenceAuditableCrudDao<MessageTemplate>
		implements AuditableCrudDao<MessageTemplate> {

	public MessageTemplateDao() {
		super(MessageTemplate.class);
	}
}
