package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Challenge extends AbstractReference implements Serializable {

	private static final long serialVersionUID = -5460841184801377719L;

	public Challenge() {
		super();
	}

	public Challenge(UUID id) {
		super(id);
	}

	public Challenge(UUID id, String name) {
		super(id, name);
	}

	public Challenge(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(Challenge)
	 */
	public void overwrite(Challenge source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
