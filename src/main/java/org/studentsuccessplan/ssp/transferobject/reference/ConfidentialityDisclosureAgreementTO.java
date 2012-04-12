package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityDisclosureAgreementTO extends
		AbstractReferenceTO<ConfidentialityDisclosureAgreement> implements
		TransferObject<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementTO() {
		super();
	}

	public ConfidentialityDisclosureAgreementTO(UUID id) {
		super(id);
	}

	public ConfidentialityDisclosureAgreementTO(UUID id, String name) {
		super(id, name);
	}

	public ConfidentialityDisclosureAgreementTO(UUID id, String name,
			String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(ConfidentialityDisclosureAgreement model) {
		super.fromModel(model);
	}

	@Override
	public ConfidentialityDisclosureAgreement addToModel(
			ConfidentialityDisclosureAgreement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityDisclosureAgreement asModel() {
		return addToModel(new ConfidentialityDisclosureAgreement());
	}

	public static List<ConfidentialityDisclosureAgreementTO> listToTOList(
			List<ConfidentialityDisclosureAgreement> models) {
		List<ConfidentialityDisclosureAgreementTO> tos = Lists.newArrayList();
		for (ConfidentialityDisclosureAgreement model : models) {
			ConfidentialityDisclosureAgreementTO obj = new ConfidentialityDisclosureAgreementTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
