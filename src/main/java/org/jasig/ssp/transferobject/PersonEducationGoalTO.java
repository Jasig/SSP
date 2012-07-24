package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.PersonEducationGoal;

import com.google.common.collect.Lists;

public class PersonEducationGoalTO
		extends AbstractAuditableTO<PersonEducationGoal>
		implements TransferObject<PersonEducationGoal> {

	private UUID educationGoalId;

	private UUID personId;

	private String description, plannedOccupation, plannedMajor;
	private int howSureAboutMajor, howSureAboutOccupation;
	private boolean additionalAcademicProgramInformationNeeded, careerDecided, confidentInAbilities;	

	public PersonEducationGoalTO() {
		super();
	}

	public PersonEducationGoalTO(final PersonEducationGoal model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonEducationGoal model) {
		super.from(model);

		howSureAboutMajor = model.getHowSureAboutMajor();
		description = model.getDescription();
		plannedOccupation = model.getPlannedOccupation();
		if ((model.getEducationGoal() != null)
				&& (model.getEducationGoal().getId() != null)) {
			educationGoalId = model.getEducationGoal().getId();
		}
		
		plannedMajor = model.getPlannedMajor();
		careerDecided = model.isCareerDecided();
		howSureAboutOccupation = model.getHowSureAboutOccupation();
		confidentInAbilities = model.isConfidentInAbilities();
		additionalAcademicProgramInformationNeeded = model.isAdditionalAcademicProgramInformationNeeded();
	}

	public static List<PersonEducationGoalTO> toTOList(
			final Collection<PersonEducationGoal> models) {
		final List<PersonEducationGoalTO> tos = Lists.newArrayList();
		for (final PersonEducationGoal model : models) {
			tos.add(new PersonEducationGoalTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getEducationGoalId() {
		return educationGoalId;
	}

	public void setEducationGoalId(final UUID educationGoalId) {
		this.educationGoalId = educationGoalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(final String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	public int getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(final int howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedMajor() {
		return plannedMajor;
	}

	public void setPlannedMajor(final String plannedMajor) {
		this.plannedMajor = plannedMajor;
	}

	public int getHowSureAboutOccupation() {
		return howSureAboutOccupation;
	}

	public void setHowSureAboutOccupation(final int howSureAboutOccupation) {
		this.howSureAboutOccupation = howSureAboutOccupation;
	}

	public boolean isAdditionalAcademicProgramInformationNeeded() {
		return additionalAcademicProgramInformationNeeded;
	}

	public void setAdditionalAcademicProgramInformationNeeded(
			final boolean additionalAcademicProgramInformationNeeded) {
		this.additionalAcademicProgramInformationNeeded = additionalAcademicProgramInformationNeeded;
	}

	public boolean isCareerDecided() {
		return careerDecided;
	}

	public void setCareerDecided(final boolean careerDecided) {
		this.careerDecided = careerDecided;
	}

	public boolean isConfidentInAbilities() {
		return confidentInAbilities;
	}

	public void setConfidentInAbilities(final boolean confidentInAbilities) {
		this.confidentInAbilities = confidentInAbilities;
	}
}