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
package org.jasig.ssp.service.jobqueue.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.jobqueue.WorkflowStatus;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.jobqueue.BulkJobQueueTask;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.jobqueue.BulkEmailStudentRequestForm;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class BulkJobQueueTaskImpl implements BulkJobQueueTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkJobQueueTaskImpl.class);

	private Date startupTime = Calendar.getInstance().getTime();
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private JobService jobService;
	
	@Value("#{configProperties.system_id}")
	private  String systemId = "";
	
	@Autowired
	private WithTransaction withTransaction;
	
	@Autowired 
	private MessageService messageService;
	
	@Autowired 
	private PersonService personService;
	
	@Autowired
	protected transient MessageTemplateService  messageTemplateService;
	
	@Autowired
	protected transient ThreadPoolTaskExecutor taskExecutor;

	public Class<Void> getBatchExecReturnType() {
		return Void.TYPE;
	}
	@Override
	public void exec(CallableExecutor<Void> batchExecutor) {
		
		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Bulk job processing because of thread interruption");
			return;
		}
		
		List<Job> jobs = jobService.getNextQueuedJobsForExecution(10,getProcessIdentifier());
		for (Job job : jobs) {
			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Bulk job processing because of thread interruption");
				return;
			}
			if(job.getExecutionComponentName().equals("bulk-email-executor") )
			{
				BulkEmailRunnable bulkEmailRunnable = new BulkEmailRunnable(job, withTransaction, jobService,personService,configService);
				taskExecutor.execute(bulkEmailRunnable);
				try {
					markAsScheduled(job);
				} catch (Exception e) {
					LOGGER.error("Error scheduling job with id {}",job.getId());
				}
			}
		}
		
	}
	
	protected Job markAsScheduled(Job job) throws Exception {
		job.setWorkflowStatus(WorkflowStatus.SCHEDULING);
		job.setSchedulingStartedDate(Calendar.getInstance().getTime());
		job.setScheduledByProcess(getProcessIdentifier());
		return saveInTransaction(job);
	}
	
	private String getProcessIdentifier() {
		return systemId+"-"+startupTime+"."+systemId;
	}
	protected Job saveInTransaction(final Job job) throws Exception {
		return withTransaction.withNewTransaction(new Callable<Job>() {
			@Override
			public Job call() throws Exception {
				jobService.save(job);
				return job;
			}
		});
	}

}
