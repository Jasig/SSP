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
package org.jasig.ssp.service.impl;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.SspUserDetailsService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Collection;

@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityServiceImpl.class);

	/**
	 * You'll get into an infinite loop if you accidentally load the current
	 * user from persistence during a Hibernate flush. So we always need to
	 * give the environment at least a shot at short-circuiting that lookup by
	 * caching the current SspUser in a ThreadLocal in the event the
	 * current SecurityContext isn't doing it for us (as is the case with an
	 * authenticated OAuth2 request, for example).
	 */
	private static final ThreadLocal<SspUser> currentSspUserFallback = new ThreadLocal<SspUser>();

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SspUserDetailsService sspUserDetailsService;

	@Override
	public SspUser anonymousUser() {

		LOGGER.debug("Using the Anonymous User");

		final SspUser user = new SspUser(SspUser.ANONYMOUS_PERSON_USERNAME,
				"", true, true, true, true,
				new ArrayList<GrantedAuthority>(0));

		user.setPerson(personService.load(SspUser.ANONYMOUS_PERSON_ID));

		return user;
	}

	@Override
	public SspUser noAuthAdminUser() {
		LOGGER.debug("Using the No Authentication Admin User");

		final SspUser user = new SspUser("no auth admin user", "", false,
				false, false, false, new ArrayList<GrantedAuthority>(0));

		user.setPerson(personService.load(Person.SYSTEM_ADMINISTRATOR_ID));

		return user;
	}

	/**
	 * Please see (at least) {@link #currentlyAuthenticatedUser()} and other
	 * call sites before changing behaviors here. This is an idiosyncratic
	 * function, but some of those idiosyncracies are depended on.
	 *
	 * @return
	 */
	@Override
	public SspUser currentUser() {
		SspUser sspUser = null;

		// assumption: SecurityContext only returns null for Authentication when
		// there is no actual web context

		final Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();


		if (null == auth) {
			// not authenticated, return null
			return null;
		}

		if (auth.isAuthenticated()) {

			final Object principal = auth.getPrincipal();

			if (principal instanceof SspUser) {

				sspUser = (SspUser) principal;
            } else if ( principal instanceof ConsumerDetails ) {

                ConsumerDetails consumerDetails = (ConsumerDetails)principal;
                String consumerKey = consumerDetails.getConsumerKey();

                // The threadlocal business here isn't just for perf...
                // you'll get into an infinite loop if Hib tries to lookup
                // a current user in persistence during a flush.
                if ( currentSspUserFallback.get() == null ||
                        !(consumerKey.equalsIgnoreCase(currentSspUserFallback.get().getUsername())) ) {

                    // Careful! This will try to create a new Person record
                    // if it can't find one with the given username. This
                    // isn't what we want on an OAuth request, but we
                    // can be reasonably sure that's not going to happen
                    // because we know this particular request has already
                    // been authenticated, which means the corresponding
                    // Person record almost certainly exists.

                    currentSspUserFallback.set((SspUser) sspUserDetailsService.loadUserDetails(
                            consumerKey, auth.getAuthorities()));

                }
                sspUser = currentSspUserFallback.get();

			} else if (principal instanceof String ) {

				if (SspUser.ANONYMOUS_PERSON_USERNAME.equalsIgnoreCase(((String)principal))) {
						sspUser = anonymousUser();

                } else if ( auth instanceof OAuth2Authentication ) {

                    // Would rather not have this coupling to OAuth2 here, but
                    // the alternative would be to change the behavior in the
                    // else clause below to *always* try to load a SspUser given
                    // the string Principal value. But the "return null"
                    // behavior in that else clause has been there a long time,
                    // so unsure whether we'd be introducing regressions by
                    // changing it.
                    //
                    // The threadlocal business here isn't just for perf...
                    // you'll get into an infinite loop if Hib tries to lookup
                    // a current user in persistence during a flush.
                    if ( currentSspUserFallback.get() == null ||
                            !(((String) principal).equalsIgnoreCase(currentSspUserFallback.get().getUsername())) ) {

                        // Careful! This will try to create a new Person record
                        // if it can't find one with the given username. This
                        // isn't what we want on an OAuth request, but we
                        // can be reasonably sure that's not going to happen
                        // because we know this particular request has already
                        // been authenticated, which means the corresponding
                        // Person record almost certainly exists.
                        currentSspUserFallback.set((SspUser) sspUserDetailsService.loadUserDetails(
                                (String) principal, auth.getAuthorities()));
                    }
                    sspUser = currentSspUserFallback.get();

                } else {

					LOGGER.error("Just tried to get an sspUser object from a user that is "
							+ principal);
					// authenticated, but something is wrong with the principal
					return null;
				}

			} else {

    			// authenticated, but something is wrong with the principal
				LOGGER.error("Just tried to get an sspUser object from an object that is really a "
						+ principal.toString());
				return null;
			}
		} else {

     		// not authenticated, return null
			return null;
		}

		if (sspUser.getPerson() == null) {
			try {
				sspUser.setPerson(personService.personFromUsername(sspUser
						.getUsername()));
			} catch (ObjectNotFoundException e) {

				return null;
			}
		}

		return sspUser;
	}

	@Override
	public SspUser currentFallingBackToAdmin() {
		final SspUser user = currentUser();

		if (null == user) {
			return noAuthAdminUser();
		} else {
			return user;
		}
	}

	/**
	 * Please be sure to check dependencies, esp
	 * {@code ScheduledTaskWrapperServiceImpl} before changing any of the
	 * behavior here. E.g. you may think it's odd that the anonymous user
	 * is not considered authenticated (b/c that's not how SpringSecurity thinks
	 * about it), but we do have code that depends on that fact.
	 *
	 * @return
	 */
	@Override
	public SspUser currentlyAuthenticatedUser() {
		final SspUser sspUser = currentUser();

		if (sspUser == null) {
			LOGGER.error("User is not authenticated");

		} else if (SspUser.ANONYMOUS_PERSON_USERNAME.equals(sspUser
				.getUsername())) {
			LOGGER.error("Is anonymous user");
			return null;

		} else if (sspUser.getPerson() == null) {
			LOGGER.error("User is not in the person table");
			return null;

		}

		return sspUser;
	}

	@Override
	public boolean isAuthenticated() {
		final boolean authenticated = (SecurityContextHolder.getContext()
				.getAuthentication() != null)
				&& SecurityContextHolder.getContext().getAuthentication()
						.isAuthenticated();

		final SspUser currentUser = currentUser();

		if (authenticated) {
			if ((currentUser == null)) {
				LOGGER.error("User is authenticated, but not in the person table");
				return false;
			} else {
				LOGGER.error("User is authenticated");
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAuthority(final String authority) {

		final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();

		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equalsIgnoreCase(authority)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getSessionId() {
		if ( SecurityContextHolder.getContext()
				.getAuthentication() instanceof OAuth2Authentication || SecurityContextHolder.getContext().getAuthentication() instanceof ConsumerAuthentication ) {
			// The session ID lookup below will force creation of a HTTP Session
			// which is undesirable for an OAuth2 request. Currently we only
			// support client_credentials authorization grants for OAuth2, for
			// which there really isn't any good value to use as a session ID,
			// e.g. the same token ID might be shared across many different app
			// instances and users. We only use the session ID for anonymous
			// use cases, though, and if you've got an OAuth2Authentication,
			// you're not anonymous. So there's not really a need to concoct an
			// OAuth2-ish session ID.
			return null;
		}

		return RequestContextHolder.currentRequestAttributes().getSessionId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void afterRequest() {

		currentSspUserFallback.remove();
		SspUser.afterRequest();
	}
}
