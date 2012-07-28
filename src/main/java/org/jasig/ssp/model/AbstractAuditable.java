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

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasig.ssp.util.uuid.UUIDCustomType;

/**
 * <p>
 * A base class that most entities in the system inherit, that allow the system
 * to track the most recent action to occur on an object. AbstractAuditable
 * objects are always persisted, so they additionally have an id property.
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
@SuppressWarnings("serial")
@MappedSuperclass
@TypeDef(name = "uuid-custom", typeClass = UUIDCustomType.class)
public abstract class AbstractAuditable implements Auditable { // NOPMD
	@Id
	@Type(type = "uuid-custom")
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
	 * {@link org.jasig.ssp.transferobject.AbstractAuditableTO#from(org.jasig.ssp.model.AbstractAuditable)}
	 * pulls the ID from the full Person object and would require an extra
	 * lookup for every entity sent through the Controllers (or anything that
	 * uses {@link AbstractAuditable} transfer objects).
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
	 * {@link org.jasig.ssp.transferobject.AbstractAuditableTO#from(org.jasig.ssp.model.AbstractAuditable)}
	 * pulls the ID from the full Person object and would require an extra
	 * lookup for every entity sent through the Controllers (or anything that
	 * uses AbstractAuditable transfer objects).
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Person modifiedBy;

	/**
	 * Entity status.
	 * 
	 * Most commonly {@link ObjectStatus#ACTIVE} or
	 * {@link ObjectStatus#INACTIVE}, but other enum values possible.
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private ObjectStatus objectStatus;

	protected abstract int hashPrime();

	@Override
	public abstract int hashCode();

	// Helper hashing methods to keep from providing 0 to any hashCode() result

	protected final int hashField(final String name, final UUID value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final ObjectStatus value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final String value) {
		return (StringUtils.isEmpty(value) ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final int value) {
		return (value == 0 ? name.hashCode() : value);
	}

	protected final int hashField(final String name, final Number value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}

	@Deprecated
	/**
	 * Two boolean fields that have reversed true/false values between to objects
	 * will return the same hashcode since these two primes are hard-coded.
	 * Therefore, each calling method must do this comparison itself with unique
	 * primes for each true/false value. So far 2 boolean fields, it would need
	 * 4 primes, 6 unique primes for 3 fields, and so on.
	 * 
	 */
	protected final int hashField(final String name, final boolean value) {
		throw new UnsupportedOperationException();
	}

	// full Integer class version is for nullable ints
	protected final int hashField(final String name, final Integer value) {
		return ((value == null) || (value == 0) ? name.hashCode() : value);
	}

	protected final int hashField(final String name, final Date value) {
		return ((value == null) || (value.getTime() == 0) ? name.hashCode()
				: value
						.hashCode());
	}

	protected final int hashField(final String name, final Auditable value) {
		return ((value == null) || (value.getId() == null) ? name.hashCode()
				: value.getId().hashCode());
	}

	// No hashField for Object type to make sure consumers explicitly pick one

	// No boolean because any non-hand-coded prime code could be duplicated if
	// more than one field is boolean for the class

	@Override
	final public boolean equals(final Object obj) {
		if (this == obj) {
			// exact references that point to the same place in memory are
			// always equal
			return true;
		}

		if (!(AbstractAuditable.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}

		final AbstractAuditable other = (AbstractAuditable) obj;

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

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void setId(final UUID id) {
		this.id = id;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	@Override
	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

	@Override
	public Person getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(final Person createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	@Override
	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	@Override
	public Person getModifiedBy() {
		return modifiedBy;
	}

	@Override
	public void setModifiedBy(final Person modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	@Override
	public void setObjectStatus(final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	/**
	 * Transient objects are not associated with an item already in storage. For
	 * instance, an object is transient if its id is null.
	 * 
	 * @return The object is associated or not with an item already in storage.
	 */
	@Override
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
	final protected boolean hasSameNonDefaultIdAs(
			final AbstractAuditable compareTo)
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