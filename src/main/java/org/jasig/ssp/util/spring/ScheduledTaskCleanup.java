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
package org.jasig.ssp.util.spring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Ensures the task scheduler and executor are shutdown prior to
 * "normal" app context cleanup. We need this because a race
 * condition exists where a currently executing job on a background
 * thread can request a bean dependency (e.g. when a
 * {@code @Transactional} method unwinds) after the bean factory
 * has destroyed all singletons. If that destruction has completed
 * there are no further protections against bean creation. This can
 * then manifest as problems such as re-acquisition of the Liquibase
 * lock, which might not then be released if the (usually daemon)
 * worker threads subsequently terminate unceremoniously. This was
 * reported as
 * <a href="https://issues.jasig.org/browse/SSP-1273">SSP-1273</a>.
 *
 * <p>This workaround is a slightly modified version of the workaround
 * described in a StackOverflow
 * <a href="http://stackoverflow.com/a/6603443">answer</a>.</p>
 *
 * <p>Decided this was a better approach than playing games with either
 * bean definition ordering or the {@code depends-on} attribute. The
 * latter might eventually make sense even if just to ensure a
 * more sane startup order, but prevents the use of XML-namespaced
 * configuration shortcuts.</p>
 *
 *
 */
@Component
public class ScheduledTaskCleanup implements ApplicationListener<ContextClosedEvent> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduledTaskCleanup.class);

	public static int DEFAULT_WAIT_MILLIS = 10000;


	@Autowired(required = false)
	private ThreadPoolTaskScheduler scheduler;

	@Autowired
	private ApplicationContext owningContext;

	@Value("#{configProperties.scheduled_task_cleanup_wait_millis}")
	private int waitMillis = DEFAULT_WAIT_MILLIS;

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if ( scheduler == null ) {
			return;
		}
		if ( owningContext.getId().equals(event.getApplicationContext().getId()) ) {
			shutdownAndAwaitTermination(scheduler.getScheduledExecutor());
		}
	}

	/**
	 * See javadoc for {@link ExecutorService} for why this is necessary.
	 * Also commented on w/r/t the SO answer linked to in class
	 * javadoc. This can be eliminated with an upgrade to Spring
	 * 3.2.1+, which fixes
	 * <a href="https://jira.springsource.org/browse/SPR-5387">SPR-5387</a>.
	 *
	 * @param pool
	 */
	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdownNow(); // Don't really need things to run to completion
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(waitMillis, TimeUnit.MILLISECONDS)) {
				LOGGER.warn("Scheduled task ExcecutorService did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
}
