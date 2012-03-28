package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MaritalStatus extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 3066558077351820570L;

	public MaritalStatus() {
		super();
	}

	public MaritalStatus(UUID id) {
		super(id);
	}

	public MaritalStatus(UUID id, String name) {
		super(id, name);
	}

	public MaritalStatus(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(MaritalStatus)
	 */
	public void overwrite(MaritalStatus source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
