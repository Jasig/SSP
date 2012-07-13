package org.jasig.ssp.dao.external;

import java.io.Serializable;

import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.service.ObjectNotFoundException;

public abstract class AbstractExternalDataDao<T>
		extends AbstractDao<T>
		implements ExternalDataDao<T> {

	protected AbstractExternalDataDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public abstract T get(Serializable id) throws ObjectNotFoundException;
}
