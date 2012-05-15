package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;

/**
 * Transfer object for copy to and from equivalent Auditable models.
 * 
 * @param <T>
 *            Any {@link Auditable} model type.
 */
public abstract class AbstractAuditableTO<T extends Auditable>
		implements TransferObject<T> {

	/**
	 * Empty constructor.
	 */
	public AbstractAuditableTO() {
		super();
	}

	/**
	 * Construct a simple Auditable with the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 */
	public AbstractAuditableTO(final UUID id) {
		super();
		this.id = id;
	}

	private UUID id;

	private Date createdDate;

	private AbstractAuditableTO<T>.PersonLiteTO createdBy;

	private Date modifiedDate;

	private AbstractAuditableTO<T>.PersonLiteTO modifiedBy;

	private ObjectStatus objectStatus;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

	public AbstractAuditableTO<T>.PersonLiteTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final AbstractAuditableTO<T>.PersonLiteTO createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public AbstractAuditableTO<T>.PersonLiteTO getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(
			final AbstractAuditableTO<T>.PersonLiteTO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	@Override
	public void from(final T model) {
		id = model.getId();
		if (model.getCreatedBy() != null) {
			createdBy = new PersonLiteTO(model.getCreatedBy());
		}

		if (model.getModifiedBy() != null) {
			modifiedBy = new PersonLiteTO(model.getModifiedBy());
		}

		createdDate = model.getCreatedDate();
		modifiedDate = model.getModifiedDate();
		objectStatus = model.getObjectStatus();
	}

	/**
	 * Encapsulate simple Person properties.
	 * 
	 * @author jon.adams
	 */
	public class PersonLiteTO implements Serializable {
		private static final long serialVersionUID = 2921442272658399L;

		private UUID id;

		private String firstName;

		private String lastName;

		public PersonLiteTO(final UUID id, final String firstName,
				final String lastName) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public PersonLiteTO(final Person person) {
			if (person == null) {
				return;
			}

			this.id = person.getId();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
		}

		public UUID getId() {
			return id;
		}

		public void setId(final UUID id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(final String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(final String lastName) {
			this.lastName = lastName;
		}
	}
}
