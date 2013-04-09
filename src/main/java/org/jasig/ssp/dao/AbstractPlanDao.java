package org.jasig.ssp.dao;

import org.jasig.ssp.model.AbstractPlan;

public abstract class  AbstractPlanDao<T extends AbstractPlan> extends AbstractAuditableCrudDao<T> implements
AuditableCrudDao<T> {

	protected AbstractPlanDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	public abstract T cloneAndSave(T plan) throws CloneNotSupportedException;
}
