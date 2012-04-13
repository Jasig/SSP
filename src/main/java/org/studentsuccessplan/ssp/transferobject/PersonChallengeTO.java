package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.model.reference.Challenge;

import com.google.common.collect.Lists;

public class PersonChallengeTO
		extends AuditableTO<PersonChallenge>
		implements TransferObject<PersonChallenge> {

	@NotNull
	private UUID challengeId;

	@NotNull
	private UUID personId;

	private String description;

	public PersonChallengeTO() {
		super();
	}

	public PersonChallengeTO(final PersonChallenge model) {
		super();
		fromModel(model);
	}

	@Override
	public final void fromModel(final PersonChallenge model) {
		super.fromModel(model);

		setDescription(model.getDescription());

		if ((model.getChallenge() != null)
				&& (model.getChallenge().getId() != null)) {
			setChallengeId(model.getChallenge().getId());
		}

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			setPersonId(model.getPerson().getId());
		}
	}

	@Override
	public PersonChallenge addToModel(final PersonChallenge model) {
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
		return addToModel(new PersonChallenge());
	}

	public static List<PersonChallengeTO> listToTOList(
			final List<PersonChallenge> models) {
		final List<PersonChallengeTO> tos = Lists.newArrayList();
		for (PersonChallenge model : models) {
			tos.add(new PersonChallengeTO(model));
		}
		return tos;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public final void setChallengeId(final UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getDescription() {
		return description;
	}

	public final void setDescription(final String description) {
		this.description = description;
	}
}
