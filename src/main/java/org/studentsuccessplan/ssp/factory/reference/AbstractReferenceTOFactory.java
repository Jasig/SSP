package org.studentsuccessplan.ssp.factory.reference;

import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.model.reference.AbstractReference;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.reference.AbstractReferenceTO;

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
