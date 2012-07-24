package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.EducationGoal;

/**
 * Students may have an Education Goal stored for use in notifications to
 * appropriate users, and for reporting purposes.
 * 
 * Students may have one associated Education Goal instance (one-to-one
 * mapping). Non-student users should never have any education goals associated
 * to them.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationGoal
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -5687416606848336981L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "education_goal_id", nullable = true)
	private EducationGoal educationGoal;

	@Column(length = 50)
	@Size(max = 50)
	private String description;

	@Column(length = 50)
	@Size(max = 50)
	private String plannedOccupation;

	@Column(length = 128)
	@Size(max = 128)
	private String militaryBranchDescription;

	@Column()
	private int howSureAboutMajor;
	
	@Column(length = 50)
	@Size(max = 50)
	private String plannedMajor;
	
	@Column()
	private boolean careerDecided;
	
	@Column()
	private int howSureAboutOccupation;
	
	@Column()
	private boolean confidentInAbilities;
	
	@Column()
	private boolean additionalAcademicProgramInformationNeeded;
	
	public PersonEducationGoal() {
		super();
	}

	public PersonEducationGoal(final EducationGoal educationGoal) {
		super();
		this.educationGoal = educationGoal;
	}

	public EducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(final EducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(final int howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(final String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	public String getMilitaryBranchDescription() {
		return militaryBranchDescription;
	}

	public void setMilitaryBranchDescription(
			final String militaryBranchDescription) {
		this.militaryBranchDescription = militaryBranchDescription;
	}
	
	public String getPlannedMajor() {
		return plannedMajor;
	}

	public void setPlannedMajor(final String plannedMajor) {
		this.plannedMajor = plannedMajor;
	}

	public boolean isCareerDecided() {
		return careerDecided;
	}

	public void setCareerDecided(final boolean careerDecided) {
		this.careerDecided = careerDecided;
	}

	public int getHowSureAboutOccupation() {
		return howSureAboutOccupation;
	}

	public void setHowSureAboutOccupation(final int howSureAboutOccupation) {
		this.howSureAboutOccupation = howSureAboutOccupation;
	}

	public boolean isConfidentInAbilities() {
		return confidentInAbilities;
	}

	public void setConfidentInAbilities(final boolean confidentInAbilities) {
		this.confidentInAbilities = confidentInAbilities;
	}

	public boolean isAdditionalAcademicProgramInformationNeeded() {
		return additionalAcademicProgramInformationNeeded;
	}

	public void setAdditionalAcademicProgramInformationNeeded(
			final boolean additionalAcademicProgramInformationNeeded) {
		this.additionalAcademicProgramInformationNeeded = additionalAcademicProgramInformationNeeded;
	}

	@Override
	protected int hashPrime() {
		return 13;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:17 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonEducationGoal
		result *= hashField("educationGoal", educationGoal);
		result *= hashField("description", description);
		result *= hashField("plannedOccupation", plannedOccupation);
		result *= hashField("militaryBranchDescription",
				militaryBranchDescription);
		result *= hashField("howSureAboutMajor", howSureAboutMajor);
		result *= hashField("plannedMajor", plannedMajor);
		result *= careerDecided ? 3 : 5;
		result *= hashField("howSureAboutOccupation", howSureAboutOccupation);
		result *= confidentInAbilities ? 7 : 11;
		result *= additionalAcademicProgramInformationNeeded ? 13 : 17;
		
		return result;
	}
}