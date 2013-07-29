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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.ExternalPersonSyncTask;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedRuntimeException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskWrapperServiceImpl
		implements ScheduledTaskWrapperService, InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduledTaskWrapperServiceImpl.class);

	private static final String EVERY_DAY_1_AM = "0 0 1 * * *";
	private static final String FIFTEEN_MINUTES_IN_MILLIS = 15 * 60 * 1000 + "";

	// Not a fan of the underscores but matches convention for existing
	// ConfigService records, and will probably be convenient for our IDs here
	// to match prefixes on that config
	private static final String EXTERNAL_PERSON_SYNC_TASK_ID = "task_external_person_sync";
	private static final String EXTERNAL_PERSON_SYNC_TASK_TRIGGER_CONFIG_NAME = "task_external_person_sync_trigger";
	private static final String EXTERNAL_PERSON_SYNC_TASK_DEFAULT_TRIGGER = EVERY_DAY_1_AM;

	private static final String SCHEDULER_CONFIG_POLL_TASK_ID = "task_scheduler_config_poll";
	private static final String SCHEDULER_CONFIG_POLL_TASK_TRIGGER_CONFIG_NAME = "task_scheduler_config_poll_trigger";
	private static final String SCHEDULER_CONFIG_POLL_TASK_DEFAULT_TRIGGER = FIFTEEN_MINUTES_IN_MILLIS;

	// see assumptions about grouping in tryExpressionAsPeriodicTrigger()
	private static final Pattern PERIODIC_TRIGGER_WITH_INITIAL_DELAY_PATTERN = Pattern.compile("^(\\d+)/(\\d+)$");

	@Autowired
	private transient ExternalPersonSyncTask externalPersonSyncTask;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient TaskScheduler taskScheduler;

	@Autowired
	private transient ConfigService configService;

	@Value("#{configProperties.scheduled_coach_sync_enabled}")
	private boolean scheduledCoachSyncEnabled;

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
					}
				},
				EXTERNAL_PERSON_SYNC_TASK_DEFAULT_TRIGGER,
				EXTERNAL_PERSON_SYNC_TASK_TRIGGER_CONFIG_NAME));
		this.tasks.put(SCHEDULER_CONFIG_POLL_TASK_ID, new Task(SCHEDULER_CONFIG_POLL_TASK_ID,
				new Runnable() {
					@Override
					public void run() {
						updateTasksTask();
					}
				},
				SCHEDULER_CONFIG_POLL_TASK_DEFAULT_TRIGGER,
				SCHEDULER_CONFIG_POLL_TASK_TRIGGER_CONFIG_NAME));

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
			return new Pair(task.defaultTriggerExpression, task.defaultTrigger);
		}
		return new Pair(task.configuredTriggerExpression, task.configuredTrigger);
	}

	protected Pair<String,Trigger> readNewTriggerConfig(String configName)
	throws BadTriggerConfigException {
		final String configValue =
				StringUtils.trimToNull(configService.getByNameNullOrDefaultValue(configName));
		if ( configValue == null ) {
			return null;
		}
		return new Pair(configValue, parseTriggerConfig(configValue));
	}

	protected Trigger parseTriggerConfig(String configValue)
	throws BadTriggerConfigException {

		Pair<String,Trigger> trigger = null;
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

	// For all of the scheduled methods below... don't need to call
	// securityService.currentFallingBackToAdmin()... it doesn't actually do any
	// good... it won't be cached as the "current" user. Do need to clean up
	// tho, b/c when securityService.currentFallingBackToAdmin() *is* called in
	// the depths of syncCoaches(), threadlocals will be set

	@Override
	@Scheduled(fixedDelay = 150000)
	// run 2.5 minutes after the end of the last invocation
	public void sendMessages() {
		try {
			messageService.sendQueuedMessages();
		} finally {
			securityService.afterRequest();
		}
	}

	@Override
	@Scheduled(fixedDelay = 300000)
	// run every 5 minutes
	public void syncCoaches() {
		if ( !(scheduledCoachSyncEnabled) ) {
			LOGGER.debug("Scheduled coach sync disabled. Abandoning sync job");
			return;
		}
		try {
			LOGGER.info("Scheduled coach sync starting.");
			PagingWrapper<Person> localCoaches = personService.syncCoaches();
			LOGGER.info("Scheduled coach sync complete. Local coach count [{}]",
					localCoaches.getResults());
		} finally {
			securityService.afterRequest();
		}
	}

	/**
	 * Not {@code @Scheduled} b/c its scheduling is now handled by the
	 * config polling job.
	 */
	@Override
	public void syncExternalPersons() {
		try {
			externalPersonSyncTask.exec();
		} finally {
			securityService.afterRequest();
		}
	}

	@Override
	@Scheduled(cron = "0 0 1 * * *")
	// run at 1 am every day
	public void sendTaskReminders() {
		try {
			taskService.sendAllTaskReminderNotifications();
		} finally {
			securityService.afterRequest();
		}
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
