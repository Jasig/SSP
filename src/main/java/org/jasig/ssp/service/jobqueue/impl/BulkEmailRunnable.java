package org.jasig.ssp.service.jobqueue.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.hibernate.service.config.spi.ConfigurationService;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.BulkEmailStudentRequestForm;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.StringUtils;


public class BulkEmailRunnable extends AbstractBulkRunnable<BulkEmailStudentRequestForm> implements Runnable {

	private BulkEmailStudentRequestForm form;

	private PersonService personService;

	private ConfigService configService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkEmailRunnable.class);
	
	public BulkEmailRunnable(Job job, WithTransaction withTransaction, JobService jobService, PersonService personService,ConfigService configService) {
		
		super(job,withTransaction,jobService);
		this.form = BulkEmailStudentRequestForm.create(job.getExecutionState()); 
		this.personService = personService;
		this.configService = configService;
	}
	

	@Override
	protected void doWork() throws Exception {
		
		String[] studentIds = form.getStudentIds().split(",");
		List<String>  remainingWork = new ArrayList<String>();
		for (String string : studentIds) {
			remainingWork.add(string);
		}
		Integer numJobs = Integer.parseInt(configService.getByName("bulk_job_queue_batch_size").getValue());
		List<List<UUID>> batches = organizeJobInBatches(studentIds,numJobs);
		for (List<UUID> batch : batches) {
			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning external person sync because of thread interruption");
				return;
			removeCurrentWorkFromRemainingWork(remainingWork, batch);
			form.setStudentIds(formCommaSeparatedValue(remainingWork));
			getJob().setExecutionState(AbstractBulkRunnable.serialize(form));
			Map<UUID,String> errors = processBatch(batch,getJob());
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


	private void removeCurrentWorkFromRemainingWork(List<String> remainingWork,
			List<UUID> batch) {
		for (UUID uuid : batch) {
			remainingWork.remove(uuid.toString());
		}
	}




	private String toWorkflowStatusDesc(Map<UUID, String> errors) {
		StringBuilder builder = new StringBuilder();
		Set<UUID> keySet = errors.keySet();
		for (UUID uuid : keySet) {
			builder.append("[  "+uuid.toString()+":  "+errors.get(uuid)+"  ]");
		}
		return builder.toString();
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


	private Map<UUID,String> processBatch(final List<UUID> batch,final Job job)
			throws Exception {
		
		 return getWithTransaction().withNewTransaction(new Callable<Map<UUID,String>>() {
			@Override
			public Map<UUID,String> call() throws Exception {
				Map<UUID,String> errors = new HashMap<UUID,String>();
				int i=0;
				for (UUID studentId : batch) {
					try 
					{
						Person student = personService.get(studentId);
						Person runAs = getJob().getRunAs();
						EmailStudentRequestForm emailStudentRequestForm = new EmailStudentRequestForm(form,student,runAs);
						personService.emailStudent(emailStudentRequestForm);
						i++;
					} catch(Exception e)
					{
						LOGGER.error("Error in bulk Email Processing: {}",e);
						errors.put(studentId, e.getMessage());
					}
				}
				getJobService().save(job);
				return errors;
			}
		});
	}


	private List<List<UUID>> organizeJobInBatches(String[] studentIds, Integer numJobs) {
		int batchLimiter = 0;
		int batchCounter = 0;
		List<List<UUID>> result = new ArrayList<List<UUID>>();
		for (String studentId : studentIds) {
			if(batchLimiter % numJobs == 0)
			{
				List<UUID> batch = new ArrayList<UUID>();
				batch.add(UUID.fromString(studentId));
				result.add(batch);
				batchCounter++;
			}
			else
			{
				List<UUID> currentBatch = result.get(batchCounter-1);
				currentBatch.add(UUID.fromString(studentId));
			}
			batchLimiter++;
		}
		return result;
	}



}
