package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ServiceReasonDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ServiceReasonTOFactory;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.transferobject.reference.ServiceReasonTO;

@Service
@Transactional(readOnly = true)
public class ServiceReasonTOFactoryImpl extends
		AbstractReferenceTOFactory<ServiceReasonTO, ServiceReason>
		implements ServiceReasonTOFactory {

	public ServiceReasonTOFactoryImpl() {
		super(ServiceReasonTO.class, ServiceReason.class);
	}

	@Autowired
	private transient ServiceReasonDao dao;

	@Override
	protected ServiceReasonDao getDao() {
		return dao;
	}

}
