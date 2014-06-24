/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
 /*
 * IRSC CUSTOMIZATIONS
 * 06/17/2014 - Jonathan Hart IRSC TAPS 20140039 - Add Faculty Interventions to EA
 */
package org.jasig.ssp.model;

import java.util.Date;
import java.util.List;
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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.EarlyAlertIntervention; //TAPS 20140039
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

	@Column(nullable = true, length = 25)
	private String courseTermCode;

	@Column(name = "email_cc", nullable = true, length = 255)
	private String emailCC;

	@ManyToOne
	@JoinColumn(name = "campus_id", nullable = false)
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

	@ManyToOne
	@JoinColumn(name = "closed_by_id", nullable = true)
	private Person closedBy;
	
	private static final String RESPONSE_COUNT_FORMULA = "(select count(*) from early_alert_response ear where ear.early_alert_id = ID)";

	private static final String RESPONSE_DATES_FORMULA = "(select max(ear.modified_date) from early_alert_response ear "
			+ "where ear.early_alert_id = id)";

	
	@Formula(RESPONSE_COUNT_FORMULA)
	private int responseCount;
	
	@Formula(RESPONSE_DATES_FORMULA)
	private Date lastResponseDate;
	/**
	 * Associated person. Changes to this Person <i>are</i> persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	// TODO: eager loading makes more sense, but causes cartesian results. so
	// hold off optimizing performance until the performance pass of the system.
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "early_alert_early_alert_reason",
			joinColumns = @JoinColumn(name = "early_alert_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_reason_id"))
	private Set<EarlyAlertReason> earlyAlertReasonIds = Sets.newHashSet();

	// TODO: eager loading makes more sense, but causes cartesian results. so
	// hold off optimizing performance until the performance pass of the system.
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "early_alert_early_alert_suggestion",
			joinColumns = @JoinColumn(name = "early_alert_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_suggestion_id"))
	private Set<EarlyAlertSuggestion> earlyAlertSuggestions = Sets
			.newHashSet();
			
	//TAPS 20140039 BEGIN
	// TODO: eager loading makes more sense, but causes cartesian results. so
	// hold off optimizing performance until the performance pass of the system.
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "early_alert_early_alert_intervention",
			joinColumns = @JoinColumn(name = "early_alert_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_intervention_id"))
	private Set<EarlyAlertIntervention> earlyAlertInterventions = Sets
			.newHashSet();
	//TAPS 20140039 END

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set; optional; max length 80 characters
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
	 *            the courseTitle to set; optional; max length 255 characters
	 */
	public void setCourseTitle(final String courseTitle) {
		this.courseTitle = courseTitle;
	}

	/**
	 * @return the courseTermCode
	 */
	public String getCourseTermCode() {
		return courseTermCode;
	}

	/**
	 * @param courseTermCode
	 *            the courseTermCode to set; optional; max length 25 characters
	 */
	public void setCourseTermCode(final String courseTermCode) {
		this.courseTermCode = courseTermCode;
	}

	/**
	 * @return the emailCC
	 */
	public String getEmailCC() {
		return emailCC;
	}

	/**
	 * @param emailCC
	 *            the emailCC to set; optional; max length 255 characters
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
	 *            the earlyAlertReasonOtherDescription to set; optional; max
	 *            length 64000 characters
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
	 *            the earlyAlertSuggestionOtherDescription to set; optional; max
	 *            length 64000 characters
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
	 *            the comment; optional; max length 64000 characters
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
		return closedBy == null ? null : closedBy.getId();
	}

	public Person getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(Person closedBy) {
		this.closedBy = closedBy;
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
	public Set<EarlyAlertReason> getEarlyAlertReasons() {
		return earlyAlertReasonIds;
	}

	/**
	 * @param earlyAlertReasonIds
	 *            the earlyAlertReasonIds to set
	 */
	public void setEarlyAlertReasons(
			final Set<EarlyAlertReason> earlyAlertReasonIds) {
		this.earlyAlertReasonIds = earlyAlertReasonIds;
	}

	/**
	 * @return the earlyAlertSuggestionIds
	 */
	public Set<EarlyAlertSuggestion> getEarlyAlertSuggestions() {
		return earlyAlertSuggestions;
	}

	/**
	 * @param earlyAlertSuggestionIds
	 *            the earlyAlertSuggestionIds to set
	 */
	public void setEarlyAlertSuggestions(
			final Set<EarlyAlertSuggestion> earlyAlertSuggestionIds) {
		this.earlyAlertSuggestions = earlyAlertSuggestionIds;
	}
	
	//TAPS 20140039 BEGIN
	/**
	 * @return the earlyAlertInterventionIds
	 */
	public Set<EarlyAlertIntervention> getEarlyAlertInterventions() {
		return earlyAlertInterventions;
	}

	/**
	 * @param earlyAlertInterventionIds
	 *            the earlyAlertInterventionIds to set
	 */
	public void setEarlyAlertInterventions(
			final Set<EarlyAlertIntervention> earlyAlertInterventionIds) {
		this.earlyAlertInterventions = earlyAlertInterventionIds;
	}
	//TAPS 20140039 END

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
		result *= hashField("courseTermCode", courseTermCode);
		result *= hashField("emailCC", emailCC);
		result *= hashField("campus", campus);
		result *= hashField("earlyAlertReasonOtherDescription",
				earlyAlertReasonOtherDescription); //TAPS 20140039
		result *= hashField("earlyAlertSuggestionOtherDescription",
				earlyAlertSuggestionOtherDescription); //TAPS 20140039
		result *= hashField("comment", comment);
		result *= hashField("person", person);
		result *= hashField("closedDate", closedDate);
		result *= hashField("closedById", closedBy == null ? null : closedBy.getId());

		return result;
	}

	public int getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}

	public Date getLastResponseDate() {
		return lastResponseDate;
	}

	public void setResponseDates(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
	}
	
}