package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonServiceReasonTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

public interface PersonServiceReasonTOFactory extends
		TOFactory<PersonServiceReasonTO, PersonServiceReason> {

	PersonServiceReason fromLite(
			final ReferenceLiteTO<ServiceReason> lite,
			final Person person) throws ObjectNotFoundException;

	Set<PersonServiceReason> fromLites(
			final Collection<ReferenceLiteTO<ServiceReason>> lites,
			final Person person) throws ObjectNotFoundException;

	void updateSetFromLites(
			final Set<PersonServiceReason> updateSet,
			final Collection<ReferenceLiteTO<ServiceReason>> lites,
			final Person person) throws ObjectNotFoundException;
}