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
package org.jasig.ssp.util.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
public class WithTransactionImpl implements WithTransaction {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T withNewTransaction(Callable<T> work) throws Exception {
		return doWork(work);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T withNewTransactionAndUncheckedExceptions(Callable<T> work) {
		return doWorkUnchecked(work);
	}

	@Override
	@Transactional
	public <T> T withTransaction(Callable<T> work) throws Exception {
		return doWork(work);
	}

	@Override
	@Transactional
	public <T> T withTransactionAndUncheckedExceptions(Callable<T> work) {
		return doWorkUnchecked(work);
	}

	private <T> T doWork(Callable<T> work) throws Exception  {
		return work.call();
	}

	private <T> T doWorkUnchecked(Callable<T> work) {
		try {
			return doWork(work);
		} catch ( RuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}
}
