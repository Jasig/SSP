package edu.sinclair.ssp.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.Auditable;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.security.SspUser;
import edu.sinclair.ssp.service.SecurityService;

@Service
public class AuditableEntityInterceptor extends EmptyInterceptor implements
		ApplicationContextAware {

	private static final long serialVersionUID = 1L;

	private SecurityService securityService;

	public Person currentUser() {
		SspUser user = getSecurityService().currentlyLoggedInSspUser();
		return user.getPerson();
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		addAuditingProps(entity, currentState, propertyNames);
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		addAuditingProps(entity, state, propertyNames);
		return super.onSave(entity, id, state, propertyNames, types);
	}

	private void addAuditingProps(Object entity, Object[] state,
			String[] propertyNames) {
		if (entity instanceof Auditable) {
			for (int i = 0; i < propertyNames.length; i++) {
				String property = propertyNames[i];
				if ("createdDate".equals(property) && state[i] == null) {
					state[i] = new Date();
				}

				if ("createdBy".equals(property) && state[i] == null) {
					state[i] = currentUser();
				}

				if ("objectStatus".equals(property) && state[i] == null) {
					state[i] = ObjectStatus.ACTIVE;
				}

				if ("modifiedDate".equals(property)) {
					state[i] = new Date();
				}

				if ("modifiedBy".equals(property)) {
					state[i] = currentUser();
				}
			}
		}
	}

	private ApplicationContext context;

	private SecurityService getSecurityService() {
		if (securityService == null) {
			this.securityService = context.getBean("securityService",
					SecurityService.class);
		}
		return securityService;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
	}
}
