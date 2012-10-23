package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.MilitaryAffiliationDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.MilitaryAffiliationTOFactory;
import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;

@Service
@Transactional(readOnly = true)
public class MilitaryAffiliationTOFactoryImpl extends
		AbstractReferenceTOFactory<MilitaryAffiliationTO, MilitaryAffiliation>
		implements MilitaryAffiliationTOFactory {

	public MilitaryAffiliationTOFactoryImpl() {
		super(MilitaryAffiliationTO.class, MilitaryAffiliation.class);
	}

	@Autowired
	private transient MilitaryAffiliationDao dao;

	@Override
	protected MilitaryAffiliationDao getDao() {
		return dao;
	}

}
