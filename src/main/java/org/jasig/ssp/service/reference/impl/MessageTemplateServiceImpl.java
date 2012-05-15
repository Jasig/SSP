package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageTemplateServiceImpl extends
		AbstractReferenceService<MessageTemplate>
		implements MessageTemplateService {

	@Autowired
	transient private MessageTemplateDao dao;

	protected void setDao(final MessageTemplateDao dao) {
		this.dao = dao;
	}

	@Override
	protected MessageTemplateDao getDao() {
		return dao;
	}
}
