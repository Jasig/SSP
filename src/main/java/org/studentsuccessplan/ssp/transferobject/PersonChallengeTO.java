package org.studentsuccessplan.ssp.transferobject;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.model.reference.Challenge;

public class PersonChallengeTO extends AuditableTO<PersonChallenge> 
		implements TransferObject<PersonChallenge> {

	private UUID challengeId, personId;
	private String description;
	
	public PersonChallengeTO() {
		super();
	}

	public PersonChallengeTO(PersonChallenge model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonChallenge model) {
		super.fromModel(model);

		setDescription(model.getDescription());
		
		if (model.getChallenge() != null 
				&& model.getChallenge().getId() != null) {
			setChallengeId(model.getChallenge().getId());
		}

		if (model.getPerson() != null 
				&& model.getPerson().getId() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	@Override
	public PersonChallenge pushAttributesToModel(PersonChallenge model) {
		super.addToModel(model);

		model.setDescription(getDescription());
		
		if (getChallengeId() != null) {
			model.setChallenge(new Challenge(getChallengeId()));
		}
		
		if (getPersonId() != null) {
			model.setPerson(new Person(getPersonId()));
		}
		
		return model;
	}

	@Override
	public PersonChallenge asModel() {
		return pushAttributesToModel(new PersonChallenge());
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
