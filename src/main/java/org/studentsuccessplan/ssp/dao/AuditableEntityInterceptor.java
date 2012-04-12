package org.studentsuccessplan.ssp.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.SecurityService;

/**
 * Intercepts Hibernate writes to automatically fill the created and modified
 * author and time stamp fields of any model that derives from the
 * {@link org.studentsuccessplan.ssp.model.Auditable} class.
 */
@Service
public class AuditableEntityInterceptor extends EmptyInterceptor implements
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
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		boolean modified = addAuditingProps(entity, currentState, propertyNames);
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
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		boolean modified = addAuditingProps(entity, state, propertyNames);
		super.onSave(entity, id, state, propertyNames, types);
		return modified;
	}

	/**
	 * If the entity parameter is an instance of an {@link Auditable}, then fill
	 * in any missing created fields, object status (defaults to
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
	private boolean addAuditingProps(Object entity, Object[] state,
			String[] propertyNames) {
		if (!(entity instanceof Auditable)) {
			// Not Auditable, so no changes are necessary.
			return false;
		}

		for (int i = 0; i < propertyNames.length; i++) {
			String property = propertyNames[i];
			if ("createdDate".equals(property) && state[i] == null) {
				state[i] = new Date();
				continue;
			}

			if ("createdBy".equals(property) && state[i] == null) {
				state[i] = currentUser();
				continue;
			}

			if ("objectStatus".equals(property) && state[i] == null) {
				state[i] = ObjectStatus.ACTIVE;
				continue;
			}

			if ("modifiedDate".equals(property)) {
				state[i] = new Date();
				continue;
			}

			if ("modifiedBy".equals(property)) {
				state[i] = currentUser();
				continue;
			}
		}

		// Since it was Auditable (didn't short circuit in test at the top of
		// this method), then last modified stamps always change, so return true
		// to indicate to Hibernate that the state has changed.
		return true;
	}

	/**
	 * Retrieve the currently authenticated user from the
	 * {@link org.studentsuccessplan.ssp.service.SecurityService}.
	 * 
	 * @return The currently authenticated user
	 */
	public Person currentUser() {
		SspUser user = getSecurityService().currentUser();
		return user.getPerson();
	}

	/**
	 * Set the Spring container context to use for looking up the
	 * SecurityService for use by {@link #currentUser()}.
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
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
