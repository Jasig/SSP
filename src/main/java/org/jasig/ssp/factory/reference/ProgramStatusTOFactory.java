package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.reference.ProgramStatusTO;

/**
 * ProgramStatus transfer object factory for converting back and forth from
 * ProgramStatus models.
 * 
 * @author jon.adams
 * 
 */
public interface ProgramStatusTOFactory extends
		TOFactory<ProgramStatusTO, ProgramStatus> {
}
