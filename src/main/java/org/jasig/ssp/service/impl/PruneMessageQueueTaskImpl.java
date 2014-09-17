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
package org.jasig.ssp.service.impl;

import java.util.concurrent.Callable;

import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.PruneMessageQueueTask;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PruneMessageQueueTaskImpl implements PruneMessageQueueTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PruneMessageQueueTaskImpl.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private transient WithTransaction withTransaction;

	@Override
	public void exec(CallableExecutor<Void> batchExecutor) {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning message archive and pruning because of thread interruption");
		}

		try { 
			Integer result = withTransaction.withNewTransaction(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return messageService.archiveAndPruneMessages();
				}
			});
			LOGGER.info("{} Messages archived and deleted",result);
		} catch (Exception e) {
			LOGGER.error("Error while archiving and pruning message table: {}", e);
		}
	}

	@Override
	public Class<Void> getBatchExecReturnType() {
		return null;
	}

}
