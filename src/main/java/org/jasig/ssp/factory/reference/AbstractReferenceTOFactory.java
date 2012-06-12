package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;

/**
 * Reference transfer object factory base class.
 * 
 * @param <TObject>
 *            Transfer object class for the associated model class
 * @param <M>
 *            The model class
 */
public abstract class AbstractReferenceTOFactory<TObject extends AbstractReferenceTO<M>, M extends AbstractReference>
		extends AbstractAuditableTOFactory<TObject, M>
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
	public AbstractReferenceTOFactory(final Class<TObject> tObjectClass,
			final Class<M> mClass) {
		super(tObjectClass, mClass);
	}

	@Override
	public M from(final TObject tObject) throws ObjectNotFoundException {
		final M model = super.from(tObject);

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());

		return model;
	}
}