package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.transferobject.reference.ConfigTO;

/**
 * Config transfer object factory for converting back and forth from
 * Config models.
 * 
 * @author daniel.bower
 * 
 */
public interface ConfigTOFactory extends
		TOFactory<ConfigTO, Config> {
}
