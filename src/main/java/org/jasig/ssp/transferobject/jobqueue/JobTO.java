package org.jasig.ssp.transferobject.jobqueue;

import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.PersonLiteTO;

public class JobTO extends AbstractAuditableTO<Job> {

	public JobTO(Job job) {
		super(job.getId());
		setCreatedBy(new PersonLiteTO(job.getCreatedBy()));
		setModifiedBy(new PersonLiteTO(job.getModifiedBy()));
		setCreatedDate(job.getCreatedDate());
		setModifiedDate(job.getModifiedDate());
	}

}
