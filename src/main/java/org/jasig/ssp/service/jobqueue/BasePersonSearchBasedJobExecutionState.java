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

import com.google.common.collect.Lists;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;

import java.io.Serializable;
import java.util.List;

/**
 * Simple base 'pea' for representing {@code Job} execution state that contains the minimum fields required to
 * support the execution template in {@link AbstractPersonSearchBasedJobExecutor}
 */
public class BasePersonSearchBasedJobExecutionState implements Serializable {
	public Integer prevPage;
	public int pageSize = 25;
	public boolean allPagesProcessed;
	public List<ImmutablePersonIdentifiersTO> retryQueue = Lists.newArrayListWithExpectedSize(100);
	public List<ImmutablePersonIdentifiersTO> dlq = Lists.newArrayListWithExpectedSize(10);
	public int maxDlqLength = 100;
	public boolean failOnDlqOverflow;
	public boolean dlqOverflowed;
	public int personsFailedCount;
	public int personsSucceededCount;
}
