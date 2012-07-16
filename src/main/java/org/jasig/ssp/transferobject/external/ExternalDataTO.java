package org.jasig.ssp.transferobject.external;

import java.io.Serializable;

import org.jasig.ssp.transferobject.TransferObject;

public interface ExternalDataTO<M> extends
		TransferObject<M> {

	Serializable getId();

	void setId(final Serializable id);
}