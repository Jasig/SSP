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
package org.jasig.ssp.service.impl

import org.jasig.ssp.service.reference.ConfigService
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.CronTrigger
import org.springframework.scheduling.support.PeriodicTrigger
import spock.lang.Specification

import java.util.concurrent.ScheduledFuture
import java.util.regex.Pattern

class ScheduledTaskWrapperServiceImplTest extends Specification {

	def ScheduledTaskWrapperServiceImpl scheduledTaskWrapper
	def ConfigService configService
	def TaskScheduler taskScheduler

	def setup() {
		this.scheduledTaskWrapper = new ScheduledTaskWrapperServiceImpl()
		resetMocks()
	}

	def resetMocks() {
		// chained assignment doesn't seem to work here. Maybe fails Spock's
		// type inference for Mocks
		this.taskScheduler = Mock(TaskScheduler)
		this.configService = Mock(ConfigService)
		scheduledTaskWrapper.taskScheduler = taskScheduler
		scheduledTaskWrapper.configService = configService
	}

	def "schedules tasks with default triggers"() {
		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper is initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "the first task is scheduled with default cron trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> null
		1 * taskScheduler.schedule(_ as Runnable, new CronTrigger("0 0 1 * * *")) >> ([:] as ScheduledFuture)

		then: "the second task is scheduled with default periodic trigger"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> null
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(15 * 60 * 1000) ) >> ([:] as ScheduledFuture)
	}

	def "schedules tasks with configured triggers"() {
		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper is initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "the first task is scheduled with the configured trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "1 1 1 1 1 1"
		1 *  taskScheduler.schedule(_ as Runnable, new CronTrigger("1 1 1 1 1 1")) >> ([:] as ScheduledFuture)

		then: "the second task is scheduled with the configured trigger"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> "999"
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(999) ) >> ([:] as ScheduledFuture)
	}

	def "reschedules tasks with reconfigured triggers"() {

		def scheduledTasks = []

		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper has already been initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "we capture the resulting futures"
		2 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ({
			def ScheduledFuture scheduledTask = Mock()
			scheduledTasks << scheduledTask
			scheduledTask
		})

		when: "the scheduled task wrapper updates its task schedules"
		scheduledTaskWrapper.updateTasks()

		then: "the first task is canceled and scheduled with a newly configured trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "2 2 2 2 2 2"
		1 * scheduledTasks[0].cancel(true)
		1 * taskScheduler.schedule(_ as Runnable, new CronTrigger("2 2 2 2 2 2")) >> ([:] as ScheduledFuture)


		then: "the second task is also canceled scheduled with a newly configured trigger"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> "111"
		1 * scheduledTasks[1].cancel(false)
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(111) ) >> ([:] as ScheduledFuture)

	}

	def "ignores bad config during initialization"() {
		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper is initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "the first task falls back to being scheduled with the default trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "foo"
		1 * taskScheduler.schedule(_ as Runnable, new CronTrigger("0 0 1 * * *")) >> ([:] as ScheduledFuture)

		then: "the second task also falls back to being scheduled with the default trigger"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> "bar"
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(15 * 60 * 1000) ) >> ([:] as ScheduledFuture)
	}

	def "ignores bad config when rescheduling"() {

		def scheduledTasks = []

		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper has already been initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "we capture the resulting futures"
		2 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ({
			def ScheduledFuture scheduledTask = Mock()
			scheduledTasks << scheduledTask
			scheduledTask
		})

		when: "the scheduled task wrapper updates its task schedules"
		scheduledTaskWrapper.updateTasks()

		then: "the config service reports bad configs for both tasks"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "foo"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> "bar"

		then: "neither task is canceled nor rescheduled"
		0 * scheduledTasks[0].cancel(_)
		0 * scheduledTasks[1].cancel(_)
		0 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ([:] as ScheduledFuture)
	}

	// probably paranoia
	def "still processes good config in spite of some bad config when rescheduling"() {
		def scheduledTasks = []

		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper has already been initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "we capture the resulting futures"
		2 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ({
			def ScheduledFuture scheduledTask = Mock()
			scheduledTasks << scheduledTask
			scheduledTask
		})

		when: "the scheduled task wrapper updates its task schedules"
		scheduledTaskWrapper.updateTasks()

		then: "the config service reports bad configs for the first task and good config for the second"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "foo"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> "222"

		then: "only the second task is rescheduled"
		0 * scheduledTasks[0].cancel(_)
		1 * scheduledTasks[1].cancel(false)
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(222)) >> ([:] as ScheduledFuture)
	}

	def "negative periods treated as disabled triggers"() {
		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper is initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "the first task is scheduled with a disabled trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "-1"
		1 *  taskScheduler.schedule(_ as Runnable, new ScheduledTaskWrapperServiceImpl.DisabledTrigger()) >> ([:] as ScheduledFuture)

		then: "the second task is unaffected and gets the default trigger"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> null
		1 * taskScheduler.schedule(_ as Runnable, new PeriodicTrigger(15 * 60 * 1000) ) >> ([:] as ScheduledFuture)
	}

	def "ignores unmodified config when rescheduling"() {
		def scheduledTasks = []

		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper has already been initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "we capture the resulting futures"
		2 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ({
			def ScheduledFuture scheduledTask = Mock()
			scheduledTasks << scheduledTask
			scheduledTask
		})

		when: "the scheduled task wrapper updates its task schedules"
		scheduledTaskWrapper.updateTasks()

		then: "the config service reports bad configs for both tasks"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> "0 0 1 * * *"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> (15 * 60 * 1000 + "")

		then: "neither task is cancelled nor rescheduled"
		0 * scheduledTasks[0].cancel(_)
		0 * scheduledTasks[1].cancel(_)
		0 * taskScheduler.schedule(_ as Runnable, _ as Trigger) >> ([:] as ScheduledFuture)
	}

	def "supports periodic triggers with initial delays"() {

		given: "a scheduled task wrapper with a mocked TaskScheduler and ConfigService"

		when: "the scheduled task wrapper is initialized"
		scheduledTaskWrapper.afterPropertiesSet()

		then: "the first task is scheduled with default cron trigger"
		1 * configService.getByNameNullOrDefaultValue("task_external_person_sync_trigger") >> null
		1 * taskScheduler.schedule(_ as Runnable, new CronTrigger("0 0 1 * * *")) >> ([:] as ScheduledFuture)

		then: "the second task is scheduled with default periodic trigger with a delay"
		1 * configService.getByNameNullOrDefaultValue("task_scheduler_config_poll_trigger") >> (15 * 60 * 1000 + "/5000")
		interaction {
			def expectedTrigger = new PeriodicTrigger(15 * 60 * 1000)
			expectedTrigger.setInitialDelay(5000)
			1 * taskScheduler.schedule(_ as Runnable, expectedTrigger ) >> ([:] as ScheduledFuture)
		}

	}

	def "quick regexp test"() {
		when:
		// use Java regexp api directly to make sure we don't run afoul
		// of subtle differences in groovy regexps
		def pattern = Pattern.compile("^(\\d+)/(\\d+)\$")

		then:
		assert true
		pattern.matcher("000/000").matches()
		pattern.matcher("111/222").matches()
		!(pattern.matcher("000")).matches()
		!(pattern.matcher("/")).matches()
		!(pattern.matcher("0/")).matches()
		!(pattern.matcher("/0")).matches()
		def matcher = pattern.matcher("111/222")
		matcher.matches() && matcher.group(1) == "111" && matcher.group(2) == "222"
	}

}
