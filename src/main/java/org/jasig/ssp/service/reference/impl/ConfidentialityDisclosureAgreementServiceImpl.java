package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityDisclosureAgreementService;

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
