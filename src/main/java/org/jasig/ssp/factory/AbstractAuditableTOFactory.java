package org.jasig.ssp.factory;

import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transfer object factory for {@link Auditable} models.
 * 
 * @param <TObject>
 *            Transfer object class for the associated model type.
 * @param <M>
 *            Model type
 */
@Transactional(readOnly = true)
public abstract class AbstractAuditableTOFactory<TObject extends AbstractAuditableTO<M>, M extends Auditable>
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
	public AbstractAuditableTOFactory(final Class<TObject> tObjectClass,
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
								+ tObject.getId().toString(), mClass.getName());
			}
		}

		model.setObjectStatus(tObject.getObjectStatus());

		return model;
	}

	@Override
	public M from(final UUID id) throws ObjectNotFoundException {
		return getDao().get(id);
	}

	/**
	 * Gets the associated data-access layer instance
	 * 
	 * @return The associated data-access layer instance
	 */
	protected abstract AuditableCrudDao<M> getDao();
}
