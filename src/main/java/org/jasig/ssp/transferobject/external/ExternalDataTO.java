package org.jasig.ssp.transferobject.external;

import org.jasig.ssp.model.external.ExternalData;
import org.jasig.ssp.transferobject.TransferObject;

public interface ExternalDataTO<M extends ExternalData> extends
		TransferObject<M> {

	String[] getId();

	void setId(final String[] id);
}
