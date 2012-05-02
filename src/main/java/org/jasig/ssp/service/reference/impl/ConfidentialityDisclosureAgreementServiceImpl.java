package org.jasig.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.reference.ConfidentialityDisclosureAgreementService;

@Service
@Transactional
public class ConfidentialityDisclosureAgreementServiceImpl extends
		AbstractReferenceService<ConfidentialityDisclosureAgreement>
		implements ConfidentialityDisclosureAgreementService {

	public ConfidentialityDisclosureAgreementServiceImpl() {
		super(ConfidentialityDisclosureAgreement.class);
	}

	@Autowired
	transient private ConfidentialityDisclosureAgreementDao dao;

	protected void setDao(final ConfidentialityDisclosureAgreementDao dao) {
		this.dao = dao;
	}

	@Override
	protected ConfidentialityDisclosureAgreementDao getDao() {
		return dao;
	}
}
