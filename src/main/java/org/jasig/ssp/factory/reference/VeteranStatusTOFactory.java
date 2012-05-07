package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;

/**
 * VeteranStatus transfer object factory for converting back and forth from
 * VeteranStatus models.
 * 
 * @author daniel.bower
 * 
 */
public interface VeteranStatusTOFactory extends
		TOFactory<VeteranStatusTO, VeteranStatus> {
}
