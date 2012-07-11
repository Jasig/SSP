package org.jasig.ssp.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.util.uuid.UUIDCustomType;

import com.google.common.collect.Sets;

/**
 * EarlyAlert
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TypeDef(name = "uuid-custom", typeClass = UUIDCustomType.class)
public class EarlyAlert // NOPMD by jon.adams on 5/24/12 1:29 PM
		extends AbstractAuditable
		implements PersonAssocAuditable { // NOPMD

	private static final long serialVersionUID = 8141595549982881039L;

	public static final String CUSTOM_GROUP_NAME = "Custom";

	@Column(nullable = true, length = 80)
	private String courseName;

	@Column(nullable = true, length = 255)
	private String courseTitle;

	@Column(name = "email_cc", nullable = true, length = 255)
	private String emailCC;

	@ManyToOne
	@JoinColumn(name = "campus_id", nullable = true)
	private Campus campus;

	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String earlyAlertReasonOtherDescription;

	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String earlyAlertSuggestionOtherDescription;

	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String comment;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedDate;

	@Column(nullable = true)
	@Type(type = "uuid-custom")
	private UUID closedById;

	/**
	 * Associated person. Changes to this Person <i>are</i> persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_early_alert_reason",
			joinColumns = @JoinColumn(name = "early_alert_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_reason_id"))
	private Set<EarlyAlertReason> earlyAlertReasonIds = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_early_alert_suggestion",
			joinColumns = @JoinColumn(name = "early_alert_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_suggestion_id"))
	private Set<EarlyAlertSuggestion> earlyAlertSuggestionIds = Sets
			.newHashSet();

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle
	 *            the courseTitle to set
	 */
	public void setCourseTitle(final String courseTitle) {
		this.courseTitle = courseTitle;
	}

	/**
	 * @return the emailCC
	 */
	public String getEmailCC() {
		return emailCC;
	}

	/**
	 * @param emailCC
	 *            the emailCC to set
	 */
	public void setEmailCC(final String emailCC) {
		this.emailCC = emailCC;
	}

	/**
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * @param campus
	 *            the campus to set
	 */
	public void setCampus(final Campus campus) {
		this.campus = campus;
	}

	/**
	 * @return the earlyAlertReasonOtherDescription
	 */
	public String getEarlyAlertReasonOtherDescription() {
		return earlyAlertReasonOtherDescription;
	}

	/**
	 * @param earlyAlertReasonOtherDescription
	 *            the earlyAlertReasonOtherDescription to set
	 */
	public void setEarlyAlertReasonOtherDescription(
			final String earlyAlertReasonOtherDescription) {
		this.earlyAlertReasonOtherDescription = earlyAlertReasonOtherDescription;
	}

	/**
	 * @return the earlyAlertSuggestionOtherDescription
	 */
	public String getEarlyAlertSuggestionOtherDescription() {
		return earlyAlertSuggestionOtherDescription;
	}

	/**
	 * @param earlyAlertSuggestionOtherDescription
	 *            the earlyAlertSuggestionOtherDescription to set
	 */
	public void setEarlyAlertSuggestionOtherDescription(
			final String earlyAlertSuggestionOtherDescription) {
		this.earlyAlertSuggestionOtherDescription = earlyAlertSuggestionOtherDescription;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/**
	 * @return the closedDate
	 */
	public Date getClosedDate() {
		return closedDate == null ? null : new Date(closedDate.getTime());
	}

	/**
	 * @param closedDate
	 *            the closedDate to set
	 */
	public void setClosedDate(final Date closedDate) {
		this.closedDate = closedDate == null ? null : new Date(
				closedDate.getTime());
	}

	/**
	 * @return the closedById
	 */
	public UUID getClosedById() {
		return closedById;
	}

	/**
	 * @param closedById
	 *            the closedById to set
	 */
	public void setClosedById(final UUID closedById) {
		this.closedById = closedById;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	/**
	 * @return the earlyAlertReasonIds
	 */
	public Set<EarlyAlertReason> getEarlyAlertReasonIds() {
		return earlyAlertReasonIds;
	}

	/**
	 * @param earlyAlertReasonIds
	 *            the earlyAlertReasonIds to set
	 */
	public void setEarlyAlertReasonIds(
			final Set<EarlyAlertReason> earlyAlertReasonIds) {
		this.earlyAlertReasonIds = earlyAlertReasonIds;
	}

	/**
	 * @return the earlyAlertSuggestionIds
	 */
	public Set<EarlyAlertSuggestion> getEarlyAlertSuggestionIds() {
		return earlyAlertSuggestionIds;
	}

	/**
	 * @param earlyAlertSuggestionIds
	 *            the earlyAlertSuggestionIds to set
	 */
	public void setEarlyAlertSuggestionIds(
			final Set<EarlyAlertSuggestion> earlyAlertSuggestionIds) {
		this.earlyAlertSuggestionIds = earlyAlertSuggestionIds;
	}

	@Override
	protected int hashPrime() {
		return 179;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 1:50 PM
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// EarlyAlert
		result *= hashField("courseName", courseName);
		result *= hashField("courseTitle", courseTitle);
		result *= hashField("emailCC", emailCC);
		result *= hashField("campus", campus);
		result *= hashField("earlyAlertSuggestionOtherDescription",
				earlyAlertReasonOtherDescription);
		result *= hashField("comment", comment);
		result *= hashField("person", person);
		result *= hashField("closedDate", closedDate);
		result *= hashField("closedById", closedById);

		return result;
	}
}