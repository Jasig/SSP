package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.EarlyAlertRoutingDao;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertRouting service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertRoutingServiceImpl extends
		AbstractAuditableCrudService<EarlyAlertRouting>
		implements EarlyAlertRoutingService {

	@Autowired
	transient private EarlyAlertRoutingDao dao;

	@Override
	protected EarlyAlertRoutingDao getDao() {
		return dao;
	}

	public PagingWrapper<EarlyAlertRouting> getAllForCampus(
			final Campus campus,
			final SortingAndPaging sAndP) {
		return getDao().getAllForCampusId(campus.getId(), sAndP);
	}

	@Override
	public EarlyAlertRouting save(final EarlyAlertRouting obj)
			throws ObjectNotFoundException {
		return getDao().save(obj);
	}
}