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

/**
 * Describes the state of {@link Job} execution, i.e. the overall status of the work being performed, as opposed to the
 * job's <em>workflow</em>, the latter having more to do with the job management infrastructure.
 */
public enum JobExecutionStatus {
	/**
	 * Work completed, and without error/failure
	 */
	DONE,
	/**
	 * No errors/failures, but there is more work to be performed. Typically used to trigger a re-execution after
	 * persisting {@code executionState}
	 */
	PARTIAL,
	/**
	 * System-level failure while performing work. Typically indicates that all work for the current execution was
	 * rolled back <em>and the job workflow should transition to a termination state.</em>
	 */
	ERROR,
	/**
	 * Business logic failure while performing work. Typically indicates that all work for the current execution was
	 * rolled back <em>and the job workflow should transition to a termination state.</em>
	 */
	FAILED,
	/**
	 * Business logic failure while performing work but job re-execution should occur after persisting
	 * {@code executionState}. The latter must occur in a separate transaction.
	 */
	FAILED_PARTIAL,
	/**
	 * Work could not complete due to an interruption request. Job should typically be re-scheduled.
	 */
	INTERRUPTED
}
