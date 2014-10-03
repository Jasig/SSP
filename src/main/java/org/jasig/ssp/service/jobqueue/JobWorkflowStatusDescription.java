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

import java.util.List;

/**
 * Standard representation of {@link Job} <em>workflow</em> progress in terms of "messages". That is, this qualifies
 * and provides details for {@link Job#getWorkflowStatus()} in the form of free-form strings. Over the long-term, it
 * is intended to provide a generic means for tracking progress quantitatively, e.g. with completion percentage. Clients
 * should make an effort to ensure messages placed into these fields are suitable for display to end users.
 *
 * <p>Workflow status is typically updated in a different transaction than is execution state and may or may not be
 * updated for each execution of a batched {@code Job}, so {@code Job}s should not use instances of this object for
 * keeping their own progress state.</p>
 */
public class JobWorkflowStatusDescription {
	private String completionMessage;
	private List<String> errorMessages;
	private List<String> failureMessages;

	public JobWorkflowStatusDescription() {
		this(null,null,null);
	}

	public JobWorkflowStatusDescription(String completionMessage, List<String> errorMessages, List<String> failureMessages) {
		this.completionMessage = completionMessage;
		this.errorMessages = errorMessages;
		this.failureMessages = failureMessages;
	}

	public String getCompletionMessage() {
		return completionMessage;
	}

	public void setCompletionMessage(String completionMessage) {
		this.completionMessage = completionMessage;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	public void setFailureMessages(List<String> failureMessages) {
		this.failureMessages = failureMessages;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JobWorkflowStatusDescription)) return false;

		JobWorkflowStatusDescription that = (JobWorkflowStatusDescription) o;

		if (completionMessage != null ? !completionMessage.equals(that.completionMessage) : that.completionMessage != null)
			return false;
		if (errorMessages != null ? !errorMessages.equals(that.errorMessages) : that.errorMessages != null)
			return false;
		if (failureMessages != null ? !failureMessages.equals(that.failureMessages) : that.failureMessages != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = completionMessage != null ? completionMessage.hashCode() : 0;
		result = 31 * result + (errorMessages != null ? errorMessages.hashCode() : 0);
		result = 31 * result + (failureMessages != null ? failureMessages.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "JobWorkflowStatusDescription{" +
				"completionMessage='" + completionMessage + '\'' +
				", errorMessages=" + errorMessages +
				", failureMessages=" + failureMessages +
				'}';
	}
}
