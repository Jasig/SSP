package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.reference.CampusTO;

/**
 * Campus transfer object factory for converting back and forth from Campus
 * models.
 * 
 * @author jon.adams
 * 
 */
public interface CampusTOFactory extends
		TOFactory<CampusTO, Campus> {
}
