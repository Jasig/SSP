package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

/**
 * PersonProgramStatus transfer object factory
 * 
 * @author jon.adams
 * 
 */
public interface PersonProgramStatusTOFactory extends
		TOFactory<PersonProgramStatusTO, PersonProgramStatus> {

	void updateSetFromLites(
			final Set<PersonProgramStatus> updateSet,
			final Collection<ReferenceLiteTO<ProgramStatus>> lites,
			final Person person) throws ObjectNotFoundException;
}