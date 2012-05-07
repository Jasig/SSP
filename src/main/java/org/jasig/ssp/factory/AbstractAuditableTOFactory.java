package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AuditableTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Transfer object factory for {@link Auditable} models.
 * 
 * @param <TObject>
 *            Transfer object class for the associated model type.
 * @param <M>
 *            Model type
 */
public abstract class AbstractAuditableTOFactory<TObject extends AuditableTO<M>, M extends Auditable>
		implements TOFactory<TObject, M> {

	private final transient Class<TObject> tObjectClass;

	private final transient Class<M> mClass;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractAuditableTOFactory.class);

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
		this.tObjectClass = tObjectClass;
		this.mClass = mClass;
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
								+ tObject.getId().toString());
			}
		}

		model.setObjectStatus(tObject.getObjectStatus());

		return model;
	}

	@Override
	public TObject from(final M model) {
		final TObject tObject = newTObject();
		tObject.from(model);
		return tObject;
	}

	@Override
	public M from(final UUID id) {
		return getDao().get(id);
	}

	@Override
	public List<TObject> asTOList(
			final Collection<M> models) {
		final List<TObject> tos = Lists.newArrayList();

		if ((models != null) && !models.isEmpty()) {
			for (M model : models) {
				tos.add(from(model));
			}
		}

		return tos;
	}

	@Override
	public Set<TObject> asTOSet(final Collection<M> models) {
		final Set<TObject> tos = Sets.newHashSet();
		for (M model : models) {
			tos.add(from(model));
		}
		return tos;
	}

	@Override
	public Set<M> asSet(final Collection<TObject> tObjects)
			throws ObjectNotFoundException {
		final Set<M> models = Sets.newHashSet();
		for (TObject tObject : tObjects) {
			models.add(from(tObject));
		}
		return models;
	}

	private TObject newTObject() {
		try {
			return tObjectClass.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error("unable to instantiate Transfer object in factory.");
		} catch (IllegalAccessException e) {
			LOGGER.error("unable to instantiate Transfer object in factory.");
		}
		return null;
	}

	private M newModel() {
		try {
			return mClass.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error("unable to instantiate Model object in factory.");
		} catch (IllegalAccessException e) {
			LOGGER.error("unable to instantiate Model object in factory.");
		}

		return null;
	}

	/**
	 * Gets the associated data-access layer instance
	 * 
	 * @return The associated data-access layer instance
	 */
	protected abstract AuditableCrudDao<M> getDao();
}
