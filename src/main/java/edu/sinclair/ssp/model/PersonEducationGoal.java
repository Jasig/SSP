package edu.sinclair.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.EducationGoal;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationGoal extends Auditable implements Serializable {

	private static final long serialVersionUID = -5687416606848336981L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_goal_id", nullable = true, insertable = false, updatable = false)
	private EducationGoal educationGoal;

	@Column(length = 50)
	@Size(max = 50)
	private String description;

	@Column(length = 50)
	@Size(max = 50)
	private String plannedOccupation;

	@Column()
	private int howSureAboutMajor;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(EducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public int getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(int howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(PersonEducationGoal)
	 */
	public void overwrite(PersonEducationGoal source) {
		this.setDescription(source.getDescription());
		this.setPlannedOccupation(source.getPlannedOccupation());
		this.setHowSureAboutMajor(source.getHowSureAboutMajor());
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(PersonEducationGoal)
	 */
	public void overwriteWithCollections(PersonEducationGoal source) {
		this.overwrite(source);

		this.getEducationGoal().overwrite(source.getEducationGoal());
	}
}
