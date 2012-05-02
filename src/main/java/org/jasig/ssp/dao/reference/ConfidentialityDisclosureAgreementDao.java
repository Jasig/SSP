package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;

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
