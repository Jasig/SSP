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
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jasig.ssp.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.collect.Lists;

/**
 * Design note on the <code>ThreadLocal</code> internal fields (SSP-854).
 * These are a hacks to deal with the fact that <code>SspUser</code> is used
 * both as a HTTP session-scoped object representing the session owner and as a
 * transient object acting as a wrapper around arbitrary <code>Person</code>
 * objects. When used in the latter mode, there might be several
 * <code>SspUser</code> instances created per request, each representing a
 * different user identity. The session-scoped mode necessitates thread-local
 * storage of the associated <code>Person</code> to avoid Hibernate lazy-load
 * issues. Specifically, while the authenticated {@code SspUser} is a singleton
 * scoped to the HTTP session, the {@link Person} to which that {@link SspUser}
 * refers needs to be scoped to each <em>Hibernate</em> session that accesses
 * that HTTP session, i.e. each {@code Thread} that accesses the latter.
 *
 * <p>But it is hard for us to implement uniform filter-based or
 * Spring <code>RequestAttributes</code>-based cleanup b/c the
 * transient mode means we can't have a static ThreadLocal for storing the
 * current <code>Person</code> - we don't know when a given SspUser represents
 * "currentness" or just any <code>Person</code> pointer/holder. Another
 * <code>ThreadLocal</code> mechanism works around this by registering each
 * call to {@link #setPerson(org.jasig.ssp.model.Person)} with thread-scoped
 * collection. Portlet and Servlet filters are usually responsible for cleaning
 * up all associated <code>SspUser</code> instances via {@link #afterRequest()}.
 */
public class SspUser extends User implements Serializable {

	public static final String ANONYMOUS_PERSON_FIRSTNAME = "Guest";
	public static final UUID ANONYMOUS_PERSON_ID = UUID
			.fromString("46DA4CB4-6EB4-4B91-8E39-8F9FA4D85552");
	public static final String ANONYMOUS_PERSON_LASTNAME = "User";
	public static final String ANONYMOUS_PERSON_USERNAME = "anonymousUser";
	private static final String REQUEST_PERSON_KEY = "org.jasig.ssp.security.sspuser.person";

	private static final long serialVersionUID = -8125829986440987725L;

	private String emailAddress;

	// Currently cannot be static b/c might have an arbitrary number of
	// SspUser's running around in the current thread, each with a different
	// identity. This is also the reason we can't use Spring's
	// RequestAttributes to store the "current" Person. See class level
	// comments for more on this and cleanupQueue
	private final transient ThreadLocal<Person> person = new ThreadLocal<Person>();

	private static final ThreadLocal<Collection<SspUser>> cleanupQueue =
			new ThreadLocal<Collection<SspUser>>();

	public SspUser(final String username, final String password,
			final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired,
			final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
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
		return person.get();
	}

	public void setPerson(final Person person) {
		this.person.set(person);
		addToCleanupQueue(this);
	}

	/**
	 * See class-level comments for an explanation.
	 * @param sspUser
	 */
	private void addToCleanupQueue(SspUser sspUser) {
		// see notes in cleanup()
		Collection<SspUser> queue = cleanupQueue.get();
		if ( queue == null ) {
			queue = Lists.newArrayList();
			cleanupQueue.set(queue);
		}
		queue.add(sspUser);
	}

	/**
	 * See class-level comments for an explanation.
	 */
	public static void afterRequest() {
		Collection<SspUser> queue = cleanupQueue.get();
		if ( queue == null ) {
			return;
		}
		Iterator<SspUser> queueIter = queue.iterator();
		while ( queueIter.hasNext() ) {
			SspUser user = queueIter.next();
			queueIter.remove();
			try {
				user.afterRequestInternal();
			} catch ( Exception e ) {
				// nothing to do
			}

		}
	}

	private void afterRequestInternal() {
		this.person.remove();
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

    public static int cleanupQueueSize() {
        return cleanupQueue.get() == null ? 0 : cleanupQueue.get().size();
    }
}
