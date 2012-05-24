package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.reference.StudentStatus;

/**
 * Students should have some Education Plan stored for use in notifications to
 * appropriate users, and for reporting purposes.
 * 
 * Students may have one associated plan instance (one-to-one mapping).
 * Non-student users should never have any plan associated to them.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationPlan
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = 1818887030744791834L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_status_id", nullable = true)
	private StudentStatus studentStatus;

	@Column
	private boolean newOrientationComplete;

	@Column
	private boolean registeredForClasses;

	@Column
	private boolean collegeDegreeForParents;

	@Column
	private boolean specialNeeds;

	@Column(length = 2)
	@Size(max = 2)
	private String gradeTypicallyEarned;

	public StudentStatus getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(final StudentStatus studentStatus) {
		this.studentStatus = studentStatus;
	}

	public boolean isNewOrientationComplete() {
		return newOrientationComplete;
	}

	public void setNewOrientationComplete(final boolean newOrientationComplete) {
		this.newOrientationComplete = newOrientationComplete;
	}

	public boolean isRegisteredForClasses() {
		return registeredForClasses;
	}

	public void setRegisteredForClasses(final boolean registeredForClasses) {
		this.registeredForClasses = registeredForClasses;
	}

	public boolean isCollegeDegreeForParents() {
		return collegeDegreeForParents;
	}

	public void setCollegeDegreeForParents(final boolean collegeDegreeForParents) {
		this.collegeDegreeForParents = collegeDegreeForParents;
	}

	public boolean isSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(final boolean specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getGradeTypicallyEarned() {
		return gradeTypicallyEarned;
	}

	public void setGradeTypicallyEarned(final String gradeTypicallyEarned) {
		this.gradeTypicallyEarned = gradeTypicallyEarned;
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see #overwrite(PersonEducationPlan)
	 */
	public void overwrite(final PersonEducationPlan source) {
		setNewOrientationComplete(source.isNewOrientationComplete());
		setRegisteredForClasses(source.isRegisteredForClasses());
		setCollegeDegreeForParents(source.isCollegeDegreeForParents());
		setSpecialNeeds(source.isSpecialNeeds());
		setGradeTypicallyEarned(source.getGradeTypicallyEarned());
		setStudentStatus(source.getStudentStatus());
	}

	@Override
	protected int hashPrime() {
		return 19;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:14 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonEducationLevel
		result *= studentStatus == null ? "studentStatus".hashCode()
				: studentStatus.hashCode();
		result *= newOrientationComplete ? 3 : 5;
		result *= registeredForClasses ? 7 : 11;
		result *= collegeDegreeForParents ? 13 : 17;
		result *= specialNeeds ? 19 : 23;
		result *= StringUtils.isEmpty(gradeTypicallyEarned) ? "gradeTypicallyEarned"
				.hashCode()
				: gradeTypicallyEarned.hashCode();

		return result;
	}
}
