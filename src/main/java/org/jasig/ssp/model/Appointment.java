package org.jasig.ssp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Appointment extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -2067714338529887268L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@Override
	protected int hashPrime() {
		return 347;
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// Appointment
		result *= hashField("startTime", startTime);
		result *= hashField("endTime", endTime);
		result *= hashField("expirationDate", expirationDate);
		result *= hashField("person", person);
		return result;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public Date getStartTime() {
		return (startTime == null) ? null : new Date(startTime.getTime());
	}

	public void setStartTime(final Date startTime) {
		this.startTime = (startTime == null) ? null : new Date(
				startTime.getTime());
	}

	public Date getEndTime() {
		return (endTime == null) ? null : new Date(endTime.getTime());
	}

	public void setEndTime(final Date endTime) {
		this.endTime = (endTime == null) ? null : new Date(
				endTime.getTime());
	}

	public Date getExpirationDate() {
		return (expirationDate == null) ? null : new Date(
				expirationDate.getTime());
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = (expirationDate == null) ? null : new Date(
				expirationDate.getTime());
	}

}
