package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;

/**
 * Data access class for the ConfidentialityDisclosureAgreement reference
 * entity.
 */
@Repository
public class ConfidentialityDisclosureAgreementDao extends
		ReferenceAuditableCrudDao<ConfidentialityDisclosureAgreement>
		implements AuditableCrudDao<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementDao() {
		super(ConfidentialityDisclosureAgreement.class);
	}
}
