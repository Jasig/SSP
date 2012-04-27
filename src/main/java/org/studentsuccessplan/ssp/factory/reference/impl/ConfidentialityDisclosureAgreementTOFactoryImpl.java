package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ConfidentialityDisclosureAgreementTOFactory;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;

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
