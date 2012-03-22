package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationGoal extends AbstractReference {

	public EducationGoal() {
		super();
	}

	public EducationGoal(UUID id) {
		super(id);
	}

	public EducationGoal(UUID id, String name) {
		super(id, name);
	}

	public EducationGoal(UUID id, String name, String description) {
		super(id, name, description);
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(EducationGoal)
	 */
	public void overwrite(EducationGoal source) {
		this.setName(source.getName());
		this.setDescription(source.getDescription());
	}
}
