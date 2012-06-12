package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * Config reference transfer objects
 * 
 * @author daniel.bower
 */
public class ConfigTO extends AbstractReferenceTO<Config>
		implements TransferObject<Config> {

	private String value, valueValidation, defaultValue;
	private short sortOrder; // NOPMD by jon on 5/4/12 11:16

	/**
	 * Empty constructor
	 */
	public ConfigTO() {
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
	public ConfigTO(@NotNull final UUID id, @NotNull final String name) {
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
	public ConfigTO(@NotNull final UUID id, @NotNull final String name,
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
	public ConfigTO(@NotNull final Config model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final Config model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		sortOrder = model.getSortOrder();
		value = model.getValue();
		defaultValue = model.getDefaultValue();
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

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getValueValidation() {
		return valueValidation;
	}

	public void setValueValidation(final String valueValidation) {
		this.valueValidation = valueValidation;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(@NotNull final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Convert a collection of models to a collection of equivalent transfer
	 * objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return A collection of equivalent transfer objects.
	 */
	public static List<ConfigTO> toTOList(
			@NotNull final Collection<Config> models) {
		final List<ConfigTO> tObjects = Lists.newArrayList();
		for (final Config model : models) {
			tObjects.add(new ConfigTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}
}