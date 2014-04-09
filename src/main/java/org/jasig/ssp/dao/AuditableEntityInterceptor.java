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
package org.jasig.ssp.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.SecurityService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Intercepts Hibernate writes to automatically fill the created and modified
 * author and time stamp fields of any model that derives from the
 * {@link org.jasig.ssp.model.AbstractAuditable} class.
 */
@Service
public class AuditableEntityInterceptor extends EmptyInterceptor implements // NOPMD
		ApplicationContextAware {

	private static final long serialVersionUID = 1L;

	private transient ApplicationContext context;

	private transient SecurityService securityService;

	/**
	 * Intercept writes to existing, but changed and therefore needing updated,
	 * entities.
	 * 
	 * @param entity
	 *            Entity that has been changed
	 * @param id
	 *            Entity identifier
	 * @param previousState
	 *            Data fields, some of which are modified by this method.
	 * @param propertyNames
	 *            Array of property names that are keys to the state field array
	 *            values.
	 * @param types
	 *            Types
	 */
	@Override
	public boolean onFlushDirty(final Object entity, final Serializable id,
			final Object[] currentState, final Object[] previousState,
			final String[] propertyNames, final Type[] types) {

		final boolean modified = addAuditingProps(entity, currentState,
				propertyNames);
		super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
		return modified;
	}

	/**
	 * Intercept writes to new, unsaved entities.
	 * 
	 * @param entity
	 *            Entity that has been changed
	 * @param id
	 *            Entity identifier
	 * @param state
	 *            Data fields, some of which are modified by this method.
	 * @param propertyNames
	 *            Array of property names that are keys to the state field array
	 *            values.
	 * @param types
	 *            Types
	 * @return If state was modified any, will return true to indicate this to
	 *         Hibernate
	 */
	@Override
	public boolean onSave(final Object entity, final Serializable id,
			final Object[] state, final String[] propertyNames,
			final Type[] types) {
		final boolean modified = addAuditingProps(entity, state, propertyNames);
		super.onSave(entity, id, state, propertyNames, types);
		return modified;
	}

	/**
	 * If the entity parameter is an instance of an {@link AbstractAuditable},
	 * then fill in any missing created fields, object status (defaults to
	 * {@link ObjectStatus#ACTIVE}, and always updates the modified fields with
	 * the currently authenticated user and the current time stamp.
	 * 
	 * @param entity
	 *            The entity that is being modified.
	 * @param state
	 *            Data fields, some of which are modified by this method.
	 * @param propertyNames
	 *            Array of property names that are keys to the state field array
	 *            values.
	 * @return If state was modified any, will return true to indicate this to
	 *         Hibernate
	 */
	private boolean addAuditingProps(final Object entity, // NOPMD
			final Object[] state, final String[] propertyNames) {
		if (!(entity instanceof AbstractAuditable)) {
			// Not AbstractAuditable, so no changes are necessary.
			return false;
		}

		final Person current = currentUser();
		final Date now = new Date();

		for (int i = 0; i < propertyNames.length; i++) {
			final String property = propertyNames[i];
			if ("createdDate".equals(property) && (state[i] == null)) {
				state[i] = now;
				continue;
			}

			if ("createdBy".equals(property) && (state[i] == null)) {
				state[i] = new AuditPerson(current.getId());
				continue;
			}

			if ("objectStatus".equals(property) && (state[i] == null)) {
				state[i] = ObjectStatus.ACTIVE;
				continue;
			}

			if ("modifiedDate".equals(property)) {
				state[i] = now;
				continue;
			}

			if ("modifiedBy".equals(property)) {
				state[i] = new AuditPerson(current.getId());
				continue;
			}
		}

		// Since it was AbstractAuditable (didn't short circuit in test at the
		// top of
		// this method), then last modified stamps always change, so return true
		// to indicate to Hibernate that the state has changed.
		return true;
	}

	/**
	 * Retrieve the currently authenticated user from the
	 * {@link org.jasig.ssp.service.SecurityService}.
	 * 
	 * @return The currently authenticated user
	 */
	public Person currentUser() {
		final SspUser user = getSecurityService().currentFallingBackToAdmin();
		return user.getPerson();
	}

	/**
	 * Set the Spring container context to use for looking up the
	 * SecurityService for use by {@link #currentUser()}.
	 */
	@Override
	public void setApplicationContext(final ApplicationContext arg0)
			throws BeansException {
		context = arg0;
	}

	/**
	 * Retrieve a {@link SecurityService} instance bean from the Spring
	 * container.
	 * 
	 * @return An initialized {@link SecurityService} instance.
	 */
	private SecurityService getSecurityService() {
		if (securityService == null) {
			securityService = context.getBean("securityService",
					SecurityService.class);
		}

		return securityService;
	}
}
