package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * <p>
 * A base class that most entities in the system inherit, that allow the system
 * to track the most recent action to occur on an object. Auditable objects are
 * always persisted, so they additionally have an id property.
 * </p>
 * 
 * <p>
 * Common fields include a primary key identifier, creation/modification author
 * and timestamps, and object status.
 * </p>
 * 
 * <p>
 * {@link org.jasig.ssp.dao.AuditableEntityInterceptor} will automatically fill
 * the creation/modification stamps as appropriate in the persistence layer.
 * </p>
 * 
 * @author daniel.bower
 * @author jon.adams
 */
@MappedSuperclass
public abstract class Auditable {
	@Id
	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2", parameters = { @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	private UUID id;

	/**
	 * Entity creation time stamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;

	/**
	 * Person that created this entity.
	 * 
	 * Set to load eagerly because
	 * {@link org.jasig.ssp.transferobject.AbstractAuditableTO#from(org.jasig.ssp.model.Auditable)}
	 * pulls the ID from the full Person object and would require an extra
	 * lookup for every entity sent through the Controllers (or anything that
	 * uses {@link Auditable} transfer objects).
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, updatable = false)
	private Person createdBy;

	/**
	 * Most recent modification time stamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date modifiedDate;

	/**
	 * Last person to modify this entity.
	 * 
	 * Set to load eagerly because
	 * {@link org.jasig.ssp.transferobject.AbstractAuditableTO#from(org.jasig.ssp.model.Auditable)}
	 * pulls the ID from the full Person object and would require an extra
	 * lookup for every entity sent through the Controllers (or anything that
	 * uses Auditable transfer objects).
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Person modifiedBy;

	/**
	 * Entity status.
	 * 
	 * Most commonly {@link ObjectStatus#ACTIVE} or {@link ObjectStatus#DELETED}
	 * , but other enum values possible.
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private ObjectStatus objectStatus;

	protected abstract int hashPrime();

	@Override
	public abstract int hashCode();

	@Override
	final public boolean equals(final Object obj) {
		if (this == obj) {
			// exact references that point to the same place in memory are
			// always equal
			return true;
		}

		if (!(Auditable.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}

		final Auditable other = (Auditable) obj;

		return hasSameNonDefaultIdAs(other) ||
				// Since the IDs aren't the same, either of them must be
				// transient to compare business value signatures
				((isTransient() || other.isTransient()) &&
				hasSameDomainSignature(other));
	}

	/**
	 * This method MUST be implemented for each class and must compare to all
	 * properties that define an equal instance for business rule comparison
	 * purposes.
	 * 
	 * @param other
	 *            The object to compare
	 * @return True if properties for business equality are all equal.
	 */
	private boolean hasSameDomainSignature(final Object other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public String toString() {
		return id == null ? super.toString() : id.toString();
	}

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

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final Person createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public Person getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final Person modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	/**
	 * Transient objects are not associated with an item already in storage. For
	 * instance, an object is transient if its id is null.
	 * 
	 * @return The object is associated or not with an item already in storage.
	 */
	final public boolean isTransient()
	{
		return id == null;
	}

	/**
	 * Checks to see if one object has an id but the other doesn't or if they
	 * have the exact same id.
	 * 
	 * @param compareTo
	 *            Object to check for persistence layer equality
	 * @return True if both objects share the same, valid id
	 */
	final protected boolean hasSameNonDefaultIdAs(final Auditable compareTo)
	{
		if (compareTo == null)
		{
			throw new IllegalArgumentException(
					"Object to be compared can not be null.");
		}

		return (id != null) &&
				(compareTo.id != null) &&
				id.equals(compareTo.id);
	}

	final protected boolean areEqual(final Object o1, final Object o2) {
		if (o1 == null) {
			if (o2 != null) {
				return false;
			}
		} else if (!o1.equals(o2)) {
			return false;
		}
		return true;
	}
}
