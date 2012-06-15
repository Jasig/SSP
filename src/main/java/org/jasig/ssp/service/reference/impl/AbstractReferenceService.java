package org.jasig.ssp.service.reference.impl;

import javax.validation.ConstraintViolationException;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base reference model service methods
 * 
 * @param <T>
 *            Model type
 */
@Transactional
public abstract class AbstractReferenceService<T extends Auditable> extends
		AbstractAuditableCrudService<T>
		implements AuditableCrudService<T> {

	@Override
	public T save(final T obj) throws ObjectNotFoundException,
			ValidationException {
		try {
			return getDao().save(obj);
		} catch (final ConstraintViolationException exc) {
			throw new ValidationException("Invalid data.", exc);
		}
	}
}