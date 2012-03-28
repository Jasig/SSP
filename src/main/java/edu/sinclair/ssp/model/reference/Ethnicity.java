package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ethnicity extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 4750159626353128179L;

	public Ethnicity() {
		super();
	}

	public Ethnicity(UUID id) {
		super(id);
	}

	public Ethnicity(UUID id, String name) {
		super(id, name);
	}

	public Ethnicity(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(Ethnicity source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
