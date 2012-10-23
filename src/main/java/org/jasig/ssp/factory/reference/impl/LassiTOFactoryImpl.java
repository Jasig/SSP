package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.LassiDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.LassiTOFactory;
import org.jasig.ssp.model.reference.Lassi;
import org.jasig.ssp.transferobject.reference.LassiTO;

@Service
@Transactional(readOnly = true)
public class LassiTOFactoryImpl extends
		AbstractReferenceTOFactory<LassiTO, Lassi>
		implements LassiTOFactory {

	public LassiTOFactoryImpl() {
		super(LassiTO.class, Lassi.class);
	}

	@Autowired
	private transient LassiDao dao;

	@Override
	protected LassiDao getDao() {
		return dao;
	}

}
