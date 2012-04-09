package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Referral;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ReferralTO extends AbstractReferenceTO<Referral> implements
		TransferObject<Referral> {

	/**
	 * Public description
	 * 
	 * Optional, null allowed, max length 150 characters.
	 */
	private String publicDescription;

	public ReferralTO() {
		super();
	}

	public ReferralTO(final UUID id) {
		super(id);
	}

	public ReferralTO(final UUID id, final String name) {
		super(id, name);
	}

	public ReferralTO(final UUID id, final String name, final String description) {
		super(id, name, description);
	}

	public ReferralTO(final UUID id, final String name,
			final String description, final String publicDescription) {
		super(id, name, description);
		this.publicDescription = publicDescription;
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	/**
	 * Sets the public description
	 * 
	 * @param publicDescription
	 *            Name; null allowed; max 150 characters
	 */
	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

	@Override
	public void pullAttributesFromModel(final Referral model) {
		super.fromModel(model);
		setPublicDescription(model.getPublicDescription());
	}

	@Override
	public Referral pushAttributesToModel(final Referral model) {
		super.addToModel(model);
		model.setPublicDescription(model.getPublicDescription());
		return model;
	}

	@Override
	public Referral asModel() {
		return pushAttributesToModel(new Referral());
	}
}
