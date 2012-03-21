package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.sinclair.ssp.model.reference.AbstractReference;
import edu.sinclair.ssp.transferobject.AuditableTO;

public abstract class AbstractReferenceTO extends AuditableTO {

	// validator
	@NotNull
	@NotEmpty
	private String name;

	private String description;

	public AbstractReferenceTO() {
		super();
	}

	public AbstractReferenceTO(UUID id) {
		super(id);
	}

	public AbstractReferenceTO(UUID id, String name) {
		super(id);
		this.name = name;
	}

	public AbstractReferenceTO(UUID id, String name, String description) {
		this(id);
		this.name = name;
		this.description = description;
	}

	public void fromModel(AbstractReference model) {
		super.fromModel(model);

		setName(model.getName());
		setDescription(model.getDescription());
	}

	public void addToModel(AbstractReference model) {
		super.addToModel(model);

		model.setName(getName());
		model.setDescription(getDescription());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
