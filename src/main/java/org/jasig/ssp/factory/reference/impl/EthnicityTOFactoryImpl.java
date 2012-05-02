package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.EthnicityDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EthnicityTOFactory;
import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.transferobject.reference.EthnicityTO;

@Service
@Transactional(readOnly = true)
public class EthnicityTOFactoryImpl extends
		AbstractReferenceTOFactory<EthnicityTO, Ethnicity>
		implements EthnicityTOFactory {

	public EthnicityTOFactoryImpl() {
		super(EthnicityTO.class, Ethnicity.class);
	}

	@Autowired
	private transient EthnicityDao dao;

	@Override
	protected EthnicityDao getDao() {
		return dao;
	}

}
