package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.CampusServiceDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.CampusServiceTOFactory;
import org.jasig.ssp.model.reference.CampusService;
import org.jasig.ssp.transferobject.reference.CampusServiceTO;

@Service
@Transactional(readOnly = true)
public class CampusServiceTOFactoryImpl extends
		AbstractReferenceTOFactory<CampusServiceTO, CampusService>
		implements CampusServiceTOFactory {

	public CampusServiceTOFactoryImpl() {
		super(CampusServiceTO.class, CampusService.class);
	}

	@Autowired
	private transient CampusServiceDao dao;

	@Override
	protected CampusServiceDao getDao() {
		return dao;
	}

}
