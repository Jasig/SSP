package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityDisclosureAgreementTO extends AbstractReferenceTO<ConfidentialityDisclosureAgreement>
		implements TransferObject<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementTO() {
		super();
	}

	public ConfidentialityDisclosureAgreementTO(final UUID id) {
		super(id);
	}

	public ConfidentialityDisclosureAgreementTO(final UUID id, final String name) {
		super(id, name);
	}

	public ConfidentialityDisclosureAgreementTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ConfidentialityDisclosureAgreementTO(final ConfidentialityDisclosureAgreement model) {
		super();
		fromModel(model);
	}

	@Override
	public ConfidentialityDisclosureAgreement addToModel(final ConfidentialityDisclosureAgreement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityDisclosureAgreement asModel() {
		return addToModel(new ConfidentialityDisclosureAgreement());
	}

	public static List<ConfidentialityDisclosureAgreementTO> listToTOList(
			final List<ConfidentialityDisclosureAgreement> models) {
		final List<ConfidentialityDisclosureAgreementTO> tos = Lists.newArrayList();
		for (ConfidentialityDisclosureAgreement model : models) {
			tos.add(new ConfidentialityDisclosureAgreementTO(model));
		}
		return tos;
	}

}
