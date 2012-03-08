package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "funding_source", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FundingSource extends AbstractReference{

	public FundingSource() {
		super();
	}
	
	public FundingSource(UUID id) {
		super(id);
	}
	
	public FundingSource(UUID id, String name) {
		super(id, name);
	}

	public FundingSource(UUID id, String name, String description) {
		super(id, name, description);
	}

}

