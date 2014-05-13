package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.ScheduledApplicationTaskStatusDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.ScheduledApplicationTaskStatusTOFactory;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.ScheduledApplicationTaskStatusTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ScheduledApplicationTaskStatusTOFactoryImpl
		extends
		AbstractAuditableTOFactory<ScheduledApplicationTaskStatusTO, ScheduledApplicationTaskStatus>
		implements ScheduledApplicationTaskStatusTOFactory {
	
	@Autowired
	private transient ScheduledApplicationTaskStatusDao dao;
	
	public ScheduledApplicationTaskStatusTOFactoryImpl() {
		super(ScheduledApplicationTaskStatusTO.class, ScheduledApplicationTaskStatus.class);
	}

	@Override
	public ScheduledApplicationTaskStatus from(final ScheduledApplicationTaskStatusTO tObject) throws ObjectNotFoundException {
		final ScheduledApplicationTaskStatus model = super.from(tObject);
		model.setStartDate(tObject.getStartDate());
		model.setCompletedDate(tObject.getCompletedDate());
		model.setStatus(tObject.getStatus());
		model.setTaskName(tObject.getTaskName());
		return model;
	}

	@Override
	protected ScheduledApplicationTaskStatusDao getDao() {
		return dao;
	}

}
