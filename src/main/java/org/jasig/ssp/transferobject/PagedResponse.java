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
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;

public class PagedResponse<T> extends ServiceResponse implements Serializable {

	private static final long serialVersionUID = -4699337121115833047L;

	private Long results;

	private Collection<T> rows;

	public PagedResponse(final boolean success) {
		super(success);
	}

	public PagedResponse(final boolean success, final String message) {
		super(success, message);
	}

	/**
	 * Constructor that initializes the success of the service call, total row
	 * count, and the paged rows to return.
	 * 
	 * @param success
	 *            Success of the service call
	 * @param totalCount
	 *            Total row count for all matching rows in the database
	 * @param rows
	 *            Only the paged rows that were requested
	 */
	public PagedResponse(final boolean success, final Long totalCount,
			final Collection<T> rows) {
		super(success);

		if (totalCount < 0) {
			throw new IllegalArgumentException(
					"Total row count may not be a negative value.");
		}

		if (rows == null) {
			throw new IllegalArgumentException(
					"Collection of rows may not be null.");
		}

		this.results = totalCount;
		this.rows = rows;
	}

	/**
	 * @return the results
	 */
	public long getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(final long results) {
		this.results = results;
	}

	/**
	 * @return the rows
	 */
	public Collection<T> getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(final Collection<T> rows) {
		this.rows = rows;
	}

}
