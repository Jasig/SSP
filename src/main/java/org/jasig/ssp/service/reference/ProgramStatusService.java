package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ReferenceService;

/**
 * ProgramStatus service
 * 
 * @author jon.adams
 * @author dan.mccallum
 */
public interface ProgramStatusService extends
		ReferenceService<ProgramStatus> {

	/**
	 * Find the {@link ProgramStatus} that represents active participation.
	 * Such a record usually needs to exist for the system to behave properly,
	 * but the implementation is not oblicated to guarantee a non-null/
	 * non-exceptional return.
	 *
	 * @return the status representing activeness
	 * @throws ObjectNotFoundException if the status representing activeness
	 *   cannot be found
	 */
	ProgramStatus getActiveStatus() throws ObjectNotFoundException;
}