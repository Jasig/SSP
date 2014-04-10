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

import org.jasig.ssp.model.Message;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.SendQueuedMessagesTask;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SendQueuedMessagesTaskImpl implements SendQueuedMessagesTask {

	private static final Class<Pair<PagingWrapper<Message>, Collection<Throwable>>> BATCH_RETURN_TYPE =
			(Class<Pair<PagingWrapper<Message>, Collection<Throwable>>>)
					new Pair<PagingWrapper<Message>, Collection<Throwable>>(null,null).getClass();

	@Autowired
	private MessageService messageService;

	@Override
	public void exec(CallableExecutor<Pair<PagingWrapper<Message>, Collection<Throwable>>> batchExecutor) {
		messageService.sendQueuedMessages(batchExecutor);
	}

	@Override
	public Class<Pair<PagingWrapper<Message>, Collection<Throwable>>> getBatchExecReturnType() {
		return BATCH_RETURN_TYPE;
	}
}
