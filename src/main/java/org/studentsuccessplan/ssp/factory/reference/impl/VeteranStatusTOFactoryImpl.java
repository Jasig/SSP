package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.VeteranStatusDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.VeteranStatusTOFactory;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.transferobject.reference.VeteranStatusTO;

@Service
@Transactional(readOnly = true)
public class VeteranStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<VeteranStatusTO, VeteranStatus>
		implements VeteranStatusTOFactory {

	public VeteranStatusTOFactoryImpl() {
		super(VeteranStatusTO.class, VeteranStatus.class);
	}

	@Autowired
	private VeteranStatusDao dao;

	@Override
	protected VeteranStatusDao getDao() {
		return dao;
	}

}
