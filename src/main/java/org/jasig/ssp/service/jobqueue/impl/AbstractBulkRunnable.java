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

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.jobqueue.WorkflowStatus;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class AbstractBulkRunnable<T extends Serializable> implements Runnable{

	private WithTransaction withTransaction;
	private JobService jobService;
	private Job job;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractBulkRunnable.class);
	protected Job getJob() {
		return job;
	}

	protected WithTransaction getWithTransaction() {
		return withTransaction;
	}

	protected JobService getJobService() {
		return jobService;
	}

	public AbstractBulkRunnable(Job job, WithTransaction withTransaction,
			JobService jobService) 
	{
		this.job = job;
		this.withTransaction = withTransaction;
		this.jobService = jobService;
	}
	
	@Override
	public void run() {
			try 
			{
				markAsExecuting();
	 			doWork();
				markAsTerminated();
					
			} catch(Exception e )
			{
				LOGGER.error("Error in bulk processing: {}", e);
				job.setRetryCount(job.getRetryCount() == null ? 0 : job.getRetryCount());
				if(job.getRetryCount() != null &&  job.getRetryCount() >= 3)
				{
					markAsFailure(e);
				}
				else
				{
					//If we fail trying to save the failure, probably a database issue so log it
					try {
						job.setRetryCount(job.getRetryCount() + 1);
						saveInTransaction(job);
					}catch(Exception ee ){
						LOGGER.error("Error in bulk processing: {}", ee);
					}				
				}
				
			}
	}
	
	protected void markAsExecuting() throws Exception {
		job.setWorkflowStatus(WorkflowStatus.EXECUTING);
		job.setExecutionStartedDate(Calendar.getInstance().getTime());
		saveInTransaction(job);
	}
	protected void markAsFailure(Exception e) {
		job.setWorkflowStatus(WorkflowStatus.FAILURE);
		job.setWorkflowStoppedDate(Calendar.getInstance().getTime());
		
		//If we fail trying to save the failure, probably a database issue so log it
		try {
			saveInTransaction(job);
		}catch(Exception ee ){
			LOGGER.error("Error in bulk processing", ee);
		}

	 
	}
	protected void markAsTerminated() throws Exception {
		if(job.getWorkflowStatusDesc() == null || job.getWorkflowStatusDesc().isEmpty())
		{
			job.setWorkflowStatus(WorkflowStatus.COMPLETED);
		} else
		{
			job.setWorkflowStatus(WorkflowStatus.ERROR);

		}
		job.setWorkflowStoppedDate(Calendar.getInstance().getTime());
		saveInTransaction(job);
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
 
	protected abstract void doWork() throws Exception;
	
	
	public static String serialize(Serializable jobSpec) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String objectJson;
		objectJson = mapper.writeValueAsString(jobSpec);
		return objectJson;
	}

}
