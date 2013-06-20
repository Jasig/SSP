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
package org.jasig.ssp.service;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;

/**
 * Person service
 */
public interface AbstractPlanService<T extends AbstractPlan,TO extends AbstractPlanTO<T>> extends AuditableCrudService<T> {

	static final public String OUTPUT_FORMAT_MATRIX = "matrixFormat";
	
	static final public String OUTPUT_FULL_MATRIX = "fullFormat";
	
	@Override
	public T get(@NotNull final UUID id) throws ObjectNotFoundException;

	public T copyAndSave(T model, Person newOwner) throws CloneNotSupportedException;
	
	SubjectAndBody createMatirxOutput(TO plan, String institutionName) throws ObjectNotFoundException;
	
	SubjectAndBody createFullOutput(AbstractPlanOutputTO<T,TO> plan, String institutionName) throws ObjectNotFoundException;
	
	public TO validate(TO model) throws ObjectNotFoundException;
}