package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;

public abstract class AbstractReferenceTOFactory<TObject extends AbstractReferenceTO<M>, M extends AbstractReference>
		extends AbstractAuditableTOFactory<TObject, M>
		implements TOFactory<TObject, M> {

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
