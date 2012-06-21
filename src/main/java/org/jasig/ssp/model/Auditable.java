package org.jasig.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * Base for all models in the system that includes the ID, current
 * {@link ObjectStatus}, and date and person for the model instance creator and
 * last modifier.
 */
public interface Auditable extends Serializable {

	/**
	 * Gets the ID
	 * 
	 * @return the ID
	 */
	UUID getId();

	void setId(@NotNull final UUID id);

	/**
	 * Gets the created date
	 * 
	 * @return the created date
	 */
	Date getCreatedDate();

	void setCreatedDate(@NotNull final Date createdDate);

	Person getCreatedBy();

	void setCreatedBy(@NotNull final Person createdBy);

	/**
	 * Gets the modified date
	 * 
	 * @return the modified date
	 */
	Date getModifiedDate();

	void setModifiedDate(final Date modifiedDate);

	Person getModifiedBy();

	void setModifiedBy(final Person modifiedBy);

	/**
	 * Gets the {@link ObjectStatus}
	 * 
	 * @return the current ObjectStatus
	 */
	ObjectStatus getObjectStatus();

	void setObjectStatus(@NotNull final ObjectStatus objectStatus);

	/**
	 * Returns true if the object has been changed but not yet persisted to
	 * storage.
	 * 
	 * @return True if the object has been changed but not yet persisted to
	 *         storage.
	 */
	boolean isTransient();
}