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
package org.jasig.ssp.model;

public enum AnomalyCode {
COURSE_NOT_TAKEN("Course Not Taken"), COURSE_NOT_PASSED("Course Not Passed"), COURSE_NOT_REGISTERED("Course Not Registered"), CURR_OR_FUT_COURSE_NO_GRADE("Current or Future Course Has No Grade"), NO_ANOMALY("No Anomaly"), 
MULTIPLE_ANOMALIES_IN_TERM("Multiple Anomalies In Term");

private String displayText;
private AnomalyCode(String displayText) {
	this.setDisplayText(displayText);
}
public String getDisplayText() {
	return displayText;
}
public void setDisplayText(String displayText) {
	this.displayText = displayText;
}
}
