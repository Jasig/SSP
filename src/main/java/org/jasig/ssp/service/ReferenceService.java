package org.jasig.ssp.service;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

public interface ReferenceService<T extends Auditable>
		extends AuditableCrudService<T> {

	public T getByName(@NotNull final String name)
			throws ObjectNotFoundException;
}
