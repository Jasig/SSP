package org.studentsuccessplan.ssp.factory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public interface TOFactory<TObject extends TransferObject<M>, M> {

	TObject from(M model);

	M from(TObject tObject) throws ObjectNotFoundException;

	M from(UUID id);

	List<TObject> asTOList(Collection<M> models);

	Set<TObject> asTOSet(Collection<M> models);

	Set<M> asSet(Collection<TObject> tObjects)
			throws ObjectNotFoundException;
}
