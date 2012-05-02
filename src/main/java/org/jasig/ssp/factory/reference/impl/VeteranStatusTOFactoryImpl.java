package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.VeteranStatusDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.VeteranStatusTOFactory;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;

@Service
@Transactional(readOnly = true)
public class VeteranStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<VeteranStatusTO, VeteranStatus>
		implements VeteranStatusTOFactory {

	public VeteranStatusTOFactoryImpl() {
		super(VeteranStatusTO.class, VeteranStatus.class);
	}

	@Autowired
	private transient VeteranStatusDao dao;

	@Override
	protected VeteranStatusDao getDao() {
		return dao;
	}

}
