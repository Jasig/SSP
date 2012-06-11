package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.MessageTemplateTOFactory;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MessageTemplate transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class MessageTemplateTOFactoryImpl extends
		AbstractReferenceTOFactory<MessageTemplateTO, MessageTemplate>
		implements MessageTemplateTOFactory {

	public MessageTemplateTOFactoryImpl() {
		super(MessageTemplateTO.class, MessageTemplate.class);
	}

	@Autowired
	private transient MessageTemplateDao dao;

	@Override
	protected MessageTemplateDao getDao() {
		return dao;
	}

	@Override
	public MessageTemplate from(final MessageTemplateTO tObject)
			throws ObjectNotFoundException {
		final MessageTemplate model = super.from(tObject);

		model.setSubject(tObject.getSubject());
		model.setBody(tObject.getBody());

		return model;
	}
}