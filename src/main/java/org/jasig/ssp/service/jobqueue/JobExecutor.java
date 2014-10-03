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
package org.jasig.ssp.service.jobqueue;

import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.exception.RuntimeIoException;
import org.jasig.ssp.web.api.validation.ValidationException;

import java.util.UUID;

/**
 * Adapts between the {@link Job} workflow and actual {@link Job} <em>execution</em>.
 *
 * @param <P>
 */
public interface JobExecutor<P> {

	/**
	 * The logical name under which this executor will be registered.
	 *
	 * @return
	 */
	String getName();

	/**
	 * Execute some or all of the work requested by the {@link Job} with the given ID. Implementations typically
	 * expect to manage their own transactions, so should not typically be called from within a wrapping transaction.
	 * {@link Job} {@code executionState} should be persisted prior to returning from this call
	 *
	 * @param jobId
	 * @return a result object instructing the workflow how to transition itself (or not, e.g. supports re-executing
	 * the {@link Job} in the same workflow state)
	 */
	JobExecutionResult<JobWorkflowStatusDescription> execute(UUID jobId);

	/**
	 * Create and enqueue a new {@link Job} using this executor as the {@code executionComponentName}. Creation is
	 * often indirected through the executor in this way b/c the executor knows how to de/serialize the
	 * {@code executionSpec} and {@code executionState}, since it adapts between the generic {@link Job} workflow
	 * and the non-generic, highly-typed business services {@link Job}s are intended to execute.
	 *
	 * @param ownerPersonId
	 * @param runAsPersonId
	 * @param jobSpec
	 * @return
	 * @throws RuntimeIoException
	 * @throws ValidationException
	 */
	Job queueNewJob(UUID ownerPersonId, UUID runAsPersonId, P jobSpec) throws RuntimeIoException, ValidationException;

}
