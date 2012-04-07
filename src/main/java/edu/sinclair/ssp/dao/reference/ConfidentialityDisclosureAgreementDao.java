package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;

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
