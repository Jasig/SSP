package org.jasig.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.VeteranStatusDao;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.reference.VeteranStatusService;

@Service
@Transactional
public class VeteranStatusServiceImpl extends
		AbstractReferenceService<VeteranStatus>
		implements VeteranStatusService {

	public VeteranStatusServiceImpl() {
		super(VeteranStatus.class);
	}

	@Autowired
	transient private VeteranStatusDao dao;

	protected void setDao(final VeteranStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected VeteranStatusDao getDao() {
		return dao;
	}
}
