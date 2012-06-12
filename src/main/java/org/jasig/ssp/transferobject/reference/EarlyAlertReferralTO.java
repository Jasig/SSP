package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * EarlyAlertReferral reference transfer objects
 * 
 * @author jon.adams
 */
public class EarlyAlertReferralTO extends
		AbstractReferenceTO<EarlyAlertReferral>
		implements TransferObject<EarlyAlertReferral> {

	private short sortOrder; // NOPMD by jon on 5/4/12 11:16

	private String acronym;

	/**
	 * Empty constructor
	 */
	public EarlyAlertReferralTO() {
		super();
	}

	/**
	 * Constructor to initialize the required properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 */
	public EarlyAlertReferralTO(@NotNull final UUID id,
			@NotNull final String name) {
		super();
		super.setId(id);
		super.setName(name);
	}

	/**
	 * Constructor to initialize all properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param description
	 *            description shown to the user in detail views
	 * @param sortOrder
	 *            default sort order for use when displaying a list of these
	 *            reference objects to the user
	 * @param acronym
	 *            acronym (a.k.a. code)
	 */
	public EarlyAlertReferralTO(@NotNull final UUID id,
			@NotNull final String name,
			final String description, final short sortOrder, // NOPMD by jon
			@NotNull final String acronym) {
		super(id, name, description);
		this.sortOrder = sortOrder;
		this.acronym = acronym;
	}

	/**
	 * Construct a transfer object based on the data in the supplied model.
	 * 
	 * @param model
	 *            Model data to copy
	 */
	public EarlyAlertReferralTO(@NotNull final EarlyAlertReferral model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final EarlyAlertReferral model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		sortOrder = model.getSortOrder();
		acronym = model.getAcronym();
	}

	/**
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon on 5/4/12 11:16
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(final short sortOrder) { // NOPMD by jon on 5/4/12
		if (sortOrder < 0) {
			throw new IllegalArgumentException(
					"Sort order must be 0 or a positive integer, not "
							+ sortOrder);
		}

		this.sortOrder = sortOrder;
	}

	/**
	 * @return the acronym (a.k.a. code)
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym
	 *            the acronym (a.k.a. code) to set
	 */
	public void setAcronym(@NotNull final String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Convert a collection of models to a collection of equivalent transfer
	 * objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return A collection of equivalent transfer objects.
	 */
	public static List<EarlyAlertReferralTO> toTOList(
			@NotNull final Collection<EarlyAlertReferral> models) {
		final List<EarlyAlertReferralTO> tObjects = Lists.newArrayList();
		for (final EarlyAlertReferral model : models) {
			tObjects.add(new EarlyAlertReferralTO(model)); // NOPMD
		}

		return tObjects;
	}
}