/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service;

import org.jasig.ssp.service.external.BatchedTask;
import org.jasig.ssp.util.collections.Pair;


/**
 * Implements the Success Indicators background job that examines
 *   only configured success indicators for students inside of
 *   SSP. Depending on the config set it can store a count
 *   of High, Medium, and Low indicators and/or creates an
 *   Early Alert on Low.
 */
public interface SuccessIndicatorsTask extends BatchedTask<Pair<Long,Long>> {
}
