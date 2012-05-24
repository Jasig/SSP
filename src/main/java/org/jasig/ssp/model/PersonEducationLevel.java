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
import org.jasig.ssp.model.reference.EducationLevel;

/**
 * Students may have zero or multiple Education Levels.
 * 
 * The PersonEducationLevel entity is an associative mapping between a student
 * (Person) and any Education Level information about them.
 * 
 * Non-student users should never have any assigned Education Levels.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationLevel // NOPMD by jon.adams on 5/24/12 1:34 PM
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -7969723552077396105L;

	@Column(length = 255)
	@Size(max = 255)
	private String description;

	private Integer lastYearAttended;

	private Integer highestGradeCompleted;

	private Integer graduatedYear;

	@Column(length = 128)
	@Size(max = 128)
	private String schoolName;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_level_id")
	private EducationLevel educationLevel;

	public PersonEducationLevel() {
		super();
	}

	public PersonEducationLevel(final Person person,
			final EducationLevel educationLevel) {
		super();
		this.person = person;
		this.educationLevel = educationLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getLastYearAttended() {
		return lastYearAttended;
	}

	public void setLastYearAttended(final Integer lastYearAttended) {
		this.lastYearAttended = lastYearAttended;
	}

	public Integer getHighestGradeCompleted() {
		return highestGradeCompleted;
	}

	public void setHighestGradeCompleted(final Integer highestGradeCompleted) {
		this.highestGradeCompleted = highestGradeCompleted;
	}

	public Integer getGraduatedYear() {
		return graduatedYear;
	}

	public void setGraduatedYear(final Integer graduatedYear) {
		this.graduatedYear = graduatedYear;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(final EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	@Override
	protected int hashPrime() {
		return 17;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:10 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonEducationLevel
		result *= StringUtils.isEmpty(description) ? "description".hashCode()
				: description.hashCode();
		result *= lastYearAttended == null ? "lastYearAttended".hashCode()
				: lastYearAttended.hashCode();
		result *= highestGradeCompleted == null ? "highestGradeCompleted"
				.hashCode() : highestGradeCompleted.hashCode();
		result *= graduatedYear == null ? "graduatedYear".hashCode()
				: graduatedYear.hashCode();
		result *= StringUtils.isEmpty(schoolName) ? "schoolName".hashCode()
				: schoolName.hashCode();
		result *= (person == null) || (person.getId() == null) ? "person"
				.hashCode() : person.getId().hashCode();
		result *= educationLevel == null ? "educationLevel".hashCode()
				: educationLevel.hashCode();

		return result;
	}
}
