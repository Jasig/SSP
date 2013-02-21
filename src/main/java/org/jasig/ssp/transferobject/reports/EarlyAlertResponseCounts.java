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
package org.jasig.ssp.transferobject.reports;

public class EarlyAlertResponseCounts {

	Long totalResponses = 0L;
	Long totalEARespondedTo = 0L;
	Long totalEARespondedToNotClosed = 0L;
	
	public Long getTotalResponses() {
		return totalResponses;
	}
	public void setTotalResponses(Long totalResponses) {
		this.totalResponses = totalResponses;
	}
	public Long getTotalEARespondedTo() {
		return totalEARespondedTo;
	}
	public void setTotalEARespondedTo(Long totalEARespondedTo) {
		this.totalEARespondedTo = totalEARespondedTo;
	}
	public Long getTotalEARespondedToNotClosed() {
		return totalEARespondedToNotClosed;
	}
	public void setTotalEARespondedToNotClosed(Long totalEARespondedToNotClosed) {
		this.totalEARespondedToNotClosed = totalEARespondedToNotClosed;
	}
}
