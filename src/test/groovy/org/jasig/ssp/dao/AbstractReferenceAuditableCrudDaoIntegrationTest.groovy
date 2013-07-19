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
package org.jasig.ssp.dao

import org.hibernate.SessionFactory
import org.jasig.ssp.dao.reference.ChildCareArrangementDao
import org.jasig.ssp.model.ObjectStatus
import org.jasig.ssp.model.Person
import org.jasig.ssp.model.reference.ChildCareArrangement
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment
import org.jasig.ssp.util.sort.SortingAndPaging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration("../service/service-testConfig.xml")
@TransactionConfiguration
@Transactional
class AbstractReferenceAuditableCrudDaoIntegrationTest extends Specification {

	// ChildCareArrangementDao is here b/c I decided to pick a bare-bones
	// concrete impl of AbstractReferenceAuditableCrudDao
	// rather than invent our own entity type for these tests. Does
	// make the tests less focused and more fragile. But was much simpler and
	// served the original purpose, which was to show that the base DAO logic
	// for dealing with application of SortingAndPaging state to Hibernate
	// Criteria instances was not itself flawed and that a reported bug with
	// ObjectStatus filteirng was specific to a particular service/dao stack
	// (JournalStepDetail).
	@Autowired
	ChildCareArrangementDao childCareArrangementDao

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

	def "paginated query returns only active records when active status filter set"() {
		given: "an inactive and an active child care arrangement"
		def inactiveArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "inactive arrangement",
						description: "inactive arrangement description",
						objectStatus: ObjectStatus.INACTIVE))
		def activeArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "active arrangement",
						description: "active arrangement description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "all active child care arrangements are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, null, null, null, null, null)
		def filteredResults = childCareArrangementDao.getAll(sAndP)

		then: "the resulting child care arrangements are all active"
		filteredResults.results >= 1
		filteredResults.rows.size() >= 1
		filteredResults.each { result -> assert(result.objectStatus == ObjectStatus.ACTIVE) }
	}

	def "default paginated query returns only inactive records when inactive status filter set"() {
		given: "an inactive and an active child care arrangement"
		def inactiveArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "inactive arrangement",
						description: "inactive arrangement description",
						objectStatus: ObjectStatus.INACTIVE))
		def activeArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "active arrangement",
						description: "active arrangement description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "all active child care arrangements are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.INACTIVE, null, null, null, null, null)
		def filteredResults = childCareArrangementDao.getAll(sAndP)

		then: "the resulting child care arrangements are all inactive"
		filteredResults.results >= 1
		filteredResults.rows.size() >= 1
		filteredResults.each { result -> assert(result.objectStatus == ObjectStatus.INACTIVE) }
	}

	def "paginated query returns all records when all status filter set"() {
		given: "an inactive and an active child care arrangement"
		def inactiveArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "inactive arrangement",
						description: "inactive arrangement description",
						objectStatus: ObjectStatus.INACTIVE))
		def activeArrangement = childCareArrangementDao.save(
				new ChildCareArrangement(name: "active arrangement",
						description: "active arrangement description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "all active child care arrangements are asked for"
		def sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ALL, null, null, null, null, null)
		def filteredResults = childCareArrangementDao.getAll(sAndP)

		then: "the resulting child care arrangements include both active and inactive records"
		filteredResults.rows.contains(inactiveArrangement)
		filteredResults.rows.contains(activeArrangement)
	}

}
