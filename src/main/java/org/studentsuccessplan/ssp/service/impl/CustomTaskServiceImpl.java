package org.studentsuccessplan.ssp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studentsuccessplan.ssp.dao.CustomTaskDao;
import org.studentsuccessplan.ssp.model.CustomTask;
import org.studentsuccessplan.ssp.service.CustomTaskService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

@Service
public class CustomTaskServiceImpl
		extends AbstractAbstractTaskService<CustomTask>
		implements CustomTaskService {

	@Autowired
	private CustomTaskDao dao;

	@Override
	protected CustomTaskDao getDao() {
		return dao;
	}

	@Override
	public CustomTask save(CustomTask obj) throws ObjectNotFoundException {
		CustomTask current = getDao().get(obj.getId());

		current.setName(obj.getName());

		current.setCompletedDate(obj.getCompletedDate());
		current.setDescription(obj.getDescription());
		current.setDueDate(obj.getDueDate());
		current.setObjectStatus(obj.getObjectStatus());
		current.setPerson(obj.getPerson());
		current.setReminderSentDate(obj.getReminderSentDate());
		current.setSessionId(obj.getSessionId());

		return getDao().save(current);
	}

}
