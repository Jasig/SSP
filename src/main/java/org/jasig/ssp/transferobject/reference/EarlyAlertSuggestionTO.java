package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.transferobject.NamedTO;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Sets;

/**
 * EarlyAlertSuggestion reference transfer objects
 * 
 * @author jon.adams
 */
public class EarlyAlertSuggestionTO extends
		AbstractReferenceTO<EarlyAlertSuggestion>
		implements TransferObject<EarlyAlertSuggestion>, NamedTO, Serializable {

	private static final long serialVersionUID = 3743316755135265679L;

	private short sortOrder; // NOPMD by jon.adams on 5/4/12 11:16

	/**
	 * Empty constructor
	 */
	public EarlyAlertSuggestionTO() {
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
	public EarlyAlertSuggestionTO(@NotNull final UUID id,
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
	 */
	public EarlyAlertSuggestionTO(@NotNull final UUID id,
			@NotNull final String name,
			final String description, final short sortOrder) { // NOPMD by jon
		super(id, name, description);
		this.sortOrder = sortOrder;
	}

	/**
	 * Construct a transfer object based on the data in the supplied model.
	 * 
	 * @param model
	 *            Model data to copy
	 */
	public EarlyAlertSuggestionTO(@NotNull final EarlyAlertSuggestion model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final EarlyAlertSuggestion model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		sortOrder = model.getSortOrder();
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
	 * Convert a collection of models to a collection of equivalent transfer
	 * objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return A collection of equivalent transfer objects.
	 */
	public static Set<EarlyAlertSuggestionTO> toTOSet(
			@NotNull final Collection<EarlyAlertSuggestion> models) {
		final Set<EarlyAlertSuggestionTO> tObjects = Sets.newHashSet();
		for (final EarlyAlertSuggestion model : models) {
			tObjects.add(new EarlyAlertSuggestionTO(model)); // NOPMD by jon
		}

		return tObjects;
	}
}