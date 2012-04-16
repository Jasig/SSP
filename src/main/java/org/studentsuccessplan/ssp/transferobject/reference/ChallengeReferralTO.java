package org.studentsuccessplan.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral>, Serializable {

	private static final long serialVersionUID = 1193408160654677796L;

	private String publicDescription;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(final UUID id) {
		super(id);
	}

	public ChallengeReferralTO(final UUID id, final String name) {
		super(id, name);
	}

	public ChallengeReferralTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeReferralTO(final ChallengeReferral model) {
		super();
		fromModel(model);
	}

	@Override
	public final void fromModel(final ChallengeReferral model) {
		super.fromModel(model);
		publicDescription = model.getPublicDescription();
	}

	@Override
	public ChallengeReferral addToModel(final ChallengeReferral model) {
		super.addToModel(model);
		model.setPublicDescription(getPublicDescription());
		return model;
	}

	@Override
	public ChallengeReferral asModel() {
		return addToModel(new ChallengeReferral());
	}

	public static List<ChallengeReferralTO> listToTOList(
			final List<ChallengeReferral> models) {
		final List<ChallengeReferralTO> tos = Lists.newArrayList();
		for (ChallengeReferral model : models) {
			tos.add(new ChallengeReferralTO(model));
		}
		return tos;
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

}
