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

import java.util.Calendar;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Person;

public abstract class  AbstractPlanDao<T extends AbstractPlan> extends AbstractAuditableCrudDao<T> implements
AuditableCrudDao<T> {

	protected AbstractPlanDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	public T cloneAndSave(T plan,Person owner) throws CloneNotSupportedException {
		T clone = plan.clonePlan();
		clone.setOwner(owner);
		clone.setObjectStatus(plan.getObjectStatus());
		return save(clone);
	}
	
	@Override
	public T save(T obj) {
		
		//Kludge:  Setting this date so hibernate considers plan level entity 'dirty'
		//The real date will be set by org.jasig.ssp.dao.AuditableEntityInterceptor
		//We do this so we naively always update modified date/by when saving a plan.  
		obj.setModifiedDate(Calendar.getInstance().getTime());
		//End Kludge
		return super.save(obj);
	}
	
}
