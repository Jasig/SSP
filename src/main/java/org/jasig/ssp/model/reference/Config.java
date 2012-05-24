package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Auditable;

/**
 * Config reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Config
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -8572858642333315262L;

	@Column(nullable = true)
	private String value;

	@Column(nullable = false)
	private String defaultValue;

	/**
	 * A regular expression for validation the value
	 */
	@Column(nullable = true)
	private String valueValidation;

	@Column(nullable = false)
	@NotNull
	private short sortOrder = 0; // NOPMD by jon.adams on 5/4/12 1:41 PM

	/**
	 * Constructor
	 */
	public Config() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Config(@NotNull final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 * @param sortOrder
	 *            Default sort order when displaying objects to the user
	 */
	public Config(@NotNull final UUID id, @NotNull final String name,
			final String description, final short sortOrder) { // NOPMD by jon
		super(id, name, description);
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the default sort order when displaying an item list to the user
	 * 
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon.adams on 5/4/12 1:41 PM
		return sortOrder;
	}

	/**
	 * Sets the default sort order when displaying an item list to the user
	 * 
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(final short sortOrder) { // NOPMD by jon.adams
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

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 263;
	}

	@Override
	public int hashCode() {
		return hashPrime()
				* super.hashCode()
				* (sortOrder > 0 ? sortOrder : hashPrime())
				* (StringUtils.isEmpty(value) ? "value".hashCode() :
						value.hashCode())
				* (StringUtils.isEmpty(defaultValue) ? "defaultValue"
						.hashCode() :
						defaultValue.hashCode())
				* (StringUtils.isEmpty(valueValidation) ? "valueValidation"
						.hashCode() : valueValidation.hashCode());
	}
}
