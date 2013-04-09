package org.jasig.ssp.dao;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Person;

public abstract class  AbstractPlanDao<T extends AbstractPlan> extends AbstractAuditableCrudDao<T> implements
AuditableCrudDao<T> {

	protected AbstractPlanDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	public abstract T cloneAndSave(T plan, Person owner) throws CloneNotSupportedException;
}
