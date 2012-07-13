package org.jasig.ssp.factory.external.impl;

import java.util.Arrays;
import java.util.UUID;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.AbstractTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.external.ExternalData;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalDataTO;

public abstract class AbstractExternalDataTOFactory<M extends ExternalData, TObject extends ExternalDataTO<M>>
		extends AbstractTOFactory<M, TObject>
		implements TOFactory<TObject, M> {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 * 
	 * @param tObjectClass
	 *            Transfer object class for the associated model class
	 * @param mClass
	 *            The model class
	 */
	public AbstractExternalDataTOFactory(final Class<TObject> tObjectClass,
			final Class<M> mClass) {
		super(tObjectClass, mClass);
	}

	@Override
	public M from(final TObject tObject) throws ObjectNotFoundException {
		M model;

		if (tObject.getId() == null) {
			model = newModel();
		} else {
			model = getDao().get(tObject.getId());
			if (model == null) {
				throw new ObjectNotFoundException(
						"id provided, but not valid: "
								+ Arrays.toString(tObject.getId()),
						mClass.getName());
			}
		}

		return model;
	}

	@Override
	public M from(final UUID id) throws ObjectNotFoundException {
		throw new UnsupportedOperationException();
	}

	public M from(final String[] id) throws ObjectNotFoundException {
		return getDao().get(id);
	}

	protected abstract ExternalDataDao<M> getDao();

}
