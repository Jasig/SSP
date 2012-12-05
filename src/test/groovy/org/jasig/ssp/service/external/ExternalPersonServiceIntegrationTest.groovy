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
package org.jasig.ssp.service.external

import org.hibernate.SessionFactory
import org.hibernate.criterion.Projections
import org.jasig.ssp.model.Person
import org.jasig.ssp.service.PersonService
import org.jasig.ssp.service.external.impl.ExternalPersonServiceImpl
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment
import org.jasig.ssp.util.transaction.WithTransaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Timeout

import static org.hamcrest.Matchers.lessThan
import org.jasig.ssp.util.service.stub.Stubs
import java.util.concurrent.Callable
import spock.util.concurrent.BlockingVariable
import spock.lang.Ignore

@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
class ExternalPersonServiceIntegrationTest extends Specification {

	@Autowired
	ExternalPersonService externalPersonService

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	PersonService personService;

	@Autowired
	SecurityServiceInTestEnvironment securityService;

	@Autowired
	WithTransaction withTransaction;

	def setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID))

	}

	def cleanup() {
		securityService.setCurrent(new Person())

		// This clears the interrupt flag on the main test thread. Important
		// b/c @Timeout will try to kill the current test by sending the main
		// thread an interrupt, but it doesn't seem to clear the interrupt flag
		// before the next test. This will cause subsequent attempt to use
		// java.util.concurrent objects or Spock niceties like BlockingVariable
		// to blow up immediately with InterruptedExceptions. We do the same
		// thing in feature method-embedded cleanup: blocks, then again here
		// just to be sure.
		Thread.currentThread().interrupted()
	}

	@Timeout(5)
	def "test external person sync job does not block person lookup"() {
		when: "we make a change the sync job will later override"
		def firstNameMod = null
		withTransaction.withNewTransaction {
			def person = findPerson(Stubs.PersonFixture.KEN)
			firstNameMod = person.firstName + "foo"
			person.firstName = firstNameMod
			personService.save(person)
		} as Callable

		then: "proceed to fire the sync job"

		when: "the sync job runs for a very long time and holds its transaction open"
		def testThread = Thread.currentThread()
		def jobErr = null
		def jobFired = new BlockingVariable<Boolean>(60)
		def job = Thread.start() {
			// set up our own transaction boundary so we can hold transaction
			// open as long as we want. assumes syncWithPerson() would be
			// opening a transaction itself under normal circumstances. will
			// always throw an exception to roll back the transaction, so we
			// just swallow anything that escapes. "real" errors will be cached
			// in jobErr
			swallow {
				withTransaction.withNewTransaction {
					try {
						externalPersonService.syncWithPerson()
						// flush() is critical... else Hib might not have
						// actually sent any writes to the db, so the locks
						// we're concerned about in the db likely won't be held
						sessionFactory.currentSession.flush()
						jobFired.set(true)
					} catch (e) {
						jobErr = e
					}
					if ( !(jobErr) ) {
						swallow {
							testThread.join(10000) // timeout here a paranoid safeguard to make sure we
												   // always exit. real timeout of interest is in the
												   // @Timeout on the method. The timeout here must
												   // always be larger than that value.
						}
					}
					throw new RuntimeException("Thrown to make sure sync job transaction rolls back")
				} as Callable
			}
		}
		assert jobFired.get() // make abs sure the job actually ran

		then: "the person record should be findable and in its pre-sync-job state even while the sync job transaction is still open"
		findPerson(Stubs.PersonFixture.KEN).firstName == firstNameMod

		cleanup:
		// without this the write in the 'job' thread will block our cleanup
		// write
		job.interrupt()
		// see notes in cleanup()
		Thread.currentThread().interrupted()
		withTransaction.withNewTransaction {
			def person = findPerson(Stubs.PersonFixture.KEN)
			person.firstName = Stubs.PersonFixture.KEN.firstName()
			personService.save(person)
		} as Callable
	}

	// This is a timeout-based test, but the timeout is in a BlockingVariable
	// rather than a test-level annotation
	def "test person create does not block external person sync job"() {

		when:
		def totalPersons = sessionFactory
				.currentSession
				.createCriteria(Person)
				.setProjection(Projections.count("id"))
				.uniqueResult() as Integer;

		then: "verify fewer than BATCH_SIZE records in person table"
		totalPersons lessThan(ExternalPersonServiceImpl.BATCH_SIZE_FOR_PERSON_)

		when: "create new person"
		createPerson()

		and: "flush new person to db without txn commit"
		sessionFactory.currentSession.flush()

		and: "simulate run of background job"
		def jobErr = null
		def jobFired = new BlockingVariable<Boolean>(5)
		def job =  Thread.start() {
			// wrap w/ our own explicit txn b/c we need to be able to roll back
			// the sync job. see notes on similar machinery in
			// "test external person sync job does not block person lookup"
			swallow {
				withTransaction.withNewTransaction {
					try {
						externalPersonService.syncWithPerson()
						// flush() is critical... else Hib might not have
						// actually sent any writes to the db, so the locks
						// we're concerned about in the db likely won't be held
						sessionFactory.currentSession.flush()
					} catch (e) {
						jobErr = e
					}
					jobFired.set(true)
					throw new RuntimeException("Thrown to make sure sync job transaction rolls back")
				}
			}
		}

		then: "job should complete firing in a reasonable time and should not encounter any errors"
		jobFired.get()
		!jobErr
		notThrown(Exception)

	}

	Person createPerson() {
		personService.create(
				new Person(firstName: "first", lastName: "last",
						username: "username", schoolId: "schoolId",
						primaryEmailAddress: "email",
						addressLine1: "address line 1", cellPhone: "867-5309"))
	}

	Person findPerson(personFixture) {
		Stubs.person(personFixture, personService);
	}

	Person findExternalPerson(personFixture) {
		externalPersonService.getBySchoolId(personFixture.schoolId)
	}

	def swallow(clos) {
		try {
			clos()
		} catch (e) {}
	}
}
