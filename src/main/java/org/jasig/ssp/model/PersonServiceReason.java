package org.jasig.ssp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.reference.ServiceReason;

/**
 * Assign a Person to a ServiceReason
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonServiceReason
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -3685614932117902730L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_reason_id", updatable = false, nullable = false)
	private ServiceReason serviceReason;

	public PersonServiceReason() {
		super();
	}

	public PersonServiceReason(final Person person,
			final ServiceReason serviceReason) {
		super();
		this.person = person;
		this.serviceReason = serviceReason;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public ServiceReason getServiceReason() {
		return serviceReason;
	}

	public void setServiceReason(
			final ServiceReason serviceReason) {
		this.serviceReason = serviceReason;
	}

	@Override
	protected int hashPrime() {
		return 293;
	}

	@Override
	final public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonServiceReason
		result *= hashField("person", person);
		result *= hashField("serviceReason", serviceReason);

		return result;
	}
}