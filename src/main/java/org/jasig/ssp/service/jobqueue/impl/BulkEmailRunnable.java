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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BulkEmailRunnable extends AbstractBulkRunnable<BulkEmailStudentRequestForm> implements Runnable {

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	private BulkEmailJobSpec jobSpec;

	private PersonService personService;

	private ConfigService configService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkEmailRunnable.class);
	
	public BulkEmailRunnable(Job job, WithTransaction withTransaction, JobService jobService, PersonService personService,ConfigService configService) throws IOException {
		super(job,withTransaction,jobService);
		this.jobSpec = JSON_MAPPER.readValue(job.getExecutionState(), BulkEmailJobSpec.class);
		this.personService = personService;
		this.configService = configService;
	}

	@Override
	protected void doWork() throws Exception {
		final List<ImmutablePersonIdentifiersTO> totalWork = jobSpec.getPersonIdentifiersFromCoreSpecCriteria();
		final List<ImmutablePersonIdentifiersTO> remainingWork = Lists.newArrayList(totalWork);
		Integer numJobs = Integer.parseInt(configService.getByName("bulk_job_queue_batch_size").getValue());
		List<List<ImmutablePersonIdentifiersTO>> batches = organizeJobInBatches(totalWork,numJobs);
		for (List<ImmutablePersonIdentifiersTO> batch : batches) {
			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Bulk job processing because of thread interruption");
				return;
			}
			removeCurrentWorkFromRemainingWork(remainingWork, batch);
			jobSpec.setPersonIdentifiersFromCoreSpecCriteria(remainingWork);
			getJob().setExecutionState(AbstractBulkRunnable.serialize(jobSpec));
			Map<ImmutablePersonIdentifiersTO,String> errors = processBatch(batch, getJob());
			if(!errors.isEmpty())
			{
				String existingDesc;
				if(getJob().getWorkflowStatusDesc()!= null)
				{
					existingDesc = getJob().getWorkflowStatusDesc();
				}
				else
				{
					existingDesc ="";
				}
				getJob().setWorkflowStatusDesc(existingDesc+toWorkflowStatusDesc(errors));
			}
		}
	}

	private void removeCurrentWorkFromRemainingWork(List<ImmutablePersonIdentifiersTO> remainingWork,
			List<ImmutablePersonIdentifiersTO> batch) {
		for (ImmutablePersonIdentifiersTO ids : batch) {
			remainingWork.remove(ids);
		}
	}

	private String toWorkflowStatusDesc(Map<ImmutablePersonIdentifiersTO, String> errors) {
		try {
			return JSON_MAPPER.writeValueAsString(errors);
		} catch ( IOException e ) {
			// TOOD don't like. Need more predictable content in this field. Spec allows *much* squishyness
			return "{\"errors:\": \"" +
					StringEscapeUtils.escapeJavaScript(errors.toString()) + "\"}";
		}
	}

	private String formCommaSeparatedValue(List<String> remainingWork) {
		StringBuilder builder = new StringBuilder();
		for (String string : remainingWork) {
			builder.append(string+",");
		}
		if(builder.length() > 0)
		{
			builder.deleteCharAt(builder.length()-1);
		}
		return builder.toString();
	}

	private Map<ImmutablePersonIdentifiersTO,String> processBatch(final List<ImmutablePersonIdentifiersTO> batch,final Job job)
			throws Exception {
		
		 return getWithTransaction().withNewTransaction(new Callable<Map<ImmutablePersonIdentifiersTO,String>>() {
			@Override
			public Map<ImmutablePersonIdentifiersTO,String> call() throws Exception {
				Map<ImmutablePersonIdentifiersTO,String> errors = new HashMap<ImmutablePersonIdentifiersTO,String>();
				int i=0;
				for (ImmutablePersonIdentifiersTO studentIds : batch) {
					try {
						final Person student;
						if (studentIds.getId() != null) {
							student = personService.get(studentIds.getId());
						} else {
							student = personService.getBySchoolId(studentIds.getSchoolId(), false);
						}
						Person runAs = getJob().getRunAs();
						EmailStudentRequestForm emailStudentRequestForm = new EmailStudentRequestForm(jobSpec.getCoreSpec(),student,runAs);
						personService.emailStudent(emailStudentRequestForm);
						i++;
					} catch(Exception e) {
						LOGGER.error("Error processing bulk email enqueue batch",e);
						errors.put(studentIds, e.getMessage());
					}
				}
				getJobService().save(job);
				return errors;
			}
		});
	}

	private List<List<ImmutablePersonIdentifiersTO>> organizeJobInBatches(List<ImmutablePersonIdentifiersTO> studentIds, Integer numJobs) {
		int batchLimiter = 0;
		int batchCounter = 0;
		List<List<ImmutablePersonIdentifiersTO>> result = new ArrayList<List<ImmutablePersonIdentifiersTO>>();
		for (ImmutablePersonIdentifiersTO studentId : studentIds) {
			if(batchLimiter % numJobs == 0)
			{
				List<ImmutablePersonIdentifiersTO> batch = new ArrayList<ImmutablePersonIdentifiersTO>();
				batch.add(studentId);
				result.add(batch);
				batchCounter++;
			}
			else
			{
				List<ImmutablePersonIdentifiersTO> currentBatch = result.get(batchCounter-1);
				currentBatch.add(studentId);
			}
			batchLimiter++;
		}
		return result;
	}

}
