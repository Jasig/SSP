package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.MessageTemplate;

/**
 * Data access class for the MessageTemplate reference entity.
 */
@Repository
public class MessageTemplateDao extends
		ReferenceAuditableCrudDao<MessageTemplate> implements
AuditableCrudDao<MessageTemplate> {

	public MessageTemplateDao() {
		super(MessageTemplate.class);
	}
}
