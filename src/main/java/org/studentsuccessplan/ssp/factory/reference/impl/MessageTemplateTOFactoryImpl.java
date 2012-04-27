package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.MessageTemplateDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.MessageTemplateTOFactory;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.transferobject.reference.MessageTemplateTO;

@Service
@Transactional(readOnly = true)
public class MessageTemplateTOFactoryImpl extends
		AbstractReferenceTOFactory<MessageTemplateTO, MessageTemplate>
		implements MessageTemplateTOFactory {

	public MessageTemplateTOFactoryImpl() {
		super(MessageTemplateTO.class, MessageTemplate.class);
	}

	@Autowired
	private MessageTemplateDao dao;

	@Override
	protected MessageTemplateDao getDao() {
		return dao;
	}

}
