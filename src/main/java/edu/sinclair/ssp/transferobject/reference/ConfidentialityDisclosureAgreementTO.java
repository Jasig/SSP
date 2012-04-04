package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ConfidentialityDisclosureAgreementTO extends AbstractReferenceTO<ConfidentialityDisclosureAgreement>
		implements TransferObject<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementTO() {
		super();
	}

	public ConfidentialityDisclosureAgreementTO(UUID id) {
		super(id);
	}

	public ConfidentialityDisclosureAgreementTO(UUID id, String name) {
		super(id, name);
	}

	public ConfidentialityDisclosureAgreementTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ConfidentialityDisclosureAgreementTO(ConfidentialityDisclosureAgreement model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(ConfidentialityDisclosureAgreement model) {
		super.fromModel(model);
	}

	@Override
	public ConfidentialityDisclosureAgreement pushAttributesToModel(ConfidentialityDisclosureAgreement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityDisclosureAgreement asModel() {
		return pushAttributesToModel(new ConfidentialityDisclosureAgreement());
	}

}
