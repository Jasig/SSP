package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral>, Serializable {

	private static final long serialVersionUID = 1193408160654677796L;

	private String publicDescription;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeReferralTO(final ChallengeReferral model) {
		super();
		from(model);
	}

	public static List<ChallengeReferralTO> toTOList(
			final Collection<ChallengeReferral> models) {
		final List<ChallengeReferralTO> tObjects = Lists.newArrayList();
		for (ChallengeReferral model : models) {
			tObjects.add(new ChallengeReferralTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final ChallengeReferral model) {
		super.from(model);
		publicDescription = model.getPublicDescription();
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

}
