package org.studentsuccessplan.ssp.security;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.studentsuccessplan.ssp.model.Person;

public class SspUser extends User {

	public static final String ANONYMOUS_PERSON_FIRSTNAME = "Guest";
	public static final UUID ANONYMOUS_PERSON_ID = UUID
			.fromString("46DA4CB4-6EB4-4B91-8E39-8F9FA4D85552");
	public static final String ANONYMOUS_PERSON_LASTNAME = "User";
	public static final String ANONYMOUS_PERSON_USERNAME = "anonymousUser";

	public SspUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	private static final long serialVersionUID = -8125829986440987725L;

	private String emailAddress;

	private Person person;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object aThat) {
		boolean res = false;
		if (aThat == null) {
			return false;
		}

		if (aThat == this) {
			return true;
		}

		if (aThat.getClass() == this.getClass()) {
			// cast to native object is now safe
			SspUser that = (SspUser) aThat;

			// now a proper field-by-field evaluation can be made
			return (emailAddress == null ? (that.getEmailAddress() == null)
					: emailAddress.equals(that.getEmailAddress()))
					&& (person == null ? that.getPerson() == null : person
							.equals(that.getPerson()));
		}
		return res;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				// two randomly chosen prime numbers
				// if deriving: appendSuper(super.hashCode()).
				.appendSuper(super.hashCode()).append(emailAddress)
				.append(person).toHashCode();
	}
}
