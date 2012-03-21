package edu.sinclair.ssp.model.tool;

import java.util.List;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.model.PersonDemographics;
import edu.sinclair.ssp.model.PersonEducationGoal;
import edu.sinclair.ssp.model.PersonEducationLevel;
import edu.sinclair.ssp.model.PersonEducationPlan;
import edu.sinclair.ssp.model.PersonFundingSource;

public class IntakeForm {

	private Person person;

	private PersonDemographics personDemographics;

	private PersonEducationGoal personEducationGoal;

	private List<PersonEducationLevel> personEducationLevels;

	private PersonEducationPlan personEducationPlan;

	private List<PersonFundingSource> personFundingSources;

	private List<PersonChallenge> personChallenges;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonDemographics getPersonDemographics() {
		return personDemographics;
	}

	public void setPersonDemographics(PersonDemographics personDemographics) {
		this.personDemographics = personDemographics;
	}

	public PersonEducationGoal getPersonEducationGoal() {
		return personEducationGoal;
	}

	public void setPersonEducationGoal(PersonEducationGoal personEducationGoal) {
		this.personEducationGoal = personEducationGoal;
	}

	public List<PersonEducationLevel> getPersonEducationLevels() {
		return personEducationLevels;
	}

	public void setPersonEducationLevels(
			List<PersonEducationLevel> personEducationLevels) {
		this.personEducationLevels = personEducationLevels;
	}

	public PersonEducationPlan getPersonEducationPlan() {
		return personEducationPlan;
	}

	public void setPersonEducationPlan(PersonEducationPlan personEducationPlan) {
		this.personEducationPlan = personEducationPlan;
	}

	public List<PersonFundingSource> getPersonFundingSources() {
		return personFundingSources;
	}

	public void setPersonFundingSources(
			List<PersonFundingSource> personFundingSources) {
		this.personFundingSources = personFundingSources;
	}

	public List<PersonChallenge> getPersonChallenges() {
		return personChallenges;
	}

	public void setPersonChallenges(List<PersonChallenge> personChallenges) {
		this.personChallenges = personChallenges;
	}

}
