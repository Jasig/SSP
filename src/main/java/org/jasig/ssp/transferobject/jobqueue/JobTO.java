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
