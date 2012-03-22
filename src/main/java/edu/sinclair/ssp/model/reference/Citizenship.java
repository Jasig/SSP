package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Citizenship extends AbstractReference {

	public Citizenship() {
		super();
	}

	public Citizenship(UUID id) {
		super(id);
	}

	public Citizenship(UUID id, String name) {
		super(id, name);
	}

	public Citizenship(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(Citizenship)
	 */
	public void overwrite(Citizenship source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
