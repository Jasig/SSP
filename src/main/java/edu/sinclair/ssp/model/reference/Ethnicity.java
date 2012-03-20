package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ethnicity extends AbstractReference{

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

}

