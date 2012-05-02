package org.jasig.ssp.transferobject.reference;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.transferobject.AuditableTO;
import org.jasig.ssp.transferobject.NamedTO;

public abstract class AbstractReferenceTO<T extends AbstractReference>
		extends AuditableTO<T> implements NamedTO {

	@NotNull
	@NotEmpty
	private String name;

	private String description;

	public AbstractReferenceTO() {
		super();
	}

	public AbstractReferenceTO(final UUID id, final String name,
			final String description) {
		super(id);
		this.name = name;
		this.description = description;
	}

	@Override
	public void from(final T model) {
		super.from(model);

		name = model.getName();
		description = model.getDescription();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
