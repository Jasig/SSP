package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.MessageTemplateTOFactory;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;

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

}
