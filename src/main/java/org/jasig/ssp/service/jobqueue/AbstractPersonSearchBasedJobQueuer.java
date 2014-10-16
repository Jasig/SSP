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
package org.jasig.ssp.service.jobqueue;

import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.HasPersonSearchRequest;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


public abstract class AbstractPersonSearchBasedJobQueuer<R extends HasPersonSearchRequest, P> {

	private transient SecurityService securityService;
	private transient PersonSearchRequestTOFactory personSearchRequestFactory;
	private transient PersonSearchService personSearchService;
	private transient JobExecutor<P> jobExecutor;


	protected AbstractPersonSearchBasedJobQueuer(JobExecutor<P> jobExecutor, SecurityService securityService,
												 PersonSearchRequestTOFactory personSearchRequestFactory,
												 PersonSearchService personSearchService) {
		this.securityService = securityService;
		this.personSearchRequestFactory = personSearchRequestFactory;
		this.personSearchService = personSearchService;
		this.jobExecutor = jobExecutor;
	}

	/**
	 * Main 'template method' that dispatches to large collection of protected methods for actual heavy lifting.
	 * Client responsible for initializing and managing a transaction if necessary.
	 *
	 */
	public JobTO enqueueJob(R jobRequest) throws ObjectNotFoundException, IOException, ValidationException, SecurityException {
		final Person currentSspPerson = currentlyAuthenticatedPerson();
		jobRequest = validateJobRequest(jobRequest);

		final PersonSearchRequest searchRequest = preparePersonSearchRequest(jobRequest, currentSspPerson);
		final long searchResultCount = countPersonSearchResults(searchRequest);
		validatePersonSearchResults(searchResultCount, searchRequest, currentSspPerson);

		return doEnqueueJob(jobRequest, currentSspPerson, searchRequest, searchResultCount);
	}

	protected Person currentlyAuthenticatedPerson() throws SecurityException {
		final SspUser currentSspUser = securityService.currentlyAuthenticatedUser();
		if ( currentSspUser == null ) {
			final SecurityException securityException = noCurrentlyAuthenticatedPersonException();
			if ( securityException == null ) {
				return null;
			}
			throw securityException;
		}
		final Person currentSspPerson = currentSspUser.getPerson();
		return currentSspPerson;
	}

	protected SecurityException noCurrentlyAuthenticatedPersonException() throws SecurityException {
		return new SecurityException("Anonymous user not allowed to submit bulk action requests");
	}

	protected abstract R validateJobRequest(R jobRequest) throws ValidationException;

	protected PersonSearchRequest preparePersonSearchRequest(R jobRequest, Person currentSspPerson) throws ObjectNotFoundException {
		final PersonSearchRequestTO criteria = jobRequest.getCriteria();
		final PersonSearchRequest searchRequest = personSearchRequestFactory.from(criteria);
		final SortingAndPaging origSortAndPage = searchRequest.getSortAndPage();
		SortingAndPaging validatedSortAndPage = origSortAndPage;
		if ( origSortAndPage == null ) {
			validatedSortAndPage = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE,
					0, 100, // these two don't matter, will be overwritten when the job actually runs
					"dp.lastName", SortDirection.ASC.name(), null);
		} else if ( !(origSortAndPage.isSorted()) && !(origSortAndPage.isDefaultSorted()) ) {
			validatedSortAndPage = SortingAndPaging.createForSingleSortWithPaging(origSortAndPage.getStatus(),
					origSortAndPage.getFirstResult(), origSortAndPage.getMaxResults(),
					"dp.lastName", SortDirection.ASC.name(), null);
		}
		searchRequest.setSortAndPage(validatedSortAndPage);
		criteria.setSortAndPage(validatedSortAndPage);
		return searchRequest;
	}

	protected long countPersonSearchResults(PersonSearchRequest searchRequest) {
		final Long tmpCnt = personSearchService.searchPersonDirectoryCount(searchRequest);
		final long cnt = tmpCnt == null ? 0L : tmpCnt.longValue();
		return cnt;
	}

	protected void validatePersonSearchResults(long searchResultCount, PersonSearchRequest searchRequest, Person currentSspPerson)
			throws ValidationException {
		if ( searchResultCount <= 0 ) {
			// TODO would be nice to have a better way of representing a no-op job, b/c an exception is really
			// overly unfriendly, but a null return is so non-descriptive as to be useless. So we opt for a
			// ValidationException so the client *knows* nothing really happened.
			final ValidationException validationException = noPersonSearchResultsException();
			if ( validationException == null ) {
				return;
			}
			throw validationException;
		}
	}

	protected ValidationException noPersonSearchResultsException() {
		return new ValidationException("Person search parameters matched no records. Can't process bulk action request.");
	}

	protected JobTO doEnqueueJob(R jobRequest, Person currentSspPerson, PersonSearchRequest searchRequest, long searchResultCount)
			throws ValidationException {
		final P jobSpec = newJobSpec(jobRequest, currentSspPerson, searchRequest, searchResultCount);
		final Job job = this.jobExecutor.queueNewJob(currentSspPerson.getId(), currentSspPerson.getId(), jobSpec);
		return new JobTO(job);
	}

	protected abstract P newJobSpec(R jobRequest, Person currentSspPerson, PersonSearchRequest searchRequest, long searchResultCount);

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public PersonSearchRequestTOFactory getPersonSearchRequestFactory() {
		return personSearchRequestFactory;
	}

	public void setPersonSearchRequestFactory(PersonSearchRequestTOFactory personSearchRequestFactory) {
		this.personSearchRequestFactory = personSearchRequestFactory;
	}

	public PersonSearchService getPersonSearchService() {
		return personSearchService;
	}

	public void setPersonSearchService(PersonSearchService personSearchService) {
		this.personSearchService = personSearchService;
	}

	public JobExecutor<P> getJobExecutor() {
		return jobExecutor;
	}

	public void setJobExecutor(JobExecutor<P> jobExecutor) {
		this.jobExecutor = jobExecutor;
	}
}
