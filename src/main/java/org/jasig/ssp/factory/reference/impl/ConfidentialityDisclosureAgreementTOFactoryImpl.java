package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityDisclosureAgreementTOFactory;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;

@Service
@Transactional(readOnly = true)
public class ConfidentialityDisclosureAgreementTOFactoryImpl
		extends
		AbstractReferenceTOFactory<ConfidentialityDisclosureAgreementTO, ConfidentialityDisclosureAgreement>
		implements ConfidentialityDisclosureAgreementTOFactory {

	public ConfidentialityDisclosureAgreementTOFactoryImpl() {
		super(ConfidentialityDisclosureAgreementTO.class,
				ConfidentialityDisclosureAgreement.class);
	}

	@Autowired
	private transient ConfidentialityDisclosureAgreementDao dao;

	@Override
	protected ConfidentialityDisclosureAgreementDao getDao() {
		return dao;
	}

}
