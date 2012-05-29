package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonReferralSource;

import com.google.common.collect.Lists;

public class PersonReferralSourceTO
		extends AbstractAuditableTO<PersonReferralSource>
		implements TransferObject<PersonReferralSource> {

	@NotNull
	private UUID referralSourceId;

	@NotNull
	private UUID personId;

	public PersonReferralSourceTO() {
		super();
	}

	public PersonReferralSourceTO(final PersonReferralSource model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonReferralSource model) {
		super.from(model);

		if (model.getReferralSource() != null) {
			setReferralSourceId(model.getReferralSource().getId());
		}

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonReferralSourceTO> toTOList(
			final Collection<PersonReferralSource> models) {
		final List<PersonReferralSourceTO> tos = Lists.newArrayList();
		for (final PersonReferralSource model : models) {
			tos.add(new PersonReferralSourceTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getReferralSourceId() {
		return referralSourceId;
	}

	public final void setReferralSourceId(final UUID referralSourceId) {
		this.referralSourceId = referralSourceId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

}
