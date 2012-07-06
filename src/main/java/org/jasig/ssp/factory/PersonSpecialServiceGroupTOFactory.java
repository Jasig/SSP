package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonSpecialServiceGroupTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

public interface PersonSpecialServiceGroupTOFactory extends
		TOFactory<PersonSpecialServiceGroupTO, PersonSpecialServiceGroup> {

	PersonSpecialServiceGroup fromLite(
			final ReferenceLiteTO<SpecialServiceGroup> lite,
			final Person person) throws ObjectNotFoundException;

	Set<PersonSpecialServiceGroup> fromLites(
			final Collection<ReferenceLiteTO<SpecialServiceGroup>> lites,
			final Person person) throws ObjectNotFoundException;

	void updateSetFromLites(
			final Set<PersonSpecialServiceGroup> updateSet,
			final Collection<ReferenceLiteTO<SpecialServiceGroup>> lites,
			final Person person) throws ObjectNotFoundException;
}