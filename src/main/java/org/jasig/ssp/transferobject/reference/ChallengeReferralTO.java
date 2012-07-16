package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral>, Serializable {

	private static final long serialVersionUID = 1193408160654677796L;

	private String publicDescription;

	private Boolean showInSelfHelpGuide;

	private Boolean showInStudentIntake;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(final ChallengeReferral model) {
		super();
		from(model);
	}

	public static List<ChallengeReferralTO> toTOList(
			final Collection<ChallengeReferral> models) {
		final List<ChallengeReferralTO> tObjects = Lists.newArrayList();
		for (final ChallengeReferral model : models) {
			tObjects.add(new ChallengeReferralTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	@Override
	public final void from(final ChallengeReferral model) {
		super.from(model);
		publicDescription = model.getPublicDescription();
		showInSelfHelpGuide = model.getShowInSelfHelpGuide();
		showInStudentIntake = model.getShowInStudentIntake();
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

	/**
	 * @return the showInSelfHelpGuide
	 */
	public Boolean getShowInSelfHelpGuide() {
		return showInSelfHelpGuide;
	}

	/**
	 * @param showInSelfHelpGuide
	 *            the showInSelfHelpGuide to set
	 */
	public void setShowInSelfHelpGuide(final Boolean showInSelfHelpGuide) {
		this.showInSelfHelpGuide = showInSelfHelpGuide;
	}

	/**
	 * @return the showInStudentIntake
	 */
	public Boolean getShowInStudentIntake() {
		return showInStudentIntake;
	}

	/**
	 * @param showInStudentIntake
	 *            the showInStudentIntake to set
	 */
	public void setShowInStudentIntake(final Boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}
}