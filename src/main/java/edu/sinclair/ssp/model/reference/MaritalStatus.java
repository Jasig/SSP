package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "marital_status", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MaritalStatus extends AbstractReference{

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

}

