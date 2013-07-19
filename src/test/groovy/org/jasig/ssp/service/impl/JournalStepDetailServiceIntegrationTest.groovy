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

import org.hibernate.SessionFactory
import org.jasig.ssp.model.ObjectStatus
import org.jasig.ssp.model.Person
import org.jasig.ssp.model.reference.JournalStep
import org.jasig.ssp.model.reference.JournalStepDetail
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment
import org.jasig.ssp.service.reference.JournalStepDetailService
import org.jasig.ssp.service.reference.JournalStepService
import org.jasig.ssp.transferobject.ServiceResponse
import org.jasig.ssp.util.sort.SortingAndPaging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
class JournalStepDetailServiceIntegrationTest extends Specification {

	@Autowired
	JournalStepService journalStepService

	@Autowired
	JournalStepDetailService journalStepDetailService

	@Autowired
	SecurityServiceInTestEnvironment securityService;

	@Autowired
	SessionFactory sessionFactory;

	def setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID))
	}

	def cleanup() {
		securityService.setCurrent(new Person())
	}

	def "can query for only inactive journal details associated with a journal step"() {

		given: "an active journal step and two details, one active and one inactive"
		def journalStep = journalStepService.create(new JournalStep(name: "journal step",
				objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail1 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 1", objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail2 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 2", objectStatus: ObjectStatus.ACTIVE))
		def binding1 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail1, journalStep)
		def binding2 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail2, journalStep)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		and: "one of the step-detail bindings is deactivated"
		journalStepService.removeJournalStepDetailFromJournalStep(journalStepDetail1, journalStep)

		and: "the hib session is flushed for good measure again"
		sessionFactory.currentSession.flush()

		when: "all inactive details for the step are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.INACTIVE, null, null, null, null, null)
		def filteredResults = journalStepDetailService.getAllForJournalStep(journalStep, sAndP)

		then: "the result only contains the inactive detail binding"
		filteredResults.results == 1
		filteredResults.rows.size() == 1
		filteredResults.each { result ->
			assert(result.objectStatus == ObjectStatus.INACTIVE)
			assert(result.id == journalStepDetail1.id)
		}

	}

	def "can query for only active journal details associated with a journal step"() {

		given: "an active journal step and two details, one active and one inactive"
		def journalStep = journalStepService.create(new JournalStep(name: "journal step",
				objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail1 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 1", objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail2 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 2", objectStatus: ObjectStatus.ACTIVE))
		def binding1 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail1, journalStep)
		def binding2 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail2, journalStep)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		and: "one of the step-detail bindings is deactivated"
		journalStepService.removeJournalStepDetailFromJournalStep(journalStepDetail1, journalStep)

		and: "the hib session is flushed for good measure again"
		sessionFactory.currentSession.flush()

		when: "all active details for the step are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, null, null, null, null, null)
		def filteredResults = journalStepDetailService.getAllForJournalStep(journalStep, sAndP)

		then: "the result only contains the inactive detail"
		filteredResults.results == 1
		filteredResults.rows.size() == 1
		filteredResults.each { result ->
			assert(result.objectStatus == ObjectStatus.ACTIVE)
			assert(result.id == journalStepDetail2.id)
		}

	}

	def "can query for all journal details associated with a journal step"() {

		given: "an active journal step and two details, one active and one inactive"
		def journalStep = journalStepService.create(new JournalStep(name: "journal step",
				objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail1 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 1", objectStatus: ObjectStatus.ACTIVE))
		def journalStepDetail2 = journalStepDetailService.create(new JournalStepDetail(name: "journal step detail 2", objectStatus: ObjectStatus.ACTIVE))
		def binding1 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail1, journalStep)
		def binding2 = journalStepService.addJournalStepDetailToJournalStep(journalStepDetail2, journalStep)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		and: "one of the step-detail bindings is deactivated"
		journalStepService.removeJournalStepDetailFromJournalStep(journalStepDetail1, journalStep)

		and: "the hib session is flushed for good measure again"
		sessionFactory.currentSession.flush()

		when: "all details for the step are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ALL, null, null, null, null, null)
		def filteredResults = journalStepDetailService.getAllForJournalStep(journalStep, sAndP)

		then: "the result only contains the inactive detail"
		filteredResults.results == 2
		filteredResults.rows.contains(journalStepDetail1)
		filteredResults.rows.contains(journalStepDetail2)

	}
}
