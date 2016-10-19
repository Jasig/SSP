/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service;

import org.jasig.ssp.service.external.BatchedTask;
import java.util.UUID;


/**
 * Exists just to give us a non-transactional location in which to launch
 * a relatively static set of scheduled jobs and clean up after them
 */
public interface ScheduledTaskWrapperService {

    /**
     * Checks and sends emails from message queue
     * Not scheduled through config fires every 2.5 minutes after completion
     */
    public void sendMessages();

    /**
     * Runs in batches a bulk add of external-only students into SSP
     * Not scheduled through config fires every 2.5 minutes after completion
     */
	public void processCaseloadBulkAddReassignment();

	/**
     * Syncs Coaches with current external list of coaches using the coach query.
     *  Typically this is ldap through SSP-Platform looking for SSP_COACH mapping or group.
     *  Not scheduled through config fires every 5 minutes after completion
     */
	public void syncCoaches();

    /**
     * Sends Action Plan Task reminders to students on overdue tasks.
     * Not scheduled through config. runs at 2 am every day
     */
	public void sendTaskReminders();

    /**
     * Syncs updated external_person data with data in person table. Also, syncs
     *   other data such as external SSG. By default runs nightly at 1 a.m., but
     *    can be scheduled in config.
     */
	public void syncExternalPersons();

    /**
     * Refreshes the mv_directory_person materialized view used for Person Search.
     * This is ran after syncExternalPersons() default nightly after 1 a.m.
     */
	public void refreshDirectoryPerson();

    /**
     * This is the same as refreshDirectoryPerson except for the alternate view which
     *  is used when the main view is being updated. Again updated nightly after
     *   person sync.
     */
	public void refreshDirectoryPersonBlue();

    /**
     * Calculates the MAP status calculation task which calculates active student's active
     *  plans against the trasncript and other data and marks them On or Off plan.
     *  Runs default 3 a.m. nightly, but can be scheduled in config.
     */
	public void calcMapStatusReports();

    /**
     * Sends Early Alert reminders which notifies coaches if an
     *   alert hasn't been responded to in configured a timeframe (default 2 days).
     *   Runs default nightly at 4 a.m, but can be scheduled in config.
     */
	public void sendEarlyAlertReminders();

    /**
     * Reset Tasks schedule where used for control if possibility, completion is interrupted by termination
     */
	public void resetTaskStatus();

    /**
     * Removes all nonces that are expired in order to keep the nonce table at a reasonable size
     */
    public void cullOAuth1Nonces();

    /**
     * Prunes the Message queue task which archives Messages in the
     *   message table after 30 days to an archive table.
     *   Runs default nightly at 10 p.m, but can be scheduled in config.
     */
	void pruneMessageQueue();

    /**
     * Schedules queued jobs to be ran.
     */
	void scheduledQueuedJobs();

    /**
     * Basically a deferred form of {@link #execWithTaskContext(Runnable)}.
     * See comments in implementation ScheduledTaskWrapperServiceImpl.java
     * @param taskName
     * @param batchedTask
     * @param isStatusedTask
     * @param runAs
     */
	void execBatchedTaskWithName(String taskName, BatchedTask batchedTask, boolean isStatusedTask, UUID runAs);

    /**
     * Wraps the given {@code Runnable} in the "standard" decorators you'd
     * typically need for execution of a background task and returns the
     * resulting {@code Runnable} for subsequent execution.
     * @param taskName
     * @param work
     * @param isStatusedTask
     * @param runAsId
     */
	void execWithTaskContext(String taskName, Runnable work, boolean isStatusedTask, UUID runAsId);

    /**
     * Runs the special service group course withdrawal notification to advisor task.
     *  This emails advisors if a student has withdrawn from a current course and is
     *   assigned to a configured SSG that has notify turned on. Default is nightly at
     *    5 a.m., but can be scheduled in config.
     */
	public void processSpecialServiceGroupCourseWithdrawal();
}
