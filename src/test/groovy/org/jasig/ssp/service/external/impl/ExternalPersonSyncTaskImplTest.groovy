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
package org.jasig.ssp.service.external.impl

import org.jasig.ssp.service.reference.ConfigService
import org.jasig.ssp.util.collections.Pair
import org.jasig.ssp.util.sort.SortingAndPaging
import org.jasig.ssp.util.transaction.WithTransactionImpl
import spock.lang.Specification

public class ExternalPersonSyncTaskImplTest extends Specification {

	StubbedExternalPersonSyncTaskImpl syncTask
	ConfigService configService = Mock(ConfigService)

	def setup() {
		this.configService = Mock(ConfigService)
		this.syncTask = new StubbedExternalPersonSyncTaskImpl(
			withTransaction: new WithTransactionImpl(),
			configService: this.configService
		)
	}


	def "defaults to processing all available records once, possibly starting in the middle"() {

		given: "a sync task starting in the middle of all persons"
		syncTask.nextPersonIndex = 4

		and: "a sync implementation that reads records endlessly"
		def batchCnt = 0
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			batchIndices << sAndP.firstResult
			// always read the next 2 of 10 records
			new Pair(2L,10L)
		}

		when: "the task is executed"
		syncTask.exec()

		then: "enough batches run, and with the correct indices, to process everyone"
		batchCnt == 5
		batchIndices == [4,6,8,0,2]
		syncTask.nextPersonIndex == 4

	}

	def "requests configured number of batches"() {
		given: "a configuration allowing only 2 of a possible 3 batches"
		configService.getByNameNullOrDefaultValue("task_external_person_sync_max_batches_per_exec") >> 2

		and: "a sync implementation that reads records endlessly"
		def batchCnt = 0
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			batchIndices << sAndP.firstResult
			// always read the next 2 of 10 records
			new Pair(2L,10L)
		}

		when: "the task is executed"
		syncTask.exec()

		then: "two batches run, with second starting off where first one ended"
		batchIndices == [0,2]
		batchCnt == 2
	}

	def "requests batches of the configured size"() {
		given: "a configuration that requests two different batch sizes"
		configService.getByNameNullOrDefaultValue("task_external_person_sync_batch_size") >>> [6, 13]

		and: "a sync job that will read two batches and record the request size of each"
		def batchSizes = []
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchSizes << sAndP.maxResults
			batchIndices << sAndP.firstResult
			batchSizes.size() == 1 ? new Pair(6L,19L) : new Pair(13L,19L)
		}

		when: "the task is executed"
		syncTask.exec()

		then: "two batches run, each with different max sizes, and with the second starting off where first one ended"
		batchSizes == [6,13]
		batchIndices == [0,6]
	}

	def "requests batches in a default size if misconfigured"() {
		given: "misconfigured batch sizes"
		configService.getByNameNullOrDefaultValue("task_external_person_sync_batch_size") >>> ["nonsense", "foo"]

		and: "a sync job that will read two batches and record the request size of each"
		def batchSizes = []
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchSizes << sAndP.maxResults
			batchIndices << sAndP.firstResult
			new Pair(100L,200L)
		}

		when: "the task is executed"
		syncTask.exec()

		then: "two batches run with the same batch size but with the second starting off where first one ended"
		batchSizes == [100,100]
		batchIndices == [0,100]
	}

	def "requests batches in a default size if config missing"() {
		given: "misconfigured batch sizes"
		configService.getByNameNullOrDefaultValue("task_external_person_sync_batch_size") >> null

		and: "a sync job that will read two batches and record the request size of each"
		def batchSizes = []
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchSizes << sAndP.maxResults
			batchIndices << sAndP.firstResult
			new Pair(100L,200L)
		}

		when: "the task is executed"
		syncTask.exec()

		then:
		batchSizes == [100,100]
		batchIndices == [0,100]
	}

	def "resets if batch yields no results"() {
		given: "a sync job that works one time but not the next"
		def batchCnt = 0
		def batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			batchIndices << sAndP.firstResult
			batchCnt == 1 ? new Pair(100L,300L) : new Pair(0L, 300L)
		}

		when: "the task is executed the first time"
		syncTask.exec()

		then:
		batchCnt == 2
		batchIndices == [0,100]

		when: "the task is executed again processes all batches starting at index zero"
		batchCnt = 0
		batchIndices = []
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			batchIndices << sAndP.firstResult
			return new Pair(100L,300L)
		}
		syncTask.exec()

		then:
		batchCnt == 3
		batchIndices == [0,100,200]

	}

	// only use batch limit for this, not batch size, b/c SortingAndPaging assigns
	// other semantics to all possible batch size values
	def "does nothing if disabled by a zeroed batch limit"() {
		given: "a configuration allowing zero batches"
		configService.getByNameNullOrDefaultValue("task_external_person_sync_max_batches_per_exec") >> 0

		when: "the task is executed "
		def batchCnt = 0
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			return new Pair(2L,10L)
		}

		then:
		batchCnt == 0
	}

	def "abandons execution and reasserts interruption if interrupted"() {
		given: "a sync job that works one time but is interrupted the next"
		def batchCnt = 0
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			if ( batchCnt == 1 ) {
				new Pair(100L,300L)
			} else {
				throw new InterruptedException("Interrupted!");
			}
		}

		when: "the task is executed the first time"
		syncTask.exec()

		then:
		batchCnt == 2
		Thread.interrupted()

		when: "the task is executed the next time it should resume where it left off"
		batchCnt = 0
		def startIdx = 0
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			startIdx = sAndP.firstResult
			new Pair(300L,300L) // claim to process everything so we just get one iteration
		}
		syncTask.exec()

		then:
		batchCnt == 1
		startIdx == 100

		cleanup:
		Thread.interrupted()
	}

	// We have to just give up b/c we're treating each batch as a transaction
	// so there's no way to just skip the problematic record/s. This might need
	// to change. Chances are, though, that the underlying issue was probably
	// transient.
	def "abandons execution if batch errors out"() {
		given: "a sync job that works one time but errors out the next"
		def batchCnt = 0
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			if ( batchCnt == 1 ) {
				new Pair(100L,300L)
			} else {
				throw new RuntimeException("Woops!")
			}
		}

		when: "the task is executed the first time"
		syncTask.exec()

		then:
		batchCnt == 2

		when: "the task is executed the next time it should resume where it left off"
		batchCnt = 0
		def startIdx = 0
		syncTask.syncWithPersonImpl = { sAndP ->
			batchCnt++
			startIdx = sAndP.firstResult
			new Pair(300L,300L) // claim to process everything so we just get one iteration
		}
		syncTask.exec()

		then:
		batchCnt == 1
		startIdx == 100

	}

}

class StubbedExternalPersonSyncTaskImpl extends ExternalPersonSyncTaskImpl {

	Closure syncWithPersonImpl

	@Override
	def Pair<Long, Long> syncWithPerson(final SortingAndPaging sAndP) {
		if (!(syncWithPersonImpl)) {
			new Pair(0L,0L)
		} else {
			syncWithPersonImpl.call(sAndP)
		}
	}

	// Some weird stuff here b/c of private field visibility rules for super
	// classes.
	// http://groovy.329449.n5.nabble.com/Sub-classes-and-private-fields-td349921.html

	void setConfigService(ConfigService cs) {
		metaClass.setAttribute(ExternalPersonSyncTaskImpl, this, "configService", cs, false, true)
	}

	void setWithTransaction(wt) {
		metaClass.setAttribute(ExternalPersonSyncTaskImpl, this, "withTransaction", wt, false, true)
	}

	void setNextPersonIndex(lr) {
		metaClass.setAttribute(ExternalPersonSyncTaskImpl, this, "nextPersonIndex", lr, false, true)
	}

	int getNextPersonIndex() {
		metaClass.getAttribute(ExternalPersonSyncTaskImpl, this, "nextPersonIndex", false, true)
	}
}
