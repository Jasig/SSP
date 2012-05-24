package org.jasig.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public interface Auditable extends Serializable {

	UUID getId();

	void setId(final UUID id);

	Date getCreatedDate();

	void setCreatedDate(final Date createdDate);

	Person getCreatedBy();

	void setCreatedBy(final Person createdBy);

	Date getModifiedDate();

	void setModifiedDate(final Date modifiedDate);

	Person getModifiedBy();

	void setModifiedBy(final Person modifiedBy);

	ObjectStatus getObjectStatus();

	void setObjectStatus(final ObjectStatus objectStatus);

	boolean isTransient();

}