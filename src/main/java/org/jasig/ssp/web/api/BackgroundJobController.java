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
package org.jasig.ssp.web.api;

import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;


/**
 * Used to manually run scheduled background jobs off schedule primarily
 *   for error resolution or data loading purposes
 */
@Controller
@RequestMapping("/1/backgroundjob")
@PreAuthorize(Permission.SECURITY_REFERENCE_SYSTEM_CONFIG_WRITE)
public class BackgroundJobController extends AbstractBaseController {
	
	@Autowired
	private ScheduledTaskWrapperService scheduledTaskWrapperService;

	private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundJobController.class);


    /**
     * These jobs are found in ScheduledTaskWrapperServiceImpl but aren't added below
     *  either because they won't be needed or because they are
     *   hard-coded to run frequently enough.
     *
     *   resetTaskStatus (system only)
     *   cullOAuth1Nonces (keeping system only for now)
     *   scheduledQueuedJobs (system only)
     */

    /**
     * Executes the external person sync task that runs nightly default 1 a.m.
     *   which syncs external_person data for records existing in person
     * @param request
     * @return
     */
    @RequestMapping(value = "/externalpersonsync", method = RequestMethod.GET)
    public ServiceResponse runExternalPersonSync(HttpServletRequest  request) {
        LOGGER.debug("Manually running externalpersonsync... ");
        scheduledTaskWrapperService.syncExternalPersons();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the mv_directory_person refresh task that is auto-updated after person
     *   and other inserts/updates, but is also ran after the external person sync task.
     *   Main usage of that materialized view is Person Search.
     *
     * Note: This does *not* run the refresh directory person alternate (blue)
     * @param request
     * @return
     */
    @RequestMapping(value = "/directorypersonrefresh", method = RequestMethod.GET)
    public ServiceResponse runDirectoryPersonRefresh(HttpServletRequest  request) {
        LOGGER.debug("Manually running refreshDirectoryPerson... ");
        scheduledTaskWrapperService.refreshDirectoryPerson();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the mv_directory_person refresh blue task.
     * @param request
     * @return
     */
    @RequestMapping(value = "/directorypesonrefreshblue", method = RequestMethod.GET)
    public ServiceResponse runDirectoryPersonRefreshBlue(HttpServletRequest  request) {
        LOGGER.debug("Manually running refreshDirectoryPersonBlue... ");
        scheduledTaskWrapperService.refreshDirectoryPersonBlue();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the send Early Alert reminders task which notifies coaches if an
     *   alert hasn't been responded to in configured a timeframe (default 2 days).
     *   Runs default nightly at 4 a.m.
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendearlyalertreminders", method = RequestMethod.GET)
    public ServiceResponse runSendEarlyAlertReminders(HttpServletRequest  request) {
        LOGGER.debug("Manually running sendearlyalertreminders... ");
        scheduledTaskWrapperService.sendEarlyAlertReminders();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the send Task reminders task which notifies coaches/students if an
     *   Action Plan Task is overdue. Runs default nightly at 2 a.m.
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendtaskreminders", method = RequestMethod.GET)
    public ServiceResponse runSendTaskReminders(HttpServletRequest  request) {
        LOGGER.debug("Manually running sendTaskReminders... ");
        scheduledTaskWrapperService.sendTaskReminders();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the send prune Message queue task which archives Messages in the
     *   message table after 30 days to an archive table.
     *   Runs default nightly at 10 p.m.
     * @param request
     * @return
     */
    @RequestMapping(value = "/prunemessagequeue", method = RequestMethod.GET)
    public ServiceResponse runPruneMessageQueue(HttpServletRequest  request) {
        LOGGER.debug("Manually running pruneMessageQueue... ");
        scheduledTaskWrapperService.pruneMessageQueue();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the MAP status calculation task which calculates active student's active
     *  plans against the trasncript and other data and marks them On or Off plan.
     *  Runs default 3 a.m. nightly.
     * @param request
     * @return
     */
    @RequestMapping(value = "/mapstatuscalculation", method = RequestMethod.GET)
    public ServiceResponse runMapStatusCalculation(HttpServletRequest  request) {
        LOGGER.debug("Manually running calcMapStatusReports... ");
        scheduledTaskWrapperService.calcMapStatusReports();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the special service group course withdrawal notification to advisor task.
     *  This emails advisors if a student has withdrawn from a current course and is
     *   assigned to a configured SSG that has notify turned on. Default is nightly at
     *    5 a.m.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ssgcoursewithdrawnotify", method = RequestMethod.GET)
    public ServiceResponse runSpecialServiceGroupCourseWithdrawalAdvisorNotification(HttpServletRequest  request) {
        LOGGER.debug("Manually running processSpecialServiceGroupCourseWithdrawal... ");
        scheduledTaskWrapperService.processSpecialServiceGroupCourseWithdrawal();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the success indicator background  task.
     *  This task examines Success Indicators that are Active and have one or both configurations set.
     *  Those configurations are: display a count of low and medium in Caseload/Watchlist/Search or
     *  Generate an Early Alert on Low. If one or both or those are set,
     *  this task runs against students inside of SSP. Default is nightly at 3 a.m.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/successindicatorcountalert", method = RequestMethod.GET)
    public ServiceResponse runConfiguredSuccessIndicatorCountOrEarlyAlertTask(HttpServletRequest  request) {
        LOGGER.debug("Manually running processConfiguredSuccessIndicators... ");
        scheduledTaskWrapperService.processConfiguredSuccessIndicators();
        return new ServiceResponse(true, "success");
    }

    /**
     * Executes the success indicator background  task.
     * Runs in batches a bulk add of external-only students into SSP from CSV
     *   Default is every 10 minutes, but can be scheduled in config.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/processcaseloadbulkadd", method = RequestMethod.GET)
    public ServiceResponse runProcessCaseloadBulkAddReassignmentTask(HttpServletRequest  request) {
        LOGGER.debug("Manually running processCaseloadBulkAddReassignment... ");
        scheduledTaskWrapperService.processCaseloadBulkAddReassignment();
        return new ServiceResponse(true, "success");
    }

    /**
     * Checks and sends emails from message queue
     *   Default is every 2.5 minutes, but can be scheduled in config.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendmessages", method = RequestMethod.GET)
    public ServiceResponse runSendMessagesTask(HttpServletRequest  request) {
        LOGGER.debug("Manually running sendMessages... ");
        scheduledTaskWrapperService.sendMessages();
        return new ServiceResponse(true, "success");
    }

    /**
     * Syncs Coaches with current external list of coaches using the coach query.
     *  Typically this is ldap through SSP-Platform looking for SSP_COACH mapping or group.
     *   Default is every 5 minutes, but can be scheduled in config.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/synccoaches", method = RequestMethod.GET)
    public ServiceResponse runSyncCoachesTask(HttpServletRequest  request) {
        LOGGER.debug("Manually running processSyncCoaches... ");
        scheduledTaskWrapperService.syncCoaches();
        return new ServiceResponse(true, "success");
    }

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
