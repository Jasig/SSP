/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jasig.ssp.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SspUser extends User implements Serializable {

	public static final String ANONYMOUS_PERSON_FIRSTNAME = "Guest";
	public static final UUID ANONYMOUS_PERSON_ID = UUID
			.fromString("46DA4CB4-6EB4-4B91-8E39-8F9FA4D85552");
	public static final String ANONYMOUS_PERSON_LASTNAME = "User";
	public static final String ANONYMOUS_PERSON_USERNAME = "anonymousUser";
	private static final String REQUEST_PERSON_KEY = "org.jasig.ssp.security.sspuser.person";

	private static final long serialVersionUID = -8125829986440987725L;

	private String emailAddress;

	private ThreadLocal<Person> person = new ThreadLocal<Person>();

	public SspUser(final String username, final String password,
			final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired,
			final boolean accountNonLocked,
			final Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Person getPerson() {
		// Prefer Spring-managed RequestAttributes b/c they're automatically
		// cleaned up per request (it's really just ThreadLocals underneath
		// but spring makes sure they're released at the end of the
		// request/response cycle)
		final RequestAttributes requestAttributes =
				RequestContextHolder.getRequestAttributes();
		if ( requestAttributes == null ) {
			// most likely running outside a web request, e.g. a scheduled job.
			// fall back to our own threadlocal and it's up to the caller to
			// cleanup properly
			return person.get();
		}
		return (Person)requestAttributes
				.getAttribute(REQUEST_PERSON_KEY, RequestAttributes.SCOPE_REQUEST);
	}

	public void setPerson(final Person person) {
		// see getPerson() for the whys
		final RequestAttributes requestAttributes =
				RequestContextHolder.getRequestAttributes();
		if ( requestAttributes == null ) {
			this.person.set(person);
		} else {
			requestAttributes.setAttribute(REQUEST_PERSON_KEY, person, RequestAttributes.SCOPE_REQUEST);
		}
	}

	@Override
	public boolean equals(final Object aThat) {
		if (aThat == null) {
			return false;
		}

		if (aThat == this) {
			return true;
		}

		if (aThat.getClass() == this.getClass()) {
			// cast to native object is now safe
			final SspUser that = (SspUser) aThat;

			// now a proper field-by-field evaluation can be made
			return (emailAddress == null ? (that.getEmailAddress() == null)
					: emailAddress.equals(that.getEmailAddress()))
					&& (person == null ? that.getPerson() == null : person
							.equals(that.getPerson()));
		}
		return false;
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
