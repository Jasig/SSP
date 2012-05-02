package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

/**
 * VeteranStatus reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class VeteranStatus extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@NotNull
	private short sortOrder = 0;

	/**
	 * Constructor
	 */
	public VeteranStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public VeteranStatus(UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public VeteranStatus(UUID id, String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public VeteranStatus(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * @return the sortOrder
	 */
	public short getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(short sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	protected int hashPrime() {
		return 149;
	};
}
