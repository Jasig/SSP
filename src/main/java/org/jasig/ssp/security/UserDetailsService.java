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

import java.util.Collection;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.dao.PersonExistsException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.exception.EmailNotFoundException;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * UserDetailsService that requires the authorities to already be determined via
 * ldap, uportal, etc...
 *
 */
public class UserDetailsService implements SspUserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDetailsService.class);

	@Autowired
	private transient PersonService personService;

	@Override
	public UserDetails loadUserDetails(final String username,
			final Collection<? extends GrantedAuthority> authorities) {
		Person person = null;

		try {
			person = personService.personFromUsername(username);
		} catch (ObjectNotFoundException e) {

			LOGGER.info(
					"Unable to load {}'s record., creating user in ssp",
					username);
			try {
				// We happen to know that the PersonService.createUserAccount()
				// impl always runs in its own transaction, which means the
				// returned Person isn't actually bound to our Hib session, so
				// we have to reload it here.
				Person createdPerson =
						personService.createUserAccount(username, (Collection<GrantedAuthority>)authorities);
				try {
					person = personService.get(createdPerson.getId());
				} catch ( ObjectNotFoundException eee ) {
					throw new UsernameNotFoundException("User with username ["
							+ username + "] could not be read back after being created.");
				}
			} catch ( ObjectExistsException ee ) {
				try {
					person = personService.personFromUsername(username);
				} catch ( ObjectNotFoundException eee ) {
					throw new UnableToCreateAccountException(
							"Couldn't create account with username" + username
							+ " because an account with that username seemed"
							+ " to already exist, but was unable to load that"
							+ " existing account.", eee);
				}
			} catch ( PersonExistsException ee ) {
				try {
					person = personService.personFromUsername(username);
				} catch ( ObjectNotFoundException eee ) {
					throw new UnableToCreateAccountException(
							"Couldn't create account with username" + username
									+ " because an account with that username seemed"
									+ " to already exist, but was unable to load that"
									+ " existing account.", eee);
				}
			}
		}

		if (person.isDisabled()) {
			LOGGER.error("User is disabled: {}", username);
			throw new UserNotEnabledException("User is disabled.");
		}

		final SspUser sspUser = new SspUser(username, "password",
				true, true, true, true, (Collection<GrantedAuthority>)authorities);

		sspUser.setPerson(person);

		if (person.getEmailAddresses().isEmpty()) {
			LOGGER.error("User {} has no email address", username);
			throw new EmailNotFoundException(
					"User does not have an assigned email address, support has been notified.");
		} else {
			sspUser.setEmailAddress(person.getEmailAddresses().get(0));
		}

		LOGGER.debug("Loaded User: {}", sspUser.toString());

		return sspUser;
	}

	@Override
	public UserDetails loadUserDetails(final Authentication token)
			throws UsernameNotFoundException {
		return loadUserDetails((String) token.getPrincipal(),
				(Collection<GrantedAuthority>) token.getAuthorities());
	}

	@Override
	public UserDetails mapUserFromContext(final DirContextOperations ctx,
			final String username, final Collection<? extends GrantedAuthority> authority) {
		return loadUserDetails(username, (Collection<GrantedAuthority>) authority);
	}

	@Override
	public void mapUserToContext(final UserDetails user,
			final DirContextAdapter ctx) {
		throw new UnsupportedOperationException("Not applicable");
	}

}
