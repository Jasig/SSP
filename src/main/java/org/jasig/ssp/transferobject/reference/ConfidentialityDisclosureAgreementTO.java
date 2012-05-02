package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityDisclosureAgreementTO extends
		AbstractReferenceTO<ConfidentialityDisclosureAgreement>
		implements TransferObject<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementTO() {
		super();
	}

	public ConfidentialityDisclosureAgreementTO(final UUID id,
			final String name,
			final String description) {
		super(id, name, description);
	}

	public ConfidentialityDisclosureAgreementTO(
			final ConfidentialityDisclosureAgreement model) {
		super();
		from(model);
	}

	public static List<ConfidentialityDisclosureAgreementTO> toTOList(
			final Collection<ConfidentialityDisclosureAgreement> models) {
		final List<ConfidentialityDisclosureAgreementTO> tObjects = Lists
				.newArrayList();
		for (ConfidentialityDisclosureAgreement model : models) {
			tObjects.add(new ConfidentialityDisclosureAgreementTO(model));
		}
		return tObjects;
	}
}
