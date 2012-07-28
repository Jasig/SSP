package org.jasig.ssp.model.external;

import java.io.Serializable;

/**
 * An ExternalData requires an identifier for Hibernate, though it is completely
 * ignored by the system.
 * 
 * @author jon.adams
 * 
 */
public interface ExternalData {

	/**
	 * Only used by Hibernate. Generator identifiers of external data are
	 * meaningless to this system.
	 * 
	 * @return the generated identifier
	 */
	@Deprecated
	Serializable getId();

	/**
	 * Only used by Hibernate. Setting this does not matter as changes are never
	 * persisted.
	 * 
	 * @param id
	 */
	@Deprecated
	void setId(Serializable id);
}