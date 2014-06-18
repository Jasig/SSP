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
package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.RefreshDirectoryPersonBlueTask;
import org.jasig.ssp.service.RefreshDirectoryPersonTask;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.SendQueuedMessagesTask;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.BatchedTask;
import org.jasig.ssp.service.external.ExternalPersonSyncTask;
import org.jasig.ssp.service.external.MapStatusReportCalcTask;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.NestedRuntimeException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ScheduledTaskWrapperServiceImpl
		implements ScheduledTaskWrapperService, InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduledTaskWrapperServiceImpl.class);

	public static final String TASK_NAME_MDC_KEY = "ssp.taskName";
	public static final String SEND_MESSAGES_TASK_NAME = "send-messages";
	public static final String SYNC_COACHES_TASK_NAME = "sync-coaches";
	public static final String SYNC_EXTERNAL_PERSONS_TASK_NAME = "sync-external-persons";
	public static final String REFRESH_DIRECTORY_PERSON_TASK_NAME = "directory-person-refresh";
	public static final String REFRESH_DIRECTORY_PERSON_BLUE_TASK_NAME = "directory-person-refresh-blue";
	public static final String CALC_MAP_STATUS_REPORTS_TASK_NAME = "calc-map-status-reports";
	public static final String SEND_TASK_REMINDERS_TASK_NAME = "send-task-reminders";
	public static final String SEND_EARLY_ALERT_REMINDERS_TASK_NAME = "send-early-alert-reminders";

	private static final String EVERY_DAY_1_AM = "0 0 1 * * *";
	private static final String EVERY_DAY_3_AM = "0 0 3 * * *";
	private static final String EVERY_DAY_4_AM = "0 0 4 * * *";
	private static final String FIFTEEN_MINUTES_IN_MILLIS = 15 * 60 * 1000 + "";
	private static final String NEVER = "0 0 0 31 12 *";

	// Not a fan of the underscores but matches convention for existing
	// ConfigService records, and will probably be convenient for our IDs here
	// to match prefixes on that config
	private static final String EXTERNAL_PERSON_SYNC_TASK_ID = "task_external_person_sync";
	private static final String EXTERNAL_PERSON_SYNC_TASK_TRIGGER_CONFIG_NAME = "task_external_person_sync_trigger";
	private static final String EXTERNAL_PERSON_SYNC_TASK_DEFAULT_TRIGGER = EVERY_DAY_1_AM;
	
	private static final String DIRECTORY_PERSON_REFRESH_TASK_ID = "task_directory_person_refresh";
	private static final String DIRECTORY_PERSON_REFRESH_TASK_TRIGGER_CONFIG_NAME = "task_directory_person_refresh_trigger";
	private static final String DIRECTORY_PERSON_REFRESH_TASK_DEFAULT_TRIGGER = NEVER;
	
	private static final String DIRECTORY_PERSON_REFRESH_STARTUP_TASK_ID = "task_directory_person_refresh_on_start_up";
	private static final String STARTUP_PERSON_REFRESH_TASK_TRIGGER_CONFIG_NAME = "task_directory_person_refresh_start_up_trigger";
	

	private static final String SCHEDULER_CONFIG_POLL_TASK_ID = "task_scheduler_config_poll";
	private static final String SCHEDULER_CONFIG_POLL_TASK_TRIGGER_CONFIG_NAME = "task_scheduler_config_poll_trigger";
	private static final String SCHEDULER_CONFIG_POLL_TASK_DEFAULT_TRIGGER = FIFTEEN_MINUTES_IN_MILLIS;
	
	private static final String MAP_STATUS_REPORT_CALC_TASK_ID = "task_map_plan_status_calc";
	private static final String MAP_STATUS_REPORT_CALC_TASK_TRIGGER_CONFIG_NAME = "task_scheduler_map_plan_status_calculation_trigger";
	private static final String MAP_STATUS_REPORT_CALC_TASK_DEFAULT_TRIGGER = EVERY_DAY_3_AM;
	
	private static final String EARLY_ALERT_TASK_ID = "task_early_alert_scheduled_tasks";
	private static final String EARLY_ALERT_TASK_TRIGGER_CONFIG_NAME = "task_scheduler_early_alert_trigger";
	private static final String EARLY_ALERT_TASK_DEFAULT_TRIGGER = EVERY_DAY_4_AM;
	private static final String DISABLED_TRIGGER_CONFIG_VALUE = "DISABLED";
	private static final String RUN_ONCE_TRIGGER_CONFIG_VALUE = "RUN_ONCE_ON_STARTUP";
	
	// see assumptions about grouping in tryExpressionAsPeriodicTrigger()
	private static final Pattern PERIODIC_TRIGGER_WITH_INITIAL_DELAY_PATTERN = Pattern.compile("^(\\d+)/(\\d+)$");

	@Autowired
	private transient ExternalPersonSyncTask externalPersonSyncTask;
	
	@Autowired
	private transient RefreshDirectoryPersonTask directoryPersonRefreshTask;
	
	@Autowired
	private transient RefreshDirectoryPersonBlueTask directoryPersonRefreshBlueTask;
	
	@Autowired
	private transient MapStatusReportCalcTask mapStatusReportCalcTask;

	@Autowired
	private transient SendQueuedMessagesTask sendQueuedMessagesTask;

	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient ScheduledApplicationTaskStatusService taskStatusService;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient TaskService taskService;
	
	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient TaskScheduler taskScheduler;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient AuthenticationManager authenticationManager;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Value("#{configProperties.scheduled_coach_sync_enabled}")
	private boolean scheduledCoachSyncEnabled;

	@Value("#{configProperties.ssp_trusted_code_run_as_key}")
	private String runAsKey;

	private HashMap<String, Task> tasks;

	@Override
	public void afterPropertiesSet() {
		initTasks();
		updateTasks();
	}

	protected synchronized void initTasks() {
		this.tasks = new LinkedHashMap<String, Task>();
		this.tasks.put(EXTERNAL_PERSON_SYNC_TASK_ID, new Task(EXTERNAL_PERSON_SYNC_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						syncExternalPersons();
						refreshDirectoryPersonBlue();
						refreshDirectoryPerson();
					}
				},
				EXTERNAL_PERSON_SYNC_TASK_DEFAULT_TRIGGER,
				EXTERNAL_PERSON_SYNC_TASK_TRIGGER_CONFIG_NAME));
		
		this.tasks.put(DIRECTORY_PERSON_REFRESH_TASK_ID, new Task(DIRECTORY_PERSON_REFRESH_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						refreshDirectoryPersonBlue();
						refreshDirectoryPerson();
					}
				},
				DIRECTORY_PERSON_REFRESH_TASK_DEFAULT_TRIGGER,
				DIRECTORY_PERSON_REFRESH_TASK_TRIGGER_CONFIG_NAME));
		
		//Schedule task to be run once on startup
		this.tasks.put(DIRECTORY_PERSON_REFRESH_STARTUP_TASK_ID, new Task(DIRECTORY_PERSON_REFRESH_STARTUP_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						resetTaskStatus();
						refreshDirectoryPersonBlue();
						refreshDirectoryPerson();
					}
				},
				DIRECTORY_PERSON_REFRESH_TASK_DEFAULT_TRIGGER,
				STARTUP_PERSON_REFRESH_TASK_TRIGGER_CONFIG_NAME));
		

		this.tasks.put(MAP_STATUS_REPORT_CALC_TASK_ID, new Task(MAP_STATUS_REPORT_CALC_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						calcMapStatusReports();
					}
				},
				MAP_STATUS_REPORT_CALC_TASK_DEFAULT_TRIGGER,
				MAP_STATUS_REPORT_CALC_TASK_TRIGGER_CONFIG_NAME));
		this.tasks.put(SCHEDULER_CONFIG_POLL_TASK_ID, new Task(SCHEDULER_CONFIG_POLL_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						updateTasksTask();
					}
				},
				SCHEDULER_CONFIG_POLL_TASK_DEFAULT_TRIGGER,
				SCHEDULER_CONFIG_POLL_TASK_TRIGGER_CONFIG_NAME));
		
		this.tasks.put(EARLY_ALERT_TASK_ID, new Task(EARLY_ALERT_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						sendEarlyAlertReminders();
					}
				},
				EARLY_ALERT_TASK_DEFAULT_TRIGGER,
				EARLY_ALERT_TASK_TRIGGER_CONFIG_NAME));

		// Can't interrupt this on cancel b/c it's responsible for rescheduling
		// itself. A scheduling attempt on an interrupted thread is very
		// likely to be refused when using java.util.concurrent schedulers
		// under the covers.
		this.tasks.get(SCHEDULER_CONFIG_POLL_TASK_ID).mayInterrupt = false;
	}

	public synchronized void updateTasks() {
		if ( Thread.currentThread().isInterrupted() ){
			LOGGER.info("Skipping task scheduling updates because of thread interruption");
			return;
		} else {
			LOGGER.info("Starting task scheduling updates");
		}
		for ( Map.Entry<String,Task> taskEntry : this.tasks.entrySet() ) {
			final Task task = taskEntry.getValue();
			if ( Thread.currentThread().isInterrupted() ){
				LOGGER.info("Abandoning updateTasksTask before processing"
						+ " task [{}] because of thread interruption", task.id);
				return;
			}
			LOGGER.debug("Checking for updated config for task [{}]", task.id);
			mergeLatestTriggerConfig(task);
			maybeReschedule(task);
		}
	}

	/**
	 * Really just a pass through to {@link #updateTasks()} but allows for
	 * extra hooks (esp logging) before that background job does its work
	 */
	protected synchronized void updateTasksTask() {
		LOGGER.info("Starting task scheduling updates from within a scheduled job");
		updateTasks();
	}

	/**
	 * Reads latest config from {@link ConfigService} for the given {@link Task}
	 * and caches it in that {@link Task}. If config is found but can't be
	 * parsed, the {@link Task}'s {@code configured*} fields will include the
	 * bad {@link Trigger} expression and a {@link BadConfigTrigger}. If config
	 * simply can't be read at all, e.g. network outage, that will just be
	 * logged and the {@link Task}'s config will be unchanged.
	 *
	 * <p>This also handles initialization of the {@link Task's}
	 * default {@link Trigger} <em>every time</em>. Which means you can
	 * technically change the default and it will be picked up.</p>
	 *
	 * @param task
	 * @return
	 * @throws BadTriggerConfigException if the default configuration for the
	 *   given {@link Task} is broken. Exceptions during read of latest config
	 *   from {@link ConfigService} are caught and handled.
	 */
	protected void mergeLatestTriggerConfig(Task task) throws BadTriggerConfigException {

		// Null will mean "have no idea what the latest config would be or
		// if it even exists." Everything else will mean "either found good
		// config, use it, or found bad config and it's up to you what to do
		// with it."
		Pair<String,Trigger> newTriggerConfig = null;
		try {
			newTriggerConfig = readNewTriggerConfig(task.triggerExpressionConfigName);
		} catch ( BadTriggerConfigException e ) {
			LOGGER.warn("Unable to parse task [{}] trigger config named [{}].",
					new Object[] { task.id, task.triggerExpressionConfigName, e });
			newTriggerConfig = new Pair<String,Trigger>(null,
					new BadConfigTrigger(e.getConfig(), e));
		} catch ( RuntimeException e ) {
			// Probably db connection or other systemic issue issue. Will end up
			// leaving this task alone.
			LOGGER.error("Unable to read trigger config named [{}]. This"
					+ " was probably a transient and/or systemic issue "
					+ " rather than a parse issue.",
					task.triggerExpressionConfigName, e);
		}

		if ( newTriggerConfig != null && newTriggerConfig.getSecond() == null ) {
			// Really a programmer error but let's just quietly normalize to
			// simplify boolean expressions below.
			newTriggerConfig = null;
		}

		if ( newTriggerConfig == null ) {
			task.configuredTriggerExpression = null;
			task.configuredTrigger = null;
		} else {
			task.configuredTriggerExpression = newTriggerConfig.getFirst();
			task.configuredTrigger = newTriggerConfig.getSecond();
		}

		// Do this every time to avoid weird stuff from mis-use where the
		// expression and trigger don't match.
		task.defaultTrigger = parseTriggerConfig(task.defaultTriggerExpression);
	}

	protected void maybeReschedule(Task task) {
		Pair<String,Trigger> configuredOrDefault = configuredOrDefaultTrigger(task);
		Pair<String,Trigger> newTrigger = null;
		if ( task.executingTrigger == null ) {

			// first time execution
			newTrigger = configuredOrDefault;
			LOGGER.debug("Preparing to schedule task [{}] for"
					+ " first-time execution with trigger expression [{}]",
					task.id, newTrigger.getFirst());

		} else if ( configuredOrDefault.getSecond() instanceof BadConfigTrigger ) {

			// broken config. nothing to do.
			LOGGER.info("Skipping scheduling for task [{}] because it has"
					+ " a bad trigger expression [{}]", task.id,
					configuredOrDefault.getFirst());

		} else if ( !(configuredOrDefault.getFirst().
				equals(task.executingTriggerExpression)) ) {

				// schedule change!
			newTrigger = configuredOrDefault;
			LOGGER.debug("Preparing to re-schedule task [{}] with trigger"
					+ " expression [{}]. Previous expression: [{}]",
					new Object[] { task.id, newTrigger.getFirst(),
							task.executingTriggerExpression });

		} else {
			LOGGER.debug("Skipping scheduling for task [{}] because no"
					+ " changes have been requested. Currently executing"
					+ " trigger expression: [{}]", task.id,
					task.executingTriggerExpression);
			return;
		}

		cancel(task);
		schedule(task, newTrigger);
	}

	protected void cancel(Task task) {
		if ( task.execution != null ) {
			LOGGER.info("Attempting task [{}] cancellation", task.id);
			task.execution.cancel(task.mayInterrupt);
		}
		
		
		task.executingTrigger = null;
		task.executingTriggerExpression = null;
	}

	protected void schedule(Task task, Pair<String,Trigger> triggerAndExpression) {
		if ( triggerAndExpression == null ) {
			throw new IllegalArgumentException("Must specify a Trigger and its expression");
		}
		LOGGER.info("Scheduling task [{}] with trigger expression [{}]",
				task.id, triggerAndExpression.getFirst());
		task.execution = taskScheduler.schedule(task.runnable, triggerAndExpression.getSecond());
		task.executingTriggerExpression = triggerAndExpression.getFirst();
		task.executingTrigger = triggerAndExpression.getSecond();
	}

	protected Pair<String,Trigger> configuredOrDefaultTrigger(Task task) {
		if ( task.configuredTrigger == null || task.configuredTrigger instanceof BadConfigTrigger ) {
			return new Pair<String,Trigger>(task.defaultTriggerExpression, task.defaultTrigger);
		}
		return new Pair<String,Trigger>(task.configuredTriggerExpression, task.configuredTrigger);
	}

	protected Pair<String,Trigger> readNewTriggerConfig(String configName)
	throws BadTriggerConfigException {
		final String configValue =
				StringUtils.trimToNull(configService.getByNameNullOrDefaultValue(configName));
		if ( configValue == null ) {
			return null;
		}
		if(configValue.toUpperCase().equals(DISABLED_TRIGGER_CONFIG_VALUE))
		   return  new Pair<String,Trigger>(configValue, new DisabledTrigger());
		
		if(configValue.toUpperCase().equals(RUN_ONCE_TRIGGER_CONFIG_VALUE))
			   return  new Pair<String,Trigger>(configValue, new OnStartUpTrigger());
		
		return new Pair<String,Trigger>(configValue, parseTriggerConfig(configValue));
	}

	protected Trigger parseTriggerConfig(String configValue)
	throws BadTriggerConfigException {

		BadTriggerConfigException badPeriodicTiggerException = null;
		BadTriggerConfigException badCronTiggerException = null;
		try {
			return tryExpressionAsPeriodicTrigger(configValue);
		} catch ( BadTriggerConfigException e ) {
			badPeriodicTiggerException = e;
		}

		try {
			return tryExpressionAsCronTrigger(configValue);
		} catch ( BadTriggerConfigException e ) {
			badCronTiggerException = e;
		}

		throw new BadTriggerConfigException(
				"All trigger config parsing attempts failed. First reason: ["
						+ badPeriodicTiggerException.getMessage()
						+ "]. Second reason: ["
						+ badCronTiggerException.getMessage() + "]", configValue);
	}

	protected Trigger tryExpressionAsPeriodicTrigger(String configValue)
	throws BadTriggerConfigException {
		BadTriggerConfigException longParseException = null;
		try {
			final long period = Long.parseLong(configValue);
			if ( period < 0 ) {
				return new DisabledTrigger();
			}
			// millis since that's what @Scheduled methods were historically
			// configured in
			return new PeriodicTrigger(period, TimeUnit.MILLISECONDS);
		} catch ( NumberFormatException e ) {
			longParseException =
				new BadTriggerConfigException("Config [" + configValue
					+ "] did not parse to a long.", configValue, e);
		} catch ( IllegalArgumentException e ) {
			longParseException =
				new BadTriggerConfigException("Config [" + configValue
					+ "] could not be used to initialize a PeriodicTrigger",
						configValue, e);
		}

		final  Matcher matcher =
				PERIODIC_TRIGGER_WITH_INITIAL_DELAY_PATTERN.matcher(configValue);
		if ( !(matcher.matches()) ) {
			throw new BadTriggerConfigException(
					"Trigger expression could not be parsed as either a" +
							" simple period or as a period with an initial "
							+ "offset. Original parse failure: ["
							+ longParseException.getMessage()
							+ "]. To be considered a period with an offset, "
							+ "the expression must match this regexp"
							+ "(without brackets): ["
							+ PERIODIC_TRIGGER_WITH_INITIAL_DELAY_PATTERN + "]",
					configValue);
		}

		try {
			final String periodStr = matcher.group(1);
			final long periodLong = Long.parseLong(periodStr);

			if ( periodLong < 0 ) {
				return new DisabledTrigger();
			}

			final String offsetStr = matcher.group(2);
			final long offsetLong = Long.parseLong(offsetStr);

			final PeriodicTrigger trigger =
					new PeriodicTrigger(periodLong, TimeUnit.MILLISECONDS);
			trigger.setInitialDelay(offsetLong);
			return trigger;
		} catch ( NumberFormatException e ) {
			throw new BadTriggerConfigException(
					"Trigger expression could not be parsed as either a" +
							" simple period or as a period with an initial "
							+ "offset. Original parse failure: ["
							+ longParseException.getMessage()
							+ "]. To be considered a period with an initial "
							+ "delay, the expression must match this regexp"
							+ "(without brackets): ["
							+ PERIODIC_TRIGGER_WITH_INITIAL_DELAY_PATTERN + "]",
					configValue, e);
		} catch ( IllegalArgumentException e ) {
			throw new BadTriggerConfigException(
					"Trigger expression parsed as a period [{}] with an "
					+ "initial delay [{}] but could not be used to "
					+ "initialize a PeriodicTrigger",
					configValue, e);
		}

	}

	protected Trigger tryExpressionAsCronTrigger(String configValue)
	throws BadTriggerConfigException {
		try {
			return new CronTrigger(configValue);
		} catch ( IllegalArgumentException e ) {
			throw new BadTriggerConfigException("Config [" + configValue
					+ "] could not be used to initialize a CronTrigger",
					configValue, e);
		}
	}

	/**
	 * Decorates the given {@link Runnable} with {@link #withSudo(Runnable)}
	 * if the current {@link SecurityContext} is not considered "auditable",
	 * otherwise the returned {@code Runnable} has no added behavior.
	 *
	 * <p>Design note: currently there is only this and the "raw"
	 * {@link #withSudo(Runnable)}... nothing that restores the previous
	 * {@link Authentication}, if any. Only reason is that we don't currently
	 * need such a thing, so there's no point in spending time making sure
	 * the replace/restore actually works.</p>
	 *
	 * @see #isCurrentAuthenticationAuditable()
	 * @param work
	 * @throws AuthenticationException
	 */
	protected Runnable withMaybeSudo(final Runnable work) throws AuthenticationException {
		return new Runnable() {
			@Override
			public void run() {
				if ( !(isCurrentAuthenticationAuditable()) ) {
					LOGGER.debug("Insufficient Authentication in SecurityContext. Executing task via sudo.");
					withSudo(work).run();
				} else {
					LOGGER.debug("Sufficient Authentication already present in SecurityContext. Skipping sudo and executing task in that context.");
					work.run();
				}
			}
		};
	}

	/**
	 * Checks to see if our Hibernate flush interceptor will consider the
	 * current {@link Authentication} sufficient for assigning to persistent
	 * entities as either a "creator" or "modifier", <em>and is not the anonymous
	 * user.</em>
	 *
	 * <p>Note that this implementation depends heavily on some quirky
	 * behavior in {@link org.jasig.ssp.service.SecurityService#currentlyAuthenticatedUser()}.
	 * Specifically, that method is assumed to return null if any of the following
	 * are true:</p>
	 *
	 * <ol>
	 *     <li>The current {@link SecurityContext} {@link Authentication}
	 *     is null, or</li>
	 *     <li>The current {@link SecurityContext} {@link Authentication} is
	 *     unauthenticated, or</li>
	 *     <li>The current {@link SecurityContext} {@link Authentication} is
	 *     the anonymous user, or</li>
	 *     <li>The current {@link SecurityContext} {@link Authentication} does
	 *     not resolve to a known {@link Person}.</li>
	 * </ol>
	 *
	 * @see #withSudo(Runnable)
	 * @return
	 */
	protected boolean isCurrentAuthenticationAuditable() {
		return securityService.currentlyAuthenticatedUser() != null;
	}

	/**
	 * Decorates the given {@code Runnable} with a login and logout of
	 * {@link org.jasig.ssp.service.SecurityService#noAuthAdminUser()}.
	 *
	 * <p>Prior to <a href="https://issues.jasig.org/browse/SSP-2241">SSP-2241</a>
	 * we didn't attempt to ensure any particular {@link SecurityContext} state
	 * prior to running jobs. This ended up causing a memory leak because our
	 * Hibernate flush interceptor would generate a new {@link SspUser} for
	 * every flushed "auditer" field, and every time that happened, that
	 * {@link SspUser} was added to a {@code ThreadLocal} list. For a large
	 * job like {@link #syncExternalPersons()}, the growth of that list was
	 * particularly explosive. {@link SspUser} is definitely due for a refactor
	 * to eliminate it's {@code ThreadLocal} dependencies, but for the time
	 * being we're able to short-circuit the leak by ensuring that there is
	 * a current {@link Authentication} that the Hibernate flush interceptor
	 * will honor. (It will not honor the anonymous user.) And this is good
	 * practice anyway - to always explicitly set up a security context rather
	 * than let obscure Hibernate extension internals make up the rules as we
	 * go.</p>
	 *
	 * @see #withMaybeSudo(Runnable)
	 * @param work
	 * @return
	 * @throws AuthenticationException
	 */
	protected Runnable withSudo(final Runnable work) throws AuthenticationException {
		return new Runnable() {
			@Override
			public void run() {
				final SspUser runAs = securityService.noAuthAdminUser();
				Authentication auth = new RunAsUserToken(runAsKey, runAs, null, runAs.getAuthorities(), null);
				auth = authenticationManager.authenticate(auth);

				// Not sure why/if we need this. Just trying to mimic long-time
				// legacy behavior in UPortalPreAuthenticatedProcessingFilter
				if (eventPublisher != null) {
					eventPublisher.publishEvent(new AuthenticationSuccessEvent(auth));
				}

				// AuthenticationManager doesn't do this for you
				SecurityContextHolder.getContext().setAuthentication(auth);

				try {
					work.run();
				} finally {
					SecurityContextHolder.getContext().setAuthentication(null);
				}
			}
		};
	}

	/**
	 * Wraps the given {@code Runnable} with the  sort of cleanup you'd normally
	 * depend on after a HTTP request. In particular, this is necessary to
	 * ensure release of {@code ThreadLocals} set by virtue of {@link SspUser}
	 * interactions.
	 */
	protected Runnable withTaskCleanup(final Runnable work) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					LOGGER.debug("SspUser cleanup queue size (pre-task): {}", SspUser.cleanupQueueSize());
					work.run();
				} finally {
					LOGGER.debug("SspUser cleanup queue size (post-task): {}", SspUser.cleanupQueueSize());
					securityService.afterRequest();
					LOGGER.debug("SspUser cleanup queue size (post-task-cleanup): " + SspUser.cleanupQueueSize());
				}
			}
		};
	}

	protected Runnable withHibernateSession(final Runnable work) {
		return new Runnable() {
			@Override
			public void run() {
				// Basically a copy/paste of Spring's
				// OpenSessionInViewFilter#doFilterInternal, with the
				// web-specific stuff removed
				boolean participate = false;
				try {
					if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
						// Do not modify the Session: just set the participate flag.
						LOGGER.debug("Scheduled task joining existing Hibernate session/transaction");
						participate = true;
					} else {
						LOGGER.debug("Scheduled task creating new Hibernate session");
						Session session = sessionFactory.openSession();
						session.setFlushMode(FlushMode.MANUAL);
						SessionHolder sessionHolder = new SessionHolder(session);
						TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
					}

					work.run();

				} finally {
					if (!participate) {
						SessionHolder sessionHolder =
								(SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
						LOGGER.debug("Scheduled task closing Hibernate session");
						SessionFactoryUtils.closeSession(sessionHolder.getSession());
					} else {
						LOGGER.debug("Scheduled task joined existing Hibernate session/transaction so skipping that cleanup step");
					}
				}
			}
		};
	}

	protected Runnable withTaskName(final String taskName, final Runnable work) {
		return new Runnable() {
			@Override
			public void run() {
				if ( StringUtils.isBlank(taskName) ) {
					work.run();
					return;
				}
				final String currentThreadName = Thread.currentThread().getName();
				final String currentMdcEntry = MDC.get(TASK_NAME_MDC_KEY);
				taskStatusService.beginTask(taskName);
				try {
					final String newThreadName = currentThreadName == null ? taskName : currentThreadName + ":" + taskName;
					Thread.currentThread().setName(newThreadName);
					final String newMdcEntry = currentMdcEntry == null ? taskName : currentMdcEntry + ":" + taskName;
					MDC.put(TASK_NAME_MDC_KEY, newMdcEntry);
					work.run();
				}finally {
					if ( currentMdcEntry == null ) {
						MDC.remove(TASK_NAME_MDC_KEY);
					} else {
						MDC.put(TASK_NAME_MDC_KEY, currentMdcEntry);
					}
					Thread.currentThread().setName(currentThreadName);
					taskStatusService.completeTask(taskName);
				}
			}
		};
	}

	/**
	 * Wraps the given {@code Runnable} in the "standard" decorators you'd
	 * typically need for execution of a background task and returns the
	 * resulting {@code Runnable} for subsequent execution.
	 *
	 * @param work
	 */
	protected Runnable withTaskContext(String taskName, Runnable work) {
		return withTaskName(taskName, withHibernateSession(withTaskCleanup(withMaybeSudo(work))));
	}

	/**
	 * Wraps the given {@code Runnable} in the "standard" decorators you'd
	 * typically need for execution of a background task, and then
	 * executed the result. Please see {@link #withSudo(Runnable)} in particular
	 * for why it's important to have a non-anonymous current
	 * {@link Authentication} before actually executing a background task.
	 *
	 * @param work
	 */
	protected void execWithTaskContext(String taskName, Runnable work) {
		withTaskContext(taskName, work).run();
	}

	/**
	 * Basically a deferred form of {@link #execWithTaskContext(Runnable)}.
	 * Useful when you have a scheduled job that does its work in batches and
	 * you'd like the effect of {@link #execWithTaskContext(Runnable)}
	 * (except for the thread naming decoration) applied
	 * independently for each batch. This is advisable for any long-running
	 * job (which is probably why it was batched in the first place) b/c
	 * otherwise you can end up with a system doing a great impression of
	 * a memory leak as the Hib session grows indefinitely.
	 *
	 * <p>Batched jobs often need results from each batch to know what to
	 * do next, hence the use of {@code Callable} rather than
	 * {@link Runnable} here.</p>
	 *
	 * <p>Since thread naming needs to happen prior to individual batch
	 * executions, the caller is responsible for wrapping the actual
	 * task invocation with that behavior, if necessary. E.g. see
	 * {@link #execBatchedTaskWithName(String, org.jasig.ssp.service.external.BatchedTask)}</p>
	 *
	 * @param batchReturnType
	 * @param <T>
	 * @return
	 */
	protected <T> CallableExecutor<T> newTaskBatchExecutor(final Class<T> batchReturnType) {
		return new CallableExecutor<T>() {
			@Override
			public T exec(final Callable<T> work) throws Exception {
				final AtomicReference<T> resultHolder = new AtomicReference<T>();
				final AtomicReference<Exception> exceptionHolder = new AtomicReference<Exception>();
				execWithTaskContext(null, new Runnable() {
					@Override
					public void run() {
						try {
							resultHolder.set(work.call());
						} catch (Exception e) {
							exceptionHolder.set(e);
						}
					}
				});
				if ( exceptionHolder.get() != null ) {
					throw exceptionHolder.get();
				}
				return resultHolder.get();
			}
		};
	}

	protected void execBatchedTaskWithName(final String taskName, final BatchedTask batchedTask) {
		withTaskName(taskName, new Runnable() {
			@Override
			public void run() {
				batchedTask.exec(newTaskBatchExecutor(batchedTask.getBatchExecReturnType()));
			}
		}).run();
	}

	@Override
	@Scheduled(fixedDelay = 150000)
	// run 2.5 minutes after the end of the last invocation
	public void sendMessages() {
		execBatchedTaskWithName(SEND_MESSAGES_TASK_NAME, sendQueuedMessagesTask);
	}

	@Override
	@Scheduled(fixedDelay = 300000)
	// run every 5 minutes
	public void syncCoaches() {
		execWithTaskContext(SYNC_COACHES_TASK_NAME, new Runnable() {
			@Override
			public void run() {
				if (!(scheduledCoachSyncEnabled)) {
					LOGGER.debug("Scheduled coach sync disabled. Abandoning sync job");
					return;
				}
				LOGGER.info("Scheduled coach sync starting.");
				PagingWrapper<Person> localCoaches = personService.syncCoaches();
				LOGGER.info("Scheduled coach sync complete. Local coach count [{}]",
						localCoaches.getResults());
			}
		});
	}
	
	@Override
	@Scheduled(cron = "0 0 1 * * *")
	// run at 1 am every day
	public void sendTaskReminders() {
		execWithTaskContext(SEND_TASK_REMINDERS_TASK_NAME, new Runnable() {
			@Override
			public void run() {
				taskService.sendAllTaskReminderNotifications();
			}
		});
	}

	/**
	 * Not {@code @Scheduled} b/c its scheduling is now handled by the
	 * config polling job.
	 */
	@Override
	public void syncExternalPersons() {
		execBatchedTaskWithName(SYNC_EXTERNAL_PERSONS_TASK_NAME, externalPersonSyncTask);
	}
	
	
	
	@Override 
	public void refreshDirectoryPerson(){
		execBatchedTaskWithName(REFRESH_DIRECTORY_PERSON_TASK_NAME, directoryPersonRefreshTask);
	}
	
	//Reset Tasks schedule where used for control if possiblity completion is interrupted by termination
	@Override 
	public void resetTaskStatus(){
		
		taskStatusService.completeTask(REFRESH_DIRECTORY_PERSON_BLUE_TASK_NAME);
		taskStatusService.completeTask(REFRESH_DIRECTORY_PERSON_TASK_NAME);
	}
	
	@Override 
	public void refreshDirectoryPersonBlue(){
		execBatchedTaskWithName(REFRESH_DIRECTORY_PERSON_BLUE_TASK_NAME, directoryPersonRefreshBlueTask);
	}

	@Override
	public void calcMapStatusReports() {
		execBatchedTaskWithName(CALC_MAP_STATUS_REPORTS_TASK_NAME, mapStatusReportCalcTask);
	}
	
	@Override
	public void sendEarlyAlertReminders() {
		execWithTaskContext(SEND_EARLY_ALERT_REMINDERS_TASK_NAME, new Runnable() {
			@Override
			public void run() {
				earlyAlertService.sendAllEarlyAlertReminderNotifications();
			}
		});
	}

	protected static class Task {

		public String id;
		

		public String triggerExpressionConfigName;
		public String configuredTriggerExpression;
		public Trigger configuredTrigger;

		public String defaultTriggerExpression;
		public Trigger defaultTrigger;

		public String executingTriggerExpression;
		public Trigger executingTrigger;
		public ScheduledFuture execution;

		public Runnable runnable;

		public boolean mayInterrupt = true;


		public Task(String id, Runnable task, String defaultTriggerExpression,
					String triggerExpressionConfigName) {
			this.id = id;
			this.runnable = task;
			this.defaultTriggerExpression = defaultTriggerExpression;
			this.triggerExpressionConfigName = triggerExpressionConfigName;
		}
	}

	protected static class DisabledTrigger implements Trigger {
		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			return null;
		}
		@Override
		public boolean equals(Object o) {
			if ( o == null ) {
				return false;
			}
			if ( o == this ) {
				return true;
			}
			return o.getClass().equals(DisabledTrigger.class);
		}
		@Override
		public int hashCode() {
			return DisabledTrigger.class.getName().hashCode();
		}
	}
	
	protected static class OnStartUpTrigger implements Trigger {
		boolean hasRun = false;
		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			if(hasRun)
				return null;
			else{
				hasRun = true;
			}
			return new Date();
		}
		@Override
		public boolean equals(Object o) {
			if ( o == null ) {
				return false;
			}
			if ( o == this ) {
				return true;
			}
			return o.getClass().equals(OnStartUpTrigger.class);
		}
		@Override
		public int hashCode() {
			return OnStartUpTrigger.class.getName().hashCode();
		}
	}

	protected static class BadConfigTrigger extends DisabledTrigger {
		public String config;
		public Throwable cause;
		public BadConfigTrigger(String config, Throwable cause) {
			this.config = config;
			this.cause = cause;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			if (!super.equals(o)) return false;

			BadConfigTrigger that = (BadConfigTrigger) o;

			if (cause != null ? !cause.equals(that.cause) : that.cause != null)
				return false;
			if (config != null ? !config.equals(that.config) : that.config != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + (config != null ? config.hashCode() : 0);
			result = 31 * result + (cause != null ? cause.hashCode() : 0);
			return result;
		}
	}

	protected static class BadTriggerConfigException extends NestedRuntimeException {
		private String config;
		public BadTriggerConfigException(String message, String config, Throwable cause) {
			super(message, cause);
			this.config = config;
		}
		public BadTriggerConfigException(String message, String config) {
			super(message);
			this.config = config;
		}
		public String getConfig() {
			return config;
		}
	}
}
