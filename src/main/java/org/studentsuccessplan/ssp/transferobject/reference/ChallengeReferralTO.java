package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral> {

	private String publicDescription;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(UUID id) {
		super(id);
	}

	public ChallengeReferralTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeReferralTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChallengeReferralTO(ChallengeReferral model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(ChallengeReferral model) {
		super.fromModel(model);
		setPublicDescription(model.getPublicDescription());
	}

	@Override
	public ChallengeReferral addToModel(ChallengeReferral model) {
		super.addToModel(model);
		model.setPublicDescription(getPublicDescription());
		return model;
	}

	@Override
	public ChallengeReferral asModel() {
		return addToModel(new ChallengeReferral());
	}

	public static List<ChallengeReferralTO> listToTOList(
			List<ChallengeReferral> models) {
		List<ChallengeReferralTO> tos = Lists.newArrayList();
		for (ChallengeReferral model : models) {
			tos.add(new ChallengeReferralTO(model));
		}
		return tos;
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(String publicDescription) {
		this.publicDescription = publicDescription;
	}

}
