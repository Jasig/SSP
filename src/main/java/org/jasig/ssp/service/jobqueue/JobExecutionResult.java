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

public class JobExecutionResult<D> {
	private JobExecutionStatus status;
	private D detail;
	private Throwable cause;

	public JobExecutionResult() {
		super();
	}

	public JobExecutionResult(JobExecutionStatus status, D detail) {
		this(status, detail, null);
	}

	public JobExecutionResult(JobExecutionStatus status, D detail, Throwable cause) {
		this();
		this.status = status;
		this.detail = detail;
		this.cause = cause;
	}

	public JobExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(JobExecutionStatus status) {
		this.status = status;
	}

	public D getDetail() {
		return detail;
	}

	public void setDetail(D detail) {
		this.detail = detail;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JobExecutionResult)) return false;

		JobExecutionResult that = (JobExecutionResult) o;

		if (cause != null ? !cause.equals(that.cause) : that.cause != null) return false;
		if (detail != null ? !detail.equals(that.detail) : that.detail != null) return false;
		if (status != that.status) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = status != null ? status.hashCode() : 0;
		result = 31 * result + (detail != null ? detail.hashCode() : 0);
		result = 31 * result + (cause != null ? cause.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "JobExecutionResult{" +
				"status=" + status +
				", detail=" + detail +
				", cause=" + cause +
				'}';
	}
}
