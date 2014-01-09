package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SapStatus extends AbstractReference implements Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4613085897012049359L;
	private String code;

	/**
	 * Constructor
	 */
	public SapStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public SapStatus(final UUID id) {
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

	public SapStatus(final UUID id, final String name) {
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
	public SapStatus(final UUID id, final String name, final String description) {
		super(id, name, description);
	}
	
	/**
	 * @return the race code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param race external ref code
	 * 				the race code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	protected int hashPrime() {
		return 111;
	}
	
	@Override
	public int hashCode() { // NOPMD by jon.adams
		return hashPrime() * super.hashCode()
				* hashField("code", code);
	}	
}
