package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationLevel extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 8607220055323069577L;

	public EducationLevel() {
		super();
	}

	public EducationLevel(UUID id) {
		super(id);
	}

	public EducationLevel(UUID id, String name) {
		super(id, name);
	}

	public EducationLevel(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(EducationLevel source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
