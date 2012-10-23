package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.LassiDao;
import org.jasig.ssp.model.reference.Lassi;
import org.jasig.ssp.service.reference.LassiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LassiServiceImpl extends
		AbstractReferenceService<Lassi>
		implements LassiService {

	@Autowired
	transient private LassiDao dao;

	protected void setDao(final LassiDao dao) {
		this.dao = dao;
	}

	@Override
	protected LassiDao getDao() {
		return dao;
	}
}
